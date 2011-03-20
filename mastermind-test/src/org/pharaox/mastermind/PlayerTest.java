package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
    private static final String INPUT = "" + COWS + "\n" + BULLS + "\n";
    private static final String[] INVALID_INPUT = { "", "" + COWS + "\n", "XXX\nYYY\n" };

    private StringReader reader;
    private StringWriter writer;
    private Player player;

    public PlayerTest()
    {
    }

    @Before
    public final void setup()
    {
        reader = new StringReader(INPUT);
        writer = new StringWriter();
        player = new ReaderWriterPlayer(reader, writer);
    }

    @After
    public final void teardown() throws IOException
    {
        reader.close();
        writer.close();
    }

    @Test
    public final void testStartGame()
    {
        player.startGame();
        assertEquals(MSG_AIOP_STARTING_GAME + "\n", writer.toString());
    }

    @Test
    public final void testEndGameWon()
    {
        player.endGame(true, ROUNDS_PLAYED);
        String expected = String.format(MSG_AIOP_GAME_WON_IN_X_ROUNDS, ROUNDS_PLAYED) + "\n";
        assertEquals(expected, writer.toString());
    }

    @Test
    public final void testEndGameLost()
    {
        player.endGame(false, ROUNDS_PLAYED);
        String expected = String.format(MSG_AIOP_GAME_LOST_IN_X_ROUNDS, ROUNDS_PLAYED) + "\n";
        assertEquals(expected, writer.toString());
    }

    @Test
    public final void testGetScore()
    {
        Score score = player.getScore(GUESS);
        assertEquals(new Score(COWS, BULLS), score);
        // @formatter:off
        String expected = String.format(MSG_AIOP_GUESS, GUESS) + "\n" 
            + MSG_AIOP_COWS + MSG_AIOP_BULLS;
        // @formatter:on
        assertEquals(expected, writer.toString());
    }

    @Test(expected = MastermindException.class)
    public final void testGetScoreInputClosed()
    {
        reader.close();
        player.getScore(GUESS);
    }

    @Test
    public final void testGetScoreInvalidInput()
    {
        for (int i = 0; i < INVALID_INPUT.length; i++)
        {
            player = new ReaderWriterPlayer(new StringReader(INVALID_INPUT[i]), writer);
            try
            {
                player.getScore(GUESS);
                fail();
            }
            catch (MastermindException e)
            // @checkstyle:off
            {
            }
            // @checkstyle:on
        }
    }
    
}
