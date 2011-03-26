package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Constants.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class AlgorithmEvaluatorTest
{
    private static final String M_WRONG_GAMES_PLAYED = "Wrong games played:";
    private static final String M_WRONG_GAMES_WON = "Wrong games won:";
    private static final String M_WRONG_MAX_ROUNDS = "Wrong max rounds:";
    private static final String M_WRONG_TOTAL_ROUNDS = "Wrong total rounds:";
    private static final String M_WRONG_AVERAGE_ROUNDS = "Wrong average rounds:";

    private static final double PRECISION = 0.0000001;

    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient int numGames;
    private final transient int maxRounds;
    private final transient int totalRounds;

    private transient AlgorithmEvaluator evaluator;

    public AlgorithmEvaluatorTest(final Mastermind mastermind, final AlgorithmFactory factory, 
        final int numGames, final int maxRounds, final int totalRounds)
    {
        super();
        this.mastermind = mastermind;
        this.factory = factory;
        this.numGames = numGames;
        this.maxRounds = maxRounds;
        this.totalRounds = totalRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off, @checkstyle:off
        final Object[][] data = new Object[][]
        {
            { MM2, new SimpleAlgorithmFactory(MM2), MM2_NUM_GAMES, MM2_MAX_ROUNDS_SIMPLE, MM2_TOTAL_ROUNDS_SIMPLE },
            { MM2, new KnuthAlgorithmFactory(MM2), MM2_NUM_GAMES, MM2_MAX_ROUNDS_KNUTH, MM2_TOTAL_ROUNDS_KNUTH },
            { MM2, new ExpectedSizeAlgorithmFactory(MM2), MM2_NUM_GAMES, MM2_MAX_ROUNDS_ESIZE, MM2_TOTAL_ROUNDS_ESIZE },
            { MM2, new DumbAlgorithmFactory(MM2), MM2_NUM_GAMES, MM2_MAX_ROUNDS_DUMB, MM2_TOTAL_ROUNDS_DUMB },
        };
        // @formatter:on, @checkstyle:on
        return Arrays.asList(data);
    }

    @Before
    public final void setUp()
    {
        // @checkstyle:off (Magic numbers)
        evaluator = new AlgorithmEvaluator(mastermind, factory, 4);
        // @checkstyle:on
    }

    @Test
    public final void testEvaluate()
    {
        evaluator.evaluate();
        assertEquals(M_WRONG_GAMES_PLAYED, numGames, evaluator.getGamesPlayed());
        final int expectedGamesWon = (factory instanceof DumbAlgorithmFactory) ? 1 : numGames;
        assertEquals(M_WRONG_GAMES_WON, expectedGamesWon, evaluator.getGamesWon());
        assertEquals(M_WRONG_MAX_ROUNDS, maxRounds, evaluator.getMaxRoundsPlayed());
        assertEquals(M_WRONG_TOTAL_ROUNDS, totalRounds, evaluator.getTotalRoundsPlayed());
        final double expectedAvgRounds = (double) totalRounds / (double) numGames;
        assertEquals(M_WRONG_AVERAGE_ROUNDS, expectedAvgRounds, evaluator.getAverageRoundsPlayed(),
            PRECISION);
    }
}
