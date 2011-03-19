package org.pharaox.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriterPlayer extends AbstractIOPlayer
{
    BufferedReader reader;
    PrintWriter writer;
    
    public ReaderWriterPlayer(Reader reader, Writer writer)
    {
        this.reader = new BufferedReader(reader);
        this.writer = new PrintWriter(writer, true);
    }

    @Override
    protected void initialize()
    {
    }
    
    @Override
    protected void printLine(String message, Object... args)
    {
        writer.printf(message + "\n", args);
        writer.flush();
    }
    
    @Override
    protected int readLineInt(String message)
    {
        writer.print(message);
        writer.flush();
        String line;
        try
        {
            line = reader.readLine();
        }
        catch (IOException e)
        {
            throw new MastermindException();
        }
        return Integer.parseInt(line);
    }
}
