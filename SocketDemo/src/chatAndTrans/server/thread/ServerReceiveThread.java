package chatAndTrans.server.thread;

import java.io.File;

import javax.swing.*;

import chatAndTrans.server.entity.Node;
import chatAndTrans.server.entity.UserLinkList;
import chatAndTrans.server.text.TextTools;

/**
 * �������˽�����Ϣ����
 * @author DossS
 *
 */
public class ServerReceiveThread extends Thread {
	JTextArea textarea;
	JTextField textfield;
	JComboBox combobox;
	Node client;
	UserLinkList userLinkList;	//�û�����
	TextTools textTools;	//	�ļ���д
	String basePath = "D:\\Project\\SocketTest\\text\\output\\";	//	�����ļ���Ż�·��
	
	public boolean isStop;
	
	public ServerReceiveThread(JTextArea textarea, JTextField textfield, JComboBox combobox, Node client, UserLinkList userLinkList) {
		this.textarea = textarea;
		this.textfield = textfield;
		this.client = client;
		this.userLinkList = userLinkList;
		this.combobox = combobox;
		
		isStop = false;
	}
	
	public void run() {
		//�������˷����û����б�
		sendUserList();
		
		while (!isStop && !client.getSocket().isClosed()) {
			try {
				String type = (String)client.getInput().readObject();
				
				if (type.equalsIgnoreCase("������Ϣ")) {
					String toSomebody = (String)client.getInput().readObject();
					String status = (String) client.getInput().readObject();
					String action = (String) client.getInput().readObject();
					String message = (String) client.getInput().readObject();
					
					String msg = client.getUsername() + " " + 
							action + " �� " +
							toSomebody + " ˵��" + 
							message + "\n";
					if (status.equalsIgnoreCase("���Ļ�")) {
						msg = "[���Ļ�] " + msg;
					}
					textarea.append(msg);
					
					if (toSomebody.equalsIgnoreCase("������") ) {
						sendToAll(msg);	//�������˷�����Ϣ
					}
					else {
						try {
							client.getOutput().writeObject("������Ϣ");
							client.getOutput().flush();
							client.getOutput().writeObject(msg);
							client.getOutput().flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						Node node = userLinkList.findUser(toSomebody);
						if (node != null) {
							node.getOutput().writeObject("������Ϣ");
							node.getOutput().flush();
							node.getOutput().writeObject(msg);
							node.getOutput().flush();
						}
					} 
						
				} else if (type.equalsIgnoreCase("��������")) {
					String toSomebody = (String)client.getInput().readObject();
					String status = (String) client.getInput().readObject();
					String action = (String) client.getInput().readObject();
					String message = (String) client.getInput().readObject();
					
					String msg = client.getUsername() + " [��������] " + 
							message + "\n";
					
					//����������д��txt�ļ�
					boolean writeRes = false;
					while(!writeRes) {
						writeRes = writeData(basePath + client.getUsername() + ".txt", message);
					}
					
					textarea.append(msg);
					
					try {
						client.getOutput().writeObject("������Ϣ");
						client.getOutput().flush();
						client.getOutput().writeObject(msg);
						client.getOutput().flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
				else if (type.equalsIgnoreCase("�û�����") ) {
					Node node = userLinkList.findUser(client.getUsername()) ;
					userLinkList.delUser(node);
					
					String msg = "�û� " + client.getUsername() + " ����\n";
					int count = userLinkList.getCount();
					
					combobox.removeAllItems();
					combobox.addItem("������");
					int i = 0;
					while (i < count) {
						node = userLinkList.findUser(i);
						if (node == null) {
							i++;
							continue;
						}
						
						combobox.addItem(node.getUsername());
						i++;
					}
					combobox.setSelectedIndex(0);
					
					textarea.append(msg);
					textfield.setText("�����û� " + userLinkList.getCount() + " ��\n");
					
					sendToAll(msg);	//�������˷�����Ϣ
					sendUserList();	//���·����û��б�ˢ��
					
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * �������˷�����Ϣ
	 */
	public void sendToAll(String msg) {
		int count = userLinkList.getCount();
		
		int i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			
			try {
				node.getOutput().writeObject("������Ϣ");
				node.getOutput().flush();
				node.getOutput().writeObject(msg);
				node.getOutput().flush();
			} catch (Exception e) {
//				e.printStackTrace();
			}
			
			i++;
		}
	}
	
	/*
	 * �������˷����û����б�
	 */
	public void sendUserList() {
		String userlist = "";
		int count = userLinkList.getCount();
		
		int i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			
			userlist += node.getUsername();
			userlist += "\n";
			i++;
		}
		
		i = 0; 
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			
			try {
				node.getOutput().writeObject("�û��б�");
				node.getOutput().flush();
				node.getOutput().writeObject(userlist);
				node.getOutput().flush();
			} catch (Exception e) {
//				e.printStackTrace();
			}
			i++;
		}
	}
	
	public boolean writeData(String filePath, String msg) throws Exception {
		try {
			File file = textTools.checkExist(filePath);
			textTools.appendInfoToFile(filePath, msg);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
