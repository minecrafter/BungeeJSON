package com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord;

import com.imaginarycode.minecraft.bungeejson.BungeeJSONUtilities;
import com.imaginarycode.minecraft.bungeejson.api.RestAction;
import com.imaginarycode.minecraft.bungeejson.api.RestObject;
import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RestPlayer implements RestObject {
    private final ProxiedPlayer player;

    @Override
    public List<RestAction> applicableActions() {
        return null;
    }

    @Override
    public Object handleDirect(HttpMethod method) {
        if (method == HttpMethod.GET) {
            ConnectedPlayer c = new ConnectedPlayer();
            c.name = player.getName();
            c.address = player.getAddress().getAddress();
            c.server = player.getServer().getInfo().getName();
            c.uuid = player.getUniqueId();
            return c;
        }
        // Unsupported.
        return BungeeJSONUtilities.error("Action not recognized.");
    }

    private class ConnectedPlayer {
        private String name;
        private InetAddress address;
        private String server;
        private UUID uuid;
    }
}
