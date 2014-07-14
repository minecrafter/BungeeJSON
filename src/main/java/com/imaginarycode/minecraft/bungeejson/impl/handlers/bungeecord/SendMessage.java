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
import com.imaginarycode.minecraft.bungeejson.api.RestAction;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SendMessage implements RestAction {
    @Override
    public Object handle(ApiRequest request) {
        if (request.getParams().containsKey("player") && request.getParams().containsKey("message")) {
            String pPlayer = request.getParams().get("player").get(0);
            String message = request.getParams().get("message").get(0);
            ProxiedPlayer pp = BungeeJSONUtilities.resolvePlayer(pPlayer);
            if (pp != null) {
                pp.sendMessage(BungeeJSONUtilities.singletonChatComponent(message));
                return BungeeJSONUtilities.ok();
            } else {
                return BungeeJSONUtilities.error("Player is not online.");
            }
        } else {
            return BungeeJSONUtilities.error("No player and/or message argument specified.");
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return true;
    }
}
