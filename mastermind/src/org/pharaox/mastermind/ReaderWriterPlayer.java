package org.pharaox.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriterPlayer extends AbstractIOPlayer
{
    private final transient BufferedReader reader;
    private final transient PrintWriter writer;

    public ReaderWriterPlayer(final Mastermind mastermind, final Reader reader, final Writer writer)
    {
        super(mastermind);
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
        final String line = readLine();
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
            {
                throw new MastermindException();
            }
        }
        catch (final IOException e)
        {
            throw new MastermindException(e);
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
        catch (final NumberFormatException e)
        {
            throw new MastermindException(e);
        }
        return result;
    }
}
