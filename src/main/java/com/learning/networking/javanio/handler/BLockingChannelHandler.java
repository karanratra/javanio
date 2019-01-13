package com.learning.networking.javanio.handler;

import com.learning.networking.javanio.handler.DecoratedHandler;
import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.handler.TransmogrifyChannelHandler;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class BLockingChannelHandler extends DecoratedHandler<SocketChannel> {
    public BLockingChannelHandler(Handler<SocketChannel> other) {
        super(other);

    }

    @Override
    public void handle(SocketChannel sc) throws IOException {
        while (sc.isConnected()) {
            super.handle(sc);
        }
    }
}
