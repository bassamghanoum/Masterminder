package org.pharaox.mastermind;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public final class Main
{
    private static final String ALPHABET = "ABCDEF";
    private static final int LENGTH = 4;
    private static final boolean UNIQUE_CHARS = false;
    private static final int MAX_ROUNDS = 10;

    private Main()
    {
        // No implementation needed
    }

    public static void main(final String[] args)
    {
        final Mastermind mastermind = new Mastermind(ALPHABET, LENGTH, UNIQUE_CHARS);
        final AlgorithmFactory factory = new KnuthAlgorithmFactory(mastermind);
        final Player player = new ReaderWriterPlayer(new InputStreamReader(System.in),
            new OutputStreamWriter(System.out));
        final Game game = new Game(mastermind, factory.getAlgorithm(), MAX_ROUNDS, player);
        game.play();
    }

}
