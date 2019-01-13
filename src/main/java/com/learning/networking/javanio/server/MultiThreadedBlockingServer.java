package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedBlockingServer {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);

        Handler<Socket> handler = new ThreadedHandler<>(
                new PrintingHandler<>(
                        new TransmogrifyHandler()
                )
            );

        while (true) {
            Socket s = ss.accept(); //never null
            handler.handle(s);
        }
    }


}
