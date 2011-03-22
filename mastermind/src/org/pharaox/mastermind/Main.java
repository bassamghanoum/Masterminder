package org.pharaox.mastermind;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public final class Main
{
    private static final String ALPHABET = "ABCDEF";
    private static final int LENGTH = 4;
    private static final boolean UNIQUE_CHARS = false;
    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.KNUTH;
    private static final int MAX_ROUNDS = 10;

    private Main()
    {
    }

    public static void main(final String[] args)
    {
        final Mastermind mastermind = new Mastermind(ALPHABET, LENGTH, UNIQUE_CHARS);
        final Player player = new ReaderWriterPlayer(new InputStreamReader(System.in),
            new OutputStreamWriter(System.out));
        final Game game = new Game(mastermind, ALGORITHM_TYPE, MAX_ROUNDS, player);
        game.play();
    }

}
