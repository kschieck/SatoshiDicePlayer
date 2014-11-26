package com.satoshidice.kyle.satoshidiceplayer.player;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;
import com.satoshidice.kyle.satoshidiceplayer.http.api.BetResult;
import com.satoshidice.kyle.satoshidiceplayer.http.api.Round;
import com.satoshidice.kyle.satoshidiceplayer.betsystem.BetSystem;
import com.satoshidice.kyle.satoshidiceplayer.http.api.ApiAdapter;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public class Player {
    private static final String TAG = "SatoshiPlayer";

    private static final int CLIENT_ROLL = 63;

    private long balance;

    private String secret; // Account Secret
    Round currentSession = null; // Session State
    BetSystem betSystem; // Bet system
    Callback callback; // Callback for results

    // Action State
    private boolean playing = false; // Automatically place next bet
    private boolean betActive = false; // Waiting for a bet response

    public interface Callback {

        public abstract void onJsonException(JSONException jsone);
        public abstract void onStartPlaying();
        public abstract void onStopPlaying();
        public abstract void onBetFailed(BetResult betResult);
        public abstract void onBetSucceeded(BetResult betResult);
        public abstract void onServerResponseFail(String responseString);

    }

    /**
     * Create a new player
     *
     * @param secret User's login secret
     * @param balance User's current balance
     * @param betSystem The bet system to use
     * @param callback Callback interface for different events
     */
    public Player(String secret, long balance, BetSystem betSystem, Callback callback) {
        this.secret = secret;
        this.balance = balance;
        this.betSystem = betSystem;
        this.callback = callback;
    }

    /**
     * Maintains game state and places bets
     */
    private void continuePlaying() {

        // One bet at a time
        if (!playing || betActive) return;

        // Getting a bet
        betActive = true;

        // Round is required to bet
        if (currentSession != null) {

            long betInSatoshis = betSystem.getBet();
            if (betInSatoshis <= 0) {
                setPlaying(false);
                return;
            }

            // Place bet
            ApiAdapter.placeBet(
                    secret,
                    betInSatoshis,
                    currentSession.getId(),
                    currentSession.getHash(),
                    CLIENT_ROLL,
                    betSystem.getLessThan(),
                    new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(TAG, "Success(JSONObject): " + response.toString());
                            betActive = false;

                            try {
                                handleBetResult(new BetResult(response));
                            } catch (JSONException jsone) {
                                callback.onJsonException(jsone);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d(TAG, "Failure(JSONObject): " + errorResponse.toString());
                            betActive = false;

                            try {
                                handleBetResult(new BetResult(errorResponse));
                            } catch (JSONException jsone) {
                                callback.onJsonException(jsone);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d(TAG, "Failure(string): " + responseString);
                            betActive = false;

                            handleOnFailure(responseString);
                        }

                    });

        } else {

            //Get a Round
            ApiAdapter.getRound(
                    secret,
                    new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(TAG, "Success(JsonObject): " + response.toString());
                            betActive = false;

                            try {
                                currentSession = new Round(response);
                                continuePlaying();
                            } catch (JSONException jsone) {
                                callback.onJsonException(jsone);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d(TAG, "Failure(String): " + responseString);
                            betActive = false;

                            handleOnFailure(responseString);
                        }

                    });

        }
    }

    /**
     * Call when server response is failure (eg "Invalid Account")
     *
     * @param responseString the string returned by the server
     */
    private void handleOnFailure(String responseString) {
        setPlaying(false);
        callback.onServerResponseFail(responseString);
    }

    /**
     * Deal with new bet. Update views, start new round if still playing
     *
     * @param result The result from the last bet. Used to determine next bet
     */
    private void handleBetResult(BetResult result) {

        // Setup next round
        currentSession = result.getNextRound();

        if (result.isSuccess()) {

            Bet bet = result.getBet();
            balance += bet.getProfitInSatoshis();
            betSystem.handleBetOutcome(bet);
            callback.onBetSucceeded(result);

            continuePlaying();

        } else {

            // Bet failed to go through
            setPlaying(false);
            callback.onBetFailed(result);

        }
    }

    /**
     * Change the state of playing (determines whether or not to send next bet)
     *
     * @param playing true to keep (or start) playing
     */
    public void setPlaying(boolean playing) {

        if (this.playing == playing) return;

        this.playing = playing;

        if (playing) {
            continuePlaying();
            callback.onStartPlaying();
        } else {
            callback.onStopPlaying();
        }

    }

    /**
     * Check if currently playing
     *
     * @return true if currently playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Get current balance of player
     *
     * @return balance in Satoshis
     */
    public long getBalance() {
        return balance;
    }

    /**
     * Check if waiting for a bet response
     *
     * @return true if bet waiting for callback
     */
    public boolean isBetActive() {
        return betActive;
    }

}
