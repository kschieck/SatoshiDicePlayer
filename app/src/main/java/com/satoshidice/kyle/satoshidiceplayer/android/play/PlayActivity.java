package com.satoshidice.kyle.satoshidiceplayer.android.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;
import com.satoshidice.kyle.satoshidiceplayer.http.api.BetResult;
import com.satoshidice.kyle.satoshidiceplayer.betsystem.Martingale;
import com.satoshidice.kyle.satoshidiceplayer.R;
import com.satoshidice.kyle.satoshidiceplayer.player.Player;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class PlayActivity extends Activity {
    private static final String TAG = "PlayActivity";

    // Read-Only
    private static final int MAX_BET_HISTORY = 10;
    private int betsShowing = 0;

    private int wins = 0;
    private int losses = 0;

    // Controls
    private TextView balanceText;
    private Button playButton;
    private ListView resultsList;
    private TextView winsText;
    private TextView lossesText;

    // Player
    Player player;

    private ArrayList<Bet> bets = new ArrayList<Bet>();
    BetArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Load controls
        balanceText = (TextView)findViewById(R.id.balance);
        playButton = (Button)findViewById(R.id.play);
        resultsList = (ListView)findViewById(R.id.list_results);
        winsText = (TextView)findViewById(R.id.wins);
        lossesText = (TextView)findViewById(R.id.losses);

        // Load data
        init();
    }

    /**
     * Load data from intent, exit if invalid data
     */
    private void init() {
        Intent intent = getIntent();
        String secret = intent.getStringExtra("secret");
        String name = intent.getStringExtra("name");
        long balance = intent.getLongExtra("balance", -1);
        String hash = intent.getStringExtra("hash");
        long maxProfit = intent.getLongExtra("max_profit", -1);

        if (secret == null ||
                name == null ||
                balance < 0 ||
                hash == null ||
                maxProfit < 0) {

            String message = "Data:" +
                    "\nSecret = " + ((secret == null)? "null":"not null") +
                    "\nName: " + name +
                    "\nBalance: " + balance +
                    "\nHash: " + hash +
                    "\nMax Profit: " + maxProfit;

            new AlertDialog.Builder(this)
                    .setTitle("Incorrect Data")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //nothing
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish(); // Activity.finish()
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {

            // Set title and balance
            setTitle(name);
            balanceText.setText(Long.toString(balance));

            // Setup list adapter
            adapter = new BetArrayAdapter(this, R.layout.listitem_bet_result, bets);
            resultsList.setAdapter(adapter);

            //Create player
            final Context context = this;
            player = new Player(secret, balance, new Martingale(), new Player.Callback() {
                @Override
                public void onJsonException(JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStartPlaying() {
                    playButton.setText(R.string.stop);
                }

                @Override
                public void onStopPlaying() {
                    playButton.setText(R.string.play);
                }

                @Override
                public void onBetFailed(BetResult betResult) {
                    Toast.makeText(context, betResult.getMessage() + " : " + betResult.getVerbose(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onBetSucceeded(BetResult betResult) {
                    Bet bet = betResult.getBet();

                    //Update bet feed
                    bets.add(0, bet);
                    if (betsShowing < MAX_BET_HISTORY) {
                        betsShowing++;
                    } else {
                        bets.remove(MAX_BET_HISTORY);
                    }
                    adapter.notifyDataSetChanged();

                    if (bet.getProfitInSatoshis() > 0) {
                        wins++;
                        winsText.setText(Integer.toString(wins));
                    } else {
                        losses++;
                        lossesText.setText(Integer.toString(losses));
                    }

                    // Update balance
                    balanceText.setText(Long.toString(player.getBalance()));
                }

                @Override
                public void onServerResponseFail(String responseString) {
                    Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /**
     * Start playing either with current state or new state
     *
     * @param view Button clicked to trigger this function
     */
    public void clickPlay(View view) {
        //Toggle playing
        player.setPlaying(!player.isPlaying());
    }

}