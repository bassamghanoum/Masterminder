package org.pharaox.mastermind;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DistributionTest
{
    // @formatter:off
    private static final int[] SAMPLE_NUMBERS = { 2, 4, 4, 4, 5, 5, 7, 9 };
    private static final int[] SAMPLE_NUMBERS_PERCENTILES = { 2, 2, 4, 4, 4, 5, 5, 5, 7, 9, 9 };
    // @formatter:on
    private static final double SAMPLE_NUMBERS_MEAN = 5.0;
    private static final double SAMPLE_NUMBERS_STANDARD_DEVIATION = 2.0;
    
    private static final int TOTAL_PERCENTS = 100;
    private static final int PERCENT_MULTIPLIER = 10;

    private Distribution dist;

    @Before
    public final void setup()
    {
        dist = new Distribution();
        for (int number : SAMPLE_NUMBERS)
            dist.add(number);
    }

    @Test
    public final void testCalculateMean()
    {
        assertTrue(SAMPLE_NUMBERS_MEAN == dist.calculateMean());
    }

    @Test
    public final void testCalculateStandardDevliation()
    {
        assertTrue(SAMPLE_NUMBERS_STANDARD_DEVIATION == dist.calculateStandardDeviation());
    }

    @Test
    public final void testCalculatePercentile()
    {
        for (int i = 0; i <= TOTAL_PERCENTS / PERCENT_MULTIPLIER; i++)
        {
            double percents = i * PERCENT_MULTIPLIER;
            assertTrue(SAMPLE_NUMBERS_PERCENTILES[i] == dist.calculatePercentile(percents));
        }
    }
}
