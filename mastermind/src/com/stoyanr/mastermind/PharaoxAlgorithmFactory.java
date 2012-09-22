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

/**
 * A factory for {@link PharaoxAlgorithm} instances.
 * 
 * @author Stoyan Rachev
 */
public class PharaoxAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    private final transient double percents;
    
    /**
     * Creates a new factory with the specified game setup.
     * 
     * @param mastermind The game setup to use.
     */
    public PharaoxAlgorithmFactory(final Mastermind mastermind, final double percents)
    {
        assert (mastermind != null && percents >= 0);
        this.mastermind = mastermind;
        this.percents = percents;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new PharaoxAlgorithm(mastermind, percents);
    }

}
