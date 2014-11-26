package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public abstract class BetSystem {

    protected static final int BELOW_FOR_2X = 32145;
    public static final long MIN_BET_SATOSHIS = 100;

    public abstract void handleBetOutcome(Bet bet);

    public int getLessThan() {
        return BELOW_FOR_2X;
    }

    public abstract long getBet();

}
