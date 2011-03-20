package org.pharaox.mastermind;

import static org.pharaox.mastermind.Score.ZERO_SCORE;
import static org.pharaox.util.Logger.debug;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class Game
{
    private static final int ROUND_1 = 0;
    private static final int ROUND_2 = 1;
    private static final int ROUND_3 = 2;
    private static final int ROUND_4 = 3;

    private Mastermind mastermind;
    private Algorithm algorithm;
    private int maxRounds;
    private ReadyGuesses readyGuesses;
    private Player player = new DefaultPlayer();
    private boolean won = false;
    private int roundsPlayed = 0;
    // @formatter:off
    private Score[] scores = new Score[] { ZERO_SCORE, ZERO_SCORE, ZERO_SCORE };
    // @formatter:on

    public Game(final Mastermind mastermind, final AlgorithmType type, final int maxRounds)
    {
        this(mastermind, type, maxRounds, null);
    }

    public Game(final Mastermind mastermind, final AlgorithmType type, final int maxRounds,
        final ReadyGuesses readyGuesses)
    {
        this.mastermind = mastermind;
        this.algorithm = new AlgorithmFactory(type, mastermind).getAlgorithm();
        this.maxRounds = maxRounds;
        this.readyGuesses = readyGuesses;
    }

    public final void setPlayer(final Player player)
    {
        this.player = player;
    }

    public final Player getPlayer()
    {
        return player;
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
            throw new MastermindException();
        player.startGame();
        int i;
        for (i = 0; i < maxRounds; i++)
        {
            Score score = playRound(i);
            if (isWinningScore(score))
            {
                won = true;
                i++;
                break;
            }
            shiftScores(score);
        }
        roundsPlayed = i;
        player.endGame(won, roundsPlayed);
        return won;
    }

    private boolean isWinningScore(final Score score)
    {
        return score.equals(mastermind.getWinningScore());
    }

    private void shiftScores(final Score score)
    {
        for (int j = scores.length - 1; j > 0; j--)
            scores[j] = scores[j - 1];
        scores[0] = score;
    }

    private Score playRound(final int round)
    {
        String guess = makeGuess(round);
        assert (mastermind.isValidCode(guess));
        Score score = player.getScore(guess);
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(final int round)
    {
        String guess;
        if (round == ROUND_1 && readyGuesses != null)
            guess = readyGuesses.getFirstGuess();
        else if (round == ROUND_2 && readyGuesses != null)
            guess = readyGuesses.getSecondGuess(scores[0]);
        else if (round == ROUND_3 && readyGuesses != null)
            guess = readyGuesses.getThirdGuess(scores[1], scores[0]);
        else if (round == ROUND_4 && readyGuesses != null)
            guess = readyGuesses.getFourthGuess(scores[2], scores[1], scores[0]);
        else
            guess = algorithm.makeGuess();
        return guess;
    }

    private void putGuessScore(final String guess, final Score score)
    {
        algorithm.putGuessScore(guess, score);
    }

    class DefaultPlayer implements Player
    {
        @Override
        public void startGame()
        {
        }

        @Override
        public void endGame(final boolean wonx, final int roundsPlayedx)
        {
        }

        @Override
        public Score getScore(final String guess)
        {
            return mastermind.evaluateScore(guess);
        }
    }
}
