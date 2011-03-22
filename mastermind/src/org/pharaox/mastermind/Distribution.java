package org.pharaox.mastermind;

import java.util.SortedMap;
import java.util.TreeMap;

public class Distribution
{
    private static final double PERCENTS_100 = 100.0;

    private final transient SortedMap<Integer, Integer> numbers = new TreeMap<Integer, Integer>();
    
    private transient int size = 0;

    public final void add(final int number)
    {
        final Integer value = numbers.get(number);
        if (value == null)
        {
            numbers.put(number, 1);
        }
        else
        {
            numbers.put(number, value.intValue() + 1);
        }
        size++;
    }

    public final double calculateMean()
    {
        double sum = 0;
        for (final int key : numbers.keySet())
        {
            sum += key * numbers.get(key);
        }
        return (sum / size);
    }

    public final double calculateStandardDeviation()
    {
        final double mean = calculateMean();
        double sum = 0;
        for (final int key : numbers.keySet())
        {
            final double diff = key - mean;
            sum += (diff * diff) * numbers.get(key);
        }
        return Math.sqrt(sum / size);
    }

    public final int calculatePercentile(final double percents)
    {
        int result = numbers.lastKey();
        int num = 0;
        for (final int key : numbers.keySet())
        {
            num += numbers.get(key);
            if (((double) num / (double) size) * PERCENTS_100 > percents)
            {
                result = key;
                break;
            }
        }
        return result;
    }
}
