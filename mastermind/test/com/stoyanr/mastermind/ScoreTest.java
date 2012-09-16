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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Test;

public class ScoreTest
{
    private static final Score[] SCORES = { Score.ZERO_SCORE, new Score(1, 2), new Score(1, 2),
        new Score(2, 3), new Score(2, 3) };

    private static final String M_EQUALITY_CHECK_FAILED = "Equality check failed:";
    private static final String M_HASHCODE_CHECK_FAILED = "Hashcode check failed:";
    
    @Test
    public final void testEquals()
    {
        // @checkstyle:off (Magic numbers)
        assertEquals(M_EQUALITY_CHECK_FAILED, SCORES[1], SCORES[2]);
        assertEquals(M_EQUALITY_CHECK_FAILED, SCORES[3], SCORES[4]);
        assertThat(M_EQUALITY_CHECK_FAILED, SCORES[1], not(equalTo(SCORES[3])));
        // @checkstyle:on
    }

    @Test
    public final void testHashCode()
    {
        // @checkstyle:off (Magic numbers)
        assertEquals(M_HASHCODE_CHECK_FAILED, SCORES[1].hashCode(), SCORES[2].hashCode());
        assertEquals(M_HASHCODE_CHECK_FAILED, SCORES[3].hashCode(), SCORES[4].hashCode());
        assertThat(M_HASHCODE_CHECK_FAILED, SCORES[1].hashCode(), not(equalTo(SCORES[3].hashCode())));
        // @checkstyle:on
    }
}
