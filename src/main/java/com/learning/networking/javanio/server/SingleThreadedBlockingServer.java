package com.learning.networking.javanio.server;

import com.learning.networking.javanio.handler.Handler;
import com.learning.networking.javanio.handler.PrintingHandler;
import com.learning.networking.javanio.handler.TransmogrifyHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket ;
import java.net.Socket;
import java.util.Objects;

public class SingleThreadedBlockingServer {

	public static void main(String[] args) throws Exception {
		
		ServerSocket ss = new ServerSocket(8080);
		Handler<Socket> handler = new PrintingHandler<Socket>(new TransmogrifyHandler());

		while(true) {
			Socket s = ss.accept(); //never null
			handler.handle(s);

		}
	}

	private static long transferTo(InputStream in,  OutputStream out) throws IOException {
		System.out.println("Inside transferTo...");
		Objects.requireNonNull(in, "Input Stream  must not be null");
		Objects.requireNonNull(out, "Output Stream  must not be null");
		long transferred = 0 ;
		byte [] buffer = new byte[1024];
		int read;
        String botName = "MyBot :: ";

		while((read = in.read(buffer,0,1024))>=0 ) {

			out.write(botName.getBytes());
			
			out.write(buffer,0,read);

			transferred+=read;

		}

		return transferred;
	}
}
