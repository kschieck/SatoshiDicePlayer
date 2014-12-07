package com.satoshidice.kyle.satoshidiceplayer.http.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 07/12/2014.
 * Version: 1
 */
public class Withdrawl {

    // Common attributes
    private double amountWithdrawn;
    private String transactionId;
    private int status;
    private String message;
    private double balance;

    // Only on Success
    private int confirmationsRequired;

    // Common attributes
    public double getAmountWithdrawn() { return amountWithdrawn; }
    public String getTransactionId() { return transactionId; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public double getBalance() { return balance; }

    // -1 on failure
    public int getConfirmationsRequired() { return confirmationsRequired; }

    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private Withdrawl() { }

    /**
     * Create a withdraw object from the server response
     *
     * @see <a href="https://satoshidice.com/api/#withdraw-funds">satoshidice.com/api/#withdraw-funds</a>
     * @param withdrawl The json object response from the server
     * @throws JSONException On parse error
     */
    public Withdrawl(JSONObject withdrawl) throws JSONException {

        amountWithdrawn = withdrawl.getDouble("amountWithdrawn");
        transactionId = withdrawl.getString("transactionId");
        status = withdrawl.getInt("status");
        message = withdrawl.getString("message");
        balance = withdrawl.getDouble("balance");

        if (withdrawl.has("confirmationsRequired")) {
            confirmationsRequired = withdrawl.getInt("confirmationsRequired");
        } else {
            confirmationsRequired = -1;
        }

    }

}
