package org.pharaox.mastermind;

import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.MM2;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class AlgorithmFactoryTest
{
    private static final String M_WRONG_ALGORITHM_TYPE = "Wrong algorithm type";
    
    private final transient AlgorithmFactory factory;
    
    public AlgorithmFactoryTest(final AlgorithmFactory factory)
    {
        this.factory = factory;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        final Object[][] data = new Object[][]
        {
            { new SimpleAlgorithmFactory(MM2)},
            { new KnuthAlgorithmFactory(MM2) },
            { new PharaoxAlgorithmFactory(MM2, 0.0) },
            { new ExpectedSizeAlgorithmFactory(MM2) },
            { new DumbAlgorithmFactory(MM2) },
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Test
    public final void testGetAlgorithm()
    {
        final Algorithm algorithm = factory.getAlgorithm();
        if (factory instanceof SimpleAlgorithmFactory)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof SimpleAlgorithm);
        }
        else if (factory instanceof KnuthAlgorithmFactory)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof KnuthAlgorithm);
        }
        else if (factory instanceof PharaoxAlgorithmFactory)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof PharaoxAlgorithm);
        }
        else if (factory instanceof ExpectedSizeAlgorithmFactory)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof ExpectedSizeAlgorithm);
        }
        else if (factory instanceof DumbAlgorithmFactory)
        {
            assertTrue(M_WRONG_ALGORITHM_TYPE, algorithm instanceof DumbAlgorithm);
        }
    }
}
