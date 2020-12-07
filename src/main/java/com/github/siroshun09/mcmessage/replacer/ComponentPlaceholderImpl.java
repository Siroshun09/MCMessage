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

package com.github.siroshun09.mcmessage.replacer;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

class ComponentPlaceholderImpl<T, C extends Component> implements ComponentPlaceholder<T, C> {

    private final String placeholder;
    private final Function<T, C> function;

    ComponentPlaceholderImpl(@NotNull String placeholder, @NotNull Function<T, C> function) {
        this.placeholder = Objects.requireNonNull(placeholder);
        this.function = Objects.requireNonNull(function);
    }

    @Override
    public @NotNull String getPlaceholder() {
        return placeholder;
    }

    @Override
    public @NotNull C createComponent(T value) {
        return function.apply(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof ComponentPlaceholder) {
            ComponentPlaceholder<?, ?> that = (ComponentPlaceholder<?, ?>) o;
            return Objects.equals(getPlaceholder(), that.getPlaceholder())
                    && Objects.equals(getFunction(), that.getFunction());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceholder(), getFunction());
    }

    @Override
    public String toString() {
        return "ComponentPlaceholderImpl{" +
                "placeholder='" + placeholder + '\'' +
                ", function=" + function +
                '}';
    }
}
