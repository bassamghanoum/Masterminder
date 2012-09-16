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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fit.ColumnFixture;

public class MainFixture extends ColumnFixture
{
    // @checkstyle:off
    public transient String argsString;
    public transient String input;
    public transient String outputSchema;
    // @checkstyle:on
    
    private transient String output;
    private transient String expectedOutput;

    public final String getInput()
    {
        return input;
    }
    
    public final boolean run()
    {
        final String preparedInput = input.replace('#', '\n');
        final String[] arguments = getArguments();
        final MainTest test = new MainTest(arguments, preparedInput, outputSchema);
        output = test.run();
        expectedOutput = test.buildOutput();
        return true;
    }

    private String[] getArguments()
    {
        String[] result = new String[] {};
        if (argsString != null && !argsString.isEmpty())
        {
            result = parseArgsString(argsString);
        }
        return result;
    }
    
    private String[] parseArgsString(final String string)
    {
        final List<String> tokens = new ArrayList<String>();
        final StringTokenizer tokenizer = new StringTokenizer(string, " ");
        while (tokenizer.hasMoreTokens())
        {
            final String token = tokenizer.nextToken();
            tokens.add(token);
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    public final String getOutput()
    {
        return output;
    }
    
    public final boolean outputConformsToSchema()
    {
        return output.equals(expectedOutput);
    }

}
