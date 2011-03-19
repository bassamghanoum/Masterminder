package org.pharaox.mastermind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScoreTest
{

    @Test
    public void testConstructor()
    {
        Score hits = new Score(0, 0);
        assertEquals(0, hits.getCows());
        assertEquals(0, hits.getBulls());
    }

    @Test
    public void testEquals()
    {
        Score hits1 = new Score(1, 2);
        Score hits2 = new Score(1, 2);
        Score hits3 = new Score(2, 3);
        Score hits4 = new Score(2, 3);
        assertTrue(hits1.equals(hits2));
        assertFalse(hits1.equals(hits3));
        assertTrue(hits3.equals(hits4));
    }
}
