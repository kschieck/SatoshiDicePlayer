package com.satoshidice.kyle.satoshidiceplayer.http.api;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class Bet {

    // Data
    private String game;
    private long betID;
    private String betTX;
    private String playerNick;
    private String playerHash;
    private String betType;
    private int target;
    private double probability;
    private int streak;
    private int roll;
    private String rollInPercent;
    private String time;
    private String result;
    private long betInSatoshis;
    private String prize;
    private long prizeInSatoshis;
    private long payoutInSatoshis;
    private String payoutTX;
    private long profitInSatoshis;


    // Getters
    public String getGame() { return game; }
    public long getBetID() { return betID; }
    public String getBetTX() { return betTX; }
    public String getPlayerNick() { return playerNick; }
    public String getPlayerHash() { return playerHash; }
    public String getBetType() { return betType; }
    public int getTarget() { return target; }
    public double getProbability() { return probability; }
    public int getStreak() { return streak; }
    public int getRoll() { return roll; }
    public String getRollInPercent() { return rollInPercent; }
    public String getTime() { return time; }
    public String getResult() { return result; }
    public long getBetInSatoshis() { return betInSatoshis; }
    public String getPrize() { return prize; }
    public long getPrizeInSatoshis() { return prizeInSatoshis; }
    public long getPayoutInSatoshis() { return payoutInSatoshis; }
    public String getPayoutTX() { return payoutTX; }
    public long getProfitInSatoshis() { return profitInSatoshis; }


    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private Bet() { }

    /**
     * Create a bet object from part of the response to placing a bet
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @param bet The json object which contains all the bet data
     * @throws JSONException On parse error
     */
    public Bet(JSONObject bet) throws JSONException {

        game = bet.getString("game");
        betID = bet.getLong("betID");
        betTX = bet.getString("betTX");
        playerNick = bet.getString("playerNick");
        playerHash = bet.getString("playerHash");
        betType = bet.getString("betType");
        target = bet.getInt("target");
        probability = bet.getDouble("probability");
        streak = bet.getInt("streak");
        roll = bet.getInt("roll");
        rollInPercent = bet.getString("rollInPercent");
        time = bet.getString("time");
        result = bet.getString("result");
        betInSatoshis = bet.getLong("betInSatoshis");
        prize = bet.getString("prize");
        prizeInSatoshis = bet.getLong("prizeInSatoshis");
        payoutInSatoshis = bet.getLong("payoutInSatoshis");
        payoutTX = bet.getString("payoutTX");
        profitInSatoshis = bet.getLong("profitInSatoshis");

    }

}
