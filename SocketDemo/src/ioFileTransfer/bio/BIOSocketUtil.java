package ioFileTransfer.bio;

import java.io.BufferedInputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.Socket;  
  
/**  
 * Socket连接工具  
 * @author zhangwenchao  
 *  
 */  
public class BIOSocketUtil{  
      
    /**  
     * 创建socket连接   
     * @throws Exception  
     *  exception  
     */  
    public static Socket CreateConnection(String ip, int port) throws Exception {  
        try {  
            Socket socket = new Socket(ip, port);  
            return socket;  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
        }  
    }  
  
    /**  
     * 发送一条信息，通知服务器本机的操作系统类型  
     * @param sendMessage 0x1:windows  0x2:unix 0x3:Linux  
     * @throws Exception  
     */  
    public static void sendMessage(Socket socket,String sendMessage) throws Exception {  
        DataOutputStream outputStream = null;  
        try {  
            outputStream = new DataOutputStream(socket.getOutputStream());  
            if (sendMessage.equals("Windows")) {  
                outputStream.writeByte(0x1);  
                outputStream.flush();  
                return;  
            }  
            if (sendMessage.equals("Unix")) {  
                outputStream.writeByte(0x2);  
                outputStream.flush();  
                return;  
            }  
            if (sendMessage.equals("Linux")) {  
                outputStream.writeByte(0x3);  
                outputStream.flush();  
            } else {  
                outputStream.writeUTF(sendMessage);  
                outputStream.flush();  
            }  
        } catch (Exception e) {  
            System.out.println("发送消息错误" + "\n");  
            e.printStackTrace();  
            if (outputStream != null)  
                outputStream.close();  
            throw e;  
        } finally {  
        }  
    }  
  
    /**  
     * 获取输入流  
     * @return  
     * @throws Exception  
     */  
    public static DataInputStream getMessageStream(Socket socket) throws Exception {  
        try {  
            DataInputStream  inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));  
            return inputStream;  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
        }  
    }  
  
    /**  
     * 关闭连接  
     */  
    public static void shutDownConnection(Socket socket) {  
        try {  
            if (socket != null){  
                socket.close();  
            }  
        } catch (Exception e) {  
  
        }  
    }  
}  