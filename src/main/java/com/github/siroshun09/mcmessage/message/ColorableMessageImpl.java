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

import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class ColorableMessageImpl implements ColorableMessage {

    private final String message;
    private final TextColor color;

    ColorableMessageImpl(@NotNull String message, @NotNull TextColor color) {
        this.message = Objects.requireNonNull(message);
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public @NotNull String getMessage() {
        return message;
    }

    @Override
    public @NotNull TextColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorableMessageImpl that = (ColorableMessageImpl) o;
        return message.equals(that.message) && color.equals(that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, color);
    }

    @Override
    public String toString() {
        return "ColorableMessageImpl{" +
                "message='" + message + '\'' +
                ", color=" + color +
                '}';
    }
}
