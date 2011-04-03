package org.pharaox.mastermind;

import static org.pharaox.util.Messages.*;

public final class Messages
{
    public static final String PACKAGE = "org.pharaox.mastermind";
    
    private static final String BUNDLE_NAME = PACKAGE + ".messages";

    // @formatter:off
    public static final String M_C_STARTING_GAME        = msg(0);
    public static final String M_C_GAME_WON             = msg(1);
    public static final String M_C_GAME_LOST            = msg(2);
    public static final String M_C_GUESS                = msg(3);
    public static final String M_C_COWS                 = msg(4);
    public static final String M_C_BULLS                = msg(5);
    // @formatter:on

    private Messages()
    {
        // No implementation needed
    }

    public static String msg(final int key)
    {
        assert (key >= 0);
        return getMessage(BUNDLE_NAME, PACKAGE + "." + key);
    }
}
