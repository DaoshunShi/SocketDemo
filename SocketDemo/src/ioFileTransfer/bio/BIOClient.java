package ioFileTransfer.bio;

import java.io.BufferedOutputStream;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.FileOutputStream;  
import java.net.Socket;  
  
/**  
 * �ͻ��ˣ�����һ����Ϣ ֪ͨ���������ز���ϵͳ���ͣ�Ȼ����ܷ��������͵��ļ�  
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
            inputStream = BIOSocketUtil.getMessageStream(socket); //����socket��ȡ������  
        } catch (Exception e) {  
            System.out.print("������Ϣ�������\n");  
            return;  
        }  
  
        try {  
            //���ر���·�����ļ������Զ��ӷ������˼̳ж�����  
            String savePath = "D:\\Project\\IOTest\\aio-file\\fileTransfer\\post\\";  
            byte[] buf = new byte[bufferSize];  
            int passedlen = 0; //ͳ���յ�������  
            //1����ȡ�ļ���  
            savePath += inputStream.readUTF();   
            //�����ļ����½�һ���ļ������  
            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)));  
              
            //2����ȡ�ļ�����  
            long fileLength = inputStream.readLong();  
            System.out.println("�ļ��ĳ���Ϊ:" + fileLength + "\n");  
            System.out.println("��ʼ�����ļ�..." + "\n");  
              
            //3����ȡ�ļ�����  
            int len = 0;  
            while((len=inputStream.read(buf))!=-1){  
                passedlen += len;  
                //�����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�  
                System.out.println("�ļ�������" +  (passedlen * 100/ fileLength) + "%\n");  
                fileOut.write(buf, 0, len);  
                  
            }  
            System.out.println("������ɣ��ļ���Ϊ" + savePath + "\n");  
            fileOut.close();  
        } catch (Exception e) {  
            System.out.println("������Ϣ����" + "\n");  
            return;  
        }  
    }  
  
    public static void main(String arg[]) {  
        String ip = "localhost";// ���óɷ�����IP  
        int port = 8821;  
        String sendMessage = "Windwos";  
        try {  
            Socket socket = BIOSocketUtil.CreateConnection(ip, port);  
            System.out.print("���ӷ������ɹ�!" + "\n");  
            BIOSocketUtil.sendMessage(socket, sendMessage); //��������  
            getFileMessage(socket); //��ȡ����  
             
         
        } catch (Exception e) {  
            System.out.print("���ӷ�����ʧ��!" + "\n");  
              
        }  
    }  
}  