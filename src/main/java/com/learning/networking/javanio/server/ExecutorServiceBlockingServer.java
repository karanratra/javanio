package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ExecutorServiceBlockingServer {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);

        Handler<Socket> handler = new ExecutorServiceHandler<>(
                new PrintingHandler<>(
                        new TransmogrifyHandler()
                ), Executors.newFixedThreadPool(10),(t,e) -> System.out.println("Uncaught: " + t + " error " + e)
            );

        while (true) {
            Socket s = ss.accept(); //never null
            handler.handle(s);
        }
    }


}
