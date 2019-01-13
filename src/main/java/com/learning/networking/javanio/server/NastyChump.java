package com.learning.networking.javanio.server;

import java.io.IOException;
import java.net.Socket;

public class NastyChump {
    public static void main(String[] args) throws IOException, InterruptedException
            {

        Socket[] socket = new Socket[3000];

        for (int i = 0; i < 100 ; i++) {
            socket[i] = new Socket("localhost",8080);
        }
        Thread.sleep(100_000);
    }
}
