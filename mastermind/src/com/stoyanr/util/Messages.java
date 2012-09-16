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
package com.stoyanr.util;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages
{
    private static Map<String, ResourceBundle> bundles;

    private Messages()
    {
        // No implementation needed
    }

    public static String getMessage(final String bundleName, final String key)
    {
        String string = "";
        try
        {
            final ResourceBundle bundle = getBundle(bundleName);
            string = bundle.getString(key);
        }
        catch (final MissingResourceException e)
        {
            string = '!' + key + '!';
        }
        return string;
    }

    private static ResourceBundle getBundle(final String bundleName)
    {
        initBundles();
        return getBundleFromBundles(bundleName);
    }

    private static void initBundles()
    {
        synchronized (new Object())
        {
            if (bundles == null)
            {
                bundles = new HashMap<String, ResourceBundle>();
            }
        }
    }
    
    private static ResourceBundle getBundleFromBundles(final String bundleName)
    {
        ResourceBundle bundle = bundles.get(bundleName);
        if (bundle == null)
        {
            bundle = ResourceBundle.getBundle(bundleName);
            bundles.put(bundleName, bundle);
        }
        return bundle;
    }
}
