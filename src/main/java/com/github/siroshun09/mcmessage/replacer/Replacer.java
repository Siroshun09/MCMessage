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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Replacer extends Placeholder {

    @Contract("_, _ -> new")
    static @NotNull Replacer create(@NotNull String placeholder, @NotNull String replacement) {
        return new ReplacerImpl(placeholder, replacement);
    }

    @NotNull
    String getReplacement();

    @NotNull
    default String replace(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        return str.replace(getPlaceholder(), getReplacement());
    }

    default @NotNull TextReplacementConfig toTextReplacementConfig() {
        return TextReplacementConfig.builder()
                .match(getPlaceholder())
                .replacement(getReplacement())
                .build();
    }
}
