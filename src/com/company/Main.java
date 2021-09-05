package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {
        new Server().bootstrap();
    }
}

class Server {
    private final static int BUFFER_SIZE = 256;
    private AsynchronousServerSocketChannel server;

    public void bootstrap() {
        try {
            server = AsynchronousServerSocketChannel.open();
            server.bind(new InetSocketAddress("127.0.0.1", 8088));
            //соединение
            Future <AsynchronousSocketChannel> future = server.accept();
            System.out.println("New client");
            AsynchronousSocketChannel clientChannel = future.get(30, TimeUnit.SECONDS);
            //чтение
            while (clientChannel != null && clientChannel.isOpen()) {
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                clientChannel.read(buffer).get();
                clientChannel.write(buffer).get();
                clientChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
