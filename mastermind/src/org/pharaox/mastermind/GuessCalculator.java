package org.pharaox.mastermind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuessCalculator
{
    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient int levels;
    
    private final transient List<Score> allScores;
    private final transient Object[] objects;
    private final transient String[] guesses;

    public GuessCalculator(final Mastermind mastermind, final AlgorithmFactory factory,
        final int levels)
    {
        super();
        this.mastermind = mastermind;
        this.factory = factory;
        this.levels = levels;
        this.allScores = mastermind.getAllPossibleScores();
        this.objects = new Object[levels];
        this.guesses = new String[levels];
        initGuesses();
    }

    private void initGuesses()
    {
        for (int i = 0; i < levels; i++)
        {
            initGuesses(new ArrayList<Score>(), i, 0);
        }
    }

    private void initGuesses(final List<Score> scores, final int level, final int n)
    {
        if (n == level)
        {
            makeAndAddGuess(scores, level);
        }
        else
        {
            for (final Score score : allScores)
            {
                final List<Score> scoresx = makeScores(scores, score);
                boolean canMakeGuess = ((n < level - 1) ? hasGuess(scoresx, n + 1) : true);
                if (isNotWinningScore(score) && canMakeGuess)
                {
                    initGuesses(scoresx, level, n + 1);
                }
            }
        }
    }

    private void makeAndAddGuess(final List<Score> scores, final int level)
    {
        final String guess = makeGuess(scores, level);
        if (!guess.isEmpty())
        {
            addGuess(scores, guess, level);
        }
    }
    
    private String makeGuess(final List<Score> scores, final int level)
    {
        Algorithm algorithm = factory.getAlgorithm();
        for (int i = 0; i < level; i++)
        {
            String guess = (i == 0) ? (String) objects[i] : guesses[i];
            algorithm.putGuessScore(guess, scores.get(i));
        }
        return algorithm.makeGuess();
    }
    
    private void addGuess(final List<Score> scores, final String guess, final int level)
    {
        assert (level < levels);
        if (level == 0)
        {
            objects[level] = guess;
        }
        else
        {
            Map<Score, Object> map = getFirstMap(level);
            addGuessToMap(map, scores, guess, level, 0);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Score, Object> getFirstMap(final int level)
    {
        Map<Score, Object> map = (Map<Score, Object>) objects[level];
        if (map == null)
        {
            map = new HashMap<Score, Object>();
            objects[level] = map;
        }
        return map;
    }

    private void addGuessToMap(final Map<Score, Object> map, final List<Score> scores, 
        final String guess, final int level, final int n)
    {
        Score score = scores.get(n);
        if (n == level - 1)
        {
            map.put(score, guess);
        }
        else
        {
            Map<Score, Object> mapx = getNextMap(map, score);
            addGuessToMap(mapx, scores, guess, level, n + 1);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Score, Object> getNextMap(final Map<Score, Object> map, final Score score)
    {
        Map<Score, Object> mapx = (Map<Score, Object>) map.get(score);
        if (mapx == null)
        {
            mapx = new HashMap<Score, Object>();
            map.put(score, mapx);
        }
        return mapx;
    }

    private List<Score> makeScores(final List<Score> scores, final Score score)
    {
        final List<Score> result = new ArrayList<Score>(scores.size() + 1);
        result.addAll(scores);
        result.add(score);
        return result;
    }

    private boolean hasGuess(final List<Score> scores, final int level)
    {
        guesses[level] = getGuess(scores, level);
        return (guesses[level] != null);
    }

    private boolean isNotWinningScore(final Score score)
    {
        return !score.equals(mastermind.getWinningScore());
    }

    public final boolean hasGuesses(final int level)
    {
        return (level < levels);
    }

    public final String getGuess(final List<Score> scores, final int level)
    {
        assert hasGuesses(level);
        return getGuess(scores, objects[level], level, 0);
    }
    
    private String getGuess(final List<Score> scores, final Object obj, final int level, 
        final int n)
    {
        String result = null;
        if (n == level)
        {
            result = (String) obj;
        }
        else
        {
            Object objx = getObject(scores, obj, n);
            if (objx != null)
            {
                result = getGuess(scores, objx, level, n + 1);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Object getObject(final List<Score> scores, final Object obj, final int n)
    {
        Map<Score, Object> map = (Map<Score, Object>) obj;
        return map.get(scores.get(n));
    }
    
}
