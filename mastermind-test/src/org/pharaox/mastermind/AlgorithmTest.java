package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pharaox.mastermind.Constants.*;
import static org.pharaox.mastermind.AlgorithmFactory.AlgorithmType.*;

import java.util.Arrays;
import java.util.Collection;

import static org.pharaox.mastermind.Score.ZERO_SCORE;
import static org.pharaox.mastermind.MastermindTest.assertValidCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

@RunWith(value = Parameterized.class)
public class AlgorithmTest
{
    private AlgorithmType type;
    private Mastermind mastermind;
    private String name;
    private String firstGuess;
    private String secondGuess;
    private Algorithm algorithm;

    public AlgorithmTest(final AlgorithmType type, final Mastermind mastermind, final String name,
        final String firstGuess, final String secondGuess)
    {
        this.type = type;
        this.mastermind = mastermind;
        this.name = name;
        this.firstGuess = firstGuess;
        this.secondGuess = secondGuess;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { SIMPLE, M2, SimpleAlgorithm.NAME, M2_FIRST_GUESS_SIMPLE, M2_SECOND_GUESS_SIMPLE },
            { KNUTH, M2, KnuthAlgorithm.NAME, M2_FIRST_GUESS_KNUTH, M2_SECOND_GUESS_KNUTH },
            { PHARAOX, M2, PharaoxAlgorithm.NAME, M2_FIRST_GUESS_KNUTH, M2_SECOND_GUESS_KNUTH },
            { ESIZE, M2, ExpectedSizeAlgorithm.NAME, M2_FIRST_GUESS_ESIZE, M2_SECOND_GUESS_ESIZE },
            { DUMB, M2, DumbAlgorithm.NAME, M2_FIRST_GUESS_DUMB, M2_SECOND_GUESS_DUMB }
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Before
    public final void setup()
    {
        algorithm = new AlgorithmFactory(type, mastermind).getAlgorithm();
    }

    @Test
    public final void testMakeGuess()
    {
        String guess = algorithm.makeGuess();
        assertValidCode(guess, mastermind.getAlphabet(), mastermind.getLength(),
            mastermind.hasUniqueChars());
        assertEquals(firstGuess, guess);
    }

    @Test
    public final void testMakeGuessRepeatedly()
    {
        String guess1 = algorithm.makeGuess();
        String guess2 = algorithm.makeGuess();
        assertEquals(guess1, guess2);
    }

    @Test
    public final void testPutGuessScore()
    {
        algorithm.putGuessScore(firstGuess, ZERO_SCORE);
        assertTrue(ZERO_SCORE.equals(algorithm.getGuessScore(firstGuess)));
    }

    @Test
    public final void testPutGuessScoreRepeatedly()
    {
        Score score = mastermind.getWinningScore();
        algorithm.putGuessScore(firstGuess, ZERO_SCORE);
        algorithm.putGuessScore(firstGuess, score);
        assertTrue(score.equals(algorithm.getGuessScore(firstGuess)));
    }

    @Test
    public final void testSecondGuess()
    {
        algorithm.putGuessScore(firstGuess, ZERO_SCORE);
        String guess = algorithm.makeGuess();
        assertEquals(secondGuess, guess);
    }

    @Test
    public final void testToString()
    {
        assertEquals(name, algorithm.toString());
    }
}
