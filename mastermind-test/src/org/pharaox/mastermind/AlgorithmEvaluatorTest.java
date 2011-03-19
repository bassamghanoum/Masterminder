package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.pharaox.mastermind.AlgorithmFactory.Type;

@RunWith(value = Parameterized.class)
public class AlgorithmEvaluatorTest
{
    private AlgorithmFactory algorithmFactory;
    private int maxRounds;
    private int totalRounds;

    private AlgorithmEvaluator evaluator;

    public AlgorithmEvaluatorTest(AlgorithmFactory algorithmFactory, int maxRounds, int totalRounds)
    {
        this.algorithmFactory = algorithmFactory;
        this.maxRounds = maxRounds;
        this.totalRounds = totalRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { new AlgorithmFactory(Type.SIMPLE, MASTERMIND), MAX_ROUNDS_SIMPLE, TOTAL_ROUNDS_SIMPLE },
            { new AlgorithmFactory(Type.KNUTH, MASTERMIND), MAX_ROUNDS_KNUTH, TOTAL_ROUNDS_KNUTH },
            { new AlgorithmFactory(Type.EXP_SIZE, MASTERMIND), MAX_ROUNDS_EXP_SIZE, TOTAL_ROUNDS_EXP_SIZE },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public void setup()
    {
        evaluator = new AlgorithmEvaluator(MASTERMIND, algorithmFactory);
    }

    @Test
    public void testEvaluate()
    {
        evaluator.evaluate();
        assertEquals(NUM_GAMES, evaluator.getGamesPlayed());
        assertEquals(NUM_GAMES, evaluator.getGamesWon());
        assertEquals(maxRounds, evaluator.getMaxRoundsPlayed());
        assertEquals(totalRounds, evaluator.getTotalRoundsPlayed());
        assertTrue((double)totalRounds / (double)NUM_GAMES == evaluator.getAverageRoundsPlayed());
    }
}
