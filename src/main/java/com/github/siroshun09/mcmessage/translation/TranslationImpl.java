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

package com.github.siroshun09.mcmessage.translation;

import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TranslationImpl implements Translation {

    private final Locale locale;
    private final Map<String, Message> messages;

    TranslationImpl(@NotNull Locale locale, @NotNull Set<KeyedMessage> messages) {
        this(locale, messages.stream().collect(Collectors.toMap(KeyedMessage::getKey, m -> Message.of(m.get()))));
    }

    TranslationImpl(@NotNull Locale locale, @NotNull Map<String, Message> messages) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(messages);

        this.locale = locale;
        this.messages = messages;
    }

    @Override
    public @NotNull Locale getLocale() {
        return locale;
    }

    @Override
    public @Nullable Message getMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        return messages.get(key);
    }

    @Override
    public @NotNull @Unmodifiable Set<KeyedMessage> getMessages() {
        return messages.entrySet().stream()
                .map(entry -> KeyedMessage.of(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof TranslationImpl) {
            TranslationImpl that = (TranslationImpl) o;
            return getLocale().equals(that.getLocale()) &&
                    getMessages().equals(that.getMessages());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocale(), getMessages());
    }

    @Override
    public String toString() {
        return "TranslationImpl{" +
                "locale=" + locale +
                ", messages=" + messages +
                '}';
    }
}
