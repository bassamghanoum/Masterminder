package org.pharaox.mastermind;

public class Logger
{
    enum Level
    {
        ERROR, WARNING, INFO, DEBUG
    }
    
    public static Level LEVEL = Level.DEBUG; 

    public static void error(String message)
    {
        if (LEVEL.ordinal() >= Level.ERROR.ordinal())
            log(message);
    }
    
    public static void warning(String message)
    {
        if (LEVEL.ordinal() >= Level.WARNING.ordinal())
            log(message);
    }
    
    public static void info(String message)
    {
        if (LEVEL.ordinal() >= Level.INFO.ordinal())
            log(message);
    }
    
    public static void debug(String message)
    {
        if (LEVEL.ordinal() >= Level.DEBUG.ordinal())
            log(message);
    }
    
    private static void log(String message)
    {
        System.out.println(message);
    }
}
