package org.pharaox.mastermind;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages
{
    // AbstractIOPlayer
    public static final String MSG_AIOP_STARTING_GAME = s("AbstractIOPlayer.0");
    public static final String MSG_AIOP_GAME_WON_IN_X_ROUNDS = s("AbstractIOPlayer.1");
    public static final String MSG_AIOP_GAME_LOST_IN_X_ROUNDS = s("AbstractIOPlayer.2");
    public static final String MSG_AIOP_GUESS = s("AbstractIOPlayer.3");
    public static final String MSG_AIOP_COWS = s("AbstractIOPlayer.4");
    public static final String MSG_AIOP_BULLS = s("AbstractIOPlayer.5");

    private static final String BUNDLE_NAME = "org.pharaox.mastermind.messages";

    private static ResourceBundle bundle;

    private Messages()
    {
    }

    public static String s(final String key)
    {
        return Messages.getString(key);
    }

    public static String getString(final String key)
    {
        try
        {
            initBundle();
            return bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }

    private static void initBundle()
    {
        if (bundle == null)
            bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }
}
