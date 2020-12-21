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

package com.github.siroshun09.mcmessage.test.replacer;

import com.github.siroshun09.mcmessage.builder.PlainTextBuilder;
import com.github.siroshun09.mcmessage.replacer.Placeholder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlaceholderTest {

    private static final Placeholder PLACEHOLDER_A = Placeholder.create("%a%");
    private static final Placeholder PLACEHOLDER_B = Placeholder.create("%b%");
    private static final Placeholder PLACEHOLDER_A_2 = Placeholder.create("%a%");
    private static final String REPLACEMENT = "REPLACED";
    private static final String ORIGINAL = "Original ";
    private static final String ORIGINAL_A = ORIGINAL + "%a%";
    private static final String ORIGINAL_A_B = ORIGINAL_A + "%b%";

    @Test
    void testToReplacer() {
        var replaced1 = PLACEHOLDER_A.toReplacer(REPLACEMENT).replace(ORIGINAL_A);
        Assertions.assertEquals(ORIGINAL + REPLACEMENT, replaced1);

        var replaced2 = PLACEHOLDER_B.toReplacer(REPLACEMENT).replace(ORIGINAL_A);
        Assertions.assertEquals(ORIGINAL_A, replaced2);

        var replaced3 =
                new PlainTextBuilder(ORIGINAL_A_B)
                        .replace(PLACEHOLDER_A, REPLACEMENT)
                        .replace(PLACEHOLDER_B, REPLACEMENT)
                        .build()
                        .get();
        Assertions.assertEquals(ORIGINAL + REPLACEMENT + REPLACEMENT, replaced3);
    }

    @Test
    void testEquals() {
        // reflexive
        Assertions.assertEquals(PLACEHOLDER_A, PLACEHOLDER_A);

        Assertions.assertNotEquals(PLACEHOLDER_A, PLACEHOLDER_B);
        Assertions.assertNotEquals(PLACEHOLDER_B, PLACEHOLDER_A_2);
        Assertions.assertEquals(PLACEHOLDER_A, PLACEHOLDER_A_2);
        Assertions.assertEquals(PLACEHOLDER_A_2, PLACEHOLDER_A);

        // symmetric
        Assertions.assertEquals(PLACEHOLDER_A.equals(PLACEHOLDER_A_2), PLACEHOLDER_A_2.equals(PLACEHOLDER_A));
        Assertions.assertEquals(PLACEHOLDER_B.equals(PLACEHOLDER_A), PLACEHOLDER_A.equals(PLACEHOLDER_B));

        var placeholder_a_3 = Placeholder.create("%a%");

        // transitive
        Assertions.assertEquals(
                PLACEHOLDER_A.equals(placeholder_a_3) == placeholder_a_3.equals(PLACEHOLDER_A_2),
                PLACEHOLDER_A.equals(PLACEHOLDER_A_2)
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertNotEquals(PLACEHOLDER_A.hashCode(), PLACEHOLDER_B.hashCode());
        Assertions.assertNotEquals(PLACEHOLDER_B.hashCode(), PLACEHOLDER_A_2.hashCode());
        Assertions.assertEquals(PLACEHOLDER_A.hashCode(), PLACEHOLDER_A_2.hashCode());
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Test
    void testNullArguments() {
        Assertions.assertThrows(Throwable.class, () -> Placeholder.create(null));
        Assertions.assertThrows(Throwable.class, () -> PLACEHOLDER_A.toReplacer(null));
        Assertions.assertThrows(Throwable.class, () -> PLACEHOLDER_A.toTextReplacementConfig(null));
        Assertions.assertDoesNotThrow(() -> PLACEHOLDER_A.equals(null));
    }
}
