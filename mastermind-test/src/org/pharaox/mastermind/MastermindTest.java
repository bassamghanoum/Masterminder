package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.pharaox.util.Logger.debug;
import static org.pharaox.mastermind.Mastermind.MAX_LENGTH;
import static org.pharaox.mastermind.Constants.*;

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
public class MastermindTest
{
    private static final int NUM_UNIQUE_CODES = 2;

    private String alphabet;
    private int length;
    private boolean uniqueChars;
    private String code;
    private Score[] scores;
    private Score[] possibleScores;
    private String[] possibleCodes;
    private String[] possibleCodes2;

    private String[] codes;
    private boolean[] valids;
    private String[] invalidAlphabets;
    private String[] validAlphabets;
    private int[] invalidLengths;
    private int[] validLengths;

    private Mastermind mastermind;

    // @checkstyle:off (Long argument list)
    public MastermindTest(final String alphabet, final int length, final boolean uniqueChars,
        final String code, final Score[] scores, final Score[] possibleScores,
        final String[] possibleCodes, final String[] possibleCodes2)
    // @checkstyle:on
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
        // @formatter:off
        this.codes = new String[]
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
        this.valids = new boolean[] { true, true, !uniqueChars, false, false, false, false, false };
        this.invalidAlphabets = new String[] { null, "", alphabet.substring(0, 1), alphabet + "#", 
            alphabet + alphabet };
        this.validAlphabets = new String[] { alphabet, code };
        this.invalidLengths =  new int[] { Integer.MIN_VALUE, 0, alphabet.length() + 1, 
            MAX_LENGTH + 1, Integer.MAX_VALUE };
        this.validLengths = new int[] { 1, alphabet.length() / 2, alphabet.length() };
        // @formatter:on
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off, @checkstyle:off (Magic numbers)
        Object[][] data = new Object[][]
        {
            { M1_ALPHABET, M1_LENGTH, false, M1_CODE, 
                new Score[] { sc(0, 1), sc(1, 1), sc(1, 1) },
                new Score[] { sc(0, 0), sc(1, 0), sc(2, 0), sc(3, 0), sc(4, 0),
                    sc(0, 1), sc(1, 1), sc(2, 1), sc(3, 1), sc(0, 2), sc(1, 2), sc(2, 2),
                    sc(0, 3), sc(0, 4) },
                new String[] {}, new String[] {} },
            { M2_ALPHABET, M2_LENGTH, false, "DA", 
                new Score[] { sc(0, 1), sc(1, 0), sc(1, 0), sc(2, 0), sc(0, 1), sc(0, 0), 
                    sc(0, 0), sc(1, 0), sc(0, 1), sc(0, 0), sc(0, 0), sc(1, 0),
                    sc(0, 2), sc(0, 1), sc(0, 1), sc(0, 1) },
                new Score[] { sc(0, 0), sc(1, 0), sc(2, 0), sc(0, 1), sc(0, 2) },
                new String[] { "AA", "AB", "AC", "AD", "BA", "BB", "BC", "BD", 
                    "CA", "CB", "CC", "CD", "DA", "DB", "DC", "DD" },
                new String[] { "BB", "BC", "CB", "CC" } },
            { "1234", 2, true, "12", 
                new Score[] { sc(0, 2), sc(0, 1), sc(0, 1), sc(2, 0), sc(1, 0), sc(1, 0), 
                    sc(1, 0), sc(0, 1), sc(0, 0), sc(1, 0), sc(0, 1), sc(0, 0) }, 
                new Score[] { sc(0, 0), sc(1, 0), sc(2, 0), sc(0, 1), sc(0, 2) },
                new String[] { "12", "13", "14", "21", "23", "24", "31", "32", "34",
                    "41", "42", "43" },
                new String[] { "34", "43" } }
        };
        // @formatter:on, @checkstyle:on
        return Arrays.asList(data);
    }

    private static Score sc(final int cows, final int bulls)
    {
        return new Score(cows, bulls);
    }

    @Before
    public final void setup()
    {
        mastermind = new Mastermind(alphabet, length, uniqueChars);
        mastermind.setCurrentCode(code);
    }

    @Test(expected = MastermindException.class)
    public final void testInvalidAlphabets()
    {
        for (String a : invalidAlphabets)
        {
            new Mastermind(a, length);
        }
    }

    @Test
    public final void testValidAlphabets()
    {
        for (String a : validAlphabets)
        {
            new Mastermind(a, length);
        }
    }

    @Test(expected = MastermindException.class)
    public final void testInvalidLengths()
    {
        for (int i : invalidLengths)
        {
            new Mastermind(alphabet, i);
        }
    }

    @Test
    public final void testValidLengths()
    {
        for (int i : validLengths)
        {
            new Mastermind(alphabet, i);
        }
    }

    @Test
    public final void testGeneratedCodeIsValid()
    {
        assertValidCode(mastermind.generateCode(), alphabet, length, uniqueChars);
    }

    @Test
    public final void testGeneratedCodesAreDifferent()
    {
        Set<String> generated = new HashSet<String>();
        for (int i = 0; i < NUM_UNIQUE_CODES; i++)
        {
            String codex = mastermind.generateCode();
            assertValidCode(codex, alphabet, length, uniqueChars);
            generated.add(codex);
        }
    }

    @Test
    public final void testGetAlphabet()
    {
        assertEquals(alphabet, mastermind.getAlphabet());
    }

    @Test
    public final void testGetLength()
    {
        assertEquals(length, mastermind.getLength());
    }

    @Test
    public final void testHasUniqueChars()
    {
        assertEquals(uniqueChars, mastermind.hasUniqueChars());
    }

    @Test
    public final void testGetCode()
    {
        assertEquals(code, mastermind.getCurrentCode());
    }

    @Test
    public final void testCodes()
    {
        for (int i = 0; i < codes.length; i++)
        {
            String codex = codes[i];
            boolean valid = valids[i];
            try
            {
                mastermind.setCurrentCode(codex);
                if (!valid)
                    fail((codex != null) ? codex : "null");
            }
            catch (MastermindException e)
            // @checkstyle:off (Empty catch block)
            {
            }
            // @checkstyle:on
        }
    }

    @Test
    public final void testGetWinningScore()
    {
        assertEquals(new Score(0, length), mastermind.getWinningScore());
    }

    @Test
    public final void testEvaluateScore()
    {
        mastermind.visitCodes(new EvaluateScoreVisitor(scores));
    }

    class EvaluateScoreVisitor implements CodeVisitor
    {
        private Score[] scores;
        private int count = 0;

        public EvaluateScoreVisitor(final Score[] scores)
        {
            this.scores = scores;
        }

        @Override
        public void visit(final String codex)
        {
            if (count < scores.length)
            {
                Score score = mastermind.evaluateScore(codex);
                debug(codex + ": " + score);
                assertTrue(score.equals(scores[count]));
            }
            count++;
        }
    }

    @Test(expected = MastermindException.class)
    public final void testEvaluateScoreInvalidScore()
    {
        mastermind.evaluateScore("");
    }

    @Test(expected = MastermindException.class)
    public final void testEvaluateScoreInvalidCode()
    {
        mastermind.evaluateScore(codes[0], "");
    }

    @Test
    public final void testGetAllPossibleScores()
    {
        List<Score> allPossibleScores = mastermind.getAllPossibleScores();
        assertEquals(possibleScores.length, allPossibleScores.size());
        for (int i = 0; i < possibleScores.length; i++)
            assertEquals(possibleScores[i], allPossibleScores.get(i));
    }

    @Test
    public final void testGetAllPossibleCodes()
    {
        SortedSet<String> allPossibleCodes = mastermind.getAllPossibleCodes();
        if (possibleCodes.length > 0)
        {
            assertEquals(possibleCodes.length, allPossibleCodes.size());
            for (int i = 0; i < possibleCodes.length; i++)
                assertTrue(allPossibleCodes.contains(possibleCodes[i]));
        }
    }

    @Test
    public final void testEvaluatePossibleCodes()
    {
        SortedSet<String> codesx = mastermind.getAllPossibleCodes();
        codesx = mastermind.evaluatePossibleCodes(code, Score.ZERO_SCORE, codesx);
        if (possibleCodes2.length > 0)
        {
            assertEquals(possibleCodes2.length, codesx.size());
            for (int i = 0; i < possibleCodes2.length; i++)
                assertTrue(codesx.contains(possibleCodes2[i]));
        }
    }

    public static void assertValidCode(final String code, final String alphabet, final int length,
        final boolean uniqueChars)
    {
        assertTrue(code != null && !code.isEmpty());
        assertTrue(code.length() == length);
        Set<Character> usedChars = new HashSet<Character>();
        for (int i = 0; i < code.length(); i++)
        {
            char c = code.charAt(i);
            assertTrue(alphabet.contains(new String(new char[] { c })));
            if (uniqueChars)
            {
                assertFalse(usedChars.contains(c));
                usedChars.add(c);
            }
        }
    }
}
