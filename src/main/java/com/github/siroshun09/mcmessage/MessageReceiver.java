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

package com.github.siroshun09.mcmessage;

import com.github.siroshun09.mcmessage.message.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface MessageReceiver {

    @NotNull Audience getAudience();

    @NotNull Locale getLocale();

    default void sendMessage(@NotNull String message) {
        if (message.isEmpty()) {
            sendMessage(Component.empty());
        } else {
            sendMessage(Component.text(message));
        }
    }

    default void sendColorizedMessage(@NotNull String message) {
        sendColorizedMessage(message, false);
    }

    default void sendColorizedMessage(@NotNull String message, boolean section) {
        if (message.isEmpty()) {
            sendMessage(Component.empty());
            return;
        }

        Component colorized;

        if (section) {
            colorized = LegacyComponentSerializer.legacySection().deserialize(message);
        } else {
            colorized = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        }

        sendMessage(colorized);
    }

    default void sendMessage(@NotNull Message message) {
        sendMessage(message.toTextComponent());
    }

    default void sendMessage(@NotNull Component component) {
        getAudience().sendMessage(component);
    }
}
