package com.imaginarycode.minecraft.bungeejson.impl.matchers;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONUtilities;
import com.imaginarycode.minecraft.bungeejson.api.RestMatcher;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord.RestPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerMatcher implements RestMatcher<RestPlayer> {
    private static final String PREFIX = "/player/";

    @Override
    public RestPlayer match(String path) {
        if (!path.startsWith(PREFIX))
            return null;

        // Strip it away and expose the actual contents
        String prefixless = path.substring(PREFIX.length());

        if (prefixless.contains("/"))
            prefixless = prefixless.substring(0, prefixless.indexOf('/'));

        ProxiedPlayer player = BungeeJSONUtilities.resolvePlayer(prefixless);

        return player == null ? null : new RestPlayer(player);
    }
}
