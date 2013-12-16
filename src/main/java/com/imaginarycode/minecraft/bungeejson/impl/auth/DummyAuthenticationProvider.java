package com.imaginarycode.minecraft.bungeejson.impl.auth;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONPlugin;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.AuthenticationProvider;

public class DummyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public void onEnable() {
        BungeeJSONPlugin.getPlugin().getLogger().warning("You are using the dummy authentication provider.");
        BungeeJSONPlugin.getPlugin().getLogger().warning("Unless you secure BungeeJSON by some other means, you will likely be subject to nasty annoyances.");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean authenticate(ApiRequest ar, String uri) {
        return true;
    }
}
