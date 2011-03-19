package org.pharaox.mastermind;

import static org.pharaox.mastermind.Logger.debug;

import java.util.HashMap;
import java.util.Map;

public class Game
{
    public static final Score ZERO_SCORE = new Score(0, 0);

    private Mastermind mastermind;
    private Algorithm algorithm;
    private int maxRounds;
    private String firstGuess;
    private Map<Score, String> secondGuesses;
    private Map<Score, Map<Score, String>> thirdGuesses;
    private Map<Score, Map<Score, Map<Score, String>>> fourthGuesses;
    private int roundsPlayed = 0;

    public Game(Mastermind mastermind, Algorithm algorithm, int maxRounds)
    {
        this(mastermind, algorithm, maxRounds, "", new HashMap<Score, String>(),
            new HashMap<Score, Map<Score, String>>(),
            new HashMap<Score, Map<Score, Map<Score, String>>>());
    }

    public Game(Mastermind mastermind, Algorithm algorithm, int maxRounds, String firstGuess,
        Map<Score, String> secondGuesses, Map<Score, Map<Score, String>> thirdGuesses,
        Map<Score, Map<Score, Map<Score, String>>> fourthGuesses)
    {
        this.mastermind = mastermind;
        this.algorithm = algorithm;
        this.maxRounds = maxRounds;
        this.firstGuess = firstGuess;
        this.secondGuesses = secondGuesses;
        this.thirdGuesses = thirdGuesses;
        this.fourthGuesses = fourthGuesses;
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
        checkGuess(guess);
        Score score = getScore(guess);
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(int round, Score[] scores)
    {
        String guess;
        if (round == 0 && !firstGuess.isEmpty())
            guess = firstGuess;
        else if (round == 1 && !secondGuesses.isEmpty())
            guess = secondGuesses.get(scores[0]);
        else if (round == 2 && !thirdGuesses.isEmpty())
            guess = thirdGuesses.get(scores[1]).get(scores[0]);
        else if (round == 3 && !fourthGuesses.isEmpty())
            guess = fourthGuesses.get(scores[2]).get(scores[1]).get(scores[0]);
        else
            guess = algorithm.makeGuess();
        return guess;
    }

    private void checkGuess(String guess)
    {
        if (!mastermind.isValidCode(guess))
            throw new MastermindException();
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
