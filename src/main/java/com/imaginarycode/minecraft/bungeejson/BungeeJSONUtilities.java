/**
 * This file is part of BungeeJSON.
 *
 * BungeeJSON is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeJSON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeJSON.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.imaginarycode.minecraft.bungeejson;

import net.md_5.bungee.api.chat.*;

import java.util.Collections;
import java.util.Map;

public class BungeeJSONUtilities {
    private BungeeJSONUtilities() {}

    private static final Map<String, String> STATUS_OK = Collections.singletonMap("status", "OK");

    public static Map<String, String> error(String error) {
        return Collections.singletonMap("error", error);
    }

    public static Map<String, String> ok() {
        return STATUS_OK;
    }

    public static BaseComponent singletonChatComponent(String text) {
        TextComponent component = new TextComponent();
        component.setText(text);
        return component;
    }
}
