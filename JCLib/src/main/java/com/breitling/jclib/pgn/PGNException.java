package com.breitling.jclib.pgn;

public class PGNException extends Exception
{
    private static final long serialVersionUID = 895114122756604774L;

    
    public PGNException(String message)
    {
        super(message);
    }
    
    public PGNException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public PGNException(Throwable cause) 
    {
        super(cause);
    }
}