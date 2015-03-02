package com.satoshidice.kyle.satoshidiceplayer.android.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.satoshidice.kyle.satoshidiceplayer.http.api.ApiAdapter;
import com.satoshidice.kyle.satoshidiceplayer.android.play.PlayActivity;
import com.satoshidice.kyle.satoshidiceplayer.R;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    private String SECRET_FILENAME = "secret";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected  void onResume() {
        super.onResume();

        EditText secretText = (EditText)findViewById(R.id.secret_text);
        try {
            secretText.setText(FileAdapter.loadFileData(this, SECRET_FILENAME));
        } catch (IOException ioe) {
            Toast.makeText(this, ioe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void clickLogin(View view) {

        EditText keyText = (EditText)findViewById(R.id.secret_text);
        final String secret = keyText.getText().toString();

        final Context context = this;

        ApiAdapter.getBalance(secret, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "Success(JsonObject): " + response.toString());

                // Success!
                try {

                    try {
                        FileAdapter.saveFileData(context, secret, SECRET_FILENAME);
                    } catch (IOException ioe) {
                        Toast.makeText(context, ioe.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra("secret", secret);
                    intent.putExtra("name", response.getString("nick"));
                    intent.putExtra("balance", response.getLong("balanceInSatoshis"));
                    intent.putExtra("hash", response.getString("hash"));
                    intent.putExtra("max_profit", response.getLong("maxProfitInSatoshis"));
                    intent.putExtra("latency", response.getDouble("queryTimeInSeconds"));
                    startActivity(intent);

                } catch (JSONException jsone) {
                    Toast.makeText(context, jsone.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Failure(String): " + responseString);
                Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();
            }

        });

    }

}
