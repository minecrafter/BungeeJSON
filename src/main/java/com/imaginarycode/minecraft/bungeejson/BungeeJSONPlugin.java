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

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.imaginarycode.minecraft.bungeejson.api.AuthenticationProvider;
import com.imaginarycode.minecraft.bungeejson.api.RequestManager;
import com.imaginarycode.minecraft.bungeejson.impl.auth.ApiKeyAuthenticationProvider;
import com.imaginarycode.minecraft.bungeejson.impl.auth.DummyAuthenticationProvider;
import com.imaginarycode.minecraft.bungeejson.impl.auth.IpBasedAuthenticationProvider;
import com.imaginarycode.minecraft.bungeejson.impl.httpserver.NettyBootstrap;
import com.imaginarycode.minecraft.bungeejson.impl.BungeeJSONRequestManager;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord.*;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson.IsAuthenticated;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson.Version;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class BungeeJSONPlugin extends Plugin {
    private NettyBootstrap nb = new NettyBootstrap();
    protected static BungeeJSONPlugin plugin;
    @Getter
    private static RequestManager requestManager = new BungeeJSONRequestManager();
    @Getter
    private AuthenticationProvider authenticationProvider = new ApiKeyAuthenticationProvider();
    @Getter
    private Gson gson = new Gson();
    @Getter
    private Configuration config = null;

    public static BungeeJSONPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration file", e);
        }
        plugin = this;
        requestManager.registerEndpoint("/bungeecord/connect", new Connect());
        requestManager.registerEndpoint("/bungeecord/find_server_for", new FindServerFor());
        requestManager.registerEndpoint("/bungeecord/invoke_command", new InvokeCommand());
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
        switch (getConfig().getString("auth-type", "api-key")) {
            case "none":
            case "dummy":
                authenticationProvider = new DummyAuthenticationProvider();
                break;
            case "ipbased":
            case "ip":
            case "ip-based":
                authenticationProvider = new IpBasedAuthenticationProvider();
                break;
            case "key":
            case "apikey":
            case "api-key":
                break;
            default:
                getLogger().info(getConfig().getString("auth-type") + " authentication is not known to this plugin, using api-key auth.");
                break;
        }
        authenticationProvider.onEnable();
        nb.start();
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration file", e);
        }
    }
}
