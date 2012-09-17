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

/**
 * Represents a particular game setup. The game setup is generalized to cover "Mastermind",
 * "Bulls and Cows", and all their variants one can imagine by introducing the following game
 * parameters:
 * <ul>
 * <li>The alphabet of possible code characters.</li>
 * <li>The length of the combination.</li>
 * <li>Whether the characters of the combination must be unique or not (uniqueChars).</li>
 * </ul>
 * 
 * <p>
 * For "Mastermind" and "Bulls and Cows" these parameters have the following values:
 * <table>
 * <tr>
 * <th>Game</th>
 * <th>alphabet</th>
 * <th>length</th>
 * <th>uniqueChars</th>
 * </tr>
 * <tr>
 * <td>Mastermind</td>
 * <td>ABCDEF</td>
 * <td>4</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>Bulls and Cows</td>
 * <td>0123456789</td>
 * <td>4</td>
 * <td>true</td>
 * </tr>
 * </table>
 * 
 * <p>
 * These three parameters are initialized upon construction and used to calculate other parameters
 * of the game setup, such as all valid codes and scores (or "answers"). This class also provides
 * methods for determining code and score validity, for evaluating how a given guess scores against
 * a given code, for visiting all valid codes, and others.
 * 
 * @author Stoyan Rachev
 */
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

    /**
     * Creates a new Mastermind game setup with the specified alphabet, length, and character
     * uniqueness.
     * 
     * @param alphabet The alphabet of possible code characters, should contain unique characters
     * from the set "0123456789ABCDEFGH". The length of the alphabet should be greater or equal to
     * the <code>length</code> parameter.
     * @param length The combination length, an integer number in the range [1, 9].
     * @param uniqueChars Whether the characters of the combination must be unique or not.
     */
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

    /**
     * Returns a list containing all possible scores (or answers) for the current game setup. This
     * list has been previously evaluated upon construction. For example, for the setup [AB, 2,
     * false], the list of possible scores is [(2,0), (0,2)]
     * 
     * @return All possible scores for the current game setup.
     */
    public final List<Score> getAllPossibleScores()
    {
        return allPossibleScores;
    }

    /**
     * Returns a set containing all possible (valid) codes for the current game setup. This set has
     * been previously evaluated upon construction. For example, for the setup [AB, 2, false], the
     * set of possible codes is [AB, BA]
     * 
     * @return All possible (valid) codes for the current game setup, sorted alphabetically.
     */
    public final SortedSet<String> getAllPossibleCodes()
    {
        return allPossibleCodes;
    }

    /**
     * Determines if the passed code is valid for the current game setup. A code is considered valid
     * if it is not null, is of the correct length, and contains only characters from the alphabet.
     * The character uniqueness is also taken into account. For example, for the setup [AB, 2,
     * false], the only valid codes are AB and BA.
     * 
     * @return true if the passed code is valid, false otherwise.
     */
    public final boolean isValidCode(final String code)
    {
        return ((code != null) && (code.length() == length) && containsValidChars(code, alphabet,
            uniqueChars));
    }

    /**
     * Determines if the passed score is valid for the current game setup. A score is considered
     * valid if it can be found in the previously evaluated set of all possible scores. For example,
     * for the setup [AB, 2, false], the only valid scores are (2,0) and (0,2).
     * 
     * @return true if the passed score is valid, false otherwise.
     */
    public final boolean isValidScore(final Score score)
    {
        return ((score != null) && allPossibleScores.contains(score));
    }

    /**
     * Generates a random valid code for the current game setup.
     * 
     * @return A random code for the current game setup.
     */
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

    /**
     * Returns the winning score for the current game setup, that is a score with 0 cows and
     * <code>length</code> bulls. For example, for the setup [AB, 2, false], the winning score is
     * (0,2).
     * 
     * @return The winning score for the current game setup.
     */
    public final Score getWinningScore()
    {
        return new Score(0, length);
    }

    /**
     * Evaluates how the passed guess scores against the passed code in the current game setup.
     * Throws an exception if either the guess or the code are not valid codes for the current game
     * setup. For example, for the setup [AB, 2, false], BA scores against AB as (2,0), while AB
     * scores against AB as (0,2).
     * 
     * @param guess The guess to be checked.
     * @param code The code against which the guess should be checked.
     * @return The score of the guess against the code.
     * @throws MastermindException If either the guess or the code are not valid codes for the
     * current game setup.
     * @see #evaluateScore(String, String)
     */
    public final Score evaluateScoreSafe(final String guess, final String code)
    {
        if ((!isValidCode(guess) || !isValidCode(code)))
        {
            throw new MastermindException();
        }
        return evaluateScore(guess, code);
    }

    /**
     * Evaluates how the passed guess scores against the passed code in the current game setup.
     * Assumes that both the guess and the code are valid codes for the current game setup. For
     * example, for the setup [AB, 2, false], BA scores against AB as (2,0), while AB scores against
     * AB as (0,2).
     * 
     * @param guess The guess to be checked.
     * @param code The code against which the guess should be checked.
     * @return The score of the guess against the code.
     * @see #evaluateScoreSafe(String, String)
     */
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

    /**
     * Determines all codes from the passed codes against which the passed guess evaluates as the
     * passed score. Assumes that both the guess and the score are valid for the current game setup.
     * For example, for the setup [AB, 2, false], for the guess BA, the score (2,0) and the set of
     * all possible codes, the returned set will contain only AB.
     * 
     * @param guess The guess to be checked.
     * @param score The score that we are aiming at.
     * @param codes The codes against which the guess should be checked.
     * @return All codes which satisfy the above conditions, sorted alphabetically.
     */
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

    /**
     * Visits all valid codes for the current game setup. These codes are passed to the
     * <code>visit()</code> method of the passed visitor.
     * 
     * @param visitor The visitor.
     */
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
