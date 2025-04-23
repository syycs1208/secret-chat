package com.galaxy.secret.server;

import com.galaxy.secret.server.boot.NettyBoot;

import java.util.Collections;

public class Application {


    public static void main(String[] args) {
        System.out.println("234234");

        NettyBoot s = new NettyBoot();
        try {
            s.start(8086, Collections.singletonList("192.168.0.2"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
