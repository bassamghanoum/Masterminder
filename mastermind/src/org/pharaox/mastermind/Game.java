package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;

public class Game
{
    public static final Score ZERO_SCORE = new Score(0, 0);

    private Mastermind mastermind;
    private Algorithm algorithm;
    private int maxRounds;
    private ReadyGuesses readyGuesses;
    private int roundsPlayed = 0;

    public Game(Mastermind mastermind, Algorithm algorithm, int maxRounds)
    {
        this(mastermind, algorithm, maxRounds, null);
    }

    public Game(Mastermind mastermind, Algorithm algorithm, int maxRounds, ReadyGuesses readyGuesses)
    {
        this.mastermind = mastermind;
        this.algorithm = algorithm;
        this.maxRounds = maxRounds;
        this.readyGuesses = readyGuesses;
    }

    public int getRoundsPlayed()
    {
        return roundsPlayed;
    }

    public boolean play() throws MastermindException
    {
        boolean won = false;
        Score[] scores = new Score[] { ZERO_SCORE, ZERO_SCORE, ZERO_SCORE };
        int i;
        for (i = 0; i < maxRounds; i++)
        {
            Score score = playRound(i, scores);
            if (score.equals(mastermind.getWinningScore()))
            {
                won = true;
                i++;
                break;
            }
            for (int j = scores.length - 1; j > 0; j--)
                scores[j] = scores[j - 1];
            scores[0] = score;
        }
        roundsPlayed = i;
        return won;
    }

    private Score playRound(int round, Score[] scores)
    {
        String guess = makeGuess(round, scores);
        assert (mastermind.isValidCode(guess));
        Score score = getScore(guess);
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(int round, Score[] scores)
    {
        String guess;
        if (round == 0 && readyGuesses != null)
            guess = readyGuesses.getFirstGuess();
        else if (round == 1 && readyGuesses != null)
            guess = readyGuesses.getSecondGuess(scores[0]);
        else if (round == 2 && readyGuesses != null)
            guess = readyGuesses.getThirdGuess(scores[1], scores[0]);
        else if (round == 3 && readyGuesses != null)
            guess = readyGuesses.getFourthGuess(scores[2], scores[1], scores[0]);
        else
            guess = algorithm.makeGuess();
        return guess;
    }

    private Score getScore(String guess)
    {
        return mastermind.evaluateScore(guess);
    }

    private void putGuessScore(String guess, Score score)
    {
        algorithm.putGuessScore(guess, score);
    }
}
