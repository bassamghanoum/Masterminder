package org.pharaox.mastermind;

import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.MM2;

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
    private static final String M_WRONG_ALGORITHM_TYPE = "Wrong algorithm type";
    
    private final transient AlgorithmType type;
    private final transient Mastermind mastermind;
    
    private transient AlgorithmFactory factory;

    public AlgorithmFactoryTest(final AlgorithmType type, final Mastermind mastermind)
    {
        this.type = type;
        this.mastermind = mastermind;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        final Object[][] data = new Object[][]
        {
            { AlgorithmType.SIMPLE, MM2 },
            { AlgorithmType.KNUTH, MM2 },
            { AlgorithmType.PHARAOX, MM2 },
            { AlgorithmType.ESIZE, MM2 },
            { AlgorithmType.DUMB, MM2 },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setUp()
    {
        factory = new AlgorithmFactory(type, mastermind);
    }

    @Test
    public final void testGetAlgorithm()
    {
        final Algorithm algorithm = factory.getAlgorithm();
        if (type == AlgorithmType.SIMPLE)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof SimpleAlgorithm);
        }
        else if (type == AlgorithmType.KNUTH)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof KnuthAlgorithm);
        }
        else if (type == AlgorithmType.PHARAOX)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof PharaoxAlgorithm);
        }
        else if (type == AlgorithmType.ESIZE)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof ExpectedSizeAlgorithm);
        }
        else if (type == AlgorithmType.DUMB)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof DumbAlgorithm);
        }
    }
}
