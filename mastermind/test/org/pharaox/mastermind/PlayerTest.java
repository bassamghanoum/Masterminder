package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Messages.*;

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
    private static final String NON_NUMERIC_INPUT = "XXX\nYYY\n";

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
        player = new ReaderWriterPlayer(reader, writer);
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
    
    private Score getScoreFromInput(final String input)
    {
        player = new ReaderWriterPlayer(new StringReader(input), writer);
        return player.getScore(GUESS);
    }

}
