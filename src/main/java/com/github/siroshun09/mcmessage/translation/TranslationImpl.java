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

import com.github.siroshun09.mcmessage.message.TranslatedMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

class TranslationImpl implements Translation {

    private final Map<String, TranslatedMessage> messageMap;
    private final Locale locale;

    TranslationImpl(@NotNull Map<String, TranslatedMessage> messageMap, @NotNull Locale locale) {
        this.messageMap = messageMap;
        this.locale = locale;
    }

    @Override
    public @Nullable TranslatedMessage getMessage(@NotNull String key) {
        return messageMap.get(key);
    }

    @Override
    public @NotNull @Unmodifiable Set<TranslatedMessage> getMessages() {
        return Set.copyOf(messageMap.values());
    }

    @Override
    public @NotNull Locale getLocale() {
        return locale;
    }
}
