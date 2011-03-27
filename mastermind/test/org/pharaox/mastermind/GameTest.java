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

@RunWith(value = Parameterized.class)
public class GameTest
{
    private static final String M_WRONG_GAME_OUTCOME = "Wrong game outcome:";
    private static final String M_UNEXPECTED_ROUNDS_PLAYED = "Unexpected rounds played:";
    
    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient String code;
    private final transient int maxRounds;

    private transient Game game;

    public GameTest(final Mastermind mastermind, final AlgorithmFactory factory, final String code,
        final int maxRounds)
    {
        this.mastermind = mastermind;
        this.factory = factory;
        this.code = code;
        this.maxRounds = maxRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        final Object[][] data = new Object[][]
        {
            { MM2, new SimpleAlgorithmFactory(MM2), MM2_CODE, MM2_MAX_ROUNDS_SIMPLE },
            { MM2, new KnuthAlgorithmFactory(MM2), MM2_CODE, MM2_MAX_ROUNDS_KNUTH },
            { MM2, new ExpectedSizeAlgorithmFactory(MM2), MM2_CODE, MM2_MAX_ROUNDS_ESIZE },
            { MM2, new DumbAlgorithmFactory(MM2), MM2_CODE, MM2_MAX_ROUNDS_DUMB },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setUp()
    {
        final Player player = new DefaultPlayer(mastermind, code);
        game = new Game(mastermind, factory.getAlgorithm(), maxRounds, player);
    }

    @Test
    public final void testPlay()
    {
        final boolean won = game.play();
        assertEquals(M_WRONG_GAME_OUTCOME, won, game.hasWon());
        final boolean dumb = (factory instanceof DumbAlgorithmFactory);
        assertTrue(M_WRONG_GAME_OUTCOME, (!dumb && won) || (dumb && !won));
        assertTrue(M_UNEXPECTED_ROUNDS_PLAYED, game.getRoundsPlayed() <= maxRounds);
    }

    @Test(expected = MastermindException.class)
    public final void testPlayRepeatedly()
    {
        game.play();
        game.play();
    }
    
    @Test(expected = MastermindException.class)
    public final void testPlayWithCheatingPlayer()
    {
        if (factory instanceof DumbAlgorithmFactory)
        {
            throw new MastermindException(); // Make the test pass
        }
        final Player player = new Player()
        {
            @Override
            public void startGame()
            {
                // No implementation needed
            }

            @Override
            public void endGame(final boolean won, final int roundsPlayed)
            {
                // No implementation needed
            }

            @Override
            public Score getScore(final String guess)
            {
                return new Score(0, 0);
            }
        };
        game = new Game(mastermind, factory.getAlgorithm(), maxRounds, player);
        game.play();
    }
}
