package org.pharaox.mastermind;

import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;

import java.util.Arrays;
import java.util.Collection;

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

    public GameTest(AlgorithmType type, Mastermind mastermind, String code, int maxRounds)
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
            { AlgorithmType.EXP_SIZE, M2, M2_CODE, M2_MAX_ROUNDS_EXP_SIZE },
            { AlgorithmType.DUMB, M2, M2_CODE, M2_MAX_ROUNDS_DUMB },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Test
    public void testPlay() throws MastermindException
    {
        mastermind.setCode(code);
        Game game = new Game(mastermind, new AlgorithmFactory(type, mastermind).getAlgorithm(), maxRounds);
        boolean won = game.play();
        assertTrue((type != AlgorithmType.DUMB && won) || (type == AlgorithmType.DUMB && !won));
        assertTrue(game.getRoundsPlayed() <= maxRounds);
    }
}
