package icq.server.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import javax.swing.*;

import icq.server.entity.Node;
import icq.server.entity.UserLinkList;

/**
 * �������˵�������
 * @author DossS
 *
 */
public class ServerListenThread extends Thread{
	ServerSocket server;
	
	JComboBox combobox;
	JTextArea textarea;
	JTextField textfield;
	UserLinkList userLinkList;	//�û�����
	
	Node client;
	ServerReceiveThread recvThread;
	
	public boolean isStop;
	
	/*
	 * ������������û�����������������
	 */
	public ServerListenThread(ServerSocket server, JComboBox combobox, JTextArea textarea, JTextField textfield, UserLinkList userLinkList) {
		this.server = server;
		this.combobox = combobox;
		this.textarea = textarea;
		this.textfield = textfield;
		this.userLinkList = userLinkList;
		
		isStop = false;
	}
	
	public void run() {
		while(!isStop && !server.isClosed()) {
			try {
				client = new Node();
				client.setSocket(server.accept());
				client.setOutput(new ObjectOutputStream(client.getSocket().getOutputStream()));
				client.getOutput().flush();
				client.setInput(new ObjectInputStream(client.getSocket().getInputStream()));;
				client.setUsername((String)client.getInput().readObject());
				
				//��ʾ��ʾ��Ϣ
				combobox.addItem(client.getUsername());
				userLinkList.addUser(client);
				textarea.append("�û� " + client.getUsername() + " ����" + "\n");
				textfield.setText("�����û� " + userLinkList.getCount() + " ��\n");
				
				recvThread = new ServerReceiveThread(textarea, textfield, combobox, client, userLinkList);
				recvThread.start();
				
			} catch(Exception e) {
//				e.printStackTrace();
			}
		}
	}
}
