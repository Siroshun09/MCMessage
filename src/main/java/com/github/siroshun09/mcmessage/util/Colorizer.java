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

package com.github.siroshun09.mcmessage.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public final class Colorizer {

    private static final String EMPTY = "";
    private static final char COLOR_MARK = '&';
    private static final char COLOR_SECTION = '§';
    private static final char HEX_MARK = '#';
    private static final char X = 'x';
    private static final String COLOR_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
    private static final Pattern COLOR_SECTION_PATTERN = Pattern.compile("(?i)" + COLOR_SECTION + "[0-9A-FK-ORX]");
    private static final Pattern COLOR_MARK_PATTERN =
            Pattern.compile(COLOR_MARK + "((" + HEX_MARK + "[0-9a-fA-F]{6})|([0-9a-fk-orxA-FK-ORX]))");

    private Colorizer() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public static String colorize(String str) {
        if (str == null || str.isEmpty()) {
            return EMPTY;
        }

        char[] b = str.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < b.length; i++) {
            if (b[i] != COLOR_MARK || b.length == i + 1) {
                builder.append(b[i]);
                continue;
            }

            if (b[i + 1] == HEX_MARK) {
                if (i + 7 < b.length) {
                    boolean added = addHexColor(str.substring(i + 1, i + 8), builder);

                    if (added) {
                        i += 7;
                    } else {
                        builder.append(b[i]).append(b[i + 1]);
                        i++;
                    }
                }

                continue;
            }

            if (-1 < COLOR_CODES.indexOf(b[i + 1])) {
                builder.append(COLOR_SECTION).append(Character.toLowerCase(b[i + 1]));
                i++;
            } else {
                builder.append(b[i]);
            }
        }

        return builder.toString();
    }

    /**
     * Strips the given string of color.
     * <p>
     * Example: {@code §aHello, §bWorld!} {@literal ->} {@code Hello, World!}
     *
     * @param str the string to strip
     * @return stripped string
     */
    @NotNull
    public static String stripColorCode(String str) {
        return stripColorCode(str, COLOR_SECTION_PATTERN);
    }

    @NotNull
    public static String stripMarkedColorCode(String str) {
        return stripColorCode(str, COLOR_MARK_PATTERN);
    }

    @NotNull
    public static String stripColorCode(String str, @NotNull Pattern pattern) {
        if (str == null || str.isEmpty()) {
            return EMPTY;
        }

        return pattern.matcher(str).replaceAll("");
    }

    private static boolean addHexColor(String hex, StringBuilder builder) {
        char[] array = hex.toCharArray();

        if (array.length != 7 || array[0] != HEX_MARK) {
            return false;
        }

        try {
            Integer.parseInt(hex.substring(1), 16);
        } catch (NumberFormatException e) {
            return false;
        }

        builder.append(COLOR_SECTION).append(X);

        for (char c : hex.substring(1).toCharArray()) {
            builder.append(COLOR_SECTION).append(c);
        }

        return true;
    }
}
