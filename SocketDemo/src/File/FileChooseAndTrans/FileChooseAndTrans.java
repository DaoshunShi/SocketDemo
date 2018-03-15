package File.FileChooseAndTrans;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;

/**
 * �����ļ�ѡ�񴰿ڵ��ļ�����Client��
 * ����˵���� ������ļ�����Client��(FileTransFerClient.java) �� �ļ�ѡ���(FileChooser.java)
 * ʵ���˴���ѡ���ļ����ϴ� 
 * 
 * 
 * @author DossS
 * @Date 2018.3.15
 */
public class FileChooseAndTrans extends Socket{
	
	private static final String SERVER_IP = "192.168.0.6";	//��������IP
	private static final int SERVER_PORT = 8899;	//�������˶˿�

	private Socket client;
	
	private FileInputStream fis;
	
	private DataOutputStream dos;
	
	/**
	 * ���캯��
	 * ���������������
	 * @throws Exception
	 */
	public FileChooseAndTrans() throws Exception {
		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Client[port:" + client.getLocalPort() + "] �ɹ����ӷ�������");
	}
	
	/**
	 * ͨ������ѡ���ļ�
	 * @return	ѡ����ļ�
	 */
	public static File getFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		return f;
		
	}
	
	/**
	 * ��������˴����ļ�
	 * @throws Exception
	 */
	public void sendFile() throws Exception {
		try {
			File file = getFile();
//			if (file.exists() && file.isFile()) {}	//�ж�file���ڣ���fileΪ�ļ�
			if (file.exists()) {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());
				
				//�ļ����ͳ���
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
				
				//��ʼ�����ļ�
				System.out.println("========��ʼ�����ļ�==========");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					if (100 * progress / file.length() > 100*(progress-length)/file.length()) {
						System.out.print("| " + (100*progress/file.length() + "% |"));
					}
				}
				System.out.println();
				System.out.println("==========�ļ�����ɹ�===========");
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) 
				fis.close();
			if (dos != null) 
				dos.close();
			client.close();
		}
	}
	
	/**
	 * ���
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileChooseAndTrans client = new FileChooseAndTrans();	//�����ͻ�������
			client.sendFile();	//�����ļ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
