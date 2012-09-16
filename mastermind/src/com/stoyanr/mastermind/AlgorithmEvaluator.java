/*
 * $Id: $
 *
 * Copyright 2012 Stoyan Rachev (stoyanr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stoyanr.mastermind;

import static com.stoyanr.util.Logger.debug;
import static com.stoyanr.util.Logger.info;

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
        assert (mastermind != null && factory != null);
        this.mastermind = mastermind;
        this.factory = factory;
        this.calc = new GuessCalculator(mastermind, factory, levels);
    }

    public final void evaluate()
    {
        mastermind.visitCodes(new GamesVisitor());
        printInfo();
    }

    class GamesVisitor implements CodeVisitor
    {
        @Override
        public void visit(final String code)
        {
            assert mastermind.isValidCode(code);
            debug("Code: " + code);
            final Player player = new DefaultPlayer(mastermind, code);
            final Algorithm algorithm = factory.getAlgorithm();
            final Game game = new Game(mastermind, algorithm, MAX_ROUNDS, player, calc);
            final boolean won = game.play();
            final int roundsPlayed = game.getRoundsPlayed();
            debugGameWon(won, roundsPlayed);
            updateStatistics(won, roundsPlayed);
        }

        private void debugGameWon(final boolean won, final int roundsPlayed)
        {
            if (won)
            {
                debug("Game won in " + roundsPlayed + " round(s)");
            }
            else
            {
                debug("Game lost in " + roundsPlayed + " round(s)");
            }
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
    
    private void printInfo()
    {
        info("Algorithm Evaluation for " + factory.getAlgorithm().getClass());
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
        assert hasFinished();
        return totalRoundsPlayed;
    }
    
    public final int getMaxRoundsPlayed()
    {
        assert hasFinished();
        return maxRoundsPlayed;
    }

    public final int getGamesPlayed()
    {
        assert hasFinished();
        return gamesPlayed;
    }

    public final int getGamesWon()
    {
        assert hasFinished();
        return gamesWon;
    }

    public final double getAverageRoundsPlayed()
    {
        assert hasFinished();
        return (double) totalRoundsPlayed / (double) gamesPlayed;
    }

    private boolean hasFinished()
    {
        return (gamesPlayed > 0);
    }
}
