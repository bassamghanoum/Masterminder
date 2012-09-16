/*
 * $Id: $
 *
 * Copyright 2012 Stoyan Rachev (stoyanr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stoyanr.mastermind;

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
        assert (reader != null && writer != null);
        this.reader = new BufferedReader(reader);
        this.writer = new PrintWriter(writer, true);
    }

    @Override
    protected final void printLine(final String message, final Object... args)
    {
        assert (message != null);
        writer.printf(message + "\n", args);
        writer.flush();
    }

    @Override
    protected final int readLineInt(final String message)
    {
        assert (message != null);
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
