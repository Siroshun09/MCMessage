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

import com.github.siroshun09.configapi.common.FileConfiguration;
import com.github.siroshun09.configapi.common.util.FileUtils;
import com.github.siroshun09.mcmessage.loader.MessageLoader;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTranslationLoader implements TranslationLoader {

    private final Path directory;
    private final Key registryKey;
    private final Locale defaultLocale;
    private final String fileExtension;
    private final Set<Locale> loadedLocales = new HashSet<>();

    private TranslationRegistry registry;

    protected AbstractTranslationLoader(@NotNull Path directory, @NotNull Key registryKey,
                                        @NotNull Locale defaultLocale) {
        this(directory, registryKey, defaultLocale, ".yml");
    }

    protected AbstractTranslationLoader(@NotNull Path directory, @NotNull Key registryKey,
                                        @NotNull Locale defaultLocale, @NotNull String fileExtension) {
        this.directory = directory;
        this.registryKey = registryKey;
        this.defaultLocale = defaultLocale;
        this.fileExtension = fileExtension;
    }

    @Override
    public void load() throws IOException {
        registry = TranslationRegistry.create(registryKey);
        registry.defaultLocale(defaultLocale);

        FileUtils.createDirectoriesIfNotExists(directory);

        saveDefaultIfNotExists();

        loadDefault();
        loadCustom();

        GlobalTranslator.get().addSource(registry);
    }

    @Override
    public void reload() throws IOException {
        if (registry != null) {
            GlobalTranslator.get().removeSource(registry);
        }

        load();
    }

    @Override
    public @NotNull @Unmodifiable Set<Locale> getLoadedLocales() {
        return Set.copyOf(loadedLocales);
    }

    protected abstract void saveDefaultIfNotExists();

    protected abstract @NotNull FileConfiguration createFileConfiguration(@NotNull Path path);

    private void loadDefault() throws IOException {
        var defaultFileName = defaultLocale + fileExtension;
        var defaultFile = directory.resolve(defaultFileName);

        loadFile(defaultFile);
    }

    private void loadCustom() throws IOException {
        try (var files = Files.list(directory)) {
            var languageFiles = files.filter(Files::isRegularFile)
                    .filter(p -> !p.toString().endsWith(defaultLocale + fileExtension))
                    .collect(Collectors.toUnmodifiableSet());

            for (var file : languageFiles) {
                loadFile(file);
            }
        }
    }

    private void loadFile(@NotNull Path path) throws IOException {
        var file = createFileConfiguration(path);
        var loader = MessageLoader.fromFileConfiguration(file);

        loader.load();
        loader.registerToRegistry(registry);

        Optional.ofNullable(loader.getLocale()).ifPresent(loadedLocales::add);
    }
}
