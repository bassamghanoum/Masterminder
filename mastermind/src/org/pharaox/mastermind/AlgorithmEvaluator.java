package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;
import static org.pharaox.util.Logger.info;

public class AlgorithmEvaluator
{
    public static final int MAX_ROUNDS = 10;

    private final transient Mastermind mastermind;
    private final transient AlgorithmFactory factory;
    private final transient GuessCalculator calc;
    
    private transient int totalRoundsPlayed = 0;
    private transient int maxRoundsPlayed = 0;
    private transient int gamesPlayed = 0;
    private transient int gamesWon = 0;

    public AlgorithmEvaluator(final Mastermind mastermind, final AlgorithmFactory factory, 
        final int levels)
    {
        this.mastermind = mastermind;
        this.factory = factory;
        this.calc = new GuessCalculator(mastermind, factory, levels);
    }

    public final void evaluate()
    {
        mastermind.visitCodes(new GamesVisitor());
        printInfo();
    }

    private void printInfo()
    {
        info("Algorithm Evaluation for " + factory.getAlgorithm());
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
            final Algorithm algorithm = factory.getAlgorithm();
            final Game game = new Game(mastermind, algorithm, MAX_ROUNDS, player, calc);
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
