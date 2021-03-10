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
import com.github.siroshun09.mcmessage.message.TranslatedMessage;
import com.github.siroshun09.mcmessage.translation.Translation;
import com.github.siroshun09.mcmessage.util.LocaleParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMessageLoader implements MessageLoader {

    private final Path path;
    private final Map<String, String> messageMap;

    protected AbstractMessageLoader(Path path) {
        this.path = path;
        messageMap = new HashMap<>();
    }

    @Override
    public boolean existsFile() {
        return Files.isRegularFile(path);
    }

    @Override
    public @Nullable Message getMessage(@NotNull String key) {
        var message = messageMap.get(key);
        return message != null ? Message.create(message) : null;
    }

    @Override
    public @NotNull @Unmodifiable Set<KeyedMessage> getMessages() {
        return messageMap.entrySet()
                .stream()
                .map(e -> KeyedMessage.create(e.getKey(), e.getValue()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @Nullable Translation toTranslation() {
        var locale = parseLocaleFromFileName();

        return locale != null ? toTranslation(locale) : null;
    }

    @Override
    public @NotNull Translation toTranslation(@NotNull Locale locale) {
        return Translation.create(
                messageMap.entrySet()
                        .stream()
                        .map(e -> TranslatedMessage.create(e.getKey(), e.getValue(), locale))
                        .collect(Collectors.toUnmodifiableMap(KeyedMessage::getKey, t -> t)),
                locale
        );
    }

    @Override
    public @NotNull Map<String, MessageFormat> toMessageFormatMap() {
        return messageMap.entrySet()
                .stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                e -> new MessageFormat(e.getValue())
                        )
                );
    }

    protected @NotNull Path getPath() {
        return path;
    }

    protected @NotNull Map<String, String> getMessageMap() {
        return messageMap;
    }

    private @Nullable Locale parseLocaleFromFileName() {
        var filePath = path.getFileName();

        if (filePath == null) {
            return null;
        }

        var fileName = filePath.toString().toCharArray();
        var builder = new StringBuilder();

        for (var c : fileName) {
            if (c != '.') {
                builder.append(c);
            } else {
                break;
            }
        }

        return LocaleParser.parse(builder.toString());
    }
}
