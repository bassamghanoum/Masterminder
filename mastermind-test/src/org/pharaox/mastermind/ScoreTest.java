package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScoreTest
{
    private static final Score[] SCORES = { Score.ZERO_SCORE, new Score(1, 2), new Score(1, 2),
        new Score(2, 3), new Score(2, 3) };

    @Test
    public final void testConstructor()
    {
        assertEquals(0, SCORES[0].getCows());
        assertEquals(0, SCORES[0].getBulls());
    }

    @Test
    public final void testEquals()
    {
        // @checkstyle:off (Magic numbers)
        assertTrue(SCORES[1].equals(SCORES[2]));
        assertTrue(SCORES[3].equals(SCORES[4]));
        assertFalse(SCORES[1].equals(SCORES[3]));
        // @checkstyle:on
    }

    @Test
    public final void testHashCode()
    {
        // @checkstyle:off (Magic numbers)
        assertTrue(SCORES[1].hashCode() == SCORES[2].hashCode());
        assertTrue(SCORES[3].hashCode() == SCORES[4].hashCode());
        assertFalse(SCORES[1].hashCode() == SCORES[3].hashCode());
        // @checkstyle:on
    }
}
