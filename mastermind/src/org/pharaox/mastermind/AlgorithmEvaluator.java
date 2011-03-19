package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;
import static org.pharaox.util.Logger.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmEvaluator
{
    public static final int MAX_ROUNDS = 10;

    Mastermind mastermind;
    AlgorithmFactory algorithmFactory;

    private String firstGuess;
    private Map<Score, String> secondGuesses;
    private Map<Score, Map<Score, String>> thirdGuesses;
    private Map<Score, Map<Score, Map<Score, String>>> fourthGuesses;
    private int totalRoundsPlayed = 0;
    private int maxRoundsPlayed = 0;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public AlgorithmEvaluator(Mastermind mastermind, AlgorithmFactory algorithmFactory)
    {
        this.mastermind = mastermind;
        this.algorithmFactory = algorithmFactory;
        initFirstGuess();
        initSecondGuesses();
        initThirdGuesses();
        initFourthGuesses();
    }

    private void initFirstGuess()
    {
        Algorithm algorithm = algorithmFactory.getAlgorithm();
        firstGuess = algorithm.makeGuess();
    }

    private void initSecondGuesses()
    {
        secondGuesses = new HashMap<Score, String>();
        List<Score> scores = mastermind.getAllPossibleScores();
        for (Score score : scores)
        {
            if (score.equals(mastermind.getWinningScore()))
                continue;
            Algorithm algorithm = algorithmFactory.getAlgorithm();
            algorithm.putGuessScore(firstGuess, score);
            String guess = algorithm.makeGuess();
            if (!guess.isEmpty())
                secondGuesses.put(score, guess);
        }
    }
    
    private void initThirdGuesses()
    {
        thirdGuesses = new HashMap<Score, Map<Score, String>>();
        List<Score> scores = mastermind.getAllPossibleScores();
        for (Score score1 : scores)
        {
            if (score1.equals(mastermind.getWinningScore()))
                continue;
            String secondGuess = secondGuesses.get(score1);
            if (secondGuess == null)
                continue;
            for (Score score2 : scores)
            {
                if (score2.equals(mastermind.getWinningScore()))
                    continue;
                Algorithm algorithm = algorithmFactory.getAlgorithm();
                algorithm.putGuessScore(firstGuess, score1);
                algorithm.putGuessScore(secondGuess, score2);
                String guess = algorithm.makeGuess();
                if (!guess.isEmpty())
                {
                    addThirdGuess(score1, score2, guess);
                }
            }
        }
    }

    private void initFourthGuesses()
    {
        fourthGuesses = new HashMap<Score, Map<Score, Map<Score, String>>>();
        List<Score> scores = mastermind.getAllPossibleScores();
        for (Score score1 : scores)
        {
            if (score1.equals(mastermind.getWinningScore()))
                continue;
            String secondGuess = secondGuesses.get(score1);
            if (secondGuess == null)
                continue;
            for (Score score2 : scores)
            {
                if (score2.equals(mastermind.getWinningScore()))
                    continue;
                String thirdGuess = getThirdGuess(score1, score2);
                if (thirdGuess == null)
                    continue;
                for (Score score3 : scores)
                {
                    if (score3.equals(mastermind.getWinningScore()))
                        continue;
                    Algorithm algorithm = algorithmFactory.getAlgorithm();
                    algorithm.putGuessScore(firstGuess, score1);
                    algorithm.putGuessScore(secondGuess, score2);
                    algorithm.putGuessScore(thirdGuess, score3);
                    String guess = algorithm.makeGuess();
                    if (!guess.isEmpty())
                    {
                        addFourthGuess(score1, score2, score3, guess);
                    }
                }
            }
        }
    }

    private void addThirdGuess(Score score1, Score score2, String guess)
    {
        Map<Score, String> map = thirdGuesses.get(score1);
        if (map == null)
        {
            map = new HashMap<Score, String>();
            thirdGuesses.put(score1, map);
        }
        map.put(score2, guess);
    }
    
    private void addFourthGuess(Score score1, Score score2, Score score3, String guess)
    {
        Map<Score, Map<Score, String>> map1 = fourthGuesses.get(score1);
        if (map1 == null)
        {
            map1 = new HashMap<Score, Map<Score, String>>();
            fourthGuesses.put(score1, map1);
        }
        Map<Score, String> map2 = map1.get(score2);
        if (map2 == null)
        {
            map2 = new HashMap<Score, String>();
            map1.put(score2, map2);
        }
        map2.put(score3, guess);
    }

    private String getThirdGuess(Score score1, Score score2)
    {
        String guess = null;
        Map<Score, String> map = thirdGuesses.get(score1);
        if (map != null)
            guess = map.get(score2);
        return guess;
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
                MAX_ROUNDS, firstGuess, secondGuesses, thirdGuesses, fourthGuesses);
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
