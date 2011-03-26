package org.pharaox.mastermind;

public final class Constants
{
    // Classical Mastermind

    public static final String MM1_ALPHABET = "ABCDEF";
    public static final int MM1_LENGTH = 4;

    public static final Mastermind MM1 = new Mastermind(MM1_ALPHABET, MM1_LENGTH, false);

    public static final String MM1_CODE = "ABCD";

    public static final int MM1_NUM_GAMES = 1296;

    public static final String MM1_FIRST_GUESS_SIMPLE = "AAAA";
    public static final String MM1_FIRST_GUESS_KNUTH = "AABB";
    public static final String MM1_FIRST_GUESS_ESIZE = "AABC";

    public static final int MM1_TOTAL_ROUNDS_SIMPLE = 7471;
    public static final int MM1_TOTAL_ROUNDS_KNUTH = 5801;
    public static final int MM1_TOTAL_ROUNDS_ESIZE = 5696;

    public static final int MM1_MAX_ROUNDS_SIMPLE = 9;
    public static final int MM1_MAX_ROUNDS_KNUTH = 5;
    public static final int MM1_MAX_ROUNDS_ESIZE = 6;

    // Test Mastermind

    public static final String MM2_ALPHABET = "ABCD";
    public static final int MM2_LENGTH = 2;

    public static final Mastermind MM2 = new Mastermind(MM2_ALPHABET, MM2_LENGTH, false);

    public static final String MM2_CODE = "AB";

    public static final int MM2_NUM_GAMES = 16;

    public static final String MM2_FIRST_GUESS_SIMPLE = "AA";
    public static final String MM2_FIRST_GUESS_KNUTH = "AB";
    public static final String MM2_FIRST_GUESS_ESIZE = "AB";
    public static final String MM2_FIRST_GUESS_DUMB = "AA";

    public static final String MM2_SECOND_GUESS_SIMPLE = "BB";
    public static final String MM2_SECOND_GUESS_KNUTH = "CC";
    public static final String MM2_SECOND_GUESS_ESIZE = "CC";
    public static final String MM2_SECOND_GUESS_DUMB = "AA";

    public static final int MM2_TOTAL_ROUNDS_SIMPLE = 53;
    public static final int MM2_TOTAL_ROUNDS_KNUTH = 45;
    public static final int MM2_TOTAL_ROUNDS_ESIZE = 46;
    public static final int MM2_TOTAL_ROUNDS_DUMB = 151;

    public static final int MM2_MAX_ROUNDS_SIMPLE = 5;
    public static final int MM2_MAX_ROUNDS_KNUTH = 4;
    public static final int MM2_MAX_ROUNDS_ESIZE = 4;
    public static final int MM2_MAX_ROUNDS_DUMB = 10;

    private Constants()
    {
        // No implementation needed
    }
}
