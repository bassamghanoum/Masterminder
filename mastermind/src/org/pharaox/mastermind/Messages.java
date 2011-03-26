package org.pharaox.mastermind;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages
{
    // AbstractIOPlayer
    public static final String M_C_STARTING_GAME = msg("AbstractIOPlayer.0");
    public static final String M_C_GAME_WON = msg("AbstractIOPlayer.1");
    public static final String M_C_GAME_LOST = msg("AbstractIOPlayer.2");
    public static final String M_C_GUESS = msg("AbstractIOPlayer.3");
    public static final String M_C_COWS = msg("AbstractIOPlayer.4");
    public static final String M_C_BULLS = msg("AbstractIOPlayer.5");

    private static final String BUNDLE_NAME = "org.pharaox.mastermind.messages";

    private static ResourceBundle bundle;

    private Messages()
    {
        // No implementation needed
    }

    public static String msg(final String key)
    {
        String string = "";
        try
        {
            initBundle();
            string = bundle.getString(key);
        }
        catch (final MissingResourceException e)
        {
            string = '!' + key + '!';
        }
        return string;
    }

    private static void initBundle()
    {
        synchronized (new Object())
        {
            if (bundle == null)
            {
                bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            }
        }
    }
}
