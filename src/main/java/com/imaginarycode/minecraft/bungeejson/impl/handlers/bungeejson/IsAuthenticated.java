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
package com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONPlugin;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.RequestHandler;

import java.util.Collections;

public class IsAuthenticated implements RequestHandler {
    @Override
    public Object handle(ApiRequest request) throws Throwable {
        return Collections.singletonMap("is_authenticated", BungeeJSONPlugin.getPlugin().getAuthenticationProvider()
                .authenticate(request, "/bungeejson/is_authenticated"));
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
