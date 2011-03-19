package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.pharaox.util.Logger.debug;
import static org.pharaox.mastermind.Mastermind.MAX_LENGTH;

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

    public MastermindTest(String alphabet, int length, boolean uniqueChars, String code, 
        Score[] scores, Score[] possibleScores, String[] possibleCodes, String[] possibleCodes2)
    {
        this.alphabet = alphabet;
        this.length = length;
        this.uniqueChars = uniqueChars;
        this.code = code;
        this.scores = scores;
        this.possibleScores = possibleScores;
        this.possibleCodes = possibleCodes;
        this.possibleCodes2 = possibleCodes2;

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
        this.valids = new boolean[]
        {
            true, 
            true, 
            !uniqueChars, 
            false, 
            false, 
            false, 
            false, 
            false
        };
        this.invalidAlphabets = new String[]
        {
            null, 
            "", 
            alphabet.substring(0, 1), 
            alphabet + "#",
            alphabet + alphabet
        };
        this.validAlphabets = new String[]
        {
            alphabet, 
            code
        };
        this.invalidLengths = new int[]
        {
            Integer.MIN_VALUE, 
            0, 
            alphabet.length() + 1,
            MAX_LENGTH + 1,
            Integer.MAX_VALUE
        };
        this.validLengths = new int[]
        {
            1, 
            alphabet.length() / 2, 
            alphabet.length()
        };
        // @formatter:on
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off
        Object[][] data = new Object[][]
        {
            { "ABCDEF", 4, false, "ABCD", 
                new Score[] { sc(0, 1), sc(1, 1), sc(1, 1) },
                new Score[] { 
                    sc(0, 0), sc(1, 0), sc(2, 0), sc(3, 0), sc(4, 0),
                    sc(0, 1), sc(1, 1), sc(2, 1), sc(3, 1),
                    sc(0, 2), sc(1, 2), sc(2, 2),
                    sc(0, 3),
                    sc(0, 4) },
                new String[] {},
                new String[] {}
            },
            { "ABCD", 2, false, "DA", 
                new Score[] { 
                    sc(0, 1), sc(1, 0), sc(1, 0), sc(2, 0), 
                    sc(0, 1), sc(0, 0), sc(0, 0), sc(1, 0), 
                    sc(0, 1), sc(0, 0), sc(0, 0), sc(1, 0),
                    sc(0, 2), sc(0, 1), sc(0, 1), sc(0, 1) },
                new Score[] { 
                    sc(0, 0), sc(1, 0), sc(2, 0),
                    sc(0, 1),
                    sc(0, 2) },
                new String[] {
                    "AA", "AB", "AC", "AD", 
                    "BA", "BB", "BC", "BD",
                    "CA", "CB", "CC", "CD",
                    "DA", "DB", "DC", "DD" },
                new String[] { "BB", "BC", "CB", "CC" }
            },
            { "1234", 2, true, "12", 
                new Score[] { 
                    sc(0, 2), sc(0, 1), sc(0, 1), 
                    sc(2, 0), sc(1, 0), sc(1, 0), 
                    sc(1, 0), sc(0, 1), sc(0, 0), 
                    sc(1, 0), sc(0, 1), sc(0, 0) }, 
                new Score[] { 
                    sc(0, 0), sc(1, 0), sc(2, 0),
                    sc(0, 1),
                    sc(0, 2) },
                new String[] {
                    "12", "13", "14", 
                    "21", "23", "24",
                    "31", "32", "34",
                    "41", "42", "43" },
                new String[] { "34", "43" }
            }
        };
        // @formatter:on
        return Arrays.asList(data);
    }

    private static Score sc(int cows, int bulls)
    {
        return new Score(cows, bulls);
    }

    @Before
    public void setup()
    {
        mastermind = new Mastermind(alphabet, length, uniqueChars);
        mastermind.setCode(code);
    }

    @Test
    public void testInvalidAlphabets()
    {
        for (String a : invalidAlphabets)
        {
            try
            {
                new Mastermind(a, length);
                fail((a != null) ? a : "null");
            }
            catch (MastermindException e)
            {
            }
        }
    }

    @Test
    public void testValidAlphabets()
    {
        for (String a : validAlphabets)
        {
            new Mastermind(a, length);
        }
    }

    @Test
    public void testInvalidLengths()
    {
        for (int i : invalidLengths)
        {
            try
            {
                new Mastermind(alphabet, i);
                fail(new Integer(i).toString());
            }
            catch (MastermindException e)
            {
            }
        }
    }

    @Test
    public void testValidLengths()
    {
        for (int i : validLengths)
        {
            new Mastermind(alphabet, i);
        }
    }

    @Test
    public void testGeneratedCodeIsValid()
    {
        String code = mastermind.generateCode();
        assertValidCode(code, alphabet, length, uniqueChars);
    }

    @Test
    public void testGeneratedCodesAreDifferent()
    {
        Set<String> generated = new HashSet<String>();
        for (int i = 0; i < NUM_UNIQUE_CODES; i++)
        {
            String code = mastermind.generateCode();
            assertValidCode(code, alphabet, length, uniqueChars);
            assertFalse(generated.contains(code));
            generated.add(code);
        }
    }

    @Test
    public void testGetAlphabet()
    {
        assertEquals(alphabet, mastermind.getAlphabet());
    }

    @Test
    public void testGetLength()
    {
        assertEquals(length, mastermind.getLength());
    }

    @Test
    public void testHasUniqueChars()
    {
        assertEquals(uniqueChars, mastermind.hasUniqueChars());
    }

    @Test
    public void testGetCode()
    {
        assertEquals(code, mastermind.getCode());
    }

    @Test
    public void testCodes()
    {
        for (int i = 0; i < codes.length; i++)
        {
            String code = codes[i];
            boolean valid = valids[i];
            try
            {
                mastermind.setCode(code);
                if (!valid)
                    fail((code != null) ? code : "null");
            }
            catch (MastermindException e)
            {
            }
        }
    }
    
    @Test
    public void testGetWinningScore()
    {
        assertEquals(new Score(0, length), mastermind.getWinningScore());
    }
    
    @Test
    public void testEvaluateScore()
    {
        mastermind.visitCodes(new EvaluateScoreVisitor(scores));
    }

    class EvaluateScoreVisitor implements CodeVisitor
    {
        private Score[] scores;
        private int count = 0;

        public EvaluateScoreVisitor(Score[] scores)
        {
            this.scores = scores;
        }

        @Override
        public void visit(String code)
        {
            if (count < scores.length)
            {
                Score score = mastermind.evaluateScore(code);
                debug(code + ": " + score);
                assertTrue(score.equals(scores[count]));
            }
            count++;
        }
    }
    
    @Test
    public void testGetAllPossibleScores()
    {
        List<Score> allPossibleScores = mastermind.getAllPossibleScores();
        assertEquals(possibleScores.length, allPossibleScores.size());
        for (int i = 0; i < possibleScores.length; i++)
            assertEquals(possibleScores[i], allPossibleScores.get(i));
    }
    
    @Test
    public void testGetAllPossibleCodes()
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
    public void testEvaluatePossibleCodes()
    {
        SortedSet<String> codes = mastermind.getAllPossibleCodes();
        codes = mastermind.evaluatePossibleCodes(code, Score.ZERO_SCORE, codes);
        if (possibleCodes2.length > 0)
        {
            assertEquals(possibleCodes2.length, codes.size());
            for (int i = 0; i < possibleCodes2.length; i++)
                assertTrue(codes.contains(possibleCodes2[i]));
        }
    }

    public static void assertValidCode(String code, String alphabet, int length, boolean unique)
    {
        assertTrue(code != null && !code.isEmpty());
        assertTrue(code.length() == length);
        String used = "";
        for (int i = 0; i < code.length(); i++)
        {
            String c = code.substring(i, i + 1);
            assertTrue(alphabet.contains(c));
            if (unique)
            {
                assertFalse(used.contains(c));
                used += c;
            }
        }
    }
}
