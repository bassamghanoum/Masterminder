package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;
import static org.pharaox.util.Logger.info;

public class AlgorithmEvaluator
{
    public static final int MAX_ROUNDS = 10;

    Mastermind mastermind;
    AlgorithmFactory algorithmFactory;
    ReadyGuesses readyGuesses;

    private int totalRoundsPlayed = 0;
    private int maxRoundsPlayed = 0;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public AlgorithmEvaluator(Mastermind mastermind, AlgorithmFactory algorithmFactory)
    {
        this.mastermind = mastermind;
        this.algorithmFactory = algorithmFactory;
        this.readyGuesses = new ReadyGuesses(mastermind, algorithmFactory);
    }

    public void evaluate()
    {
        mastermind.visitCodes(new GamesVisitor());
        printInfo();
    }

    private void printInfo()
    {
        info("Algorithm Evaluation for " + algorithmFactory.getAlgorithm());
        info("===============================================");
        info("Total Rounds Played: " + totalRoundsPlayed);
        info("Max Rounds Played: " + maxRoundsPlayed);
        info("Games Played: " + gamesPlayed);
        info("Games Won: " + gamesWon);
        info("Average Rounds Played: " + getAverageRoundsPlayed());
        info("");
    }

    public int getTotalRoundsPlayed()
    {
        return totalRoundsPlayed;
    }

    public int getMaxRoundsPlayed()
    {
        return maxRoundsPlayed;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public double getAverageRoundsPlayed()
    {
        return (double) totalRoundsPlayed / (double) gamesPlayed;
    }
    
    class GamesVisitor implements CodeVisitor
    {
        @Override
        public void visit(String code)
        {
            debug("Code: " + code);
            mastermind.setCode(code);
            Game game = new Game(mastermind, algorithmFactory.getAlgorithm(), 
                MAX_ROUNDS, readyGuesses);
            boolean won = game.play();
            int roundsPlayed = game.getRoundsPlayed();
            debug("Game " + (won ? "won" : "lost") + " in " + roundsPlayed + " round(s)");
            updateStatistics(won, roundsPlayed);
        }

        private void updateStatistics(boolean won, int roundsPlayed)
        {
            totalRoundsPlayed += roundsPlayed;
            maxRoundsPlayed = Math.max(maxRoundsPlayed, roundsPlayed);
            gamesPlayed++;
            gamesWon += (won) ? 1 : 0;
        }
    }
}
