package org.pharaox.mastermind;

import static org.pharaox.mastermind.Score.ZERO_SCORE;
import static org.pharaox.util.Logger.debug;

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
        this.mastermind = mastermind;
        this.algorithm = algorithm;
        this.maxRounds = maxRounds;
        this.player = player;
        this.calc = calc;
    }

    public final boolean hasWon()
    {
        return won;
    }

    public final int getRoundsPlayed()
    {
        return roundsPlayed;
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
}
