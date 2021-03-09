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

package com.github.siroshun09.mcmessage.message;

import com.github.siroshun09.mcmessage.color.Colorable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ColorableMessage extends Message, Colorable {

    @Contract("_, _ -> new")
    static @NotNull ColorableMessage create(@NotNull String message, @NotNull TextColor color) {
        return new ColorableMessageImpl(message, color);
    }

    default @NotNull Component colorize() {
        return colorize(toTextComponent());
    }
}
