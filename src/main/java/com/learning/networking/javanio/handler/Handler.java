package com.learning.networking.javanio.handler;

import java.io.IOException;

//Component
public interface Handler<S> {
    void handle(S s) throws IOException;

}
