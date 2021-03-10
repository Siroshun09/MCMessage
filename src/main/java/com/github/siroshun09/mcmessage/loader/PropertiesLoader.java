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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

class PropertiesLoader extends AbstractMessageLoader {

    PropertiesLoader(@NotNull Path path) {
        super(path);
    }

    @Override
    public @NotNull @Unmodifiable Set<DuplicateKeyMessage> load() throws IOException {
        if (!existsFile()) {
            return Collections.emptySet();
        }

        var properties = new Properties();
        var duplicate = new HashSet<DuplicateKeyMessage>();

        try (var reader = Files.newBufferedReader(getPath())) {
            properties.load(reader);
        }

        for (var key : properties.keySet()) {
            var value = properties.get(key);

            if (key == null || value == null) {
                continue;
            }

            var strKey = key instanceof String ? (String) key : key.toString();
            var strValue = value instanceof String ? (String) value : value.toString();

            if (getMessageMap().containsKey(strKey)) {
                duplicate.add(new DuplicateKeyMessage(strKey, strValue));
            } else {
                getMessageMap().put(strKey, strValue);
            }
        }

        return duplicate;
    }

    @Override
    public void save(@NotNull Iterable<? extends KeyedMessage> messages) throws IOException {
        if (!existsFile()) {
            Files.createDirectories(getPath().getParent());
            Files.createFile(getPath());
        }

        var properties = new Properties();

        messages.forEach(m -> properties.put(m.getKey(), m.getMessage()));

        try (var writer = Files.newBufferedWriter(getPath())) {
            properties.store(writer, null);
        }
    }
}
