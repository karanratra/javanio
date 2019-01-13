package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

public class BlockingNIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));

        Handler<SocketChannel> handler = new ExecutorServiceHandler<>(
                new PrintingHandler<>(
                        new BLockingChannelHandler(
                                new TransmogrifyChannelHandler()
                        )
                ),
                Executors.newFixedThreadPool(10),
                (t, e) -> System.out.println("Uncaught: " + t + " error " + e));

        while (true) {
            SocketChannel s = ssc.accept(); //never null
            handler.handle(s);
        }
    }


}


