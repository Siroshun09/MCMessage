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

package com.github.siroshun09.mcmessage.message;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class KeyedMessageImpl implements KeyedMessage {

    private final String key;
    private final String message;

    KeyedMessageImpl(@NotNull String key, @NotNull String message) {
        this.key = Objects.requireNonNull(key);
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public @NotNull String getMessage() {
        return message;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyedMessageImpl that = (KeyedMessageImpl) o;
        return key.equals(that.key) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, message);
    }

    @Override
    public String toString() {
        return "KeyedMessageImpl{" +
                "key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
