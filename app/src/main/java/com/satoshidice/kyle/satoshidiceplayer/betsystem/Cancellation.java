package com.satoshidice.kyle.satoshidiceplayer.betsystem;

import com.satoshidice.kyle.satoshidiceplayer.http.api.Bet;

import java.util.ArrayList;

/**
 * Created by Kyle on 25/11/2014.
 * Version: 1
 */
public class Cancellation extends BetSystem {

    ArrayList<Long> betUnits;
    long bankroll;

    public Cancellation(int numUnits, long bankroll) {

        // Create bet units arraylist
        betUnits = new ArrayList<Long>();
        for (int i=0; i<numUnits; i++) {
            betUnits.add(MIN_BET_SATOSHIS);
        }

        this.bankroll = bankroll;
    }

    @Override
    public void handleBetOutcome(Bet bet) {
        long betAmount = bet.getBetInSatoshis();

        // Fix bankroll
        bankroll += bet.getProfitInSatoshis();

        // Fix Bet Unit array
        boolean win = bet.getProfitInSatoshis() > 0;
        if (win) {

            long leftValue;

            int betUnitsRemaining = betUnits.size();
            switch (Math.min(betUnitsRemaining, 2)) {
                case 2:
                    long rightValue = betUnits.get(betUnitsRemaining - 1);
                    leftValue = betUnits.get(0);
                    // bet == right + left, remove right
                    if (betAmount == rightValue + leftValue) {
                        betUnits.remove(betUnitsRemaining - 1);
                    }
                    //Fall through
                case 1:
                    leftValue = betUnits.get(0);
                    // bet >= leftValue => remove left value, else subtract from left
                    if (betAmount >= leftValue) {
                        betUnits.remove(0);//remove from front (left)
                    } else {
                        betUnits.set(0, betUnits.get(0) - betAmount); // subtract from left amount
                    }
                    return;
                case 0:
                default:
                    throw new IllegalStateException();
            }

        } else {

            betUnits.add(betAmount);

        }

    }

    @Override
    public long getBet() {

        long maxBet = 0;

        int betUnitsRemaining = betUnits.size();
        switch (Math.min(betUnitsRemaining, 2)) {
            case 2:
                maxBet += betUnits.get(betUnitsRemaining - 1);
                //Fall through
            case 1:
                long leftValue = betUnits.get(0);
                maxBet += leftValue;

                if (bankroll < maxBet) {
                    return Math.min(leftValue, bankroll);
                }
                return maxBet;
            default:
                return 0;
        }
    }

}
