package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public class Martingale extends BetSystem {

    private static final int BELOW_FOR_2X = 32145;

    @Override
    public void handleBetOutcome(Bet bet) {

        if (bet.getBetInSatoshis() != betInSatoshis) {
            throw new IllegalStateException("Current bet doesn't match response from server");
        }

        if (bet.getPayoutInSatoshis() > 0) {
            betInSatoshis = MIN_BET_SATOSHIS;
        } else {
            betInSatoshis *= 2;
        }

    }

    @Override
    public int getLessThan() {
        return BELOW_FOR_2X;
    }

}
