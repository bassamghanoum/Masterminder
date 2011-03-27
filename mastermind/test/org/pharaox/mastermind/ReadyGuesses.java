package org.pharaox.mastermind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyGuesses
{
    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient String firstGuess;
    private final transient Map<Score, String> secondGuesses = new HashMap<Score, String>();
    private final transient Map<Score, Map<Score, String>> thirdGuesses =
        new HashMap<Score, Map<Score, String>>();
    private final transient Map<Score, Map<Score, Map<Score, String>>> fourthGuesses =
        new HashMap<Score, Map<Score, Map<Score, String>>>();
    
    private transient String secondGuess = "";
    private transient String thirdGuess = "";

    public ReadyGuesses(final Mastermind mastermind, final AlgorithmFactory factory)
    {
        this.mastermind = mastermind;
        this.factory = factory;
        this.firstGuess = makeFirstGuess();
        initSecondGuesses();
        initThirdGuesses();
        initFourthGuesses();
    }

    private String makeFirstGuess()
    {
        final Algorithm algorithm = factory.getAlgorithm();
        return algorithm.makeGuess();
    }

    private void initSecondGuesses()
    {
        final List<Score> scores = mastermind.getAllPossibleScores();
        for (final Score score : scores)
        {
            if (isNotWinningScore(score))
            {
                makeAndAddSecondGuess(score);
            }
        }
    }

    private boolean isNotWinningScore(final Score score)
    {
        return !score.equals(mastermind.getWinningScore());
    }

    private void makeAndAddSecondGuess(final Score score)
    {
        final String guess = makeSecondGuess(score);
        if (!guess.isEmpty())
        {
            addSecondGuess(score, guess);
        }
    }

    private String makeSecondGuess(final Score score)
    {
        final Algorithm algorithm = factory.getAlgorithm();
        algorithm.putGuessScore(firstGuess, score);
        return algorithm.makeGuess();
    }

    private void addSecondGuess(final Score score, final String guess)
    {
        secondGuesses.put(score, guess);
    }

    private void initThirdGuesses()
    {
        final List<Score> scores = mastermind.getAllPossibleScores();
        for (final Score score1 : scores)
        {
            if (isNotWinningScore(score1) && hasSecondGuess(score1))
            {
                initThirdGuessesLevel2(scores, score1);
            }
        }
    }

    private boolean hasSecondGuess(final Score score)
    {
        secondGuess = getSecondGuess(score);
        return (!secondGuess.isEmpty());
    }

    private void initThirdGuessesLevel2(final List<Score> scores, final Score score1)
    {
        for (final Score score2 : scores)
        {
            if (isNotWinningScore(score2))
            {
                makeAndAddThirdGuess(score1, score2);
            }
        }
    }

    private void makeAndAddThirdGuess(final Score score1, final Score score2)
    {
        final String guess = makeThirdGuess(score1, score2);
        if (!guess.isEmpty())
        {
            addThirdGuess(score1, score2, guess);
        }
    }

    private String makeThirdGuess(final Score score1, final Score score2)
    {
        final Algorithm algorithm = factory.getAlgorithm();
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
        final List<Score> scores = mastermind.getAllPossibleScores();
        for (final Score score1 : scores)
        {
            if (isNotWinningScore(score1) && hasSecondGuess(score1))
            {
                initFourthGuessesLevel2(scores, score1);
            }
        }
    }

    private void initFourthGuessesLevel2(final List<Score> scores, final Score score1)
    {
        for (final Score score2 : scores)
        {
            if (isNotWinningScore(score2) && hasThirdGuess(score1, score2))
            {
                initFourthGuessesLevel3(scores, score1, score2);
            }
        }
    }

    private boolean hasThirdGuess(final Score score1, final Score score2)
    {
        thirdGuess = getThirdGuess(score1, score2);
        return (!thirdGuess.isEmpty());
    }

    private void initFourthGuessesLevel3(final List<Score> scores, final Score score1,
        final Score score2)
    {
        for (final Score score3 : scores)
        {
            if (isNotWinningScore(score3))
            {
                makeAndAddFourthGuess(score1, score2, score3);
            }
        }
    }

    private void makeAndAddFourthGuess(final Score score1, final Score score2, final Score score3)
    {
        final String guess = makeFourthGuess(score1, score2, score3);
        if (!guess.isEmpty())
        {
            addFourthGuess(score1, score2, score3, guess);
        }
    }

    private String makeFourthGuess(final Score score1, final Score score2, final Score score3)
    {
        final Algorithm algorithm = factory.getAlgorithm();
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
        return getGuessFromMap(secondGuesses, score);
    }

    private String getGuessFromMap(final Map<Score, String> map, final Score score)
    {
        String guess = map.get(score);
        if (guess == null)
        {
            guess = "";
        }
        return guess;
    }

    public final String getThirdGuess(final Score score1, final Score score2)
    {
        String guess = "";
        final Map<Score, String> map = thirdGuesses.get(score1);
        if (map != null)
        {
            guess = getGuessFromMap(map, score2);
        }
        return guess;
    }

    public final String getFourthGuess(final Score score1, final Score score2, final Score score3)
    {
        String guess = "";
        final Map<Score, Map<Score, String>> map1 = fourthGuesses.get(score1);
        if (map1 != null)
        {
            final Map<Score, String> map2 = map1.get(score2);
            if (map2 != null)
            {
                guess = getGuessFromMap(map2, score3);
            }
        }
        return guess;
    }

}
