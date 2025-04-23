package com.galaxy.secret.server.boot;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.ArrayList;
import java.util.List;

public class NettyBoot {
    public void start(final int port, final List<String> addresses) throws InterruptedException {
        try {
            List<ChannelFuture> futures = startInternal(port, addresses);
            accept(futures);
        } finally {
            close();
        }
    }

    private void accept(final List<ChannelFuture> futures) throws InterruptedException {
        for (ChannelFuture future : futures) {
            future.channel().closeFuture().sync();
        }
    }
    private List<ChannelFuture> startInternal(final int port, final List<String> addresses) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        initServerBootstrap(bootstrap);
        List<ChannelFuture> futures = new ArrayList<>();
        for (String address : addresses) {
            futures.add(bootstrap.bind(address, port).sync());
        }
        return futures;
    }

    private void initServerBootstrap(final ServerBootstrap bootstrap) {
        bootstrap.group(bossGroup, workerGroup)
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024 * 1024, 16 * 1024 * 1024))
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 2)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerHandlerInitializer());
    }
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyBoot() {

        bossGroup = Epoll.isAvailable() ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
        workerGroup = Epoll.isAvailable() ? new EpollEventLoopGroup(2) : new NioEventLoopGroup(2);
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }


    private void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
