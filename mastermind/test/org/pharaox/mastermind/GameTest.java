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
public class GameTest
{
    private static final String M_WRONG_GAME_OUTCOME = "Wrong game outcome:";
    private static final String M_UNEXPECTED_ROUNDS_PLAYED = "Unexpected rounds played:";
    
    private final transient AlgorithmType type;
    private final transient Mastermind mastermind;
    private final transient String code;
    private final transient int maxRounds;

    private transient Game game;

    public GameTest(final AlgorithmType type, final Mastermind mastermind, final String code,
        final int maxRounds)
    {
        this.type = type;
        this.mastermind = mastermind;
        this.code = code;
        this.maxRounds = maxRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        final Object[][] data = new Object[][]
        {
            { AlgorithmType.SIMPLE, MM2, MM2_CODE, MM2_MAX_ROUNDS_SIMPLE },
            { AlgorithmType.KNUTH, MM2, MM2_CODE, MM2_MAX_ROUNDS_KNUTH },
            { AlgorithmType.ESIZE, MM2, MM2_CODE, MM2_MAX_ROUNDS_ESIZE },
            { AlgorithmType.DUMB, MM2, MM2_CODE, MM2_MAX_ROUNDS_DUMB },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setUp()
    {
        final Player player = new DefaultPlayer(mastermind, code);
        game = new Game(mastermind, type, maxRounds, player);
    }

    @Test
    public final void testPlay()
    {
        final boolean won = game.play();
        assertEquals(M_WRONG_GAME_OUTCOME, won, game.hasWon());
        assertTrue(M_WRONG_GAME_OUTCOME, 
            ((type != AlgorithmType.DUMB) && won) || ((type == AlgorithmType.DUMB) && !won));
        assertTrue(M_UNEXPECTED_ROUNDS_PLAYED, game.getRoundsPlayed() <= maxRounds);
    }

    @Test(expected = MastermindException.class)
    public final void testPlayRepeatedly()
    {
        game.play();
        game.play();
    }
}
