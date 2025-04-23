package com.galaxy.secret.client;

import com.galaxy.secret.client.boot.ClientBoot;
import com.galaxy.secret.model.Request;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Application {


    public static void main(String[] args) {
        System.out.println("234234");

        ClientBoot s = new ClientBoot();
        try {
            ChannelFuture future = s.connect(8086, "192.168.0.2");
            future.sync();
            Channel c = future.channel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String line = null;
            while (null != (line = reader.readLine())) {
                if (line.contains("-1")) {
                    break;
                }
                c.write(Request.sample());
            }

            c.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
