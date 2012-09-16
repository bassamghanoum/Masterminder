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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DistributionTest
{
    // @formatter:off
    private static final int[] SAMPLE_NUMBERS = { 2, 4, 4, 4, 5, 5, 7, 9 };
    private static final int[] SAMPLE_NUMBERS_PERCENTILES = { 2, 2, 4, 4, 4, 5, 5, 5, 7, 9, 9 };
    // @formatter:on
    private static final double SAMPLE_NUMBERS_MEAN = 5.0;
    private static final double SAMPLE_NUMBERS_STD_DEV = 2.0;

    private static final int TOTAL_PERCENTS = 100;
    private static final int PERCENT_MULTIPLIER = 10;

    private static final double EPSILON = 0.000001;

    private static final String M_WRONG_CALC_RESULT = "Wrong calculation result:";

    private transient Distribution dist;

    @Before
    public final void setUp()
    {
        dist = new Distribution();
        for (final int number : SAMPLE_NUMBERS)
        {
            dist.add(number);
        }
    }

    @Test
    public final void testCalculateMean()
    {
        assertEquals(M_WRONG_CALC_RESULT, SAMPLE_NUMBERS_MEAN, dist.calculateMean(), EPSILON);
    }

    @Test
    public final void testCalculateStandardDevliation()
    {
        assertEquals(M_WRONG_CALC_RESULT, SAMPLE_NUMBERS_STD_DEV,
            dist.calculateStandardDeviation(), EPSILON);
    }

    @Test
    public final void testCalculatePercentile()
    {
        for (int i = 0; i <= TOTAL_PERCENTS / PERCENT_MULTIPLIER; i++)
        {
            final double percents = i * PERCENT_MULTIPLIER;
            assertEquals(M_WRONG_CALC_RESULT, SAMPLE_NUMBERS_PERCENTILES[i],
                dist.calculatePercentile(percents), EPSILON);
        }
    }
}
