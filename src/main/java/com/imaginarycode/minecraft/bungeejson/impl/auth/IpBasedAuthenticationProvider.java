package com.imaginarycode.minecraft.bungeejson.impl.auth;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONPlugin;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.AuthenticationProvider;

import java.util.Collections;
import java.util.List;

public class IpBasedAuthenticationProvider implements AuthenticationProvider {
    @Override
    public void onEnable() {
        if (BungeeJSONPlugin.getPlugin().getConfig().get("authenticated-ips") == null ||
                !(BungeeJSONPlugin.getPlugin().getConfig().get("authenticated-ips") instanceof List)) {
            BungeeJSONPlugin.getPlugin().getLogger().info("No IPs are defined in your configuration.");
            BungeeJSONPlugin.getPlugin().getLogger().info("I will add the section for you.");
            BungeeJSONPlugin.getPlugin().getConfig().set("authenticated-ips", Collections.singletonList("127.0.0.1"));
            BungeeJSONPlugin.getPlugin().saveConfig();
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean authenticate(ApiRequest ar, String uri) {
        return ar.getRemoteIp().isLoopbackAddress() ||
                (BungeeJSONPlugin.getPlugin().getConfig().getStringList("authenticated-ips")
                        .contains(ar.getRemoteIp().getHostAddress()));
    }
}
