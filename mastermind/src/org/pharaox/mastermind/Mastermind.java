package org.pharaox.mastermind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Mastermind
{ // NOPMD TooManyMethods
    public static final int MAX_LENGTH = 9;
    public static final String VALID_ALPHA_CHARS = "0123456789ABCDEFGH";

    private static final Random RANDOM = new Random();
    private static final char MARK = '*';

    private transient String alphabet;
    private transient int length;
    private transient boolean uniqueChars;

    public Mastermind(final String alphabet, final int length)
    {
        this(alphabet, length, false);
    }

    public Mastermind(final String alphabet, final int length, final boolean uniqueChars)
    {
        if (!isValidLength(length) || !isValidAlphabet(alphabet, length))
        {
            throw new MastermindException();
        }
        this.alphabet = alphabet;
        this.length = length;
        this.uniqueChars = uniqueChars;
    }

    private static boolean isValidLength(final int length)
    {
        return ((length > 0) && (length <= MAX_LENGTH));
    }

    private static boolean isValidAlphabet(final String alphabet, final int length)
    {
        // @formatter:off
        return ((alphabet != null) 
            && (alphabet.length() > 1) && (alphabet.length() >= length) 
            && containsValidChars(alphabet, VALID_ALPHA_CHARS, true));
        // @formatter:on
    }

    private static boolean containsValidChars(final String string, final String validChars,
        final boolean uniqueChars)
    {
        boolean result = true;
        final Set<Character> usedChars = new HashSet<Character>();
        for (int i = 0; i < string.length(); i++)
        {
            final char chi = string.charAt(i);
            if (!stringContainsChar(validChars, chi)
                || (uniqueChars && usedChars.contains(chi)))
            {
                result = false;
                break;
            }
            if (uniqueChars)
            {
                usedChars.add(chi);
            }
        }
        return result;
    }
    
    private static boolean stringContainsChar(final String string, final char chx)
    {
        return string.contains(new String(new char[] { chx }));
    }

    public final String getAlphabet()
    {
        return alphabet;
    }

    public final int getLength()
    {
        return length;
    }

    public final boolean hasUniqueChars()
    {
        return uniqueChars;
    }

    public final boolean isValidCode(final String code)
    {
        return ((code != null) && (code.length() == length) && containsValidChars(code, alphabet,
            uniqueChars));
    }

    public final String generateCode()
    {
        final StringBuilder builder = new StringBuilder();
        final Set<Character> usedChars = new HashSet<Character>();
        for (int i = 0; i < length; i++)
        {
            char chi;
            do
            {
                final int index = RANDOM.nextInt(alphabet.length());
                chi = alphabet.charAt(index);
            }
            while (uniqueChars && usedChars.contains(chi));
            if (uniqueChars)
            {
                usedChars.add(chi);
            }
            builder.append(chi);
        }
        final String result = builder.toString();
        assert isValidCode(result);
        return result;
    }

    public final Score getWinningScore()
    {
        return new Score(0, length);
    }

    public final Score evaluateScore(final String guess, final String code)
    {
        return evaluateScore(guess, code, true);
    }

    public final Score evaluateScore(final String guess, final String code, final boolean safe)
    {
        if (safe && (!isValidCode(guess) || !isValidCode(code)))
        {
            throw new MastermindException();
        }
        Score result;
        if (uniqueChars)
        {
            result = evaluateScoreUniqueChars(guess, code);
        }
        else
        {
            result = evaluateScoreNonUniqueChars(guess, code);
        }
        return result;
    }

    private Score evaluateScoreUniqueChars(final String guess, final String code)
    {
        assert (isValidCode(guess) && isValidCode(code));
        int cows = 0, bulls = 0;
        for (int i = 0; i < guess.length(); i++)
        {
            final char chi = guess.charAt(i);
            if (code.charAt(i) == chi)
            {
                bulls++;
            }
            else if (stringContainsChar(code, chi))
            {
                cows++;
            }
        }
        return new Score(cows, bulls);
    }

    private Score evaluateScoreNonUniqueChars(final String guess, final String code)
    {
        assert (isValidCode(guess) && isValidCode(code));
        int cows = 0, bulls = 0;
        final char[] guessChars = guess.toCharArray(), codeChars = code.toCharArray();
        for (int i = 0; i < length; i++)
        {
            if (codeChars[i] == guessChars[i])
            {
                bulls++;
                guessChars[i] = MARK;
                codeChars[i] = MARK;
            }
        }
        for (int i = 0; i < length; i++)
        {
            if (guessChars[i] != MARK)
            {
                for (int j = 0; j < length; j++)
                {
                    if (codeChars[j] == guessChars[i])
                    {
                        cows++;
                        codeChars[j] = MARK;
                        break;
                    }
                }
            }
        }
        return new Score(cows, bulls);
    }

    public final List<Score> getAllPossibleScores()
    {
        final List<Score> result = new ArrayList<Score>();
        for (int bulls = 0; bulls < length + 1; bulls++)
        {
            final int maxCows = length - bulls;
            int minCows = 0;
            if (uniqueChars)
            {
                minCows = Math.max((length - bulls - (alphabet.length() - length)), 0);
            }
            for (int cows = minCows; cows < maxCows + 1; cows++)
            {
                if ((bulls == length - 1) && (cows == 1))
                {
                    continue;
                }
                result.add(new Score(cows, bulls)); // NOPMD AvoidInstantiatingObjectsInLoops
            }
        }
        return result;
    }

    public final SortedSet<String> getAllPossibleCodes()
    {
        final SortedSet<String> result = new TreeSet<String>();
        visitCodes(new CodeVisitor()
        {
            @Override
            public void visit(final String code)
            {
                final boolean added = result.add(code);
                assert added;
            }
        });
        return result;
    }

    public final SortedSet<String> evaluatePossibleCodes(final String guess, final Score score,
        final SortedSet<String> codes)
    {
        return evaluatePossibleCodes(guess, score, codes, true);
    }

    public final SortedSet<String> evaluatePossibleCodes(final String guess, final Score score,
        final SortedSet<String> codes, final boolean safe)
    {
        final SortedSet<String> result = new TreeSet<String>();
        for (final String code : codes)
        {
            final Score evaluatedScore = evaluateScore(guess, code, safe);
            if (evaluatedScore.equals(score))
            {
                result.add(code);
            }
        }
        return result;
    }

    public final void visitCodes(final CodeVisitor visitor)
    {
        visitCodes(new char[length], 0, visitor);
    }

    private void visitCodes(final char[] chars, final int index, final CodeVisitor visitor)
    {
        for (int i = 0; i < alphabet.length(); i++)
        {
            chars[index] = alphabet.charAt(i);
            if (uniqueChars && isDuplicate(chars, index))
            {
                continue;
            }
            if (index == length - 1)
            {
                visitor.visit(new String(chars)); // NOPMD AvoidInstantiatingObjectsInLoops
            }
            else
            {
                visitCodes(chars, index + 1, visitor);
            }
        }
    }

    private static boolean isDuplicate(final char[] chars, final int index)
    {
        boolean duplicate = false;
        for (int i = 0; i < index; i++)
        {
            if (chars[index] == chars[i])
            {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

}
