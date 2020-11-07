/*
 *     Copyright 2020 Siroshun09
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

package com.github.siroshun09.mcmessage.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public interface RegexPlaceholder extends Placeholder {

    static RegexPlaceholder create(@NotNull Pattern pattern, @NotNull String replacement) {
        return new RegexPlaceholderImpl(pattern, replacement);
    }

    static RegexPlaceholder create(@NotNull String pattern, @NotNull String replacement) throws PatternSyntaxException {
        return create(Pattern.compile(pattern), replacement);
    }

    @NotNull Pattern getPattern();

    @Override
    @NotNull
    default String getPlaceholder() {
        return getPattern().toString();
    }

    @Override
    @NotNull
    default String replace(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        return getPattern().matcher(str).replaceAll(getReplacement());
    }
}
