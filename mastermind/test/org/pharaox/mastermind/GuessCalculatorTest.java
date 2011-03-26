package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Constants.MM2;

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
    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient List<Score> scores;
    
    private transient GuessCalculator calc;
    private transient ReadyGuesses guesses;
    
    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off, @checkstyle:off
        final Object[][] data = new Object[][]
        {
            { MM2, new SimpleAlgorithmFactory(MM2) },
            { MM2, new KnuthAlgorithmFactory(MM2) },
            { MM2, new ExpectedSizeAlgorithmFactory(MM2) },
            { MM2, new DumbAlgorithmFactory(MM2) },
        };
        // @formatter:on, @checkstyle:on
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
        String guess = calc.getGuess(new ArrayList<Score>(), 0);
        String expected = guesses.getFirstGuess();
        assertEquals(expected, guess);
    }
    
    @Test
    public final void testGetSecondGuess()
    {
        for (Score score : scores)
        {
            String guess = calc.getGuess(Arrays.asList(score), 1);
            String expected = guesses.getSecondGuess(score);
            assertEquals(expected, guess);
        }
    }

    @Test
    public final void testGetThirdGuess()
    {
        for (Score score1 : scores)
        {
            for (Score score2 : scores)
            {
                String guess = calc.getGuess(Arrays.asList(score1, score2), 2);
                String expected = guesses.getThirdGuess(score1, score2);
                assertEquals(expected, guess);
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
                    String guess = calc.getGuess(Arrays.asList(score1, score2, score3), 3);
                    // @checkstyle:on
                    String expected = guesses.getFourthGuess(score1, score2, score3);
                    assertEquals(expected, guess);
                }
            }
        }
    }
}
