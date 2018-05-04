package File.multiTrans;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;

import File.FileChooseAndTrans.FileChooseAndTrans;

public class MultiTrans extends Socket{
//	private static final String SERVER_IP = "192.168.0.8";	//服务器端IP
	private static final String SERVER_IP = "127.0.0.1";	//服务器端IP
	private static final int SERVER_PORT = 8818;	//服务器端端口

	private static Socket client;
	
	private static FileInputStream fis;
	
	private static DataOutputStream dos;
	
	/**
	 * 构造函数
	 * 与服务器建立连接
	 * @throws Exception
	 */
	public MultiTrans() throws Exception {
		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Client[port:" + client.getLocalPort() + "] 成功连接服务器端");
	}
	
	/**
	 * 通过窗口选择文件（可多选）
	 * @return 选择的文件列表
	 */
	public static File[] getFiles() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);
		File[] fs = chooser.getSelectedFiles();
		return fs;
	}
	
	/**
	 * 向服务器端传输文件
	 * @throws Exception
	 */	
	public static void send(File file) throws Exception {
		try {
			if (file.exists()) {
				client = new Socket(SERVER_IP, SERVER_PORT);
				
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());
				
				//文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
				
				//开始传输文件
				System.out.println("========开始传输文件==========");
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
				System.out.println("==========文件传输成功===========");
				
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
	 * 向服务器传送文件列表
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
					
					//文件名和长度
					dos.writeUTF(file.getName());
					dos.flush();
					dos.writeLong(file.length());
					dos.flush();
					
					//开始传输文件
					System.out.println("========" + file.getName() + " 开始传输文件==========");
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
					System.out.println("========== " + file.getName() + " 文件传输成功===========");
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
	 * 入口
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
