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

package com.github.siroshun09.mcmessage.test.loader;

import com.github.siroshun09.configapi.yaml.YamlConfiguration;
import com.github.siroshun09.mcmessage.loader.MessageLoader;
import com.github.siroshun09.mcmessage.message.TranslatedMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileConfigurationLoaderTest {

    @Test
    void yamlTest() throws Exception {
        var resourceUrl = getClass().getClassLoader().getResource("example.yml");

        assertNotNull(resourceUrl);

        var path = Path.of(resourceUrl.toURI());
        var config = YamlConfiguration.create(path);
        var loader = MessageLoader.fromFileConfiguration(config);

        var duplicate = assertDoesNotThrow(loader::load);
        assertTrue(duplicate.isEmpty());

        var translation = loader.toTranslation(Locale.getDefault());
        assertEquals(getExpected(), translation.getMessages());
    }


    private @NotNull Set<TranslatedMessage> getExpected() {
        var set = new HashSet<TranslatedMessage>();

        set.add(create("message1", "test1"));
        set.add(create("messages.message2", "test2"));
        set.add(create("messages.message3", "test3"));
        set.add(create("messages.child1.message4", "test4"));
        set.add(create("messages.child1.message5", "test5"));
        set.add(create("messages.child2.message4", "test4"));
        set.add(create("messages.child2.message5", "test5"));
        set.add(create("number1", "1053"));
        set.add(create("number2", "14.51"));

        return set;
    }

    private @NotNull TranslatedMessage create(@NotNull String key, @NotNull String msg) {
        return TranslatedMessage.create(key, msg, Locale.getDefault());
    }
}
