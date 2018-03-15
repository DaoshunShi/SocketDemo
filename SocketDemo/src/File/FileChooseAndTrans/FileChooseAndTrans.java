package File.FileChooseAndTrans;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;

/**
 * 带有文件选择窗口的文件传输Client端
 * 功能说明： 结合了文件传输Client端(FileTransFerClient.java) 和 文件选择框(FileChooser.java)
 * 实现了窗口选择文件并上传 
 * 
 * 
 * @author DossS
 * @Date 2018.3.15
 */
public class FileChooseAndTrans extends Socket{
	
	private static final String SERVER_IP = "192.168.0.6";	//服务器端IP
	private static final int SERVER_PORT = 8899;	//服务器端端口

	private Socket client;
	
	private FileInputStream fis;
	
	private DataOutputStream dos;
	
	/**
	 * 构造函数
	 * 与服务器建立连接
	 * @throws Exception
	 */
	public FileChooseAndTrans() throws Exception {
		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Client[port:" + client.getLocalPort() + "] 成功连接服务器端");
	}
	
	/**
	 * 通过窗口选择文件
	 * @return	选择的文件
	 */
	public static File getFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		return f;
		
	}
	
	/**
	 * 向服务器端传输文件
	 * @throws Exception
	 */
	public void sendFile() throws Exception {
		try {
			File file = getFile();
//			if (file.exists() && file.isFile()) {}	//判断file存在，且file为文件
			if (file.exists()) {
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
	 * 入口
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileChooseAndTrans client = new FileChooseAndTrans();	//启动客户端连接
			client.sendFile();	//传输文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
