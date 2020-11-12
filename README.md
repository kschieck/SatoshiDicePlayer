SatoshiDicePlayer
=================

An Android app that plays SatoshiDice automatically

This app utilizes the SatoshiDice API: https://session.satoshidice.com/userapi to play the game using a custom UI and automatically making bets and displaying results.

The game uses the Martingale betting system:
```
bet = minBet
while not stopped:
    play
    if win, bet = minBet
    if loss, bet = bet * 2
```

Here's what the app looks like once the user has logged in with their secret key from the SatoshiDice website and let it play for a few bets:
![Example UI](https://raw.githubusercontent.com/kschieck/SatoshiDicePlayer/master/pic2.png)
