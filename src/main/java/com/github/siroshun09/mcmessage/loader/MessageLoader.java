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

import com.github.siroshun09.configapi.common.FileConfiguration;
import com.github.siroshun09.mcmessage.MessageHoldable;
import com.github.siroshun09.mcmessage.message.KeyedMessage;
import com.github.siroshun09.mcmessage.translation.Translation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface MessageLoader extends MessageHoldable {

    static @NotNull MessageLoader fromProperties(@NotNull Path path) {
        return new PropertiesLoader(path);
    }

    static @NotNull MessageLoader fromFileConfiguration(@NotNull FileConfiguration config) {
        return new FileConfigurationLoader(config);
    }

    boolean existsFile();

    @NotNull @Unmodifiable Set<DuplicateKeyMessage> load() throws IOException;

    void save(@NotNull Iterable<? extends KeyedMessage> messages) throws IOException;

    @Nullable Locale getLocale();

    @Nullable Translation toTranslation();

    @NotNull Translation toTranslation(@NotNull Locale locale);

    @NotNull Map<String, MessageFormat> toMessageFormatMap();

    final class DuplicateKeyMessage {

        private final String key;
        private final String message;

        protected DuplicateKeyMessage(@NotNull String key, @NotNull String message) {
            this.key = Objects.requireNonNull(key);
            this.message = Objects.requireNonNull(message);
        }

        public @NotNull String getKey() {
            return key;
        }

        public @NotNull String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DuplicateKeyMessage that = (DuplicateKeyMessage) o;
            return key.equals(that.key) && message.equals(that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, message);
        }

        @Override
        public String toString() {
            return "DuplicateKeyMessage{" +
                    "key='" + key + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
