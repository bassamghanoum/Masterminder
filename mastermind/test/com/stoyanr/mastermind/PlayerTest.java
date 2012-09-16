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

import static com.stoyanr.mastermind.Constants.*;
import static com.stoyanr.mastermind.Messages.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest
{
    private static final int ROUNDS_PLAYED = 5;
    private static final String GUESS = "AABB";
    private static final int COWS = 1;
    private static final int BULLS = 0;
    private static final String INPUT = 
        Integer.toString(COWS) + "\n" + Integer.toString(BULLS) + "\n";
    private static final String INCOMPLETE_INPUT = Integer.toString(COWS) + "\n";
    private static final String NON_NUMERIC_INPUT = "X\n0\n";
    private static final String NEGATIVE_NUMBERS_INPUT = "-1\n0\n";
    private static final String INVALID_INPUT = "1\n3\n";

    private static final String M_WRONG_CONSOLE_MSG = "Wrong console message:";
    private static final String M_WRONG_SCORE = "Wrong score:";
    
    private transient StringReader reader;
    private transient StringWriter writer;
    private transient Player player;

    @Before
    public final void setUp()
    {
        reader = new StringReader(INPUT);
        writer = new StringWriter();
        player = new ReaderWriterPlayer(MM1, reader, writer);
    }

    @After
    public final void tearDown() throws IOException
    {
        reader.close();
        writer.close();
    }

    @Test
    public final void testStartGame()
    {
        player.startGame();
        assertEquals(M_WRONG_CONSOLE_MSG, M_C_STARTING_GAME + "\n", writer.toString());
    }

    @Test
    public final void testEndGameWon()
    {
        player.endGame(true, ROUNDS_PLAYED);
        final String expected = String.format(M_C_GAME_WON, ROUNDS_PLAYED) + "\n";
        assertEquals(M_WRONG_CONSOLE_MSG, expected, writer.toString());
    }

    @Test
    public final void testEndGameLost()
    {
        player.endGame(false, ROUNDS_PLAYED);
        final String expected = String.format(M_C_GAME_LOST, ROUNDS_PLAYED) + "\n";
        assertEquals(M_WRONG_CONSOLE_MSG, expected, writer.toString());
    }

    @Test
    public final void testGetScore()
    {
        final Score score = player.getScore(GUESS);
        assertEquals(M_WRONG_SCORE, new Score(COWS, BULLS), score);
        final String expected = String.format(M_C_GUESS, GUESS) + "\n" + M_C_COWS + M_C_BULLS;
        assertEquals(M_WRONG_CONSOLE_MSG, expected, writer.toString());
    }

    @Test(expected = MastermindException.class)
    public final void testGetScoreInputClosed()
    {
        reader.close();
        player.getScore(GUESS);
    }

    @Test(expected = MastermindException.class)
    public final void testGetScoreEmptyInput()
    {
        getScoreFromInput("");
    }

    @Test(expected = MastermindException.class)
    public final void testGetScoreIncompleteInput()
    {
        getScoreFromInput(INCOMPLETE_INPUT);
    }
    
    @Test(expected = MastermindException.class)
    public final void testGetScoreNonNumericInput()
    {
        getScoreFromInput(NON_NUMERIC_INPUT);
    }
    
    @Test(expected = MastermindException.class)
    public final void testGetScoreNegativeNumbersInput()
    {
        getScoreFromInput(NEGATIVE_NUMBERS_INPUT);
    }
    
    @Test(expected = MastermindException.class)
    public final void testGetScoreInvalidInput()
    {
        getScoreFromInput(INVALID_INPUT);
    }
    
    private Score getScoreFromInput(final String input)
    {
        player = new ReaderWriterPlayer(MM1, new StringReader(input), writer);
        return player.getScore(GUESS);
    }

}
