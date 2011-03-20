package org.pharaox.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriterPlayer extends AbstractIOPlayer
{
    private BufferedReader reader;
    private PrintWriter writer;

    public ReaderWriterPlayer(final Reader reader, final Writer writer)
    {
        this.reader = new BufferedReader(reader);
        this.writer = new PrintWriter(writer, true);
    }

    @Override
    protected final void printLine(final String message, final Object... args)
    {
        writer.printf(message + "\n", args);
        writer.flush();
    }

    @Override
    protected final int readLineInt(final String message)
    {
        print(message);
        String line = readLine();
        return parseInt(line);
    }

    private void print(final String message)
    {
        writer.print(message);
        writer.flush();
    }

    private String readLine()
    {
        String line;
        try
        {
            line = reader.readLine();
            if (line == null)
                throw new MastermindException();
        }
        catch (IOException e)
        {
            throw new MastermindException();
        }
        return line;
    }

    private int parseInt(final String text)
    {
        int result;
        try
        {
            result = Integer.parseInt(text);
        }
        catch (NumberFormatException e)
        {
            throw new MastermindException();
        }
        return result;
    }
}
