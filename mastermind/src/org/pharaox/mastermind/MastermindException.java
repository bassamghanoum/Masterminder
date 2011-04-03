package org.pharaox.mastermind;

public class MastermindException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public MastermindException()
    {
        super();
    }

    public MastermindException(final Exception exc)
    {
        super(exc);
        assert (exc != null);
    }
}
