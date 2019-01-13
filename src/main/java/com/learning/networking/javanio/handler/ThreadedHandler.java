package com.learning.networking.javanio.handler;

public class ThreadedHandler<S> extends UncheckedIOHandlerExceptionConverterHandler<S> {
    public ThreadedHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) {
        new Thread(() -> super.handle(s)).start();
    }
}
