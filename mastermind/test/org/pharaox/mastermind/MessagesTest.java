package org.pharaox.mastermind;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Messages.*;

public class MessagesTest
{
    private static final int KEY = 10;
    
    private static final String M_WRONG_MESSAGE = "Wrong message:";

    @Test
    public final void testMsg()
    {
        final String expected = "!" + PACKAGE + "." + Integer.toString(KEY) + "!";
        assertEquals(M_WRONG_MESSAGE, expected, msg(KEY));
    }

}
