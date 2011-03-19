package org.pharaox.mastermind;

import java.util.SortedMap;
import java.util.TreeMap;

public class Distribution
{
    private SortedMap<Integer, Integer> numbers = new TreeMap<Integer, Integer>();
    private int size = 0;

    public void add(int number)
    {
        Integer key = new Integer(number);
        Integer value = numbers.get(key);
        if (value != null)
            numbers.put(key, new Integer(value.intValue() + 1));
        else
            numbers.put(key, new Integer(1));
        size++;
    }

    public double calculateMean()
    {
        double sum = 0;
        for (int key : numbers.keySet())
            sum += key * numbers.get(key);
        return (sum / (double)size);
    }

    public double calculateStandardDeviation()
    {
        double mean = calculateMean();
        double sum = 0;
        for (int key : numbers.keySet())
        {
            double diff = (double)key - mean;
            sum += (diff * diff) * numbers.get(key);
        }
        return Math.sqrt(sum / (double)size);
    }

    public int calculatePercentile(double percents)
    {
        int x = 0;
        for (int key : numbers.keySet())
        {
            x += numbers.get(key);
            if (((double)x / (double)size) * 100.0 > percents)
                return key;
        }
        return numbers.lastKey();
    }
}
