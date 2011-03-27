package org.pharaox.mastermind;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Messages.*;

public class MessagesTest
{
    private static final String M_WRONG_MESSAGE = "Wrong message:";

    @Test
    public final void testMsg()
    {
        final int key = -1;
        final String expected = "!" + PACKAGE + "." + Integer.toString(key) + "!";
        assertEquals(M_WRONG_MESSAGE, expected, msg(key));
    }

}
