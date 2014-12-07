package com.satoshidice.kyle.satoshidiceplayer.http.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 07/12/2014.
 * Version: 1
 */
public class DepositAddress {

    private String nick;
    private String depositaddress;
    private double queryTimeInSeconds;

    public String getNick() { return nick; }
    public String getDepositaddress() { return depositaddress; }
    public double getQueryTimeInSeconds() { return queryTimeInSeconds; }

    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private DepositAddress() { }

    /**
     * Create a depositAddress from the json object response from the server
     *
     * @see <a href="https://satoshidice.com/api/#get-deposit-address">satoshidice.com/api/#get-deposit-address</a>
     * @param depositAddress Response from the server call to get deposit address
     * @throws JSONException On parse error
     */
    public DepositAddress(JSONObject depositAddress) throws JSONException {

        nick = depositAddress.getString("nick");
        depositaddress = depositAddress.getString("depositaddress");
        queryTimeInSeconds = depositAddress.getDouble("queryTimeInSeconds");

    }

}
