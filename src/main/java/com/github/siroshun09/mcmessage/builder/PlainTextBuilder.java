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

package com.github.siroshun09.mcmessage.builder;

import com.github.siroshun09.mcmessage.MessageReceiver;
import com.github.siroshun09.mcmessage.message.Message;
import com.github.siroshun09.mcmessage.replacer.Replacer;
import com.github.siroshun09.mcmessage.util.Colorizer;
import org.jetbrains.annotations.NotNull;

public class PlainTextBuilder implements Builder<Message> {

    private final Message original;
    private String str;
    private boolean colorize;

    public PlainTextBuilder(@NotNull Message original) {
        this.original = original;
    }

    @NotNull
    public PlainTextBuilder replace(@NotNull Replacer replacer) {
        str = replacer.replace(getString());
        return this;
    }

    @NotNull
    public PlainTextBuilder setColorize(boolean colorize) {
        this.colorize = colorize;
        return this;
    }

    @Override
    public @NotNull Message build() {
        if (colorize) {
            str = Colorizer.colorize(getString());
        }

        return this::getString;
    }

    public void send(@NotNull MessageReceiver messageReceiver) {
        messageReceiver.sendMessage(build());
    }

    @NotNull
    private String getString() {
        if (str == null) {
            str = original.get();
        }

        return str;
    }
}
