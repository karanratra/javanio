package com.learning.networking.javanio.handler;

import com.learning.networking.javanio.utl.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TransmogrifyChannelHandler implements Handler<SocketChannel> {

    @Override
    public void handle(SocketChannel sc) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(80);
        int read = sc.read(buf);

        if(read == -1){
            sc.close();
            return;
        }

        if(read > 0){
            Util.transmogrify(buf);
            while(buf.hasRemaining()){
                sc.write(buf);
            }
            buf.compact();
        }

    }
}
