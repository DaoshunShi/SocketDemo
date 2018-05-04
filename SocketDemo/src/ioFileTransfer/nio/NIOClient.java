package ioFileTransfer.nio;

import java.io.FileInputStream;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.util.Set;

import org.omg.CORBA.COMM_FAILURE;

import ioFileTransfer.comm.Vary;  

public class NIOClient {  
//    private int port = 8000;  
      
    /* 发送数据缓冲区 */  
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);  
      
    /* 接受数据缓冲区 */  
    private static ByteBuffer revBuffer = ByteBuffer.allocate(1024);  
      
    private InetSocketAddress SERVER;  
    private static Selector selector;  
    private static SocketChannel client;  
       
    public NIOClient(){  
        try{  
            SERVER = new InetSocketAddress(Vary.IP, Vary.PORT);  
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
            System.out.println("文件发送开始....");  
            fis = new FileInputStream("D:\\Project\\SocketTest\\Post\\File\\3.jpg");  
            channel = fis.getChannel();  
            int i = 1;  
            int count = 0;  
            while((count = channel.read(sendBuffer)) != -1) {  
                sendBuffer.flip();   
                int send = client.write(sendBuffer);  
                System.out.println("i===========" + (i++) + "   count:" + count + " send:" + send);  
                // 服务器端可能因为缓存区满，而导致数据传输失败，需要重新发送  
                while(send == 0){  
                    Thread.sleep(10);  
                    send = client.write(sendBuffer);  
                    System.out.println("i重新传输====" + i + "   count:" + count + " send:" + send);  
                }  
                sendBuffer.clear();   
             }  
             System.out.println("文件发送完成！");  
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