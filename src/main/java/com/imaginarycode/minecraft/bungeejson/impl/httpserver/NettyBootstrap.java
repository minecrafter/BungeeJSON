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

import java.util.concurrent.ThreadFactory;

public class NettyBootstrap extends Thread {
    private final ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("BungeeJSON Netty/HTTP Server - #%d")
            .setDaemon(true).build();

    public NettyBootstrap() {
        super("BungeeJSON Netty Bootstrap");
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup(5, factory);
        int port = BungeeJSONPlugin.getPlugin().getConfig().getInt("http-server-port", 7432);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.config().setAllocator(PooledByteBufAllocator.DEFAULT);
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("messageCodec", new HttpServerCodec());
                            pipeline.addLast("messageHandler", new HttpServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
