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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Mastermind
{
    public static final int MAX_LENGTH = 9;
    public static final String VALID_ALPHABET_CHARS = "0123456789ABCDEFGH";

    private static final Random RANDOM = new Random();
    private static final char MARK = '*';

    private final transient String alphabet;
    private final transient int length;
    private final transient boolean uniqueChars;
    
    private final transient List<Score> allPossibleScores;
    private final transient SortedSet<String> allPossibleCodes;

    public Mastermind(final String alphabet, final int length, final boolean uniqueChars)
    {
        if (!isValidLength(length) || !isValidAlphabet(alphabet, length))
        {
            throw new MastermindException();
        }
        this.alphabet = alphabet;
        this.length = length;
        this.uniqueChars = uniqueChars;
        this.allPossibleScores = calcAllPossibleScores();
        this.allPossibleCodes = calcAllPossibleCodes();
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
            && containsValidChars(alphabet, VALID_ALPHABET_CHARS, true));
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
            if (validChars.indexOf(chi) == -1 || (uniqueChars && usedChars.contains(chi)))
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
    
    private List<Score> calcAllPossibleScores()
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
                result.add(new Score(cows, bulls));
            }
        }
        return result;
    }

    private SortedSet<String> calcAllPossibleCodes()
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

    public final List<Score> getAllPossibleScores()
    {
        return allPossibleScores;
    }

    public final SortedSet<String> getAllPossibleCodes()
    {
        return allPossibleCodes;
    }

    public final boolean isValidCode(final String code)
    {
        return ((code != null) && (code.length() == length) && containsValidChars(code, alphabet,
            uniqueChars));
    }

    public final boolean isValidScore(final Score score)
    {
        return ((score != null) && allPossibleScores.contains(score));
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

    public final Score evaluateScoreSafe(final String guess, final String code)
    {
        if ((!isValidCode(guess) || !isValidCode(code)))
        {
            throw new MastermindException();
        }
        return evaluateScore(guess, code);
    }

    public final Score evaluateScore(final String guess, final String code)
    {
        assert (isValidCode(guess) && isValidCode(code));
        Score result;
        if (uniqueChars)
        {
            result = evaluateScoreUniqueChars(guess, code);
        }
        else
        {
            result = evaluateScoreNonUniqueChars(guess, code);
        }
        assert isValidScore(result);
        return result;
    }

    private Score evaluateScoreUniqueChars(final String guess, final String code)
    {
        int cows = 0, bulls = 0;
        for (int i = 0; i < guess.length(); i++)
        {
            final char chi = guess.charAt(i);
            if (code.charAt(i) == chi)
            {
                bulls++;
            }
            else if (code.indexOf(chi) != -1)
            {
                cows++;
            }
        }
        return new Score(cows, bulls);
    }

    private Score evaluateScoreNonUniqueChars(final String guess, final String code)
    {
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

    public final SortedSet<String> evaluatePossibleCodes(final String guess, final Score score,
        final SortedSet<String> codes)
    {
        assert (isValidCode(guess) && isValidScore(score));
        final SortedSet<String> result = new TreeSet<String>();
        for (final String code : codes)
        {
            final Score evaluatedScore = evaluateScore(guess, code);
            if (evaluatedScore.equals(score))
            {
                result.add(code);
            }
        }
        return result;
    }

    public final void visitCodes(final CodeVisitor visitor)
    {
        assert (visitor != null);
        visitCodes(new char[length], 0, visitor);
    }

    private void visitCodes(final char[] chars, final int index, final CodeVisitor visitor)
    {
        for (int i = 0; i < alphabet.length(); i++)
        {
            chars[index] = alphabet.charAt(i);
            if (uniqueChars && isDuplicateCharAtIndex(chars, index))
            {
                continue;
            }
            if (index == length - 1)
            {
                visitor.visit(new String(chars));
            }
            else
            {
                visitCodes(chars, index + 1, visitor);
            }
        }
    }

    private static boolean isDuplicateCharAtIndex(final char[] chars, final int index)
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
