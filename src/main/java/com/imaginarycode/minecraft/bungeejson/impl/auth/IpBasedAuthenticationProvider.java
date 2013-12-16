package com.imaginarycode.minecraft.bungeejson.impl.auth;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONPlugin;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.AuthenticationProvider;

public class IpBasedAuthenticationProvider implements AuthenticationProvider {
    @Override
    public void onEnable() {
        if (!BungeeJSONPlugin.getPlugin().getConfig().contains("authenticated-ips") ||
                !BungeeJSONPlugin.getPlugin().getConfig().isList("authenticated-ips")) {
            BungeeJSONPlugin.getPlugin().getLogger().info("No IPs are defined in your configuration.");
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean authenticate(ApiRequest ar, String uri) {
        if (ar.getRemoteIp().isLoopbackAddress()) return true; // localhost can be trusted
        return BungeeJSONPlugin.getPlugin().getConfig().contains("authenticated-ips") &&
                BungeeJSONPlugin.getPlugin().getConfig().isList("authenticated-ips") &&
                BungeeJSONPlugin.getPlugin().getConfig().getStringList("authenticated-ips").contains(ar.getRemoteIp().getHostAddress());
    }
}
