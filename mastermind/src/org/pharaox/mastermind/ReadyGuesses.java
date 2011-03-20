package org.pharaox.mastermind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class ReadyGuesses
{
    private Mastermind mastermind;
    private AlgorithmFactory factory;
    private String firstGuess = "";
    private Map<Score, String> secondGuesses = new HashMap<Score, String>();
    private Map<Score, Map<Score, String>> thirdGuesses = new HashMap<Score, Map<Score, String>>();
    private Map<Score, Map<Score, Map<Score, String>>> fourthGuesses =
        new HashMap<Score, Map<Score, Map<Score, String>>>();
    private String secondGuess = null;
    private String thirdGuess = null;

    public ReadyGuesses(final Mastermind mastermind, final AlgorithmType type)
    {
        this.mastermind = mastermind;
        this.factory = new AlgorithmFactory(type, mastermind);
        initFirstGuess();
        initSecondGuesses();
        initThirdGuesses();
        initFourthGuesses();
    }

    private void initFirstGuess()
    {
        firstGuess = makeFirstGuess();
    }

    private String makeFirstGuess()
    {
        Algorithm algorithm = factory.getAlgorithm();
        return algorithm.makeGuess();
    }

    private void initSecondGuesses()
    {
        for (Score score : mastermind.getAllPossibleScores())
        {
            if (isNotWinningScore(score))
            {
                String guess = makeSecondGuess(score);
                if (!guess.isEmpty())
                    addSecondGuess(score, guess);
            }
        }
    }

    private boolean isNotWinningScore(final Score score)
    {
        return !score.equals(mastermind.getWinningScore());
    }

    private String makeSecondGuess(final Score score)
    {
        Algorithm algorithm = factory.getAlgorithm();
        algorithm.putGuessScore(firstGuess, score);
        return algorithm.makeGuess();
    }

    private void addSecondGuess(final Score score, final String guess)
    {
        secondGuesses.put(score, guess);
    }

    private void initThirdGuesses()
    {
        List<Score> scores = mastermind.getAllPossibleScores();
        for (Score score1 : scores)
        {
            if (isNotWinningScore(score1) && hasSecondGuess(score1))
            {
                for (Score score2 : scores)
                {
                    if (isNotWinningScore(score2))
                    {
                        String guess = makeThirdGuess(score1, score2);
                        if (!guess.isEmpty())
                            addThirdGuess(score1, score2, guess);
                    }
                }
            }
        }
    }

    private boolean hasSecondGuess(final Score score)
    {
        secondGuess = getSecondGuess(score);
        return (secondGuess != null);
    }

    private String makeThirdGuess(final Score score1, final Score score2)
    {
        Algorithm algorithm = factory.getAlgorithm();
        algorithm.putGuessScore(firstGuess, score1);
        algorithm.putGuessScore(secondGuess, score2);
        return algorithm.makeGuess();
    }

    private void addThirdGuess(final Score score1, final Score score2, final String guess)
    {
        Map<Score, String> map = thirdGuesses.get(score1);
        if (map == null)
        {
            map = new HashMap<Score, String>();
            thirdGuesses.put(score1, map);
        }
        map.put(score2, guess);
    }

    private void initFourthGuesses()
    {
        List<Score> scores = mastermind.getAllPossibleScores();
        for (Score score1 : scores)
        {
            if (isNotWinningScore(score1) && hasSecondGuess(score1))
            {
                for (Score score2 : scores)
                {
                    if (isNotWinningScore(score2) && hasThirdGuess(score1, score2))
                    {
                        for (Score score3 : scores)
                        {
                            if (isNotWinningScore(score3))
                            {
                                String guess = makeFourthGuess(score1, score2, score3);
                                if (!guess.isEmpty())
                                    addFourthGuess(score1, score2, score3, guess);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasThirdGuess(final Score score1, final Score score2)
    {
        thirdGuess = getThirdGuess(score1, score2);
        return (thirdGuess != null);
    }

    private String makeFourthGuess(final Score score1, final Score score2, final Score score3)
    {
        Algorithm algorithm = factory.getAlgorithm();
        algorithm.putGuessScore(firstGuess, score1);
        algorithm.putGuessScore(secondGuess, score2);
        algorithm.putGuessScore(thirdGuess, score3);
        return algorithm.makeGuess();
    }

    private void addFourthGuess(final Score score1, final Score score2, final Score score3,
        final String guess)
    {
        Map<Score, Map<Score, String>> map1 = fourthGuesses.get(score1);
        if (map1 == null)
        {
            map1 = new HashMap<Score, Map<Score, String>>();
            fourthGuesses.put(score1, map1);
        }
        Map<Score, String> map2 = map1.get(score2);
        if (map2 == null)
        {
            map2 = new HashMap<Score, String>();
            map1.put(score2, map2);
        }
        map2.put(score3, guess);
    }

    public final String getFirstGuess()
    {
        return firstGuess;
    }

    public final String getSecondGuess(final Score score)
    {
        return secondGuesses.get(score);
    }

    public final String getThirdGuess(final Score score1, final Score score2)
    {
        String guess = null;
        Map<Score, String> map = thirdGuesses.get(score1);
        if (map != null)
            guess = map.get(score2);
        return guess;
    }

    public final String getFourthGuess(final Score score1, final Score score2, final Score score3)
    {
        String guess = null;
        Map<Score, Map<Score, String>> map1 = fourthGuesses.get(score1);
        if (map1 != null)
        {
            Map<Score, String> map2 = map1.get(score2);
            if (map2 != null)
                guess = map2.get(score3);
        }
        return guess;
    }

}
