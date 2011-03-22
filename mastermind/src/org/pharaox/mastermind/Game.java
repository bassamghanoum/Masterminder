package org.pharaox.mastermind;

import static org.pharaox.mastermind.Score.ZERO_SCORE;
import static org.pharaox.util.Logger.debug;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class Game
{
    private final transient Mastermind mastermind;
    private final transient Algorithm algorithm;
    private final transient int maxRounds;
    private final transient Player player;
    private final transient ReadyGuesses readyGuesses;

    private transient boolean won = false;
    private transient int roundsPlayed = 0;
    // @formatter:off
    private transient Score[] scores = new Score[] { ZERO_SCORE, ZERO_SCORE, ZERO_SCORE };
    // @formatter:on

    public Game(final Mastermind mastermind, final AlgorithmType type, final int maxRounds,
        final Player player)
    {
        this(mastermind, type, maxRounds, player, null);
    }

    public Game(final Mastermind mastermind, final AlgorithmType type, final int maxRounds,
        final Player player, final ReadyGuesses readyGuesses)
    {
        this.mastermind = mastermind;
        this.algorithm = new AlgorithmFactory(type, mastermind).getAlgorithm();
        this.maxRounds = maxRounds;
        this.player = player;
        this.readyGuesses = readyGuesses;
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
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(final int round)
    {
        String guess;
        if ((round == 0) && (readyGuesses != null))
        {
            guess = readyGuesses.getFirstGuess();
        }
        else if ((round == 1) && (readyGuesses != null))
        {
            guess = readyGuesses.getSecondGuess(scores[0]);
        }
        else if ((round == 2) && (readyGuesses != null))
        {
            guess = readyGuesses.getThirdGuess(scores[1], scores[0]);
        }
        // @checkstyle:off (Magic numbers)
        else if ((round == 3) && (readyGuesses != null))
        // @checkstyle:on
        {
            guess = readyGuesses.getFourthGuess(scores[2], scores[1], scores[0]);
        }
        else
        {
            guess = algorithm.makeGuess();
        }
        return guess;
    }

    private void putGuessScore(final String guess, final Score score)
    {
        algorithm.putGuessScore(guess, score);
    }
}
