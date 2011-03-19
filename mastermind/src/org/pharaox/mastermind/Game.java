package org.pharaox.mastermind;

import static org.pharaox.util.Logger.debug;

import org.pharaox.mastermind.AlgorithmFactory.AlgorithmType;

public class Game
{
    public static final Score ZERO_SCORE = new Score(0, 0);

    private Mastermind mastermind;
    private Algorithm algorithm;
    private int maxRounds;
    private ReadyGuesses readyGuesses;
    private Player player = new DefaultConsole();
    private Score[] scores = new Score[] { ZERO_SCORE, ZERO_SCORE, ZERO_SCORE };
    boolean won = false;
    private int roundsPlayed = 0;

    public Game(Mastermind mastermind, AlgorithmType type, int maxRounds)
    {
        this(mastermind, type, maxRounds, null);
    }

    public Game(Mastermind mastermind, AlgorithmType type, int maxRounds,
        ReadyGuesses readyGuesses)
    {
        this.mastermind = mastermind;
        this.algorithm = new AlgorithmFactory(type, mastermind).getAlgorithm();
        this.maxRounds = maxRounds;
        this.readyGuesses = readyGuesses;
    }
    
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public boolean hasWon()
    {
        return won;
    }

    public int getRoundsPlayed()
    {
        return roundsPlayed;
    }

    public boolean play()
    {
        if (roundsPlayed > 0)
            throw new MastermindException();
        player.startGame();
        int i;
        for (i = 0; i < maxRounds; i++)
        {
            Score score = playRound(i);
            if (isWinningScore(score))
            {
                won = true;
                i++;
                break;
            }
            shiftScores(score);
        }
        roundsPlayed = i;
        player.endGame(won, roundsPlayed);
        return won;
    }

    private boolean isWinningScore(Score score)
    {
        return score.equals(mastermind.getWinningScore());
    }

    private void shiftScores(Score score)
    {
        for (int j = scores.length - 1; j > 0; j--)
            scores[j] = scores[j - 1];
        scores[0] = score;
    }

    private Score playRound(int round)
    {
        String guess = makeGuess(round);
        assert (mastermind.isValidCode(guess));
        Score score = player.getScore(guess);
        debug(guess + " => " + score);
        putGuessScore(guess, score);
        return score;
    }

    private String makeGuess(int round)
    {
        String guess;
        if (round == 0 && readyGuesses != null)
            guess = readyGuesses.getFirstGuess();
        else if (round == 1 && readyGuesses != null)
            guess = readyGuesses.getSecondGuess(scores[0]);
        else if (round == 2 && readyGuesses != null)
            guess = readyGuesses.getThirdGuess(scores[1], scores[0]);
        else if (round == 3 && readyGuesses != null)
            guess = readyGuesses.getFourthGuess(scores[2], scores[1], scores[0]);
        else
            guess = algorithm.makeGuess();
        return guess;
    }

    private void putGuessScore(String guess, Score score)
    {
        algorithm.putGuessScore(guess, score);
    }
    
    class DefaultConsole implements Player
    {
        @Override
        public void startGame()
        {
        }

        @Override
        public void endGame(boolean won, int roundsPlayed)
        {
        }
        
        @Override
        public Score getScore(String guess)
        {
            return mastermind.evaluateScore(guess);
        }
    }
}
