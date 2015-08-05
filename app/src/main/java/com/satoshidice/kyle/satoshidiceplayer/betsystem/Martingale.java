package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public class Martingale extends BetSystem {

    long minBetInSatoshis;

    public Martingale() {
        init(MIN_BET_SATOSHIS);
    }

    public Martingale(long minBetInSatoshis) {
        init(minBetInSatoshis);
    }

    private void init(long minBetInSatoshis) {
        this.minBetInSatoshis = minBetInSatoshis;
        betInSatoshis = minBetInSatoshis;
    }

    @Override
    public void handleWin(Bet bet) {
        betInSatoshis = minBetInSatoshis;
    }

    @Override
    public void handleLoss(Bet bet) {
        betInSatoshis *= 2;
    }

    @Override
    public int getLessThan() {
        return LT_PERCENT_50;
    }
    
}
