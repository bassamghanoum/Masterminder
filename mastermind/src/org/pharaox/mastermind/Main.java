package org.pharaox.mastermind;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class Main
{
    private static final String ALPHABET = "ABCDEF";
    private static final int LENGTH = 4;
    private static final boolean UNIQUE_CHARS = false;
    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.KNUTH;
    private static final int MAX_ROUNDS = 10;

    public static void main(String[] args)
    {
        Mastermind mastermind = new Mastermind(ALPHABET, LENGTH, UNIQUE_CHARS);
        Game game = new Game(mastermind, ALGORITHM_TYPE, MAX_ROUNDS);
        game.setPlayer(new ReaderWriterPlayer(new InputStreamReader(System.in), 
            new OutputStreamWriter(System.out)));
        game.play();
    }

}
