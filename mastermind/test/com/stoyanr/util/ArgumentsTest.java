package com.stoyanr.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ArgumentsTest
{
    private static final String[] EMPTY_ARR = new String[] {};

    private static final String DEFAULT_MSG = "Test failed:";
    
    private final transient String schema;
    private final transient String[] args;
    private final transient boolean creatable;
    private final transient String[] booleanArgs;
    private final transient String[] stringArgs;
    private final transient String[] intArgs;

    private transient Arguments arguments;

    public ArgumentsTest(final String schema, final String[] args, final boolean creatable, 
        final String[] booleanArgs, final String[] stringArgs, final String[] intArgs)
    {
        this.schema = schema;
        this.args = Arrays.copyOf(args, args.length);
        this.creatable = creatable;
        this.booleanArgs = Arrays.copyOf(booleanArgs, booleanArgs.length);
        this.stringArgs = Arrays.copyOf(stringArgs, stringArgs.length);
        this.intArgs = Arrays.copyOf(intArgs, intArgs.length);
    }

    // @formatter:off, @checkstyle:off
    @Parameters
    public static Collection<Object[]> data()
    {
        final Object[][] data = new Object[][]
        {
            { "a!", new String[] { "-a" }, true, new String[] { "a:true" }, EMPTY_ARR, EMPTY_ARR }, // NOPMD
            { "a!", EMPTY_ARR, true, new String[] { "!a" }, EMPTY_ARR, EMPTY_ARR },
            { "a!", new String[] { "a" } , false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "a!", new String[] { "-b" } , false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "a!", new String[] { "-a" } , true, new String[] { "c:%" }, EMPTY_ARR, EMPTY_ARR },
            { "a!", new String[] { "-a" } , true, EMPTY_ARR, new String[] { "a:%" }, EMPTY_ARR },
            { "a!,b!", new String[] { "-a", "-b" }, true, new String[] { "a:true", "b:true" }, EMPTY_ARR, EMPTY_ARR },
            { "a!,b!", new String[] { "-a" }, true, new String[] { "a:true", "!b" }, EMPTY_ARR, EMPTY_ARR },
            { "a!,b!", EMPTY_ARR, true, new String[] { "!a", "!b" }, EMPTY_ARR, EMPTY_ARR },
            
            { "s*", new String[] { "-s", "foo" }, true, EMPTY_ARR, new String[] { "s:foo" }, EMPTY_ARR }, // NOPMD
            { "s*", EMPTY_ARR, true, EMPTY_ARR, new String[] { "!s" }, EMPTY_ARR },
            { "s*", new String[] { "s" } , false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "s*", new String[] { "-t" } , false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "s*", new String[] { "-s" } , false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "s*", new String[] { "-s", "foo" } , true, EMPTY_ARR, new String[] { "t:%" }, EMPTY_ARR },
            { "s*", new String[] { "-s", "foo" } , true, new String[] { "s:%" }, EMPTY_ARR, EMPTY_ARR },
            { "s*,t*", new String[] { "-s", "foo", "-t", "bar" }, true, EMPTY_ARR, new String[] { "s:foo", "t:bar" }, EMPTY_ARR },
            { "s*,t*", new String[] { "-s", "foo" }, true, EMPTY_ARR, new String[] { "s:foo", "!t" }, EMPTY_ARR },
            { "s*,t*", EMPTY_ARR, true, EMPTY_ARR, new String[] { "!s", "!t" }, EMPTY_ARR },
            
            { "i#", new String[] { "-i", "10" }, true, EMPTY_ARR, EMPTY_ARR, new String[] { "i:10" } }, // NOPMD
            { "i#", EMPTY_ARR, true, EMPTY_ARR, EMPTY_ARR, new String[] { "!i" } },
            { "i#", new String[] { "i" }, false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "i#", new String[] { "-k" }, false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "i#", new String[] { "-i" }, false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "i#", new String[] { "-i", "foo" }, false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "i#", new String[] { "-i", Integer.toString(Integer.MAX_VALUE) + "0" }, false, EMPTY_ARR, EMPTY_ARR, EMPTY_ARR },
            { "i#", new String[] { "-i", "10" }, true, EMPTY_ARR, EMPTY_ARR, new String[] { "k:%" } },
            { "i#", new String[] { "-i", "10" }, true, EMPTY_ARR, new String[] { "i:%" }, EMPTY_ARR },
            { "i#,k#", new String[] { "-i", "10", "-k", "20" }, true, EMPTY_ARR, EMPTY_ARR, new String[] { "i:10", "k:20" } },
            { "i#,k#", new String[] { "-i", "10" }, true, EMPTY_ARR, EMPTY_ARR, new String[] { "i:10", "!k" } },
            { "i#,k#", EMPTY_ARR, true, EMPTY_ARR, EMPTY_ARR, new String[] { "!i", "!k" } },
            
            { "a!,s*,i#", new String[] { "-a", "-s", "foo", "-i", "10" }, true, new String[] { "a:true" }, new String[] { "s:foo" }, new String[] { "i:10" } },
            { "a!,s*,i#", new String[] { "-a", "-i", "-15" }, true, new String[] { "a:true" }, new String[] { "!s" }, new String[] { "i:-15" } },
            { "a!,s*,i#", new String[] { "/a", "/i", "0" }, true, new String[] { "a:true" }, new String[] { "!s" }, new String[] { "i:0" } },
        };
        return Arrays.asList(data);
    }
    // @formatter:on, @checkstyle:on

    @Before
    public final void setUp()
    {
        try
        {
            arguments = new Arguments(schema, args);
            assertEquals(DEFAULT_MSG, true, creatable);
        }
        catch (ArgumentsException e)
        {
            assertEquals(DEFAULT_MSG, false, creatable);
        }
    }

    @Test
    public final void testGetBoolean()
    {
        for (final String arg : booleanArgs)
        {
            getBoolean(getArgName(arg), getArgExpected(arg));
        }
    }

    private void getBoolean(final String name, final String expected)
    {
        try
        {
            final boolean value = arguments.getBoolean(name, false);
            assertEquals(DEFAULT_MSG, Boolean.parseBoolean(expected), value);
        }
        catch (ArgumentsException e)
        {
            assertEquals(DEFAULT_MSG, "%", expected);
        }
    }
    
    @Test
    public final void testGetString()
    {
        for (final String arg : stringArgs)
        {
            getString(getArgName(arg), getArgExpected(arg));
        }
    }

    private void getString(final String name, final String expected)
    {
        try
        {
            final String value = arguments.getString(name, "");
            assertEquals(DEFAULT_MSG, expected, value);
        }
        catch (ArgumentsException e)
        {
            assertEquals(DEFAULT_MSG, "%", expected);
        }
    }
    
    @Test
    public final void testGetInt()
    {
        for (final String arg : intArgs)
        {
            getInt(getArgName(arg), getArgExpected(arg));
        }
    }

    private void getInt(final String name, final String expected)
    {
        try
        {
            final int value = arguments.getInt(name, 0);
            final int expectedValue = expected.isEmpty() ? 0 : Integer.parseInt(expected);
            assertEquals(DEFAULT_MSG, expectedValue, value);
        }
        catch (ArgumentsException e)
        {
            assertEquals(DEFAULT_MSG, "%", expected);
        }
    }

    private String getArgName(final String arg)
    {
        assert (arg.length() > 1);
        String result = "";
        if (arg.charAt(0) == '!')
        {
            result = arg.substring(1);
        }
        else
        {
            final int colonIndex = arg.indexOf(':');
            if (colonIndex > 0)
            {
                result = arg.substring(0, colonIndex);
            }
        }
        return result;
    }
    
    private String getArgExpected(final String arg)
    {
        assert (arg.length() > 1);
        String result = "";
        if (arg.charAt(0) != '!')
        {
            final int colonIndex = arg.indexOf(':');
            if (colonIndex > 0)
            {
                result = arg.substring(colonIndex + 1);
            }
        }
        return result;
    }
    
}
