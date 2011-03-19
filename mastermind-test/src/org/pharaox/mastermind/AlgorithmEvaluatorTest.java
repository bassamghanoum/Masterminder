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
import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

@RunWith(value = Parameterized.class)
public class AlgorithmEvaluatorTest
{
    private AlgorithmType type;
    private Mastermind mastermind;
    private int numGames;
    private int maxRounds;
    private int totalRounds;
    private AlgorithmEvaluator evaluator;

    public AlgorithmEvaluatorTest(AlgorithmType type, Mastermind mastermind, int numGames,
        int maxRounds, int totalRounds)
    {
        this.type = type;
        this.mastermind = mastermind;
        this.numGames = numGames;
        this.maxRounds = maxRounds;
        this.totalRounds = totalRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { AlgorithmType.SIMPLE, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_SIMPLE, M2_TOTAL_ROUNDS_SIMPLE },
            { AlgorithmType.KNUTH, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_KNUTH, M2_TOTAL_ROUNDS_KNUTH },
            { AlgorithmType.EXP_SIZE, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_EXP_SIZE, M2_TOTAL_ROUNDS_EXP_SIZE },
            { AlgorithmType.DUMB, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_DUMB, M2_TOTAL_ROUNDS_DUMB },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public void setup()
    {
        evaluator = new AlgorithmEvaluator(mastermind, new AlgorithmFactory(type, mastermind));
    }

    @Test
    public void testEvaluate()
    {
        evaluator.evaluate();
        assertEquals(numGames, evaluator.getGamesPlayed());
        assertEquals((type != AlgorithmType.DUMB)? numGames : 1, evaluator.getGamesWon());
        assertEquals(maxRounds, evaluator.getMaxRoundsPlayed());
        assertEquals(totalRounds, evaluator.getTotalRoundsPlayed());
        assertTrue((double)totalRounds / (double)numGames == evaluator.getAverageRoundsPlayed());
    }
}
