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

package com.github.siroshun09.mcmessage;

import com.github.siroshun09.mcmessage.builder.PlainTextBuilder;
import com.github.siroshun09.mcmessage.message.DefaultMessage;
import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;
import java.util.Set;

public interface MessageHoldable {

    @Nullable Message getMessage(@NotNull String key);

    default @NotNull Message getMessage(@NotNull String key, @NotNull String def) {
        Message message = getMessage(key);
        return message != null ? message : Message.of(def);
    }

    default @NotNull Message getMessage(@NotNull DefaultMessage defaultMessage) {
        Objects.requireNonNull(defaultMessage);
        return getMessage(defaultMessage.getKey(), defaultMessage.getDefault());
    }

    @NotNull @Unmodifiable Set<KeyedMessage> getMessages();

    default @Nullable PlainTextBuilder newPlainTextBuilder(@NotNull String key) {
        Message message = getMessage(key);
        return message != null ? new PlainTextBuilder(message) : null;
    }

    default @NotNull PlainTextBuilder newPlainTextBuilder(@NotNull String key, @NotNull String def) {
        PlainTextBuilder builder = newPlainTextBuilder(key);
        return builder != null ? builder : new PlainTextBuilder(Message.of(def));
    }

    default @NotNull PlainTextBuilder newPlainTextBuilder(@NotNull DefaultMessage defaultMessage) {
        Objects.requireNonNull(defaultMessage);
        return newPlainTextBuilder(defaultMessage.getKey(), defaultMessage.getDefault());
    }
}
