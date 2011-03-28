package org.pharaox.mastermind;

import fit.ColumnFixture;

public class MainFixture extends ColumnFixture
{
    // @checkstyle:off
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
        final MainTest test = new MainTest(preparedInput, outputSchema);
        output = test.run();
        expectedOutput = test.buildOutput();
        return true;
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
