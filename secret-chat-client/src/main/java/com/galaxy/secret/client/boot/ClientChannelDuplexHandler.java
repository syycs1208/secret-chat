package com.galaxy.secret.client.boot;

import com.galaxy.secret.model.Request;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ClientChannelDuplexHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof Request) {
                System.out.println("Request");
            }
            System.out.println(msg.getClass().getName() + msg);
        } catch (Exception e){

        } finally {
        }
    }



    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // super.write(ctx, msg, promise);
        try {
            System.out.println(msg.getClass().getName() + msg);
        } catch (Exception e){

        } finally {

        }
    }

}
