package com.learning.networking.javanio.handler;

import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.utl.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

public class ReadHandler implements Handler<SelectionKey> {

    private Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public ReadHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {

        SocketChannel sc = (SocketChannel) selectionKey.channel();

        ByteBuffer buf = ByteBuffer.allocateDirect(80);

        int read = sc.read(buf);

        if(read == -1 ){
            pendingData.remove(sc);
            sc.close();
            System.out.println("Disconnected from " + sc + " (in read())");
            return;
    }
        if(read > 0 ){
            Util.transmogrify(buf);
            pendingData.get(sc).add(buf);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }



    }
}
