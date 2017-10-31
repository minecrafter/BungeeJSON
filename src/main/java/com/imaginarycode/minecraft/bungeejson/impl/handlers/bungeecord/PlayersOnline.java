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

import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.RequestHandler;
import lombok.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayersOnline implements RequestHandler {
    @Override
    public Object handle(ApiRequest request) {
        List<Player> players = new ArrayList<>();
        for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            Player player = new Player();
            player.setName(pp.getName());
            player.setUuid(pp.getUniqueId());
            player.setServerName(pp.getServer().getInfo().getName());
            players.add(player);
        }
        return players;
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }

    @Data
    private class Player {
        private String name;
        private UUID uuid;
        private String serverName;
    }
}
