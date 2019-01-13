package com.learning.networking.javanio.handler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public class ExecutorServiceHandler<S> extends DecoratedHandler<S> {

    private final ExecutorService pool;
    private final Thread.UncaughtExceptionHandler exceptionHandler;

    public ExecutorServiceHandler(Handler<S> other, ExecutorService pool, Thread.UncaughtExceptionHandler exceptionHandler) {
        super(other);
        this.pool = pool;
        this.exceptionHandler = exceptionHandler;

    }

    @Override
    public void handle(S s) throws IOException {
        this.pool.submit(new FutureTask<S>(() -> {
                             super.handle(s);
                             return null;
                         }) {
                             @Override
                             protected void setException(Throwable t) {
                                 exceptionHandler.uncaughtException(Thread.currentThread(),t);
                             }
                         }
        );
    }
}
