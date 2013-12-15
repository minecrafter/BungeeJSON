package com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord;

import com.google.common.collect.ImmutableMap;
import com.imaginarycode.minecraft.bungeejson.BungeeJSONUtilities;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.RequestHandler;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;

import java.net.InetAddress;
import java.util.*;

public class InvokeCommand implements RequestHandler {
    @Override
    public Object handle(ApiRequest request) {
        if (request.getParams().containsKey("command")) {
            BungeeJSONInvokeCommandSender commandSender = new BungeeJSONInvokeCommandSender(request.getRemoteIp());
            ProxyServer.getInstance().getPluginManager().dispatchCommand(commandSender, request.getParams().get("command").get(0));
            return ImmutableMap.of("status", "OK", "result", commandSender.getOutput());
        } else {
            return BungeeJSONUtilities.error("No command specified.");
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return true;
    }

    private class BungeeJSONInvokeCommandSender implements CommandSender {
        @Getter List<String> output = new ArrayList<>();
        InetAddress ia;

        public BungeeJSONInvokeCommandSender(InetAddress ia) {
            this.ia = ia;
        }

        @Override
        public String getName() {
            return ia.toString();
        }

        @Override
        public void sendMessage(String s) {
            output.add(ChatColor.stripColor(s));
        }

        @Override
        public void sendMessages(String... strings) {
            for (String string : strings)
                sendMessage(string);
        }

        @Override
        public void sendMessage(BaseComponent... baseComponents) {
            for (BaseComponent component : baseComponents) {
                output.add(component.toPlainText());
            }
        }

        @Override
        public void sendMessage(BaseComponent baseComponent) {
            output.add(baseComponent.toPlainText());
        }

        @Override
        public Collection<String> getGroups() {
            return Collections.emptySet();
        }

        @Override
        public void addGroups(String... strings) {
        }

        @Override
        public void removeGroups(String... strings) {
        }

        @Override
        public boolean hasPermission(String s) {
            return true;
        }

        @Override
        public void setPermission(String s, boolean b) {
        }
    }
}
