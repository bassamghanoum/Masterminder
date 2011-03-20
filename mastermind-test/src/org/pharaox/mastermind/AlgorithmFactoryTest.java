package org.pharaox.mastermind;

import static org.pharaox.mastermind.Constants.M2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

@RunWith(value = Parameterized.class)
public class AlgorithmFactoryTest
{
    private AlgorithmType type;
    private Mastermind mastermind;
    private AlgorithmFactory factory;

    public AlgorithmFactoryTest(final AlgorithmType type, final Mastermind mastermind)
    {
        this.type = type;
        this.mastermind = mastermind;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { AlgorithmType.SIMPLE, M2 },
            { AlgorithmType.KNUTH, M2 },
            { AlgorithmType.PHARAOX, M2 },
            { AlgorithmType.ESIZE, M2 },
            { AlgorithmType.DUMB, M2 },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setup()
    {
        factory = new AlgorithmFactory(type, mastermind);
    }

    @Test
    public final void testGetAlgorithm()
    {
        Algorithm algorithm = factory.getAlgorithm();
        if (type == AlgorithmType.SIMPLE)
            assertTrue(algorithm instanceof SimpleAlgorithm);
        else if (type == AlgorithmType.KNUTH)
            assertTrue(algorithm instanceof KnuthAlgorithm);
        else if (type == AlgorithmType.PHARAOX)
            assertTrue(algorithm instanceof PharaoxAlgorithm);
        else if (type == AlgorithmType.ESIZE)
            assertTrue(algorithm instanceof ExpectedSizeAlgorithm);
        else if (type == AlgorithmType.DUMB)
            assertTrue(algorithm instanceof DumbAlgorithm);
    }

    @Test
    public final void testAlgorithmTypeValueOf()
    {
        assertEquals(type, AlgorithmType.valueOf(type.toString()));
    }

    @Test
    public final void testAlgorithmTypeValues()
    {
        AlgorithmType.values();
    }
}
