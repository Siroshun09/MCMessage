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

package com.github.siroshun09.mcmessage.translation;

import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.message.TranslatedMessage;
import com.github.siroshun09.mcmessage.MessageHoldable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface Translation extends MessageHoldable {

    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull Translation create(@NotNull Map<String, TranslatedMessage> messageMap, @NotNull Locale locale) {
        return new TranslationImpl(messageMap, locale);
    }

    @Contract("_, _ -> new")
    static @NotNull Translation create(@NotNull Set<TranslatedMessage> messageSet, @NotNull Locale locale) {
        return new TranslationImpl(
                messageSet.stream().collect(
                        Collectors.toUnmodifiableMap(
                                TranslatedMessage::getKey,
                                t -> t
                        )
                ),
                locale
        );
    }

    @Override
    @Nullable TranslatedMessage getMessage(@NotNull String key);

    @Override
    default @NotNull TranslatedMessage getMessage(@NotNull String key, @NotNull String def) {
        var message = getMessage(key);
        return message != null ? message : TranslatedMessage.create(key, def, getLocale());
    }

    @Override
    default @NotNull TranslatedMessage getMessage(@NotNull KeyedMessage keyedMessage) {
        Objects.requireNonNull(keyedMessage);
        return getMessage(keyedMessage.getKey(), keyedMessage.getMessage());
    }

    @Override
    @NotNull @Unmodifiable Set<TranslatedMessage> getMessages();

    @NotNull Locale getLocale();
}
