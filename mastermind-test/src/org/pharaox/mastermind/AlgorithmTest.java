package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;

import java.util.Arrays;
import java.util.Collection;

import static org.pharaox.mastermind.MastermindTest.assertValidCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.pharaox.mastermind.AlgorithmFactory.Type;

@RunWith(value = Parameterized.class)
public class AlgorithmTest
{
    private AlgorithmFactory algorithmFactory;
    private String firstGuess;
    private Algorithm algorithm;
    
    public AlgorithmTest(AlgorithmFactory algorithmFactory, String firstGuess)
    {
        this.algorithmFactory = algorithmFactory;
        this.firstGuess = firstGuess;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { new AlgorithmFactory(Type.SIMPLE, MASTERMIND), FIRST_GUESS_SIMPLE },
            { new AlgorithmFactory(Type.KNUTH, MASTERMIND), FIRST_GUESS_KNUTH },
            { new AlgorithmFactory(Type.EXP_SIZE, MASTERMIND), FIRST_GUESS_EXP_SIZE }
        };
        // @formatter:on
        return Arrays.asList(data);
    }
    
    @Before
    public void setup()
    {
        algorithm = algorithmFactory.getAlgorithm();
    }

    @Test
    public void testMakeGuess()
    {
        String guess = algorithm.makeGuess();
        assertValidCode(guess, ALPHABET, LENGTH, UNIQUE);
    }

    @Test
    public void testMakeGuessRepeatedly()
    {
        String guess1 = algorithm.makeGuess();
        assertValidCode(guess1, ALPHABET, LENGTH, UNIQUE);
        String guess2 = algorithm.makeGuess();
        assertEquals(guess1, guess2);
    }

    @Test
    public void testPutGuessScore()
    {
        String guess = algorithm.makeGuess();
        algorithm.putGuessScore(guess, ZERO_SCORE);
        assertTrue(ZERO_SCORE.equals(algorithm.getGuessScore(guess)));
    }

    @Test
    public void testPutGuessScoreRepeatedly()
    {
        String guess = algorithm.makeGuess();
        algorithm.putGuessScore(guess, ZERO_SCORE);
        algorithm.putGuessScore(guess, ZERO_SCORE);
    }
    
    @Test
    public void testFirstGuess()
    {
        String guess = algorithm.makeGuess();
        assertEquals(firstGuess, guess);
    }

}
