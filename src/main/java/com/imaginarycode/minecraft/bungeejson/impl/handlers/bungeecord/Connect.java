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
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Connect implements RequestHandler {
    @Override
    public Object handle(ApiRequest request) {
        // Verify if we have our 2 parameters:
        if (request.getParams().containsKey("player") && request.getParams().containsKey("server")) {
            String pPlayer = request.getParams().get("player").get(0);
            String pServer = request.getParams().get("server").get(0);
            if (ProxyServer.getInstance().getPlayer(pPlayer) != null) {
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(pPlayer);
                if (ProxyServer.getInstance().getServerInfo(pServer) != null) {
                    ServerInfo si = ProxyServer.getInstance().getServerInfo(pServer);
                    if (pp.getServer().getInfo().equals(si)) {
                        return BungeeJSONUtilities.error("Player is already connected to this server.");
                    }
                    pp.connect(si);
                    return BungeeJSONUtilities.ok();
                } else {
                    return BungeeJSONUtilities.error("The server specified is not valid.");
                }
            } else
                return BungeeJSONUtilities.error("This player is not online.");
        } else {
            return BungeeJSONUtilities.error("At least one parameter is missing. player and server are required arguments.");
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return true;
    }
}
