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

    public Game(final Mastermind mastermind, final Algorithm algorithm, final int maxRounds,
        final Player player)
    {
        this(mastermind, algorithm, maxRounds, player, null);
    }

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
    
    public final boolean hasWon()
    {
        assert isOver();
        return won;
    }
    
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
