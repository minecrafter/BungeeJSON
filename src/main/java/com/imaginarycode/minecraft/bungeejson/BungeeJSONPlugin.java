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

import com.google.gson.Gson;
import com.imaginarycode.minecraft.bungeejson.api.AuthenticationProvider;
import com.imaginarycode.minecraft.bungeejson.api.RequestManager;
import com.imaginarycode.minecraft.bungeejson.httpserver.NettyBootstrap;
import com.imaginarycode.minecraft.bungeejson.impl.BungeeJSONRequestManager;
import com.imaginarycode.minecraft.bungeejson.impl.auth.SimpleKeyAuthentication;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord.*;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson.IsAuthenticated;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson.Version;
import lombok.Getter;
import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

public class BungeeJSONPlugin extends ConfigurablePlugin {
    private NettyBootstrap nb = new NettyBootstrap();
    protected static BungeeJSONPlugin plugin;
    @Getter
    private static RequestManager requestManager = new BungeeJSONRequestManager();
    @Getter
    private AuthenticationProvider authenticationProvider = new SimpleKeyAuthentication();
    @Getter
    private Gson gson = new Gson();

    public static BungeeJSONPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void onLoading() {
        saveDefaultConfig();
        plugin = this;
        getLogger().info("Pre-registering handlers...");
        requestManager.registerEndpoint("/bungeecord/connect", new Connect());
        requestManager.registerEndpoint("/bungeecord/find_server_for", new FindServerFor());
        requestManager.registerEndpoint("/bungeecord/kick_player", new KickPlayer());
        requestManager.registerEndpoint("/bungeecord/player_count", new PlayerCount());
        requestManager.registerEndpoint("/bungeecord/players_online", new PlayersOnline());
        requestManager.registerEndpoint("/bungeecord/send_message", new SendMessage());
        requestManager.registerEndpoint("/bungeecord/server_list", new ServerList());
        requestManager.registerEndpoint("/bungeejson/version", new Version());
        requestManager.registerEndpoint("/bungeejson/is_authenticated", new IsAuthenticated());
    }

    @Override
    public void onEnable() {
        authenticationProvider.onEnable();
        nb.start();
    }
}
