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

package com.github.siroshun09.mcmessage.test.builder;

import com.github.siroshun09.mcmessage.message.Message;
import com.github.siroshun09.mcmessage.replacer.FunctionalPlaceholder;
import com.github.siroshun09.mcmessage.replacer.Placeholder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuilderTest {

    private static final Message ORIGINAL_1 = () -> "&7username: &#123456%user% &8(#%id%, %id%, %id)";
    private static final String EXCEPT_1 = "[Prefix] §7username: §x§1§2§3§4§5§6test §8(#10, 10, %id) [Suffix]";
    private static final String USERNAME = "test";
    private static final String PREFIX = "[Prefix] ";
    private static final String SUFFIX = " [Suffix]";
    private static final int ID = 10;
    private static final Placeholder USERNAME_PLACEHOLDER = () -> "%user%";
    private static final FunctionalPlaceholder<Integer> ID_PLACEHOLDER = FunctionalPlaceholder.create("%id%", String::valueOf);

    @Test
    void plainTextBuilderTest() {
        Message message =
                ORIGINAL_1.toPlainTextBuilder()
                        .addPrefix(PREFIX)
                        .append(SUFFIX)
                        .replace(USERNAME_PLACEHOLDER.toReplacer(USERNAME))
                        .replace(ID_PLACEHOLDER.toReplacer(ID))
                        .setColorize(true)
                        .build();
        Assertions.assertEquals(EXCEPT_1, message.get());
    }
}
