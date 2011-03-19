package org.pharaox.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class Mastermind
{
    public static final String VALID_ALPHABET_CHARS = "0123456789ABCDEFGH";

    private static final Random RANDOM = new Random();
    private static char MARK = '*';

    private String alphabet;
    private int length;
    private boolean unique;
    private String code = "";

    public Mastermind(String alphabet, int length)
    {
        this(alphabet, length, false);
    }

    public Mastermind(String alphabet, int length, boolean unique)
    {
        if (length <= 0 || !isValidAlphabet(alphabet, length))
            throw new MastermindException();
        this.alphabet = alphabet;
        this.length = length;
        this.unique = unique;
    }

    private static boolean isValidAlphabet(String alphabet, int length)
    {
        return (alphabet != null && alphabet.length() > 1 && alphabet.length() >= length && containsValidChars(
            alphabet, VALID_ALPHABET_CHARS, true));
    }

    private static boolean containsValidChars(String string, String validChars, boolean unique)
    {
        boolean result = true;
        String usedChars = "";
        for (int i = 0; i < string.length(); i++)
        {
            String c = string.substring(i, i + 1);
            if (!validChars.contains(c) || (unique && usedChars.contains(c)))
            {
                result = false;
                break;
            }
            if (unique)
                usedChars += c;
        }
        return result;
    }

    public void setCode(String code)
    {
        if (!isValidCode(code))
            throw new MastermindException();
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public boolean isValidCode(String code)
    {
        return (code != null && code.length() == length && containsValidChars(code, alphabet,
            unique));
    }

    public String generateCode()
    {
        String result = "";
        for (int i = 0; i < length; i++)
        {
            String c;
            do
            {
                int index = RANDOM.nextInt(alphabet.length());
                c = alphabet.substring(index, index + 1);
            }
            while (unique && result.contains(c));
            result += c;
        }
        assert (isValidCode(result));
        return result;
    }

    public Score evaluateScore(String guess)
    {
        return evaluateScore(guess, code);
    }

    public Score evaluateScore(String guess, String code)
    {
        return (unique)? evaluateScoreUnique(guess, code) : evaluateScoreNonUnique(guess, code);
    }
    
    public Score evaluateScoreSafe(String guess)
    {
        if (!isValidCode(guess))
            throw new MastermindException();
        return evaluateScore(guess);
    }

    public Score evaluateScoreSafe(String guess, String code)
    {
        if (!isValidCode(guess) || !isValidCode(code))
            throw new MastermindException();
        return evaluateScore(guess, code);
    }
    
    private Score evaluateScoreUnique(String guess, String code)
    {
        int cows = 0, bulls = 0;
        for (int i = 0; i < guess.length(); i++)
        {
            char c = guess.charAt(i);
            if (code.charAt(i) == c)
                bulls++;
            else if (code.contains(new String(new char[] { c })))
                cows++;
        }
        return new Score(cows, bulls);
    }

    private Score evaluateScoreNonUnique(String guess, String code)
    {
        int cows = 0, bulls = 0;
        char[] gc = guess.toCharArray(), cc = code.toCharArray();
        for (int i = 0; i < length; i++)
        {
            if (cc[i] == gc[i])
            {
                bulls++;
                gc[i] = cc[i] = MARK;
            }
        }
        for (int i = 0; i < length; i++)
        {
            if (gc[i] != MARK)
            {
                for (int j = 0; j < length; j++)
                {
                    if (cc[j] == gc[i])
                    {
                        cows++;
                        cc[j] = MARK;
                        break;
                    }
                }
            }
        }
        return new Score(cows, bulls);
    }
    
    public List<Score> getAllPossibleScores()
    {
        List<Score> result = new ArrayList<Score>();
        for (int bulls = 0; bulls < length + 1; bulls++)
        {
            int maxCows = length - bulls;
            int minCows = (unique)? (length - bulls - (alphabet.length() - length)) : 0;
            minCows = (minCows > 0) ? minCows : 0;
            for (int cows = minCows; cows < maxCows + 1; cows++)
            {
                if ((bulls == length - 1) && (cows == 1))
                    continue;
                result.add(new Score(cows, bulls));
            }
        }
        return result;
    }

    public SortedSet<String> getAllPossibleCodes()
    {
        final SortedSet<String> result = new TreeSet<String>();
        try
        {
            visitCodes(new CodeVisitor()
            {
                @Override
                public void visit(String code)
                {
                    boolean added = result.add(code);
                    assert (added);
                }
            });
        }
        catch (MastermindException e)
        {
            assert (false); // Not possible
        }
        return result;
    }

    public SortedSet<String> evaluatePossibleCodes(String guess, Score score,
        SortedSet<String> codes)
    {
        SortedSet<String> result = new TreeSet<String>();
        for (String code : codes)
        {
            Score evaluatedScore = evaluateScore(guess, code);
            if (evaluatedScore.equals(score))
                result.add(code);
        }
        return result;
    }

    public void visitCodes(CodeVisitor visitor)
    {
        visitCodes(new char[length], 0, visitor);
    }

    private void visitCodes(char[] chars, int index, CodeVisitor visitor)
    {
        for (int i = 0; i < alphabet.length(); i++)
        {
            chars[index] = alphabet.charAt(i);
            if (unique && isDuplicate(chars, index))
                continue;
            if (index == length - 1)
                visitor.visit(new String(chars));
            else
                visitCodes(chars, index + 1, visitor);
        }
    }

    private static boolean isDuplicate(char[] chars, int index)
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

    public Object getWinningScore()
    {
        return new Score(0, length);
    }
}
