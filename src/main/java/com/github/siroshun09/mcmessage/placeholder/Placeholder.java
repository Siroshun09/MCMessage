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

package com.github.siroshun09.mcmessage.placeholder;

import com.github.siroshun09.mcmessage.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public interface Placeholder {

    @NotNull String getPlaceholder();

    @Contract("_ -> new")
    default @NotNull TextReplacementConfig toTextReplacementConfig(@NotNull String replacement) {
        return Objects.requireNonNull(replacement).isEmpty()
                ? toTextReplacementConfig(Component.empty())
                : toTextReplacementConfig(Component.text(replacement));
    }

    @Contract("_ -> new")
    default @NotNull TextReplacementConfig toTextReplacementConfig(@NotNull Message message) {
        Objects.requireNonNull(message);

        return toTextReplacementConfig(message.toTextComponent());
    }

    @Contract("_ -> new")
    default @NotNull TextReplacementConfig toTextReplacementConfig(@NotNull Component replacement) {
        Objects.requireNonNull(replacement);

        return TextReplacementConfig.builder()
                .matchLiteral(getPlaceholder())
                .replacement(replacement)
                .build();
    }
}
