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
        return message != null ? message : Message.create(def);
    }

    default @NotNull Message getMessage(@NotNull KeyedMessage keyedMessage) {
        Objects.requireNonNull(keyedMessage);
        return getMessage(keyedMessage.getKey(), keyedMessage.getMessage());
    }

    @NotNull @Unmodifiable Set<? extends KeyedMessage> getMessages();
}
