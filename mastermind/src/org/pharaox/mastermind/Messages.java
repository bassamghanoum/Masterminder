package org.pharaox.mastermind;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
    // AbstractIOPlayer
    public static final String MSG_AIOP_STARTING_GAME = Messages.getString("AbstractIOPlayer.0");  //$NON-NLS-1$
    public static final String MSG_AIOP_GAME_WON_IN_X_ROUNDS = Messages.getString("AbstractIOPlayer.1");  //$NON-NLS-1$
    public static final String MSG_AIOP_GAME_LOST_IN_X_ROUNDS = Messages.getString("AbstractIOPlayer.2");  //$NON-NLS-1$
    public static final String MSG_AIOP_GUESS = Messages.getString("AbstractIOPlayer.3");  //$NON-NLS-1$
    public static final String MSG_AIOP_COWS = Messages.getString("AbstractIOPlayer.4");  //$NON-NLS-1$
    public static final String MSG_AIOP_BULLS = Messages.getString("AbstractIOPlayer.5");  //$NON-NLS-1$

    private static final String BUNDLE_NAME = "org.pharaox.mastermind.messages"; //$NON-NLS-1$

    private static ResourceBundle bundle;
    
    private Messages()
    {
    }

    public static String getString(String key)
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
