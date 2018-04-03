package ioFileTransfer.bio;

import java.io.BufferedInputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.Socket;  
  
/**  
 * Socket���ӹ���  
 * @author zhangwenchao  
 *  
 */  
public class BIOSocketUtil{  
      
    /**  
     * ����socket����   
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
     * ����һ����Ϣ��֪ͨ�����������Ĳ���ϵͳ����  
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
            System.out.println("������Ϣ����" + "\n");  
            e.printStackTrace();  
            if (outputStream != null)  
                outputStream.close();  
            throw e;  
        } finally {  
        }  
    }  
  
    /**  
     * ��ȡ������  
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
     * �ر�����  
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