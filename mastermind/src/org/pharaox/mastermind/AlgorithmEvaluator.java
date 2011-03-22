package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;
import static org.pharaox.util.Logger.info;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class AlgorithmEvaluator
{
    public static final int MAX_ROUNDS = 10;

    private final transient Mastermind mastermind;
    private final transient AlgorithmType type;
    private final transient ReadyGuesses readyGuesses;
    
    private transient int totalRoundsPlayed = 0;
    private transient int maxRoundsPlayed = 0;
    private transient int gamesPlayed = 0;
    private transient int gamesWon = 0;

    public AlgorithmEvaluator(final Mastermind mastermind, final AlgorithmType type)
    {
        this.mastermind = mastermind;
        this.type = type;
        this.readyGuesses = new ReadyGuesses(mastermind, type);
    }

    public final void evaluate()
    {
        mastermind.visitCodes(new GamesVisitor());
        printInfo();
    }

    private void printInfo()
    {
        info("Algorithm Evaluation for " + new AlgorithmFactory(type, mastermind).getAlgorithm());
        info("===============================================");
        info("Total Rounds Played: " + totalRoundsPlayed);
        info("Max Rounds Played: " + maxRoundsPlayed);
        info("Games Played: " + gamesPlayed);
        info("Games Won: " + gamesWon);
        info("Average Rounds Played: " + getAverageRoundsPlayed());
        info("");
    }

    public final int getTotalRoundsPlayed()
    {
        return totalRoundsPlayed;
    }

    public final int getMaxRoundsPlayed()
    {
        return maxRoundsPlayed;
    }

    public final int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public final int getGamesWon()
    {
        return gamesWon;
    }

    public final double getAverageRoundsPlayed()
    {
        return (double) totalRoundsPlayed / (double) gamesPlayed;
    }

    class GamesVisitor implements CodeVisitor
    {
        @Override
        public void visit(final String code)
        {
            debug("Code: " + code);
            final Player player = new DefaultPlayer(mastermind, code);
            final Game game = new Game(mastermind, type, MAX_ROUNDS, player, readyGuesses);
            final boolean won = game.play();
            final int roundsPlayed = game.getRoundsPlayed();
            if (won)
            {
                debug("Game won in " + roundsPlayed + " round(s)");
            }
            else
            {
                debug("Game lost in " + roundsPlayed + " round(s)");
            }
            updateStatistics(won, roundsPlayed);
        }

        private void updateStatistics(final boolean won, final int roundsPlayed)
        {
            totalRoundsPlayed += roundsPlayed;
            maxRoundsPlayed = Math.max(maxRoundsPlayed, roundsPlayed);
            gamesPlayed++;
            if (won)
            {
                gamesWon++;
            }
        }
    }
}
