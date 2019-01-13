package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.utl.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class WriteHandler implements Handler<SelectionKey> {

    private Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        Queue<ByteBuffer> queue = pendingData.get(sc);
        while (!queue.isEmpty()){
            ByteBuffer buf = queue.peek();
            int written = sc.write(buf);
            if(written == -1){
                sc.close();
                pendingData.remove(sc);
                System.out.println("Disconnected from " + sc + " (in write())");
                return;
            }
            if(buf.hasRemaining()) return;
            queue.remove();

        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
