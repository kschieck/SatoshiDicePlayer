package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public class Martingale extends BetSystem {

    protected long minBetInSatoshis = MIN_BET_SATOSHIS;
    protected long betInSatoshis; // current bet amount

    public Martingale() {
        betInSatoshis = this.minBetInSatoshis;
    }

    public Martingale(long minBetInSatoshis) {
        this.minBetInSatoshis = minBetInSatoshis;
        betInSatoshis = minBetInSatoshis;
    }

    @Override
    public void handleBetOutcome(Bet bet) {

        if (bet.getBetInSatoshis() != betInSatoshis) {
            throw new IllegalStateException("Current bet doesn't match response from server");
        }

        if (bet.getPayoutInSatoshis() > 0) {
            betInSatoshis = minBetInSatoshis;
        } else {
            betInSatoshis *= 2;
        }

    }

    @Override
    public long getBet() {
        return betInSatoshis;
    }

}
