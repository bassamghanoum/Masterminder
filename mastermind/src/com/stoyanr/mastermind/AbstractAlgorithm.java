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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractAlgorithm implements Algorithm
{
    private static final double EPSILON = 0.0000001;

    private final transient Mastermind mastermind;
    private final transient Map<String, Score> guessScores = new HashMap<String, Score>();
    private final transient Set<String> evaluated = new HashSet<String>();

    private transient SortedSet<String> possibleCodes;

    public AbstractAlgorithm(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
        initPossibleCodes();
    }

    private void initPossibleCodes()
    {
        possibleCodes = new TreeSet<String>();
        possibleCodes.addAll(mastermind.getAllPossibleCodes());
    }

    protected final List<Score> getAllPossibleScores()
    {
        return mastermind.getAllPossibleScores();
    }

    public final SortedSet<String> getPossibleCodes()
    {
        return possibleCodes;
    }

    @Override
    public final String makeGuess()
    {
        evaluateGuesses();
        String guess = "";
        if (!possibleCodes.isEmpty())
        {
            guess = makeNextGuess();
        }
        assert (guess.isEmpty() == possibleCodes.isEmpty());
        return guess;
    }

    private void evaluateGuesses()
    {
        for (final String guess : guessScores.keySet())
        {
            if (!evaluated.contains(guess))
            {
                final Score score = guessScores.get(guess);
                possibleCodes = evaluatePossibleCodes(guess, score);
                evaluated.add(guess);
            }
        }
    }

    protected final SortedSet<String> evaluatePossibleCodes(final String guess, final Score score)
    {
        return mastermind.evaluatePossibleCodes(guess, score, possibleCodes);
    }

    private String makeNextGuess()
    {
        String bestGuess = possibleCodes.first();
        double maxRating = 0.0;
        final SortedSet<String> allPossibleCodes = mastermind.getAllPossibleCodes();
        for (final String guess : allPossibleCodes)
        {
            final double rating = calculateGuessRating(guess);
            if (isBetterGuess(guess, rating, bestGuess, maxRating))
            {
                bestGuess = guess;
                maxRating = Math.max(maxRating, rating);
            }
        }
        return bestGuess;
    }

    protected abstract double calculateGuessRating(final String guess);

    // @checkstyle:off (Too many parameters)
    private boolean isBetterGuess(final String guess, final double rating, final String bestGuess,
        final double maxRating)
    // @checkstyle:on
    {
        boolean result = false;
        if (rating > maxRating)
        {
            result = true;
        }
        else if (Math.abs(maxRating - rating) < EPSILON)
        {
            result = possibleCodes.contains(guess) && !possibleCodes.contains(bestGuess);
        }
        return result;
    }

    @Override
    public final void putGuessScore(final String guess, final Score score)
    {
        assert (mastermind.isValidCode(guess) && mastermind.isValidScore(score));
        guessScores.put(guess, score);
    }
}
