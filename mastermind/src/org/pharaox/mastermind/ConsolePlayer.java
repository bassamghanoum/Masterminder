package org.pharaox.mastermind;

public class ConsolePlayer extends AbstractIOPlayer
{
    @Override
    protected void initialize()
    {
        if (System.console() == null) 
        {
            System.err.println("No console.");
            System.exit(1);
        }
    }

    @Override
    protected void printLine(String message, Object... args)
    {
        System.console().printf(message + "\n", args);
    }

    @Override
    protected int readLineInt(String message)
    {
        String line = System.console().readLine(message);
        return Integer.parseInt(line);
    }

}
