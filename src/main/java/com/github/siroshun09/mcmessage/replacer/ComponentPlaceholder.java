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

import com.github.siroshun09.mcmessage.util.Colorizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface ComponentPlaceholder<T, C extends Component> extends FunctionalPlaceholder<T> {

    @Contract("_, _ -> new")
    static <T, C extends Component> ComponentPlaceholderImpl<T, C> create(@NotNull String placeholder, @NotNull Function<T, C> function) {
        return new ComponentPlaceholderImpl<>(placeholder, function);
    }

    @NotNull C createComponent(T value);

    @Override
    default @NotNull Function<T, String> getFunction() {
        return v -> {
            var component = createComponent(v);
            var serialized = LegacyComponentSerializer.legacySection().serialize(component);
            return Colorizer.colorize(serialized);
        };
    }

    @Override
    default @NotNull TextReplacementConfig toTextReplacementConfig(@NotNull T value) {
        return TextReplacementConfig.builder()
                .match(getPlaceholder())
                .replacement(createComponent(value))
                .build();
    }
}
