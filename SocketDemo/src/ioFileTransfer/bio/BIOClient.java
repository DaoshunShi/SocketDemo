package ioFileTransfer.bio;

import java.io.BufferedOutputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.FileOutputStream;  
import java.net.Socket;  
  
/**  
 * 客户端：发送一条消息 通知服务器本地操作系统类型，然后接受服务器发送的文件  
 * @author zhangwenchao  
 *  
 */  
public class BIOClient {  
      
    public static final int bufferSize = 8192;  
  
    private static void getFileMessage(Socket socket) {  
        if (socket == null)  
            return;  
        DataInputStream inputStream = null;  
        try {  
            inputStream = BIOSocketUtil.getMessageStream(socket); //根据socket获取输入流  
        } catch (Exception e) {  
            System.out.print("接收消息缓存错误\n");  
            return;  
        }  
  
        try {  
            //本地保存路径，文件名会自动从服务器端继承而来。  
            String savePath = "D:\\Project\\IOTest\\aio-file\\fileTransfer\\post\\";  
            byte[] buf = new byte[bufferSize];  
            int passedlen = 0; //统计收到的数据  
            //1、读取文件名  
            savePath += inputStream.readUTF();   
            //根据文件名新建一个文件输出流  
            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)));  
              
            //2、读取文件长度  
            long fileLength = inputStream.readLong();  
            System.out.println("文件的长度为:" + fileLength + "\n");  
            System.out.println("开始接收文件..." + "\n");  
              
            //3、读取文件内容  
            int len = 0;  
            while((len=inputStream.read(buf))!=-1){  
                passedlen += len;  
                //下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比  
                System.out.println("文件接收了" +  (passedlen * 100/ fileLength) + "%\n");  
                fileOut.write(buf, 0, len);  
                  
            }  
            System.out.println("接收完成，文件存为" + savePath + "\n");  
            fileOut.close();  
        } catch (Exception e) {  
            System.out.println("接收消息错误" + "\n");  
            return;  
        }  
    }  
  
    public static void main(String arg[]) {  
        String ip = "localhost";// 设置成服务器IP  
        int port = 8821;  
        String sendMessage = "Windwos";  
        try {  
            Socket socket = BIOSocketUtil.CreateConnection(ip, port);  
            System.out.print("连接服务器成功!" + "\n");  
            BIOSocketUtil.sendMessage(socket, sendMessage); //发送数据  
            getFileMessage(socket); //获取数据  
             
         
        } catch (Exception e) {  
            System.out.print("连接服务器失败!" + "\n");  
              
        }  
    }  
}  