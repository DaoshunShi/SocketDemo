package ioFileTransfer.nio4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;  
import java.net.ServerSocket;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Set;

import ioFileTransfer.comm.Vary;  
   
public class NIOServer{  
      
    private static Selector selector;  
    public NIOServer(){  
        try{  
            init();  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
    private void init() throws Exception{  
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  
        serverSocketChannel.configureBlocking(false);  
        ServerSocket serverSocket = serverSocketChannel.socket();  
        serverSocket.bind(new InetSocketAddress(Vary.PORT));  
        selector = Selector.open();  
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
        System.out.println("server start on port:" + Vary.PORT);  
        while (true) {  
            try {  
                selector.select();// ����ֵΪ���δ������¼���  
                Set<SelectionKey> selectionKeys = selector.selectedKeys();                  
                for (SelectionKey key : selectionKeys) {  
                    ServerSocketChannel server = null;  
                    SocketChannel client = null;  
                    int count = 0;  
                    if (key.isAcceptable()) {  
                        server = (ServerSocketChannel) key.channel();  
                        System.out.println("�пͻ������ӽ���=============)");  
                        client = server.accept();  
                        client.configureBlocking(false);  
                        client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);  
                    } else if (key.isReadable()) {  
                        client = (SocketChannel) key.channel();         
                        recFile(client);
                    }else if (key.isWritable()) {  
                        System.out.println("selectionKey.isWritable()");              
                    }  
                }  
                System.out.println("=======selectionKeys.clear()");  
                selectionKeys.clear();  
            } catch (Exception e) {  
                e.printStackTrace();  
                break;  
            }  
        }  
    }  
    
    /** 
     * �����ļ� 
     *  
     * @param sendChannel 
     * @throws IOException 
     */  
    private void recFile(SocketChannel clientSocket) throws IOException {  
        // �����ļ���  
        ByteBuffer buffer = ByteBuffer.allocate(256);  
        StringBuffer fileName = new StringBuffer("");  
        int readLength = 0;  
        while ((readLength = clientSocket.read(buffer)) > 0) {  
            buffer.flip();  
            fileName.append(new String(buffer.array()));  
            buffer.clear();  
            // ���һ����ȡ���⴦��,��Ȼ��һֱ�ȴ�����  
            if (readLength != buffer.capacity()) {  
                break;  
            }  
        }  
  
        // �����ļ�����  
        String filePath = "D:\\Project\\SocketTest\\Get\\5.5\\";
        File saveDir = new File(filePath);  
        if (!saveDir.exists()) {  
            saveDir.mkdirs();  
        }  
        filePath = filePath + fileName.toString().trim();  
        System.out.println("Received File name " + fileName.toString());  
  
        // response getFile Name succ  
        clientSocket.write(ByteBuffer.wrap(new String("0000").getBytes()));  
  
        // �ļ���ȡ  
        Path path = Paths.get(filePath);  
        FileChannel fileChannel = FileChannel.open(path,  
                EnumSet.of(StandardOpenOption.CREATE,   
                           StandardOpenOption.TRUNCATE_EXISTING,   
                           StandardOpenOption.WRITE));  
  
        // д�뵽����  
        while (clientSocket.read(buffer) >= 0) {  
            buffer.flip();  
            fileChannel.write(buffer);  
            buffer.clear();  
        }  
        fileChannel.close();  
        System.out.println("Received Remote File Succ!");  
        clientSocket.close();  
    }  
    
    public static void main(String[] args){  
        new NIOServer();  
    }  
}  