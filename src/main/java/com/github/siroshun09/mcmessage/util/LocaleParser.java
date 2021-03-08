/*
 *     Copyright 2021 Siroshun09
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.github.siroshun09.mcmessage.util;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class LocaleParser {

    private LocaleParser() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable Locale parseLocale(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        String[] segments = str.split("_", 3);
        int length = segments.length;

        if (length == 1) {
            return new Locale(str); // language
        }

        if (length == 2) {
            return new Locale(segments[0], segments[1]); // language + country
        }

        if (length == 3) {
            return new Locale(segments[0], segments[1], segments[2]); // language + country + variant
        }

        return null;
    }
}
