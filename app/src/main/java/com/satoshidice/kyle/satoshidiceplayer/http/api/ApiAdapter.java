package com.satoshidice.kyle.satoshidiceplayer.http.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.satoshidice.kyle.satoshidiceplayer.http.HttpUtil;

public class ApiAdapter extends HttpUtil {

    /**
     * Attempt to getBalance using secret (validate secret)
     *
     * @see <a href="https://www.satoshidice.com/api/#retreive-your-balance">satoshidice.com/api/#retreive-your-balance</a>
     * @param secret Account secret identifier
     * @param responseHandler Callback interface
     */
    public static void getBalance(String secret,
                                  JsonHttpResponseHandler responseHandler) {

        String url = "/userbalance/?secret=" + secret;

        get(url, responseHandler);
    }

    /**
     * Initiate game round (get data required for game round)
     *
     * @see <a href="https://www.satoshidice.com/api/#initiate-game-round">satoshidice.com/api/#initiate-game-round</a>
     * @param secret Account secret identifier
     * @param responseHandler Callback interface
     */
    public static void getRound(String secret,
                                JsonHttpResponseHandler responseHandler) {

        String url = "/startround.php?secret=" + secret;

        get(url, responseHandler);
    }

    /**
     * Place a bet
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @param secret Account secret identifier
     * @param betInSatoshis Amount of bet in Satoshis (1 Satoshi = 0.00000001 ฿)
     * @param id Round Id provided by server
     * @param serverHash Hash provided by server
     * @param clientRoll Number chosen by client (required to prove fair outcome)
     * @param belowRollToWin Determines win probability and payout (1-65535)
     * @param responseHandler Callback interface
     */
    public static void placeBet(String secret,
                                long betInSatoshis,
                                long id,
                                String serverHash,
                                int clientRoll,
                                int belowRollToWin,
                                JsonHttpResponseHandler responseHandler) {

        String url = "/placebet.php?secret=" + secret +
                "&betInSatoshis=" + betInSatoshis +
                "&id=" + id +
                "&serverHash=" + serverHash +
                "&clientRoll=" + clientRoll +
                "&belowRollToWin=" + belowRollToWin;

        get(url, responseHandler);
    }

}

/*
new JsonHttpResponseHandler() {

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d(TAG, "Success(JsonObject): " + response.toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.d(TAG, "Success(JsonArray): " + response.toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.d(TAG, "Success(String): " + responseString);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d(TAG, "Failure(JsonObject): " + errorResponse.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.d(TAG, "Failure(JsonArray): " + errorResponse.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.d(TAG, "Failure(String): " + responseString);
    }

}
*/
