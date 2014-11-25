package com.satoshidice.kyle.satoshidiceplayer.http.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 19/11/2014.
 */
public class BetResult {

    // Success exclusive fields
    private double newLuck;
    private int serverRoll;
    private String serverSalt;
    private String serverHash;
    private int clientRoll;
    private int resultingRoll;
    private long userBalanceInSatoshis;
    private Bet bet;
    private double queryTimeInSeconds;

    // Fail exclusive fields
    private int failcode;
    private String verbose;
    private String processLog;

    // Common fields
    private Round nextRound;
    private String status;
    private String message;


    //Getters
    public double getNewLuck() { return newLuck; }
    public int getServerRoll() { return serverRoll; }
    public String getServerSalt() { return serverSalt; }
    public String getServerHash() { return serverHash; }
    public int getClientRoll() { return clientRoll; }
    public int getResultingRoll() { return resultingRoll; }
    public long getUserBalanceInSatoshis() { return userBalanceInSatoshis; }
    public double getQueryTimeInSeconds() { return queryTimeInSeconds; }
    public int getFailcode() { return failcode; }
    public String getVerbose() { return verbose; }
    public String getProcessLog() { return processLog; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public Round getNextRound() { return nextRound; }
    public Bet getBet() { return bet; }


    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private BetResult() { }

    /**
     * Construct a result from response to bet request
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @param betResult The json object in the response
     * @throws JSONException On parsing errors
     */
    public BetResult(JSONObject betResult) throws JSONException {

        // Common fields
        nextRound = new Round(betResult.getJSONObject("nextRound"));
        message = betResult.getString("message");
        status = betResult.getString("status");

        if (isSuccess()) {

            newLuck = betResult.getDouble("newLuck");
            serverRoll = betResult.getInt("serverRoll");
            serverSalt = betResult.getString("serverSalt");
            serverHash = betResult.getString("serverHash");
            clientRoll = betResult.getInt("clientRoll");
            resultingRoll = betResult.getInt("resultingRoll");
            userBalanceInSatoshis = betResult.getLong("userBalanceInSatoshis");
            bet = new Bet(betResult.getJSONObject("bet"));
            queryTimeInSeconds = betResult.getDouble("queryTimeInSeconds");

        } else {

            // Fail exlusive fields
            failcode = betResult.getInt("failcode");
            verbose = betResult.getString("verbose");
            processLog = betResult.getString("processLog");

        }

    }

    /**
     * Check if bet was rejected (didn't go through)
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @return true if bet failed to go through
     */
    public boolean isFail() {
        if (status.equals("fail")) {
            return true;
        } else if (status.equals("success")) {
            return false;
        } else {
            throw new IllegalStateException("Status is neither fail nor success");
        }
    }

    /**
     * Check if bet was accepted (went through)
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @return true if bet was successfully went through
     */
    public boolean isSuccess() {
        //isFail implements logic already
        return !isFail();
    }

}
