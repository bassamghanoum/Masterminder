package org.pharaox.mastermind;

import java.util.SortedMap;
import java.util.TreeMap;

public class Distribution
{
    private static final double PERCENT_MULTIPLIER = 100.0;

    private SortedMap<Integer, Integer> numbers = new TreeMap<Integer, Integer>();
    private int size = 0;

    public final void add(final int number)
    {
        Integer value = numbers.get(number);
        if (value != null)
            numbers.put(number, value.intValue() + 1);
        else
            numbers.put(number, 1);
        size++;
    }

    public final double calculateMean()
    {
        double sum = 0;
        for (int key : numbers.keySet())
            sum += key * numbers.get(key);
        return (sum / (double) size);
    }

    public final double calculateStandardDeviation()
    {
        double mean = calculateMean();
        double sum = 0;
        for (int key : numbers.keySet())
        {
            double diff = (double) key - mean;
            sum += (diff * diff) * numbers.get(key);
        }
        return Math.sqrt(sum / (double) size);
    }

    public final int calculatePercentile(final double percents)
    {
        int x = 0;
        for (int key : numbers.keySet())
        {
            x += numbers.get(key);
            if (((double) x / (double) size) * PERCENT_MULTIPLIER > percents)
                return key;
        }
        return numbers.lastKey();
    }
}
