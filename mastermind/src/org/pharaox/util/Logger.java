package org.pharaox.util;

public final class Logger
{
    enum Level
    {
        ERROR, WARNING, INFO, DEBUG
    }

    private Logger()
    {
    }

    private static Level level = Level.ERROR;

    public static void error(final String message)
    {
        if (level.ordinal() >= Level.ERROR.ordinal())
            log(message);
    }

    public static void warning(final String message)
    {
        if (level.ordinal() >= Level.WARNING.ordinal())
            log(message);
    }

    public static void info(final String message)
    {
        if (level.ordinal() >= Level.INFO.ordinal())
            log(message);
    }

    public static void debug(final String message)
    {
        if (level.ordinal() >= Level.DEBUG.ordinal())
            log(message);
    }

    private static void log(final String message)
    {
        System.out.println(message);
    }
}
