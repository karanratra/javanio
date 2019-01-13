package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.handler.ReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SingleThreadedSelectorNonBlockingServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();


        Handler<SelectionKey> accepthandler = new AcceptHandler(pendingData);
        Handler<SelectionKey> readhandler = new ReadHandler(pendingData);
        Handler<SelectionKey> writehandler = new WriteHandler(pendingData);

        Collection<SocketChannel> sockets = new ArrayList<>();
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accepthandler.handle(key);
                    } else if ((key.isReadable())) {
                        readhandler.handle(key);
                    } else if (key.isWritable()) {
                        writehandler.handle(key);
                    }

                }
            }

        }
    }

}


