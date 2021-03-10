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

import com.github.siroshun09.configapi.common.Configuration;
import com.github.siroshun09.configapi.common.FileConfiguration;
import com.github.siroshun09.mcmessage.message.KeyedMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class FileConfigurationLoader extends AbstractMessageLoader {

    private final FileConfiguration config;

    FileConfigurationLoader(@NotNull FileConfiguration config) {
        super(config.getPath());

        this.config = config;
    }

    @Override
    public @NotNull @Unmodifiable Set<DuplicateKeyMessage> load() throws IOException {
        if (!config.isLoaded()) {
            config.load();
        }

        return getMessagesFromConfig(config, "");
    }

    @Override
    public void save(@NotNull Iterable<? extends KeyedMessage> messages) throws IOException {
        messages.forEach(m -> config.set(m.getKey(), m.getMessage()));
        config.save();
    }

    @SuppressWarnings("unchecked")
    private Set<DuplicateKeyMessage> getMessagesFromConfig(@NotNull Configuration config, @NotNull String keyPrefix) {
        var duplicate = new HashSet<DuplicateKeyMessage>();

        for (String key : config.getKeys()) {
            var object = config.get(key);

            if (object instanceof Map) {
                var map = (Map<String, Object>) object;
                var child = Configuration.create(map);
                var newKeyPrefix = keyPrefix + key + Configuration.KEY_SEPARATOR;

                var temp = getMessagesFromConfig(child, newKeyPrefix);
                duplicate.addAll(temp);
                continue;
            }

            if (object != null) {
                var k = keyPrefix + key;
                var msg = object.toString();
                if (getMessageMap().containsKey(k)) {
                    duplicate.add(new DuplicateKeyMessage(k, msg));
                } else {
                    getMessageMap().put(keyPrefix + key, msg);
                }
            }
        }

        return duplicate;
    }
}
