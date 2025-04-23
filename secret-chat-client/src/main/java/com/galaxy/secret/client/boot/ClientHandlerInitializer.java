package com.galaxy.secret.client.boot;

import com.galaxy.secret.common.decoder.MessageDecoder;
import com.galaxy.secret.common.encoder.MessageEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class ClientHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {

        channel.pipeline().addLast("decoder", new MessageDecoder())
                .addLast("encoder", new MessageEncoder())
                 .addLast("handler", new ClientChannelDuplexHandler())
        ;
    }
}
