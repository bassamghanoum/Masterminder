/*
 * $Id: $
 *
 * Copyright 2012 Stoyan Rachev (stoyanr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stoyanr.mastermind;

import static com.stoyanr.mastermind.Constants.MM2;
import static org.junit.Assert.assertTrue;

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
