package chatAndTrans.client.listener;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import javax.swing.JFileChooser;

import chatAndTrans.client.frame.ConnectConf;
import chatAndTrans.client.frame.MainFrame;
import chatAndTrans.client.frame.UserConf;
import chatAndTrans.client.thread.ClientReceiveThread;
import chatAndTrans.server.frame.Help;

/**
 * 客户端监听类
 * @author DossShi
 *
 */
public class ClientListener implements ActionListener {
	
	String ip = "127.0.0.1";	//连接到服务端的ip地址
	int port = 8888;	//连接到服务器端的端口号
	public String userName = "李强";	//用户名
	public int type = 0;	//0表示未连接，1表示已连接
	
	private static String FILE_IP = "192.168.0.8";	//传输文件的目标ip
	private static int FILE_PORT = 8818;	//传输文件的目标接口
	private static FileInputStream fis;
	private static DataOutputStream dos;
	
	Socket socket;
	ObjectOutputStream output;	//网络套接字输出流
	ObjectInputStream input;	//网络套接字输入流
	
	ClientReceiveThread recvThread;
	
	static MainFrame mainFrame;
	
	

	
	public ClientListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		//为菜单栏添加事件监听
		mainFrame.loginItem.addActionListener(this);
		mainFrame.logoffItem.addActionListener(this);
		mainFrame.exitItem.addActionListener(this);
		mainFrame.userItem.addActionListener(this);
		mainFrame.connectItem.addActionListener(this);
		mainFrame.helpItem.addActionListener(this);
		
		//添加按钮的事件侦听
		mainFrame.loginButton.addActionListener(this);
		mainFrame.logoffButton.addActionListener(this);
		mainFrame.userButton.addActionListener(this);
		mainFrame.connectButton.addActionListener(this);
		mainFrame.exitButton.addActionListener(this);
		
		//添加系统消息的事件侦听
		mainFrame.clientMessage.addActionListener(this);
		mainFrame.clientMessageButton.addActionListener(this);
		mainFrame.clientFileButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == mainFrame.userItem || obj == mainFrame.userButton) {	//用户信息设置
			//调出用户信息设置对话框
			UserConf userConf = new UserConf(mainFrame, userName);
			userConf.setVisible(true);
			userName = userConf.userInputName;
		} else if (obj == mainFrame.connectItem || obj == mainFrame.connectButton) {	//连接服务器设置
			//调出连接设置对话框
			ConnectConf conConf = new ConnectConf(mainFrame, ip, port);
			conConf.setVisible(true);
			ip = conConf.userInputIp;
			port = conConf.userInputPort;
		} else if (obj == mainFrame.loginItem || obj == mainFrame.loginButton ) {	//登录
			Connect();
		} else if (obj == mainFrame.logoffItem || obj == mainFrame.logoffButton) {	//注销
			DisConnect();
			mainFrame.showStatus.setText("");
		} else if (obj == mainFrame.clientMessage || obj == mainFrame.clientMessageButton) {	//发送消息
			SendMessage();
			mainFrame.clientMessage.setText("");
		} else if (obj == mainFrame.clientFileButton ) {
			try {
				sendFiles();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if (obj == mainFrame.exitButton || obj == mainFrame.exitItem) { 	//退出
			int j = mainFrame.showConfirmDialog("真的要退出吗？", "退出");
			
			if (j == 1) {
				if (type == 1) {
					DisConnect();
				}
				System.exit(0);
			}
		} else if (obj == mainFrame.helpItem) {	//菜单栏中的帮助
			//调出帮助对话框
			Help helpDialog = new Help(mainFrame);
			helpDialog.setVisible(true);
		}
	}
	
	public void Connect() {
		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {
			mainFrame.showConfirmDialog("不能连接到指定的服务器。\n请确认连接设置是否正确。", "提示");
			return ;
		}
		
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
			
			output.writeObject(userName);
			output.flush();
			
			recvThread = new ClientReceiveThread(socket, output, input, mainFrame.combobox, mainFrame.messageShow, mainFrame.showStatus);
			recvThread.start();
			
			mainFrame.loginButton.setEnabled(false);
			mainFrame.loginItem.setEnabled(false);
			mainFrame.userButton.setEnabled(false);
			mainFrame.userItem.setEnabled(false);
			mainFrame.connectButton.setEnabled(false);
			mainFrame.connectItem.setEnabled(false);
			mainFrame.logoffButton.setEnabled(true);
			mainFrame.logoffItem.setEnabled(true);
			mainFrame.clientMessage.setEnabled(true);
			mainFrame.messageShow.append("连接服务器 " + ip + ":" + port + " 成功……\n");
			type = 1;	//标志位设为已连接
		} catch (Exception e) {
//			e.printStackTrace();
			return;
		}
		
	}
	
	public void DisConnect() {
		mainFrame.loginButton.setEnabled(true);
		mainFrame.loginItem.setEnabled(true);
		mainFrame.userButton.setEnabled(true);
		mainFrame.userItem.setEnabled(true);
		mainFrame.connectButton.setEnabled(true);
		mainFrame.connectItem.setEnabled(true);
		mainFrame.logoffButton.setEnabled(false);
		mainFrame.logoffItem.setEnabled(false);
		mainFrame.clientMessage.setEnabled(false);
		
		if (socket.isClosed()) {
			return ;
		}
		
		try {
			output.writeObject("用户下线");
			output.flush();
			
			input.close();
			output.close();
			socket.close();
			mainFrame.messageShow.append("已经与服务器断开连接……\n");
			type = 0;	//标志位设置为未连接
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
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
		
		if (file.exists()) {
			Socket fileClient = new Socket(FILE_IP, FILE_PORT);
			try {
				
				fis = new FileInputStream(file);
				dos = new DataOutputStream(fileClient.getOutputStream());
				
				//文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
				
				//开始传输文件
				System.out.println("========开始传输文件==========");
//				mainFrame.messageShow.append(file.getName() + " 传输开始\n");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					if (100 * progress / file.length() > 100*(progress-length)/file.length()) {
						System.out.print("| " + (100*progress/file.length() + "% |"));
//						mainFrame.messageShow.append("*");
					}
				}
				System.out.println();
				System.out.println("==========文件传输成功===========");
				mainFrame.messageShow.append(file.getName() + " 传输成功……\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) 
					fis.close();
				if (dos != null) 
					dos.close();
				if (fileClient != null) {
					fileClient.close();
				}
			}
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
	
	public void SendMessage() {
		String toSomebody = mainFrame.combobox.getSelectedItem().toString();
		String status = "";
		if (mainFrame.checkbox.isSelected()) {
			status = "悄悄话";
		}
		
		String action = mainFrame.actionlist.getSelectedItem().toString();
		String message = mainFrame.clientMessage.getText();
		
		if (socket.isClosed()) {
			return ;
		}
		
		try {
			output.writeObject("聊天信息");
			output.flush();
			output.writeObject(toSomebody);
			output.flush();
			output.writeObject(status);
			output.flush();
			output.writeObject(action);
			output.flush();
			output.writeObject(message);
			output.flush();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	
}
