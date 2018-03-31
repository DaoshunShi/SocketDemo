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
 * �ͻ��˼�����
 * @author DossShi
 *
 */
public class ClientListener implements ActionListener {
	
	String ip = "127.0.0.1";	//���ӵ�����˵�ip��ַ
	int port = 8888;	//���ӵ��������˵Ķ˿ں�
	public String userName = "��ǿ";	//�û���
	public int type = 0;	//0��ʾδ���ӣ�1��ʾ������
	
	private static String FILE_IP = "192.168.0.8";	//�����ļ���Ŀ��ip
	private static int FILE_PORT = 8818;	//�����ļ���Ŀ��ӿ�
	private static FileInputStream fis;
	private static DataOutputStream dos;
	
	Socket socket;
	ObjectOutputStream output;	//�����׽��������
	ObjectInputStream input;	//�����׽���������
	
	ClientReceiveThread recvThread;
	
	static MainFrame mainFrame;
	
	

	
	public ClientListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		//Ϊ�˵�������¼�����
		mainFrame.loginItem.addActionListener(this);
		mainFrame.logoffItem.addActionListener(this);
		mainFrame.exitItem.addActionListener(this);
		mainFrame.userItem.addActionListener(this);
		mainFrame.connectItem.addActionListener(this);
		mainFrame.helpItem.addActionListener(this);
		
		//��Ӱ�ť���¼�����
		mainFrame.loginButton.addActionListener(this);
		mainFrame.logoffButton.addActionListener(this);
		mainFrame.userButton.addActionListener(this);
		mainFrame.connectButton.addActionListener(this);
		mainFrame.exitButton.addActionListener(this);
		
		//���ϵͳ��Ϣ���¼�����
		mainFrame.clientMessage.addActionListener(this);
		mainFrame.clientMessageButton.addActionListener(this);
		mainFrame.clientFileButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == mainFrame.userItem || obj == mainFrame.userButton) {	//�û���Ϣ����
			//�����û���Ϣ���öԻ���
			UserConf userConf = new UserConf(mainFrame, userName);
			userConf.setVisible(true);
			userName = userConf.userInputName;
		} else if (obj == mainFrame.connectItem || obj == mainFrame.connectButton) {	//���ӷ���������
			//�����������öԻ���
			ConnectConf conConf = new ConnectConf(mainFrame, ip, port);
			conConf.setVisible(true);
			ip = conConf.userInputIp;
			port = conConf.userInputPort;
		} else if (obj == mainFrame.loginItem || obj == mainFrame.loginButton ) {	//��¼
			Connect();
		} else if (obj == mainFrame.logoffItem || obj == mainFrame.logoffButton) {	//ע��
			DisConnect();
			mainFrame.showStatus.setText("");
		} else if (obj == mainFrame.clientMessage || obj == mainFrame.clientMessageButton) {	//������Ϣ
			SendMessage();
			mainFrame.clientMessage.setText("");
		} else if (obj == mainFrame.clientFileButton ) {
			try {
				sendFiles();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if (obj == mainFrame.exitButton || obj == mainFrame.exitItem) { 	//�˳�
			int j = mainFrame.showConfirmDialog("���Ҫ�˳���", "�˳�");
			
			if (j == 1) {
				if (type == 1) {
					DisConnect();
				}
				System.exit(0);
			}
		} else if (obj == mainFrame.helpItem) {	//�˵����еİ���
			//���������Ի���
			Help helpDialog = new Help(mainFrame);
			helpDialog.setVisible(true);
		}
	}
	
	public void Connect() {
		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {
			mainFrame.showConfirmDialog("�������ӵ�ָ���ķ�������\n��ȷ�����������Ƿ���ȷ��", "��ʾ");
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
			mainFrame.messageShow.append("���ӷ����� " + ip + ":" + port + " �ɹ�����\n");
			type = 1;	//��־λ��Ϊ������
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
			output.writeObject("�û�����");
			output.flush();
			
			input.close();
			output.close();
			socket.close();
			mainFrame.messageShow.append("�Ѿ���������Ͽ����ӡ���\n");
			type = 0;	//��־λ����Ϊδ����
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
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
		
		if (file.exists()) {
			Socket fileClient = new Socket(FILE_IP, FILE_PORT);
			try {
				
				fis = new FileInputStream(file);
				dos = new DataOutputStream(fileClient.getOutputStream());
				
				//�ļ����ͳ���
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();
				
				//��ʼ�����ļ�
				System.out.println("========��ʼ�����ļ�==========");
//				mainFrame.messageShow.append(file.getName() + " ���俪ʼ\n");
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
				System.out.println("==========�ļ�����ɹ�===========");
				mainFrame.messageShow.append(file.getName() + " ����ɹ�����\n");
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
	
	public void SendMessage() {
		String toSomebody = mainFrame.combobox.getSelectedItem().toString();
		String status = "";
		if (mainFrame.checkbox.isSelected()) {
			status = "���Ļ�";
		}
		
		String action = mainFrame.actionlist.getSelectedItem().toString();
		String message = mainFrame.clientMessage.getText();
		
		if (socket.isClosed()) {
			return ;
		}
		
		try {
			output.writeObject("������Ϣ");
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
