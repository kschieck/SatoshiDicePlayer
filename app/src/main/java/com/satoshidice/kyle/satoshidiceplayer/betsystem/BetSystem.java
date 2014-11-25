package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public abstract class BetSystem {

    protected static final long MIN_BET_SATOSHIS = 100;
    protected long betInSatoshis = MIN_BET_SATOSHIS; // current bet amount

    public abstract void handleBetOutcome(Bet bet);

    public abstract int getLessThan();

    public long getBet() {
        return betInSatoshis;
    }

}
