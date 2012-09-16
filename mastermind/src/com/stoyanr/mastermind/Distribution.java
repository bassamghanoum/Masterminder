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
        assert (percents >= 0);
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
