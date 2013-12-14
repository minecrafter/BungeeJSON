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
package com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONUtilities;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.RequestHandler;
import net.md_5.bungee.api.ProxyServer;

import java.util.Collections;

public class FindServerFor implements RequestHandler {
    @Override
    public Object handle(ApiRequest request) throws Throwable {
        if (request.getParams().containsKey("player")) {
            String pPlayer = request.getParams().get("player").get(0);
            if (ProxyServer.getInstance().getPlayer(pPlayer) != null) {
                return Collections.singletonMap("server", ProxyServer.getInstance().getPlayer(pPlayer).getServer().getInfo().getName());
            } else {
                return BungeeJSONUtilities.error("Player is not online.");
            }
        } else {
            return BungeeJSONUtilities.error("No player argument specified.");
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return true;
    }
}
