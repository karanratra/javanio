package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.handler.PooledReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectorWithWorkPoolNonBlockingServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ExecutorService pool = Executors.newFixedThreadPool(10);
        Map<SocketChannel, Queue<ByteBuffer>> pendingData = new ConcurrentHashMap<>();
        Queue<Runnable> selectorActions = new ConcurrentLinkedDeque<>();

        Handler<SelectionKey> accepthandler = new AcceptHandler(pendingData);
        Handler<SelectionKey> readhandler = new PooledReadHandler(pool, selectorActions, pendingData);

        Handler<SelectionKey> writehandler = new WriteHandler(pendingData);

        Collection<SocketChannel> sockets = new ArrayList<>();
        while (true) {
            selector.select();
            processSelectorActions(selectorActions);
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

    private static void processSelectorActions(Queue<Runnable> selectorActions) {
        Runnable action ;
        while((action = selectorActions.poll()) != null){
            action.run();
        }

    }

}


