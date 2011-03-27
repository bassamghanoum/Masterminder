package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.pharaox.mastermind.Constants.*;
import static org.pharaox.mastermind.Mastermind.MAX_LENGTH;
import static org.pharaox.util.Logger.debug;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class MastermindTest // NOPMD TooManyFields
{ // NOPMD TooManyMethods
    private static final String M_WRONG_ALPHABET = "Wrong alphabet:";
    private static final String M_WRONG_LENGTH = "Wrong length:";
    private static final String M_WRONG_UNIQUE_CHARS_FLAG = "Wrong unique chars flag:";
    private static final String M_WRONG_CODE_VALIDITY = "Wrong code validity:";
    private static final String M_WRONG_SCORE_VALIDITY = "Wrong score validity:";
    private static final String M_WRONG_NUMBER_OF_SCORES = "Wrong number of scores:";
    private static final String M_WRONG_SCORE = "Wrong score:";
    private static final String M_WRONG_NUMBER_OF_CODES = "Wrong number of codes:";
    private static final String M_WRONG_CODE = "Wrong code:";
    private static final String M_INVALID_CODE = "Invalid code";

    private static final int NUM_UNIQUE_CODES = 2;

    private final transient String alphabet;
    private final transient int length;
    private final transient boolean uniqueChars;
    private final transient String code;
    private final transient Score[] scores;
    private final transient Score[] possibleScores;
    private final transient String[] possibleCodes;
    private final transient String[] possibleCodes2;

    private transient String[] invalidAlphabets;
    private transient String[] validAlphabets;
    private transient int[] invalidLengths;
    private transient int[] validLengths;
    private transient String[] testCodes;
    private transient boolean[] testCodesAreValid;
    private transient Score[] testScores;
    private transient boolean[] testScoresAreValid;

    private transient Mastermind mastermind;

    public MastermindTest(final String alphabet, final int length, final boolean uniqueChars,
        final String code, final Score[] scores, final Score[] possibleScores,
        final String[] possibleCodes, final String[] possibleCodes2)
    {
        this.alphabet = alphabet;
        this.length = length;
        this.uniqueChars = uniqueChars;
        this.code = code;
        this.scores = Arrays.copyOf(scores, scores.length);
        this.possibleScores = Arrays.copyOf(possibleScores, possibleScores.length);
        this.possibleCodes = Arrays.copyOf(possibleCodes, possibleCodes.length);
        this.possibleCodes2 = Arrays.copyOf(possibleCodes2, possibleCodes2.length);

        initalizeArrays();
    }

    private void initalizeArrays()
    {
        initializeAlphabetsAndLengths();
        initializeTestCodes();
        initializeTestScores();
    }

    private void initializeAlphabetsAndLengths()
    {
        // @formatter:off
        this.invalidAlphabets = new String[] 
        { 
            null, "", alphabet.substring(0, 1), alphabet + "#", alphabet + alphabet 
        };
        this.validAlphabets = new String[] 
        { 
            alphabet, code 
        };
        this.invalidLengths =  new int[] 
        { 
            Integer.MIN_VALUE, 0, alphabet.length() + 1, MAX_LENGTH + 1, Integer.MAX_VALUE 
        };
        this.validLengths = new int[] 
        { 
            1, alphabet.length() / 2, alphabet.length() 
        };
        // @formatter:on
    }

    private void initializeTestCodes()
    {
        // @formatter:off
        this.testCodes = new String[]
        {
            alphabet.substring(0, length),
            alphabet.substring(alphabet.length() - length, alphabet.length()),
            code.substring(0, 1) + code.substring(0, 1) + code.substring(2, code.length()), 
            null, 
            "",
            alphabet.substring(0, 1), 
            code + alphabet.substring(0, 1),
            code.substring(0, code.length() - 1) + "#"
        };
        this.testCodesAreValid = new boolean[] 
        { 
            true, true, !uniqueChars, false, false, false, false, false 
        };
        // @formatter:on
    }

    private void initializeTestScores()
    {
        // @formatter:off
        this.testScores = new Score[]
        {
            scr(0, 0), scr(0, 1), scr(1, 0), scr(0, length), scr(length, 0),
            scr(0, -1), scr(-1, 0), scr(0, length + 1), scr(length + 1, 0), scr(1, length - 1),
            scr(1, length), scr(length, 1), null
        };
        this.testScoresAreValid = new boolean[] 
        { 
            true, true, true, true, true, false, false, false, false, false, false, false, false 
        };
        // @formatter:on
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off, @checkstyle:off (Magic numbers)
        final Object[][] data = new Object[][]
        {
            { MM1_ALPHABET, MM1_LENGTH, false, MM1_CODE, 
                new Score[] { scr(0, 1), scr(1, 1), scr(1, 1) },
                new Score[] { scr(0, 0), scr(1, 0), scr(2, 0), scr(3, 0), scr(4, 0),
                    scr(0, 1), scr(1, 1), scr(2, 1), scr(3, 1), scr(0, 2), scr(1, 2), scr(2, 2),
                    scr(0, 3), scr(0, 4) },
                new String[] {}, new String[] {} },
            { MM2_ALPHABET, MM2_LENGTH, false, "DA", 
                new Score[] { scr(0, 1), scr(1, 0), scr(1, 0), scr(2, 0), scr(0, 1), scr(0, 0), 
                    scr(0, 0), scr(1, 0), scr(0, 1), scr(0, 0), scr(0, 0), scr(1, 0),
                    scr(0, 2), scr(0, 1), scr(0, 1), scr(0, 1) },
                new Score[] { scr(0, 0), scr(1, 0), scr(2, 0), scr(0, 1), scr(0, 2) },
                new String[] { "AA", "AB", "AC", "AD", "BA", "BB", "BC", "BD", 
                    "CA", "CB", "CC", "CD", "DA", "DB", "DC", "DD" },
                new String[] { "BB", "BC", "CB", "CC" } },
            { "1234", 2, true, "12", 
                new Score[] { scr(0, 2), scr(0, 1), scr(0, 1), scr(2, 0), scr(1, 0), scr(1, 0), 
                    scr(1, 0), scr(0, 1), scr(0, 0), scr(1, 0), scr(0, 1), scr(0, 0) }, 
                new Score[] { scr(0, 0), scr(1, 0), scr(2, 0), scr(0, 1), scr(0, 2) },
                new String[] { "12", "13", "14", "21", "23", "24", "31", "32", "34",
                    "41", "42", "43" },
                new String[] { "34", "43" } }
        };
        // @formatter:on, @checkstyle:on
        return Arrays.asList(data);
    }

    private static Score scr(final int cows, final int bulls)
    {
        return new Score(cows, bulls);
    }

    @Before
    public final void setUp()
    {
        mastermind = new Mastermind(alphabet, length, uniqueChars);
    }

    @Test
    public final void testInvalidAlphabets()
    {
        for (final String a : invalidAlphabets)
        {
            createMastermind(a, length, false);
        }
    }

    @Test
    public final void testValidAlphabets()
    {
        for (final String a : validAlphabets)
        {
            createMastermind(a, length, true);
        }
    }

    @Test
    public final void testInvalidLengths()
    {
        for (final int i : invalidLengths)
        {
            createMastermind(alphabet, i, false);
        }
    }

    @Test
    public final void testValidLengths()
    {
        for (final int i : validLengths)
        {
            createMastermind(alphabet, i, true);
        }
    }

    @SuppressWarnings("unused")
    private static void createMastermind(final String alphabetx, final int lengthx, 
        final boolean valid)
    {
        try
        {
            new Mastermind(alphabetx, lengthx);
            failIf(!valid);
        }
        catch (final MastermindException e)
        {
            failIf(valid);
        }
    }

    private static void failIf(final boolean condition)
    {
        if (condition)
        {
            fail();
        }
    }

    @Test
    public final void testGeneratedCodeIsValid()
    {
        assertValidCode(mastermind.generateCode());
    }

    @Test
    public final void testGeneratedCodesAreDifferent()
    {
        final Set<String> generated = new HashSet<String>();
        for (int i = 0; i < NUM_UNIQUE_CODES; i++)
        {
            final String codex = mastermind.generateCode();
            assertValidCode(codex);
            generated.add(codex);
        }
    }

    @Test
    public final void testGetAlphabet()
    {
        assertEquals(M_WRONG_ALPHABET, alphabet, mastermind.getAlphabet());
    }

    @Test
    public final void testGetLength()
    {
        assertEquals(M_WRONG_LENGTH, length, mastermind.getLength());
    }

    @Test
    public final void testHasUniqueChars()
    {
        assertEquals(M_WRONG_UNIQUE_CHARS_FLAG, uniqueChars, mastermind.hasUniqueChars());
    }

    @Test
    public final void testIsValidCode()
    {
        for (int i = 0; i < testCodes.length; i++)
        {
            assertEquals(M_WRONG_CODE_VALIDITY, testCodesAreValid[i], 
                mastermind.isValidCode(testCodes[i]));
        }
    }
    
    @Test
    public final void testIsValidScore()
    {
        for (int i = 0; i < testScores.length; i++)
        {
            assertEquals(M_WRONG_SCORE_VALIDITY, testScoresAreValid[i], 
                mastermind.isValidScore(testScores[i]));
        }
    }

    @Test
    public final void testGetWinningScore()
    {
        assertEquals(M_WRONG_SCORE, new Score(0, length), mastermind.getWinningScore());
    }

    @Test
    public final void testEvaluateScore()
    {
        mastermind.visitCodes(new EvaluateScoreVisitor());
    }

    class EvaluateScoreVisitor implements CodeVisitor
    {
        private transient int count = 0;

        @Override
        public void visit(final String codex)
        {
            if (count < scores.length)
            {
                final Score score = mastermind.evaluateScoreSafe(codex, code);
                debug(codex + ": " + score);
                assertEquals(M_WRONG_SCORE, score, scores[count]);
            }
            count++;
        }
    }

    @Test(expected = MastermindException.class)
    public final void testEvaluateScoreInvalidScore()
    {
        mastermind.evaluateScoreSafe("", code);
    }

    @Test(expected = MastermindException.class)
    public final void testEvaluateScoreInvalidCode()
    {
        mastermind.evaluateScoreSafe(testCodes[0], "");
    }

    @Test
    public final void testGetAllPossibleScores()
    {
        final List<Score> allPossibleScores = mastermind.getAllPossibleScores();
        assertEquals(M_WRONG_NUMBER_OF_SCORES, possibleScores.length, allPossibleScores.size());
        for (int i = 0; i < possibleScores.length; i++)
        {
            assertEquals(M_WRONG_SCORE, possibleScores[i], allPossibleScores.get(i));
        }
    }

    @Test
    public final void testGetAllPossibleCodes()
    {
        final SortedSet<String> allPossibleCodes = mastermind.getAllPossibleCodes();
        if (possibleCodes.length > 0)
        {
            assertEquals(M_WRONG_NUMBER_OF_CODES, possibleCodes.length, allPossibleCodes.size());
            for (final String possibleCode : possibleCodes)
            {
                assertTrue(M_WRONG_CODE, allPossibleCodes.contains(possibleCode));
            }
        }
    }

    @Test
    public final void testEvaluatePossibleCodes()
    {
        SortedSet<String> codesx = mastermind.getAllPossibleCodes();
        codesx = mastermind.evaluatePossibleCodes(code, Score.ZERO_SCORE, codesx);
        if (possibleCodes2.length > 0)
        {
            assertEquals(M_WRONG_NUMBER_OF_CODES, possibleCodes2.length, codesx.size());
            for (final String element : possibleCodes2)
            {
                assertTrue(M_WRONG_CODE, codesx.contains(element));
            }
        }
    }

    @SuppressWarnings("null")
    private void assertValidCode(final String codex)
    {
        assertTrue(M_INVALID_CODE, (codex != null) && !codex.isEmpty());
        assertEquals(M_INVALID_CODE, length, codex.length());
        final Set<Character> usedChars = new HashSet<Character>();
        for (int i = 0; i < codex.length(); i++)
        {
            final char chi = codex.charAt(i);
            assertThat(M_INVALID_CODE, alphabet.indexOf(chi), not(equalTo(-1)));
            if (uniqueChars)
            {
                assertFalse(M_INVALID_CODE, usedChars.contains(chi));
                usedChars.add(chi);
            }
        }
    }
}
