package org.pharaox.mastermind;

public class Constants
{
    // Classical Mastermind
    
    public static Mastermind M1 = new Mastermind("ABCDEF", 4, false);
    
    public static final String M1_CODE = "ABCD";

    public static final int M1_NUM_GAMES = 1296;
    
    public static final String M1_FIRST_GUESS_SIMPLE = "AAAA";
    public static final String M1_FIRST_GUESS_KNUTH = "AABB";
    public static final String M1_FIRST_GUESS_EXP_SIZE = "AABC";

    public static final int M1_TOTAL_ROUNDS_SIMPLE = 7471;
    public static final int M1_TOTAL_ROUNDS_KNUTH = 5801;
    public static final int M1_TOTAL_ROUNDS_EXP_SIZE = 5696;
    
    public static final int M1_MAX_ROUNDS_SIMPLE = 9;
    public static final int M1_MAX_ROUNDS_KNUTH = 5;
    public static final int M1_MAX_ROUNDS_EXP_SIZE = 6;
    
    // Test Mastermind
    
    public static Mastermind M2 = new Mastermind("ABCD", 2, false);
    
    public static final String M2_CODE = "AB";

    public static final int M2_NUM_GAMES = 16;
    
    public static final String M2_FIRST_GUESS_SIMPLE = "AA";
    public static final String M2_FIRST_GUESS_KNUTH = "AB";
    public static final String M2_FIRST_GUESS_EXP_SIZE = "AB";
    public static final String M2_FIRST_GUESS_DUMB = "AA";

    public static final String M2_SECOND_GUESS_SIMPLE = "BB";
    public static final String M2_SECOND_GUESS_KNUTH = "CC";
    public static final String M2_SECOND_GUESS_EXP_SIZE = "CC";
    public static final String M2_SECOND_GUESS_DUMB = "AA";
    
    public static final int M2_TOTAL_ROUNDS_SIMPLE = 53;
    public static final int M2_TOTAL_ROUNDS_KNUTH = 45;
    public static final int M2_TOTAL_ROUNDS_EXP_SIZE = 46;
    public static final int M2_TOTAL_ROUNDS_DUMB = 151;
    
    public static final int M2_MAX_ROUNDS_SIMPLE = 5;
    public static final int M2_MAX_ROUNDS_KNUTH = 4;
    public static final int M2_MAX_ROUNDS_EXP_SIZE = 4;
    public static final int M2_MAX_ROUNDS_DUMB = 10;
}
