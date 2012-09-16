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

import java.util.SortedSet;

public class PharaoxAlgorithm extends AbstractAlgorithm
{
    private final transient double percents;

    public PharaoxAlgorithm(final Mastermind mastermind, final double percents)
    {
        super(mastermind);
        assert (percents >= 0);
        this.percents = percents;
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        final Distribution dist = new Distribution();
        for (final Score score : getAllPossibleScores())
        {
            final SortedSet<String> codes = evaluatePossibleCodes(guess, score);
            final int diff = getPossibleCodes().size() - codes.size();
            dist.add(diff);
        }
        return dist.calculatePercentile(percents);
    }
}
