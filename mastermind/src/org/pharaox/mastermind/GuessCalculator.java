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

    private void initGuesses(final List<Score> scores, final int level, final int depth)
    {
        if (depth == level)
        {
            makeAndAddGuess(scores, level);
        }
        else
        {
            for (final Score score : allScores)
            {
                final List<Score> scoresx = makeScores(scores, score);
                final boolean canMakeGuess =
                    ((depth < level - 1) ? hasGuess(scoresx, depth + 1) : true);
                if (isNotWinningScore(score) && canMakeGuess)
                {
                    initGuesses(scoresx, level, depth + 1);
                }
            }
        }
    }

    private void makeAndAddGuess(final List<Score> scores, final int level)
    {
        final String guess = makeGuess(scores, level);
        if (!guess.isEmpty())
        {
            addGuess(scores, level, guess);
        }
    }

    private String makeGuess(final List<Score> scores, final int level)
    {
        final Algorithm algorithm = factory.getAlgorithm();
        for (int i = 0; i < level; i++)
        {
            final String guess = (i == 0) ? (String) objects[i] : guesses[i];
            algorithm.putGuessScore(guess, scores.get(i));
        }
        return algorithm.makeGuess();
    }

    private void addGuess(final List<Score> scores, final int level, final String guess)
    {
        assert (level < levels);
        if (level == 0)
        {
            objects[level] = guess;
        }
        else
        {
            final Map<Score, Object> map = getFirstMap(level);
            addGuessToMap(scores, level, guess, map, 0);
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

    private void addGuessToMap(final List<Score> scores, final int level, final String guess,
        final Map<Score, Object> map, final int depth)
    {
        final Score score = scores.get(depth);
        if (depth == level - 1)
        {
            map.put(score, guess);
        }
        else
        {
            final Map<Score, Object> mapx = getNextMap(map, score);
            addGuessToMap(scores, level, guess, mapx, depth + 1);
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
        return getGuess(scores, level, objects[level], 0);
    }

    private String getGuess(final List<Score> scores, final int level, final Object obj,
        final int depth)
    {
        String result = null;
        if (depth == level)
        {
            result = (String) obj;
        }
        else
        {
            final Object objx = getObject(scores, obj, depth);
            if (objx != null)
            {
                result = getGuess(scores, level, objx, depth + 1);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Object getObject(final List<Score> scores, final Object obj, final int depth)
    {
        final Map<Score, Object> map = (Map<Score, Object>) obj;
        return map.get(scores.get(depth));
    }

}
