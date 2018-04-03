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
 * ����ʵ�ֻ���BIO��Socket�ļ����䣬�ͻ������ӷ������󣬷���������һ���ļ����ͻ��ˣ�  
 * �ͻ��˽����ļ������浽���ء�(ͬ���ʵ�ֿͻ�������������ļ�����)  
 *   
 * �����Ƿ���ˣ�ʵ�ֽ������Ӻͷ����ļ�  
 * @author zhangwenchao  
 */  
public class BIOServer {  
      
    public static final int port=8821;  
      
    public static final int bufferSize = 8192;  
      
    public void serverStart(){  
          
        Socket s =null;  //����  
          
        try {  
            ServerSocket ss = new ServerSocket(port);//��ʼ��ServerSocket  
            File f = new File("D:\\Project\\IOTest\\aio-file\\fileTransfer\\post\\1.jpg");   
            System.out.println("Ҫ���͵��ļ�Ϊ��"+f.getName()+" �ļ���С��"+f.length());  
            while(true){//����ѭ�����ܿͻ�������  
                System.out.println("���������ڼ����˿ڣ�"+port+"...");  
                // public Socket accept() throws IOException  
                // ���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������  
                s = ss.accept();  //��������  
                System.out.println("Socket�����Ѿ������ɹ���");  
                                  
                    //Socket�������� ��ȡ�ͻ��˷��͹���������  
                DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));  
                byte message = dis.readByte();  
                System.out.println(message);  
                  
                //�ļ�����������ȡ�ļ�  
                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));  
                  
                //Socket������� �����ļ�  
                DataOutputStream  dos = new DataOutputStream(s.getOutputStream());   
                  
                //���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java 4th�����ֳɵĴ��롣  
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
                // ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����  
                // ֱ��socket��ʱ���������ݲ�������                  
                fis.close();  
                s.close();                  
                System.out.println("�ļ��������");  
                  
            }  
              
        } catch (IOException e) {  
              
            e.printStackTrace();  
            System.out.println("�������ӳ����쳣��");  
        }                 
          
    }  
      
      
    public static void main(String arg[]) {  
        new BIOServer().serverStart();  
    }  
      
      
  
}  