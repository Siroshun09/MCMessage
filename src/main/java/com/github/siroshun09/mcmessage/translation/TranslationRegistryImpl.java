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

import com.github.siroshun09.mcmessage.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

class TranslationRegistryImpl implements TranslationRegistry {

    private final Translation def;
    private final Map<Locale, Translation> translations = new HashMap<>();

    TranslationRegistryImpl(@NotNull Translation def) {
        this.def = Objects.requireNonNull(def);
    }

    @Override
    public void register(@NotNull Translation translation) {
        Objects.requireNonNull(translation);

        synchronized (translations) {
            translations.put(translation.getLocale(), translation);
        }
    }

    @Override
    public void unregister(@NotNull Locale locale) {
        Objects.requireNonNull(locale);

        synchronized (translations) {
            translations.remove(locale);
        }
    }

    @Override
    public void unregister(@NotNull Translation translation) {
        Objects.requireNonNull(translation);

        synchronized (translations) {
            translations.remove(translation.getLocale(), translation);
        }
    }

    @Override
    public void unregisterAll() {
        synchronized (translations) {
            translations.clear();
        }
    }

    @Override
    public @NotNull Translation getDefault() {
        return def;
    }

    @Override
    public @Nullable Translation getTranslation(@NotNull Locale locale) {
        return translations.get(locale);
    }

    @Override
    public @NotNull Collection<Translation> getTranslations() {
        return translations.values();
    }

    @Override
    public @Nullable Message getMessage(@NotNull String key, @NotNull Locale locale) {
        Translation translation = translations.get(locale);
        return translation != null ? translation.getMessage(key) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof TranslationRegistryImpl) {
            TranslationRegistryImpl that = (TranslationRegistryImpl) o;
            return def.equals(that.def) && translations.equals(that.translations);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(def, translations);
    }

    @Override
    public String toString() {
        return "TranslationRegistryImpl{" +
                "def=" + def +
                ", translations=" + translations +
                '}';
    }
}
