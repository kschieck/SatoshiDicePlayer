package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

/**
 * Created by Kyle on 23/11/2014.
 * Version: 1
 */
public abstract class BetSystem {

    public static final int LT_PROFIT_2x = 32145;

    public static final int LT_PERCENT_1 = 655;
    public static final int LT_PERCENT_50 = 32768;

    public static final long MIN_BET_SATOSHIS = 100;

    protected long betInSatoshis = MIN_BET_SATOSHIS; // current bet amount

    public final void handleBetOutcome(Bet bet) {
        if (bet.getBetInSatoshis() != betInSatoshis) {
            throw new IllegalStateException("Current bet doesn't match response from server");
        }

        if (bet.getPayoutInSatoshis() > 0) {
            handleWin(bet);
        } else {
            handleLoss(bet);
        }
    }

    public long getBet() {
        return betInSatoshis;
    }

    public abstract void handleWin(Bet bet);
    public abstract void handleLoss(Bet bet);

    public abstract int getLessThan();
    public final void overrideBetAmount(long newBet) {
        this.betInSatoshis = newBet;
    }

}
