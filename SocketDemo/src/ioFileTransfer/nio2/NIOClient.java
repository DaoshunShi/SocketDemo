package ioFileTransfer.nio2;

import java.io.File;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.util.Set;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class NIOClient {
    private Selector selector = null;
    private SocketChannel sc = null;
    int count = 0;

    public static void main(String[] args) {
        NIOClient mc = new NIOClient();
        mc.startServer();
    }

    public void initServer() {

        try {
            selector = Selector.open();
            sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7777)); 
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);

        } catch(Exception e) {e.printStackTrace();}
    }

    public void startServer() {
        initServer();
        startReader();
    }

    public void startReader() {
        try {
            while (true) {
                selector.select();

                Set<SelectionKey> keys = selector.selectedKeys();

                for (SelectionKey key : keys) {
                    if (key.isReadable()) {
                        read(key);
                        System.exit(0);

                    }
                }
            }

        } catch(Exception e) {e.printStackTrace();}
    }

    private void read(SelectionKey key) {
        System.out.println("!!read activated!!");
        SocketChannel sc = (SocketChannel) key.channel();
        try {

        File dir = new File("D:\\Project\\SocketTest\\Post\\vedio\\");
        FileOutputStream fos = new FileOutputStream(dir + "\\" + count + ".rmvb"); // file name has been set as a number
        count++;
        FileChannel outChannel = fos.getChannel();
        System.out.println("Channel size : " + (int)outChannel.size());
        outChannel.transferFrom(sc, 0, (int)outChannel.size());
        fos.close();    

        } catch(Exception e) {e.printStackTrace();}
    }
}