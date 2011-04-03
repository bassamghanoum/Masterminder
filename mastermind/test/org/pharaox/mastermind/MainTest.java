package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.pharaox.mastermind.Messages.*;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class MainTest
{
    private static final String[] TEST_ARGS_1 = new String[] { "-a", "ABCD", "-l", "2", "-r", "4",
        "-s", "knuth", "-p", "3" };

    private static final String T_INTRO = "i";
    private static final String T_COWS = "c";
    private static final String T_BULLS = "b";
    private static final String O_ERROR = "e";
    private static final String O_WON = "w";
    private static final String O_LOST = "l";

    private static final String M_WRONG_OUTPUT = "Wrong output:";

    private static final String DELIM = "-";

    private final transient String[] args;
    private final transient String input;
    private final transient String outputSchema;

    private transient int numRounds;

    public MainTest(final String[] args, final String input, final String outputSchema)
    {
        super();
        this.args = Arrays.copyOf(args, args.length);
        this.input = input;
        this.outputSchema = outputSchema;
    }

    @Parameters
    public static Collection<Object[]> data()
    {
        // @formatter:off, @checkstyle:off
        final Object[][] data = new Object[][]
        {
            { TEST_ARGS_1, "", "i-AB-c-e" },
            { TEST_ARGS_1, "a", "i-AB-c-e" },
            { TEST_ARGS_1, "-1\n0\n", "i-AB-c-b-e" }, // NOPMD AvoidDuplicateLiterals
            { TEST_ARGS_1, "3\n0\n", "i-AB-c-b-e" },
            { TEST_ARGS_1, "1\n1\n", "i-AB-c-b-e" },
            { TEST_ARGS_1, "2\n2\n", "i-AB-c-b-e" },
            { TEST_ARGS_1, "0\n0\n0\n0\n0\n0\n", "i-AB-c-b-CC-c-b-DD-c-b-e" },
            { TEST_ARGS_1, "0\n2\n", "i-AB-c-b-w" },
            { TEST_ARGS_1, "0\n0\n0\n2\n", "i-AB-c-b-CC-c-b-w" },
            { TEST_ARGS_1, "0\n0\n0\n0\n0\n2\n", "i-AB-c-b-CC-c-b-DD-c-b-w" },
/*            
            { new String[] { "-a", "ABCDEF", "-l", "4", "-r", "9", "-s", "simple" }, "a", "i-AAAA-c-e" },
            { new String[] { "-a", "ABCDEF", "-l", "4", "-r", "5", "-s", "knuth" }, "a", "i-AABB-c-e" },
            { new String[] { "-a", "ABCDEF", "-l", "4", "-r", "6", "-s", "exp_size" }, "a", "i-AABC-c-e" },
*/            
        };
        // @formatter:on, @checkstyle:on
        return Arrays.asList(data);
    }

    @Test
    public final void testRun()
    {
        final String output = run();
        assertEquals(M_WRONG_OUTPUT, buildOutput(), output);
    }

    public final String run()
    {
        final Reader reader = new StringReader(input);
        final Writer writer = new StringWriter();
        final Main main = new Main(args, reader, writer);
        main.run();
        return writer.toString();
    }

    public final String buildOutput()
    {
        final List<String> tokens = tokenizeSchema(outputSchema);
        return buildOutput(tokens);
    }

    private List<String> tokenizeSchema(final String schema)
    {
        final List<String> tokens = new ArrayList<String>();
        final StringTokenizer tokenizer = new StringTokenizer(schema, DELIM);
        while (tokenizer.hasMoreTokens())
        {
            final String token = tokenizer.nextToken();
            tokens.add(token);
        }
        return tokens;
    }

    private String buildOutput(final List<String> tokens)
    {
        final StringBuilder builder = new StringBuilder();
        appendGuesses(tokens, builder);
        appendOutcome(tokens, builder);
        return builder.toString();
    }

    private void appendGuesses(final List<String> tokens, final StringBuilder builder)
    {
        numRounds = 0;
        for (int i = 0; i < tokens.size() - 1; i++)
        {
            final String token = tokens.get(i);
            if (token.equals(T_INTRO))
            {
                builder.append(M_C_STARTING_GAME);
                builder.append("\n");
            }
            else if (token.equals(T_COWS))
            {
                builder.append(M_C_COWS);
            }
            else if (token.equals(T_BULLS))
            {
                builder.append(M_C_BULLS);
            }
            else
            {
                builder.append(String.format(M_C_GUESS, token));
                builder.append("\n");
                numRounds++;
            }
        }
    }

    private void appendOutcome(final List<String> tokens, final StringBuilder builder)
    {
        final String outcome = tokens.get(tokens.size() - 1);
        if (outcome.equals(O_ERROR))
        {
            builder.append(MastermindException.class.toString());
        }
        else if (outcome.equals(O_WON))
        {
            builder.append(String.format(M_C_GAME_WON, numRounds));
        }
        else if (outcome.equals(O_LOST))
        {
            builder.append(String.format(M_C_GAME_LOST, numRounds));
        }
        builder.append("\n");
    }
}
