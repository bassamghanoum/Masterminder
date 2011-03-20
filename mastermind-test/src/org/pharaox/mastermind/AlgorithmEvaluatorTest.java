package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;
import static org.pharaox.mastermind.AlgorithmFactory.AlgorithmType.*;

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

    public AlgorithmEvaluatorTest(final AlgorithmType type, final Mastermind mastermind,
        final int numGames, final int maxRounds, final int totalRounds)
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
            { SIMPLE, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_SIMPLE, M2_TOTAL_ROUNDS_SIMPLE },
            { KNUTH, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_KNUTH, M2_TOTAL_ROUNDS_KNUTH },
            { ESIZE, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_ESIZE, M2_TOTAL_ROUNDS_ESIZE },
            { DUMB, M2, M2_NUM_GAMES, M2_MAX_ROUNDS_DUMB, M2_TOTAL_ROUNDS_DUMB },
/*            
            { SIMPLE, M1, M1_NUM_GAMES, M1_MAX_ROUNDS_SIMPLE, M1_TOTAL_ROUNDS_SIMPLE },
            { KNUTH, M1, M1_NUM_GAMES, M1_MAX_ROUNDS_KNUTH, M1_TOTAL_ROUNDS_KNUTH },
            { EXP_SIZE, M1, M1_NUM_GAMES, M1_MAX_ROUNDS_EXP_SIZE, M1_TOTAL_ROUNDS_EXP_SIZE },
*/            
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setup()
    {
        evaluator = new AlgorithmEvaluator(mastermind, type);
    }

    @Test
    public final void testEvaluate()
    {
        evaluator.evaluate();
        assertEquals(numGames, evaluator.getGamesPlayed());
        int expectedGamesWon = (type != AlgorithmType.DUMB) ? numGames : 1;
        assertEquals(expectedGamesWon, evaluator.getGamesWon());
        assertEquals(maxRounds, evaluator.getMaxRoundsPlayed());
        assertEquals(totalRounds, evaluator.getTotalRoundsPlayed());
        assertTrue((double) totalRounds / (double) numGames == evaluator.getAverageRoundsPlayed());
    }
}
