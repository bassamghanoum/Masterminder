package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.AlgorithmFactory.AlgorithmType.*;
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
    private static final String M_WRONG_GAMES_PLAYED = "Wrong games played:";
    private static final String M_WRONG_GAMES_WON = "Wrong games won:";
    private static final String M_WRONG_MAX_ROUNDS = "Wrong max rounds:";
    private static final String M_WRONG_TOTAL_ROUNDS = "Wrong total rounds:";
    private static final String M_WRONG_AVERAGE_ROUNDS = "Wrong average rounds:";

    private static final double PRECISION = 0.0000001;

    private final transient AlgorithmType type;
    private final transient Mastermind mastermind;
    private final transient int numGames;
    private final transient int maxRounds;
    private final transient int totalRounds;

    private transient AlgorithmEvaluator evaluator;

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
        final Object[][] data = new Object[][]
        {
            { SIMPLE, MM2, MM2_NUM_GAMES, MM2_MAX_ROUNDS_SIMPLE, MM2_TOTAL_ROUNDS_SIMPLE },
            { KNUTH, MM2, MM2_NUM_GAMES, MM2_MAX_ROUNDS_KNUTH, MM2_TOTAL_ROUNDS_KNUTH },
            { ESIZE, MM2, MM2_NUM_GAMES, MM2_MAX_ROUNDS_ESIZE, MM2_TOTAL_ROUNDS_ESIZE },
            { DUMB, MM2, MM2_NUM_GAMES, MM2_MAX_ROUNDS_DUMB, MM2_TOTAL_ROUNDS_DUMB },
/*            
            { SIMPLE, M1, MM1_NUM_GAMES, MM1_MAX_ROUNDS_SIMPLE, MM1_TOTAL_ROUNDS_SIMPLE },
            { KNUTH, M1, MM1_NUM_GAMES, MM1_MAX_ROUNDS_KNUTH, MM1_TOTAL_ROUNDS_KNUTH },
            { EXP_SIZE, M1, MM1_NUM_GAMES, MM1_MAX_ROUNDS_EXP_SIZE, MM1_TOTAL_ROUNDS_EXP_SIZE },
*/            
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setUp()
    {
        evaluator = new AlgorithmEvaluator(mastermind, type);
    }

    @Test
    public final void testEvaluate()
    {
        evaluator.evaluate();
        assertEquals(M_WRONG_GAMES_PLAYED, numGames, evaluator.getGamesPlayed());
        final int expectedGamesWon = (type == AlgorithmType.DUMB) ? 1 : numGames;
        assertEquals(M_WRONG_GAMES_WON, expectedGamesWon, evaluator.getGamesWon());
        assertEquals(M_WRONG_MAX_ROUNDS, maxRounds, evaluator.getMaxRoundsPlayed());
        assertEquals(M_WRONG_TOTAL_ROUNDS, totalRounds, evaluator.getTotalRoundsPlayed());
        final double expectedAvgRounds = (double) totalRounds / (double) numGames;
        assertEquals(M_WRONG_AVERAGE_ROUNDS, expectedAvgRounds,
            evaluator.getAverageRoundsPlayed(), PRECISION);
    }
}
