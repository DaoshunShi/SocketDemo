package ioFileTransfer.nio3;


import java.io.File;
import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.SocketChannel;  
import java.nio.file.Path;  
import java.nio.file.Paths;  
  
/** 
 * 发送文件Demo 
 */  
public class FileSender {  
    public static void main(String[] args) throws IOException {  
        FileSender fileSender = new FileSender();  
        SocketChannel sendChannel = fileSender.createSocketChannel();  
//        String filePath = "D:/temp/1.jpg";
//        String filePath = "D:\\Project\\SocketTest\\Post\\img\\3.jpg";
        String filePath = "D:\\Project\\SocketTest\\Post\\File\\新木乃伊.rmvb";
        fileSender.sendFileAndName(sendChannel, filePath);  
    }  
  
    /** 
     * 发送文件 
     *  
     * @param sendChannel 
     * @throws IOException 
     */  
    private void sendFileAndName(SocketChannel clientChannel, String filePath) throws IOException {  
    	//获取文件名
    	 File file = new File(filePath);  
    	 
        // 发送文件流  
//        clientChannel.write(ByteBuffer.wrap(new String("2.jpg").getBytes())); 
    	 clientChannel.write(ByteBuffer.wrap(new String(file.getName()).getBytes()));
  
        ByteBuffer buffer = ByteBuffer.allocate(6);  
        StringBuffer answerCode = new StringBuffer();  
        int length = 0;  
        while ((length = clientChannel.read(buffer)) != -1) {  
            buffer.flip();  
            answerCode.append(new String(buffer.array()));  
            if (length != buffer.capacity()) {  
                break;  
            }  
            buffer.clear();  
        }  
  
        System.out.println("server answer is " + answerCode.toString().trim());  
        if (answerCode.toString().trim().equals("0000")) {  
            sendFile(clientChannel, filePath);  
        } else {  
            System.out.println("rec server answer error");  
            clientChannel.close();  
        }  
    }  
  
    /** 
     * 创建流渠道 
     *  
     * @return 
     * @throws IOException 
     */  
    private SocketChannel createSocketChannel() throws IOException {  
        SocketChannel sendChannel = SocketChannel.open();  
        sendChannel.connect(new InetSocketAddress("localhost", 8000));  
        return sendChannel;  
    }  
  
    /** 
     * 发送文件 
     *  
     * @param sendChannel 
     * @throws IOException 
     */  
    private void sendFile(SocketChannel sendChannel, String filePath) throws IOException {  
        // 发送文件流  
        Path path = Paths.get(filePath);  
        FileChannel fileChannel = FileChannel.open(path);  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        while (fileChannel.read(buffer) != -1) {  
            buffer.flip();  
            sendChannel.write(buffer);  
            buffer.clear();  
        }  
        sendChannel.close();  
    }  
}  