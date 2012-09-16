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
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class GuessCalculatorTest
{
    private static final String M_WRONG_GUESS = "Wrong guess:";
    
    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient List<Score> scores;
    
    private transient GuessCalculator calc;
    private transient ReadyGuesses guesses;
    
    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        final Object[][] data = new Object[][]
        {
            { MM2, new SimpleAlgorithmFactory(MM2) },
            { MM2, new KnuthAlgorithmFactory(MM2) },
            { MM2, new ExpectedSizeAlgorithmFactory(MM2) },
            { MM2, new DumbAlgorithmFactory(MM2) },
        };
        // @formatter:on
        return Arrays.asList(data);
    }
    
    public GuessCalculatorTest(final Mastermind mastermind, final AlgorithmFactory factory)
    {
        super();
        this.mastermind = mastermind;
        this.factory = factory;
        this.scores = mastermind.getAllPossibleScores();
    }

    @Before
    public final void setUp()
    {
        // @checkstyle:off (Magic numbers)
        calc = new GuessCalculator(mastermind, factory, 4);
        // @checkstyle:on
        guesses = new ReadyGuesses(mastermind, factory);
    }

    @Test
    public final void testGetFirstGuess()
    {
        final String guess = calc.getGuess(new ArrayList<Score>(), 0);
        final String expected = guesses.getFirstGuess();
        assertEquals(M_WRONG_GUESS, expected, guess);
    }
    
    @Test
    public final void testGetSecondGuess()
    {
        for (Score score : scores)
        {
            final String guess = calc.getGuess(Arrays.asList(score), 1);
            final String expected = guesses.getSecondGuess(score);
            assertEquals(M_WRONG_GUESS, expected, guess);
        }
    }

    @Test
    public final void testGetThirdGuess()
    {
        for (Score score1 : scores)
        {
            for (Score score2 : scores)
            {
                final String guess = calc.getGuess(Arrays.asList(score1, score2), 2);
                final String expected = guesses.getThirdGuess(score1, score2);
                assertEquals(M_WRONG_GUESS, expected, guess);
            }
        }
    }
    
    @Test
    public final void testGetFourthGuess()
    {
        for (Score score1 : scores)
        {
            for (Score score2 : scores)
            {
                for (Score score3 : scores)
                {
                    // @checkstyle:off (Magic numbers)
                    final String guess = calc.getGuess(Arrays.asList(score1, score2, score3), 3);
                    // @checkstyle:on
                    final String expected = guesses.getFourthGuess(score1, score2, score3);
                    assertEquals(M_WRONG_GUESS, expected, guess);
                }
            }
        }
    }
}
