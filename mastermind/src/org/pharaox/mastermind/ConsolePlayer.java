package org.pharaox.mastermind;

import java.io.Console;

public class ConsolePlayer extends AbstractIOPlayer
{
    private Console console;

    public ConsolePlayer(final Console console)
    {
        if (console == null)
            throw new MastermindException();
        this.console = console;
    }

    @Override
    protected final void printLine(final String message, final Object... args)
    {
        console.printf(message + "\n", args);
    }

    @Override
    protected final int readLineInt(final String message)
    {
        String line = console.readLine(message);
        return Integer.parseInt(line);
    }

}
