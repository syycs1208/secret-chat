package com.galaxy.secret.client.boot;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class ClientBoot {

    public ClientBoot() {
        bootstrap = new Bootstrap();
        workerGroup = Epoll.isAvailable() ? new EpollEventLoopGroup(2) : new NioEventLoopGroup(2);
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        startInternal();
    }


    private void startInternal() {

        initBootstrap(bootstrap);
    }

    private void initBootstrap(final Bootstrap bootstrap) {
        bootstrap.group(workerGroup)
                .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024 * 1024, 16 * 1024 * 1024))
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 2)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ClientHandlerInitializer());
    }
    private final EventLoopGroup workerGroup;

    private final Bootstrap bootstrap;

    public ChannelFuture connect(int port, String host) {
        return bootstrap.connect(host, port);
    }
    private void close() {
        workerGroup.shutdownGracefully();
    }
}
