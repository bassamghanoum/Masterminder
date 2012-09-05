package com.stoyanr.mastermind;

import org.junit.Test;

import static com.stoyanr.mastermind.Messages.*;
import static org.junit.Assert.assertEquals;

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
