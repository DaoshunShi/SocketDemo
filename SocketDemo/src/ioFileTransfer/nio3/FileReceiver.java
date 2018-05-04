package ioFileTransfer.nio3;


import java.io.File;  
import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.nio.file.Path;  
import java.nio.file.Paths;  
import java.nio.file.StandardOpenOption;  
import java.util.EnumSet;  
 
public class FileReceiver {  
   public static void main(String[] args) throws IOException {  
       FileReceiver fileSender = new FileReceiver();  
       SocketChannel clientSocket = fileSender.createSocketChannel();  
       fileSender.recFile(clientSocket);  
   }  
 
   /** 
    * ���������� 
    *  
    * @return 
    * @throws IOException 
    */  
   private SocketChannel createSocketChannel() throws IOException {  
       ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  
       serverSocketChannel.bind(new InetSocketAddress(8081));  
       SocketChannel clientSocket = serverSocketChannel.accept();  
       System.out.println("Connection Client " + clientSocket.getRemoteAddress());  
       return clientSocket;  
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
//       StringBuffer fileName = new StringBuffer("rec_");  
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
//       String filePath = "D:/temp/";  
       String filePath = "D:\\Project\\SocketTest\\Get\\img\\";
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
}  