package ioFileTransfer.nio2;

import java.io.File;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.util.Set;
import java.nio.ByteBuffer;

public class NIOServer {
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private ServerSocket serverSocket = null;
    File dir = new File("D:\\Project\\SocketTest\\Get\\vedio\\");
    boolean test = true;

    public static void main(String[] args) {
        NIOServer ms = new NIOServer();
        ms.initServer();
        ms.startServer();
    }

    public void initServer() {
        try {

            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); 
            serverSocket = serverSocketChannel.socket();
            InetSocketAddress isa = new InetSocketAddress("localhost", 7777);
            serverSocket.bind(isa); 
            serverSocketChannel.register(selector,  SelectionKey.OP_ACCEPT); 

        } catch(Exception e) {e.printStackTrace();}

    }

    public void startServer() {

        System.out.println("Server is started...");

        try {
            while (true) {
                selector.select();

                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {

                    SelectableChannel channel = key.channel();

                    if (channel instanceof ServerSocketChannel) {
                        if (key.isAcceptable())
                            accept(key);
                    } else {
                        if (key.isWritable()) {
                            if(test)
                                write(key);
                        }

                    }
                }   
            }
        } catch(Exception e) {e.printStackTrace();}

    }

    private void accept(SelectionKey key) { 
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        try {
            SocketChannel sc = server.accept();
            if (sc == null) return;
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_WRITE); 
            System.out.println(sc.toString() + "Here comes a new client!");

        } catch(Exception e) {e.printStackTrace();}

    }
    private void write(SelectionKey key) {
        if(test)
        System.out.println("!!write activated!!");
        test = false;

        SocketChannel sc = (SocketChannel) key.channel();
        try {

            File[] files = dir.listFiles();

            int count = 0;
            for (File file : files) {
                count++;
                FileInputStream fis = new FileInputStream(file);
                FileChannel inChannel = fis.getChannel();
                System.out.println("Channel size : " + (int)inChannel.size());
                System.out.println("filename : " + file);

                inChannel.transferTo(0, (int)inChannel.size(), sc);

                fis.close();
                break;
            }
            System.out.println(sc.toString() + "The number of files transferred :  " + count);

        } catch(Exception e) {e.printStackTrace();} 
    }   
}