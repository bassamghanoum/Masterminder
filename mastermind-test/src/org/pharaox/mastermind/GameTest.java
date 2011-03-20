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
    private AlgorithmType type;
    private Mastermind mastermind;
    private String code;
    private int maxRounds;
    private Game game;

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
        Object[][] data = new Object[][]
        {
            { AlgorithmType.SIMPLE, M2, M2_CODE, M2_MAX_ROUNDS_SIMPLE },
            { AlgorithmType.KNUTH, M2, M2_CODE, M2_MAX_ROUNDS_KNUTH },
            { AlgorithmType.ESIZE, M2, M2_CODE, M2_MAX_ROUNDS_ESIZE },
            { AlgorithmType.DUMB, M2, M2_CODE, M2_MAX_ROUNDS_DUMB },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setup()
    {
        game = new Game(mastermind, type, maxRounds);
    }

    @Test
    public final void testSetPlayer()
    {
        game.setPlayer(null);
        assertEquals(null, game.getPlayer());
    }

    @Test
    public final void testPlay()
    {
        mastermind.setCurrentCode(code);
        boolean won = game.play();
        assertEquals(won, game.hasWon());
        assertTrue((type != AlgorithmType.DUMB && won) || (type == AlgorithmType.DUMB && !won));
        assertTrue(game.getRoundsPlayed() <= maxRounds);
    }

    @Test(expected = MastermindException.class)
    public final void testPlayRepeatedly()
    {
        mastermind.setCurrentCode(code);
        game.play();
        game.play();
    }
}
