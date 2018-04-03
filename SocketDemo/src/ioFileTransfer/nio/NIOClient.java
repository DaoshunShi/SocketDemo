package ioFileTransfer.nio;

import java.io.FileInputStream;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.util.Set;  

public class NIOClient {  
    private int port = 8000;  
      
    /* �������ݻ����� */  
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);  
      
    /* �������ݻ����� */  
    private static ByteBuffer revBuffer = ByteBuffer.allocate(1024);  
      
    private InetSocketAddress SERVER;  
    private static Selector selector;  
    private static SocketChannel client;  
       
    public NIOClient(){  
        try{  
            SERVER = new InetSocketAddress("localhost", port);  
            init();  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
           
    }  
    private void init(){  
        try {  
            SocketChannel socketChannel = SocketChannel.open();  
            socketChannel.configureBlocking(false);  
            selector = Selector.open();  
            socketChannel.register(selector, SelectionKey.OP_CONNECT);  
            socketChannel.connect(SERVER);  
            while (true) {  
                selector.select();  
                Set<SelectionKey> keySet = selector.selectedKeys();  
                for(SelectionKey key : keySet) {  
                    if(key.isConnectable()){  
                        client = (SocketChannel)key.channel();  
                        client.finishConnect();  
                        client.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);  
   
                    }else if(key.isWritable()){                       
                        sendFile(client);  
                    }else if(key.isReadable()){  
                        System.out.println("selectionKey.isReadable()");  
                    }  
                }  
                keySet.clear();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
       
    private void sendFile(SocketChannel client) {  
        FileInputStream fis = null;  
        FileChannel channel = null;  
        try {  
            System.out.println("�ļ����Ϳ�ʼ....");  
            fis = new FileInputStream("D:\\Project\\IOTest\\aio-file\\fileTransfer\\post\\1.jpg");  
            channel = fis.getChannel();  
            int i = 1;  
            int count = 0;  
            while((count = channel.read(sendBuffer)) != -1) {  
                sendBuffer.flip();   
                int send = client.write(sendBuffer);  
                System.out.println("i===========" + (i++) + "   count:" + count + " send:" + send);  
                // �������˿�����Ϊ�������������������ݴ���ʧ�ܣ���Ҫ���·���  
                while(send == 0){  
                    Thread.sleep(10);  
                    send = client.write(sendBuffer);  
                    System.out.println("i���´���====" + i + "   count:" + count + " send:" + send);  
                }  
                sendBuffer.clear();   
             }  
             System.out.println("�ļ�������ɣ�");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                channel.close();  
                fis.close();  
                client.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
   
        }  
    }  
       
       
    public static void main(String[] args){  
        new NIOClient();  
    }  
}  