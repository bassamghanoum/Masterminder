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

public class DefaultPlayer implements Player
{
    private final transient Mastermind mastermind;
    private final transient String code;
    
    public DefaultPlayer(final Mastermind mastermind, final String code)
    {
        assert (mastermind != null && mastermind.isValidCode(code));
        this.mastermind = mastermind;
        this.code = code;
    }

    @Override
    public final void startGame()
    {
        // No implementation needed
    }

    @Override
    public final void endGame(final boolean wonx, final int roundsPlayedx)
    {
        // No implementation needed
    }

    @Override
    public final Score getScore(final String guess)
    {
        assert mastermind.isValidCode(guess);
        return mastermind.evaluateScore(guess, code);
    }
}
