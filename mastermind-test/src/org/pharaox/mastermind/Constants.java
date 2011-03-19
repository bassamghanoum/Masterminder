package org.pharaox.mastermind;

public class Constants
{
    public static final String ALPHABET = "ABCDEF";
    public static final int LENGTH = 4;
    public static final boolean UNIQUE = false;
    
    public static final String CODE_1 = "ABCD";

    public static final int NUM_GAMES = 1296;
    
    public static final Score ZERO_SCORE = new Score(0, 0);
    
    public static final String FIRST_GUESS_SIMPLE = "AAAA";
    public static final String FIRST_GUESS_KNUTH = "AABB";
    public static final String FIRST_GUESS_EXP_SIZE = "AABC";

    public static final int TOTAL_ROUNDS_SIMPLE = 7471;
    public static final int TOTAL_ROUNDS_KNUTH = 5801;
    public static final int TOTAL_ROUNDS_EXP_SIZE = 5696;
    
    public static final int MAX_ROUNDS_SIMPLE = 9;
    public static final int MAX_ROUNDS_KNUTH = 5;
    public static final int MAX_ROUNDS_EXP_SIZE = 6;
    
    public static Mastermind MASTERMIND = new Mastermind(ALPHABET, LENGTH, UNIQUE);
}
