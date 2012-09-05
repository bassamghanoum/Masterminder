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
