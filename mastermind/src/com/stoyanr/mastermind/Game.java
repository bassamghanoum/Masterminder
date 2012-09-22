/*
 * $Id: $
 *
 * Copyright 2012 Stoyan Rachev (stoyanr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stoyanr.mastermind;

import static com.stoyanr.mastermind.Score.ZERO_SCORE;
import static com.stoyanr.util.Logger.debug;

import java.util.ArrayList;
import java.util.List;

/**
 * A single Mastermind game. A game is initialized with a {@link Mastermind} instance (game setup),
 * a particular {@link Algorithm}, max number of rounds, and a {@link Player} instance. To play a
 * game, call its {@link #play()} method. The two methods {@link #hasWon()} and
 * {@link #getRoundsPlayed} should be invoked only after the game has finished to determine whether
 * the game is won and the number of rounds it took. A game can be played only once.
 * 
 * @author Stoyan Rachev
 */
public class Game
{
    private final transient Mastermind mastermind;
    private final transient Algorithm algorithm;
    private final transient int maxRounds;
    private final transient Player player;
    private final transient GuessCalculator calc;

    private transient boolean won = false;
    private transient int roundsPlayed = 0;
    // @formatter:off
    private transient Score[] scores = new Score[] { ZERO_SCORE, ZERO_SCORE, ZERO_SCORE };
    // @formatter:on

    /**
     * Creates a new game with the specified setup, algorithm, max number of rounds, and player.
     * This constructor delegates to
     * {@link #Game(Mastermind, Algorithm, int, Player, GuessCalculator)} by passing null for the
     * guess calculator.
     * 
     * @param mastermind The game setup to use.
     * @param algorithm The algorithm to use.
     * @param maxRounds Max number of rounds allowed. If the game is not won within this number of
     * rounds, it is terminated and declared "lost".
     * @param player The player to use.
     */
    public Game(final Mastermind mastermind, final Algorithm algorithm, final int maxRounds,
        final Player player)
    {
        this(mastermind, algorithm, maxRounds, player, null);
    }

    /**
     * Creates a new game with the specified setup, algorithm, max number of rounds, player, and
     * guess calculator.
     * 
     * @param mastermind The game setup to use.
     * @param algorithm The algorithm to use.
     * @param maxRounds Max number of rounds allowed. If the game is not won within this number of
     * rounds, it is terminated and declared "lost".
     * @param player The player to use.
     * @param calc A guess calculator used to optimize the performance of multiple games played with
     * the same algorithm, can be null if a guess calculator should not be used.
     */
    public Game(final Mastermind mastermind, final Algorithm algorithm, final int maxRounds,
        final Player player, final GuessCalculator calc)
    {
        assert (mastermind != null && algorithm != null && maxRounds > 0 && player != null);
        this.mastermind = mastermind;
        this.algorithm = algorithm;
        this.maxRounds = maxRounds;
        this.player = player;
        this.calc = calc;
    }

    /**
     * Plays the game. Calls {@link Player#startGame()} and {@link Player#endGame(boolean, int)} at
     * the beginning and at the end of the game respectively. At each game round, calls
     * {@link Algorithm#makeGuess()}, {@link Player#getScore(String)}, and
     * {@link Algorithm#putGuessScore(String, Score)} in this order, until a correct guess is made.
     * If this does not happen within the specified max number of rounds, it is terminated and
     * declared lost.
     * 
     * <p>
     * A game can be played only once. If this method is called for a second time on the same
     * instance, an exception is thrown.
     * 
     * @return true if the game is won, false otherwise.
     * @throws MastermindException If the method is called for a second time on the same instance,
     * if an empty guess is returned by the algorithm, or if another unexpected condition occurs.
     */
    public final boolean play()
    {
        if (roundsPlayed > 0)
        {
            throw new MastermindException();
        }
        player.startGame();
        while (roundsPlayed < maxRounds)
        {
            final Score score = playRound(roundsPlayed);
            roundsPlayed++;
            if (isWinningScore(score))
            {
                won = true;
                break;
            }
            shiftScores(score);
        }
        player.endGame(won, roundsPlayed);
        return won;
    }

    private boolean isWinningScore(final Score score)
    {
        return score.equals(mastermind.getWinningScore());
    }

    private void shiftScores(final Score score)
    {
        final Score[] scoresx = new Score[scores.length];
        System.arraycopy(scores, 0, scoresx, 1, scores.length - 1);
        scoresx[0] = score;
        scores = scoresx;
    }

    private Score playRound(final int round)
    {
        final String guess = makeGuess(round);
        assert mastermind.isValidCode(guess);
        final Score score = player.getScore(guess);
        assert mastermind.isValidScore(score);
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(final int round)
    {
        String guess;
        if ((calc != null) && calc.hasGuesses(round))
        {
            final List<Score> scoresx = makeScoresForCalc(round);
            guess = calc.getGuess(scoresx, round);
        }
        else
        {
            guess = algorithm.makeGuess();
        }
        if (guess.isEmpty())
        {
            throw new MastermindException();
        }
        return guess;
    }

    private List<Score> makeScoresForCalc(final int round)
    {
        final List<Score> result = new ArrayList<Score>();
        for (int i = round - 1; i >= 0; i--)
        {
            result.add(scores[i]);
        }
        return result;
    }

    private void putGuessScore(final String guess, final Score score)
    {
        algorithm.putGuessScore(guess, score);
    }

    /**
     * Returns whether the game has been won or not. Should only be called after the game has been
     * played, i.e. the {@link #play()} method has finished.
     * 
     * @return true if the game has been won, false otherwise.
     */
    public final boolean hasWon()
    {
        assert isOver();
        return won;
    }

    /**
     * Returns the actual number of rounds played. Should only be called after the game has been
     * played, i.e. the {@link #play()} method has finished.
     * 
     * @return The actual number of rounds played.
     */
    public final int getRoundsPlayed()
    {
        assert isOver();
        return roundsPlayed;
    }

    private boolean isOver()
    {
        return (roundsPlayed > 0);
    }
}
