package File.fileScannerAndNio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import ioFileTransfer.comm.Vary;

public class NIOClient {  
	   public static void main(String[] args) throws IOException {  
		   String filePath = "D:\\Project\\SocketTest\\Post\\File\\异星觉醒.mkv";
	        init(filePath);
	    }  
	   
	   public static void init(String filePath) throws IOException {
		   NIOClient nioClient = new NIOClient();  
	        SocketChannel sendChannel = nioClient.createSocketChannel();  
//	        String filePath = "D:\\Project\\SocketTest\\Post\\File\\异星觉醒.mkv";
	        nioClient.sendFileAndName(sendChannel, filePath);  
	   }
	   
	   public static void send(SocketChannel clientChannel, String filePath) throws IOException {
		   sendFileAndName(clientChannel, filePath);
	   }
	  
	    /** 
	     * 发送文件 
	     *  
	     * @param sendChannel 
	     * @throws IOException 
	     */  
	    private static void sendFileAndName(SocketChannel clientChannel, String filePath) throws IOException {  
	    	//获取文件名
	    	 File file = new File(filePath);  
	    	 
	        // 发送文件流  
//	        clientChannel.write(ByteBuffer.wrap(new String("2.jpg").getBytes())); 
	    	 clientChannel.write(ByteBuffer.wrap(new String(file.getName()).getBytes()));
	  
	        ByteBuffer buffer = ByteBuffer.allocate(32);  
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
	        sendChannel.connect(new InetSocketAddress(Vary.IP, Vary.PORT));  
	        //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式  
//	        sendChannel.configureBlocking(false);//开启非阻塞模式  
	        return sendChannel;  
	    }  
	  
	    /** 
	     * 发送文件 
	     *  
	     * @param sendChannel 
	     * @throws IOException 
	     */  
	    private static void sendFile(SocketChannel sendChannel, String filePath) throws IOException {  
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