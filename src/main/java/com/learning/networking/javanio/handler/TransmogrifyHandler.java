package com.learning.networking.javanio.handler;

import com.learning.networking.javanio.utl.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransmogrifyHandler implements Handler<Socket> {

    @Override
    public void handle(Socket s) throws IOException {

        try (Socket temp = s;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream();
        ){
            //Just echo the message
            //transferTo(in, out);

            //When we have to do the processing
            int data;
            while ((data = in.read()) != -1){
                if(data == '%') throw new IOException("Oopsie !!");
                out.write(Util.transmogrify(data));
            }
        }
    }
}
