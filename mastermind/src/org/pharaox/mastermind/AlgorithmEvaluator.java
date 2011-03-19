package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;
import static org.pharaox.util.Logger.info;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class AlgorithmEvaluator
{
    public static final int MAX_ROUNDS = 10;

    private Mastermind mastermind;
    private AlgorithmType type;
    private ReadyGuesses readyGuesses;
    private int totalRoundsPlayed = 0;
    private int maxRoundsPlayed = 0;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public AlgorithmEvaluator(Mastermind mastermind, AlgorithmType type)
    {
        this.mastermind = mastermind;
        this.type = type;
        this.readyGuesses = new ReadyGuesses(mastermind, type);
    }

    public void evaluate()
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
            Game game = new Game(mastermind, type, MAX_ROUNDS, readyGuesses);
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
