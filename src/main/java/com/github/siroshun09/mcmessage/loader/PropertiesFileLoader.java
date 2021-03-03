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

package com.github.siroshun09.mcmessage.loader;

import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.message.Message;
import com.github.siroshun09.mcmessage.util.InvalidMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class PropertiesFileLoader extends AbstractLanguageLoader {

    PropertiesFileLoader(@NotNull Path filePath) {
        super(filePath);
    }

    @Override
    public @NotNull @Unmodifiable Set<InvalidMessage> load() throws IOException {
        if (!Files.exists(getFilePath())) {
            return Collections.emptySet();
        }

        var invalidMessages = new HashSet<InvalidMessage>();

        try (BufferedReader reader = Files.newBufferedReader(getFilePath(), StandardCharsets.UTF_8)) {
            int currentLine = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                currentLine++;

                if (line.startsWith("#")) {
                    continue;
                }

                var split = line.split("=", 2);

                if (split.length < 2) {
                    invalidMessages.add(new InvalidMessage(currentLine, line, InvalidMessage.Reason.INVALID_FORMAT));
                    continue;
                }

                var key = split[0];

                if (getMessageMap().containsKey(key)) {
                    invalidMessages.add(new InvalidMessage(currentLine, line, InvalidMessage.Reason.DUPLICATE_KEY));
                } else {
                    getMessageMap().put(key, Message.of(split[1]));
                }
            }
        }

        return Collections.unmodifiableSet(invalidMessages);
    }

    @Override
    public void save(@NotNull Iterable<? extends KeyedMessage> keyedMessages) throws IOException {
        Objects.requireNonNull(keyedMessages);

        if (!Files.exists(getFilePath())) {
            Files.createDirectories(getFilePath().getParent());
            Files.createFile(getFilePath());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(getFilePath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE)) {
            var builder = new StringBuilder();

            for (KeyedMessage msg : keyedMessages) {
                builder.setLength(0);
                builder.append(msg.getKey()).append('=').append(msg.get());
                writer.write(builder.toString());
                writer.newLine();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (getClass() == o.getClass()) {
            var that = (PropertiesFileLoader) o;
            return getFilePath().equals(that.getFilePath()) &&
                    getMessages().equals(that.getMessages());

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilePath(), getMessageMap());
    }

    @Override
    public String toString() {
        return "PropertiesFileLoader{" +
                "filePath=" + getFilePath() +
                ", messageMap=" + getMessageMap() +
                '}';
    }
}
