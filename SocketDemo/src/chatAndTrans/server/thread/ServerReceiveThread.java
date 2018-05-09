package chatAndTrans.server.thread;

import java.io.File;

import javax.swing.*;

import chatAndTrans.server.entity.Node;
import chatAndTrans.server.entity.UserLinkList;
import chatAndTrans.server.text.TextTools;

/**
 * 服务器端接受消息的类
 * @author DossS
 *
 */
public class ServerReceiveThread extends Thread {
	JTextArea textarea;
	JTextField textfield;
	JComboBox combobox;
	Node client;
	UserLinkList userLinkList;	//用户链表
	TextTools textTools;	//	文件读写
	String basePath = "D:\\Project\\SocketTest\\text\\output\\";	//	数据文件存放基路径
	
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
		//向所有人发送用户的列表
		sendUserList();
		
		while (!isStop && !client.getSocket().isClosed()) {
			try {
				String type = (String)client.getInput().readObject();
				
				if (type.equalsIgnoreCase("聊天信息")) {
					String toSomebody = (String)client.getInput().readObject();
					String status = (String) client.getInput().readObject();
					String action = (String) client.getInput().readObject();
					String message = (String) client.getInput().readObject();
					
					String msg = client.getUsername() + " " + 
							action + " 对 " +
							toSomebody + " 说：" + 
							message + "\n";
					if (status.equalsIgnoreCase("悄悄话")) {
						msg = "[悄悄话] " + msg;
					}
					textarea.append(msg);
					
					if (toSomebody.equalsIgnoreCase("所有人") ) {
						sendToAll(msg);	//向所有人发送消息
					}
					else {
						try {
							client.getOutput().writeObject("聊天信息");
							client.getOutput().flush();
							client.getOutput().writeObject(msg);
							client.getOutput().flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						Node node = userLinkList.findUser(toSomebody);
						if (node != null) {
							node.getOutput().writeObject("聊天信息");
							node.getOutput().flush();
							node.getOutput().writeObject(msg);
							node.getOutput().flush();
						}
					} 
						
				} else if (type.equalsIgnoreCase("传感数据")) {
					String toSomebody = (String)client.getInput().readObject();
					String status = (String) client.getInput().readObject();
					String action = (String) client.getInput().readObject();
					String message = (String) client.getInput().readObject();
					
					String msg = client.getUsername() + " [传感数据] " + 
							message + "\n";
					
					//将传感数据写入txt文件
					boolean writeRes = false;
					while(!writeRes) {
						writeRes = writeData(basePath + client.getUsername() + ".txt", message);
					}
					
					textarea.append(msg);
					
					try {
						client.getOutput().writeObject("聊天信息");
						client.getOutput().flush();
						client.getOutput().writeObject(msg);
						client.getOutput().flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
				else if (type.equalsIgnoreCase("用户下线") ) {
					Node node = userLinkList.findUser(client.getUsername()) ;
					userLinkList.delUser(node);
					
					String msg = "用户 " + client.getUsername() + " 下线\n";
					int count = userLinkList.getCount();
					
					combobox.removeAllItems();
					combobox.addItem("所有人");
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
					textfield.setText("在线用户 " + userLinkList.getCount() + " 人\n");
					
					sendToAll(msg);	//向所有人发送消息
					sendUserList();	//重新发送用户列表，刷新
					
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * 向所有人发送消息
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
				node.getOutput().writeObject("聊天信息");
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
	 * 向所有人发送用户的列表
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
				node.getOutput().writeObject("用户列表");
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
