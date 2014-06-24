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
package com.imaginarycode.minecraft.bungeejson.impl.httpserver;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.imaginarycode.minecraft.bungeejson.BungeeJSONPlugin;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.GenericProgressiveFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import lombok.Getter;

import java.util.concurrent.ThreadFactory;

public class NettyBootstrap {
    private final ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("BungeeJSON Netty Thread #%d")
            .setDaemon(true).build();
    @Getter
    private ChannelFuture channelFuture;
    @Getter
    private EventLoopGroup group;

    public void initialize() {
        group = new NioEventLoopGroup(5, factory);
        int port = BungeeJSONPlugin.getPlugin().getConfig().getInt("http-server-port", 7432);
        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("messageCodec", new HttpServerCodec());
                        pipeline.addLast("messageHandler", new HttpServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        channelFuture = b.bind(port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                BungeeJSONPlugin.getPlugin().getLogger().info("BungeeJSON server started on " + channelFuture.channel().localAddress());
            }
        });
    }
}
