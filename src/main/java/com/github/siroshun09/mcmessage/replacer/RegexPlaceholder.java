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

import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public interface RegexPlaceholder extends Placeholder {

    static @NotNull RegexPlaceholder create(@NotNull Pattern pattern) {
        return new RegexPlaceholderImpl(pattern);
    }

    static @NotNull RegexPlaceholder create(@NotNull String pattern) throws PatternSyntaxException {
        return create(Pattern.compile(pattern));
    }

    @NotNull Pattern getPattern();

    @Override
    default @NotNull String getPlaceholder() {
        return getPattern().toString();
    }

    @Override
    @NotNull
    default Replacer toReplacer(@NotNull String replacement) {
        return RegexReplacer.create(getPattern(), replacement);
    }

    @Override
    default @NotNull TextReplacementConfig toTextReplacementConfig(@NotNull String replacement) {
        return TextReplacementConfig.builder()
                .match(getPattern())
                .replacement(replacement)
                .build();
    }
}
