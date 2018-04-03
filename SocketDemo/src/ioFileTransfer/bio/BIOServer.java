package ioFileTransfer.bio;

import java.io.BufferedInputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
/**  
 * 本例实现基于BIO的Socket文件传输，客户端连接服务器后，服务器发送一个文件到客户端，  
 * 客户端接受文件，保存到本地。(同理可实现客户端向服务器的文件发送)  
 *   
 * 本类是服务端：实现接受连接和发送文件  
 * @author zhangwenchao  
 */  
public class BIOServer {  
      
    public static final int port=8821;  
      
    public static final int bufferSize = 8192;  
      
    public void serverStart(){  
          
        Socket s =null;  //连接  
          
        try {  
            ServerSocket ss = new ServerSocket(port);//初始化ServerSocket  
            File f = new File("D:\\Project\\IOTest\\aio-file\\fileTransfer\\post\\1.jpg");   
            System.out.println("要发送的文件为："+f.getName()+" 文件大小："+f.length());  
            while(true){//不断循环接受客户端请求  
                System.out.println("服务器正在监听端口："+port+"...");  
                // public Socket accept() throws IOException  
                // 侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。  
                s = ss.accept();  //建立连接  
                System.out.println("Socket连接已经建立成功！");  
                                  
                    //Socket输入流： 读取客户端发送过来的数据  
                DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));  
                byte message = dis.readByte();  
                System.out.println(message);  
                  
                //文件输入流：读取文件  
                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));  
                  
                //Socket输出流： 发送文件  
                DataOutputStream  dos = new DataOutputStream(s.getOutputStream());   
                  
                //将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java 4th里有现成的代码。  
                dos.writeUTF(f.getName());  
                dos.flush();  
                  
                dos.writeLong((long) f.length());  
                dos.flush();  
                    
                byte[] buf = new byte[bufferSize];  
                int len = 0;  
                while((len=fis.read(buf))!=-1){  
                    dos.write(buf, 0, len);  
                }  
                dos.flush();  
                // 注意关闭socket链接哦，不然客户端会等待server的数据过来，  
                // 直到socket超时，导致数据不完整。                  
                fis.close();  
                s.close();                  
                System.out.println("文件传输完成");  
                  
            }  
              
        } catch (IOException e) {  
              
            e.printStackTrace();  
            System.out.println("网络连接出现异常！");  
        }                 
          
    }  
      
      
    public static void main(String arg[]) {  
        new BIOServer().serverStart();  
    }  
      
      
  
}  