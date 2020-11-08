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

import com.github.siroshun09.mcmessage.message.DefaultMessage;
import com.github.siroshun09.mcmessage.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Locale;

public interface TranslationRegistry {

    static TranslationRegistry create() {
        return new TranslationRegistryImpl();
    }

    void register(@NotNull Translation translation);

    void unregister(@NotNull Locale locale);

    void unregister(@NotNull Translation translation);

    void unregisterAll();

    @Nullable Translation getTranslation(@NotNull Locale locale);

    @NotNull Collection<Translation> getTranslations();

    @Nullable Message getMessage(@NotNull String key, @NotNull Locale locale);

    default @NotNull Message getMessage(@NotNull DefaultMessage def, @NotNull Locale locale) {
        return getMessage(def.getKey(), def.getDefault(), locale);
    }

    default @NotNull Message getMessage(@NotNull String key, @NotNull String def, @NotNull Locale locale) {
        Message message = getMessage(key, locale);
        return message != null ? message : Message.of(def);
    }
}
