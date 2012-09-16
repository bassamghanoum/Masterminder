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

import static com.stoyanr.mastermind.Mastermind.MAX_LENGTH;

public class Score
{
    public static final Score ZERO_SCORE = new Score(0, 0);

    private final transient int cows;
    private final transient int bulls;

    public Score(final int cows, final int bulls)
    {
        this.cows = cows;
        this.bulls = bulls;
    }

    @Override
    public final String toString()
    {
        return "(" + cows + ", " + bulls + ")";
    }

    @Override
    public final boolean equals(final Object obj)
    {
        boolean result = false;
        if (obj instanceof Score)
        {
            final Score other = (Score) obj;
            result = (cows == other.cows) && (bulls == other.bulls);
        }
        return result;
    }

    @Override
    public final int hashCode()
    {
        return bulls * (MAX_LENGTH + 1) + cows;
    }

}
