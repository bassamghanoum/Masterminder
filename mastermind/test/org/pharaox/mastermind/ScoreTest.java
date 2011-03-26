package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Test;

public class ScoreTest
{
    private static final Score[] SCORES = { Score.ZERO_SCORE, new Score(1, 2), new Score(1, 2),
        new Score(2, 3), new Score(2, 3) };

    private static final String M_WRONG_COWS = "Wrong cows:";
    private static final String M_WRONG_BULLS = "Wrong bulls:";
    private static final String M_EQUALITY_CHECK_FAILED = "Equality check failed:";
    private static final String M_HASHCODE_CHECK_FAILED = "Hashcode check failed:";
    
    @Test
    public final void testConstructor()
    {
        assertEquals(M_WRONG_COWS, 0, SCORES[0].getCows());
        assertEquals(M_WRONG_BULLS, 0, SCORES[0].getBulls());
    }

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
