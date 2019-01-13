package com.learning.networking.javanio.handler;


import java.io.IOException;
import java.io.UncheckedIOException;

public class UncheckedIOHandlerExceptionConverterHandler<S> extends DecoratedHandler<S> {

    public UncheckedIOHandlerExceptionConverterHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) {
        try {
            super.handle(s);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
