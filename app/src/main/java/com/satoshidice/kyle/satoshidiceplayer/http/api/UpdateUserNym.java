package com.satoshidice.kyle.satoshidiceplayer.http.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kyle on 07/12/2014.
 * Version: 1
 */
public class UpdateUserNym {

    private String result;
    private String message;

    public String getResult() { return result; }
    public String getMessage() { return message; }

    // Hide default constructor
    @SuppressWarnings("UnusedDeclaration")
    private UpdateUserNym() {}

    public UpdateUserNym(JSONObject updateUserNym) throws JSONException {

        result = updateUserNym.getString("result");
        message = updateUserNym.getString("message");

    }

}
