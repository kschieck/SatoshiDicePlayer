package com.satoshidice.kyle.satoshidiceplayer.http.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class Round {

    private long id;
    private String hash;
    private String welcomeMessage;
    private long maxProfitInSatoshis;

    public long getId() { return id; }
    public String getHash() { return hash; }
    public String getWelcomeMessage() { return welcomeMessage; }
    public long getMaxProfitInSatoshis() { return maxProfitInSatoshis; }

    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private Round() { }

    /**
     * Create next round object from part of json object response to placing a bet or initiating a game round
     *
     * @see <a href="https://www.satoshidice.com/api/#place-bet">satoshidice.com/api/#place-bet</a>
     * @param nextRound Json object containing all Round data
     * @throws JSONException On parse error
     */
    public Round(JSONObject nextRound) throws JSONException {

        id = nextRound.getLong("id");
        hash = nextRound.getString("hash");
        welcomeMessage = nextRound.getString("welcomeMessage");

        if (nextRound.has("maxProfitInSatoshis")) {
            maxProfitInSatoshis = nextRound.getLong("maxProfitInSatoshis");
        }

    }

}
