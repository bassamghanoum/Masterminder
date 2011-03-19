package org.pharaox.mastermind;

import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.pharaox.mastermind.AlgorithmFactory.Type;

@RunWith(value = Parameterized.class)
public class GameTest
{
    private AlgorithmFactory algorithmFactory;
    private int maxRounds;

    public GameTest(AlgorithmFactory algorithmFactory, int maxRounds)
    {
        this.algorithmFactory = algorithmFactory;
        this.maxRounds = maxRounds;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { new AlgorithmFactory(Type.SIMPLE, MASTERMIND), MAX_ROUNDS_SIMPLE },
            { new AlgorithmFactory(Type.KNUTH, MASTERMIND), MAX_ROUNDS_KNUTH },
            { new AlgorithmFactory(Type.EXP_SIZE, MASTERMIND), MAX_ROUNDS_EXP_SIZE },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Test
    public void testGame() throws MastermindException
    {
        MASTERMIND.setCode(CODE_1);
        boolean won = new Game(MASTERMIND, algorithmFactory.getAlgorithm(), maxRounds).play();
        assertTrue(won);
    }
}
