package File.multiTrans;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;

import File.FileChooseAndTrans.FileChooseAndTrans;

public class MultiTrans extends Socket{
//	private static final String SERVER_IP = "192.168.0.8";	//��������IP
	private static final String SERVER_IP = "127.0.0.1";	//��������IP
	private static final int SERVER_PORT = 8818;	//�������˶˿�

	private static Socket client;
	
	private static FileInputStream fis;
	
	private static DataOutputStream dos;
	
	/**
	 * ���캯��
	 * ���������������
	 * @throws Exception
	 */
	public MultiTrans() throws Exception {
		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Client[port:" + client.getLocalPort() + "] �ɹ����ӷ�������");
	}
	
	/**
	 * ͨ������ѡ���ļ����ɶ�ѡ��
	 * @return ѡ����ļ��б�
	 */
	public static File[] getFiles() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);
		File[] fs = chooser.getSelectedFiles();
		return fs;
	}
	
	/**
	 * ��������˴����ļ�
	 * @throws Exception
	 */	
	public static void send(File file) throws Exception {
		try {
			if (file.exists()) {
				client = new Socket(SERVER_IP, SERVER_PORT);
				
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
	 * ������������ļ��б�
	 * @throws Exception
	 */
	public static void sendFiles() throws Exception {
		try {
			File[] files = getFiles();
			if (files.length <= 0) {
				return ;
			}
			
			for (File file : files) {
				send(file);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	public void sendFiles2() throws Exception {
		try {
			File[] files = getFiles();
			if (files.length <= 0) {
				return ;
			}
			dos = new DataOutputStream(client.getOutputStream());
			
			dos.writeInt(files.length);
			dos.flush();
			for (File file : files) {
				if (file.exists()) {
					fis = new FileInputStream(file);
					
					//�ļ����ͳ���
					dos.writeUTF(file.getName());
					dos.flush();
					dos.writeLong(file.length());
					dos.flush();
					
					//��ʼ�����ļ�
					System.out.println("========" + file.getName() + " ��ʼ�����ļ�==========");
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
					System.out.println("========== " + file.getName() + " �ļ�����ɹ�===========");
				}
			}
		}  catch (Exception e) {
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
			sendFiles();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
