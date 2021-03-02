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

package com.github.siroshun09.mcmessage.loader;

import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.message.Message;
import com.github.siroshun09.mcmessage.translation.Translation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

abstract class AbstractLanguageLoader implements LanguageLoader {

    private final Path path;
    private final Map<String, Message> messageMap;

    protected AbstractLanguageLoader(@NotNull Path path) {
        this.path = path;
        this.messageMap = new HashMap<>();
    }

    @Override
    public @Nullable Message getMessage(@NotNull String key) {
        Objects.requireNonNull(key);
        return messageMap.get(key);
    }

    @Override
    public @NotNull @Unmodifiable Set<KeyedMessage> getMessages() {
        return messageMap.entrySet().stream()
                .map(entry -> KeyedMessage.of(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @NotNull Translation toTranslation(@NotNull Locale locale) {
        return Translation.of(locale, messageMap);
    }

    @Override
    public @Nullable Locale parseLocaleFromFileName() {
        String fileName = getFilePath().getFileName().toString();
        return Translation.parseLocale(fileName.substring(0, fileName.length() - 11)); // .properties
    }

    protected @NotNull Path getFilePath() {
        return path;
    }

    protected @NotNull Map<String, Message> getMessageMap() {
        return messageMap;
    }
}
