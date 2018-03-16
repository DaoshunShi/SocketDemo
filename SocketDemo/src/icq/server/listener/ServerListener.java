package icq.server.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import icq.server.entity.Node;
import icq.server.entity.UserLinkList;
import icq.server.frame.Help;
import icq.server.frame.MainFrame;
import icq.server.frame.PortConf;
import icq.server.thread.ServerListenThread;

/**
 * ������Ϣ������
 * @author DossS
 *
 */
public class ServerListener implements ActionListener {
	
	public static int port = 8888;	//�������˵������˿�
	ServerSocket serverSocket;		//��������Socket
	UserLinkList userLinkList;		//�û�����
	ServerListenThread listenThread;	//�����������û�������
	
	MainFrame mainFrame;
	
	public ServerListener(MainFrame mainFrame) {
		this.mainFrame  = mainFrame;
		
		//Ϊ�˵�������¼�����
		mainFrame.portItem.addActionListener(this);
		mainFrame.startItem.addActionListener(this);
		mainFrame.stopItem.addActionListener(this);
		mainFrame.exitItem.addActionListener(this);
		mainFrame.helpItem.addActionListener(this);
		
		//��Ӱ�ť���¼�����
		mainFrame.portSet.addActionListener(this);
		mainFrame.startServer.addActionListener(this);
		mainFrame.stopServer.addActionListener(this);
		mainFrame.exitButton.addActionListener(this);
		
		//���ϵͳ��Ϣ���¼�����
		mainFrame.sysMessage.addActionListener(this);
		mainFrame.sysMessageButton.addActionListener(this);
	}
	
	/**
	 * �¼�����
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == mainFrame.startServer || obj == mainFrame.startItem) {	//������������
			startService();
		} else if (obj == mainFrame.stopServer || obj == mainFrame.stopItem) {	//ֹͣ��������
			int j = mainFrame.showConfirmDialog("���ֹͣ������", "ֹͣ����");
			
			if (j == 1) {
				stopService();
			}
		} else if (obj == mainFrame.portSet || obj == mainFrame.portItem) {	//�˿�����
			//�����˿����õĶԻ���
			PortConf portConf = new PortConf(mainFrame);
			portConf.setVisible(true);
		} else if (obj == mainFrame.exitButton || obj == mainFrame.exitItem) {	//�˳�����
			int j = mainFrame.showConfirmDialog("���Ҫ�Ƴ���?", "�˳�");
			
			if (j == 1) {
				stopService();
				System.exit(0);
			}
		} else if (obj == mainFrame.helpItem) {	//�˵����еİ���
			//���������Ի���
			Help helpDialog = new Help(mainFrame);
			helpDialog.setVisible(true);
		} else if (obj == mainFrame.sysMessage || obj == mainFrame.sysMessageButton) {	//����ϵͳ��Ϣ
			sendSystemMessage();
		}
	}
	
	/**
	 * ���������
	 */
	public void startService(){
		try{
			serverSocket = new ServerSocket(port,10);
			mainFrame.messageShow.append("������Ѿ���������"+port+"�˿�����...\n");
			
			mainFrame.startServer.setEnabled(false);
			mainFrame.startItem.setEnabled(false);
			mainFrame.portSet.setEnabled(false);
			mainFrame.portItem.setEnabled(false);

			mainFrame.stopServer .setEnabled(true);
			mainFrame.stopItem .setEnabled(true);
			mainFrame.sysMessage.setEnabled(true);
		}
		catch (Exception e){
			//System.out.println(e);
		}
		
		
		userLinkList = new UserLinkList();
		
		
		
		listenThread = new ServerListenThread(serverSocket,mainFrame.combobox,
				mainFrame.messageShow,mainFrame.showStatus,userLinkList);
		listenThread.start();
	}
	
	/**
	 * �رշ�����
	 */
	public void stopService() {
		try {
			//�������˷��ͷ������رյ���Ϣ
			sendStopToAll();
			listenThread.isStop = true;
			serverSocket.close();
			
			int count = userLinkList.getCount();
			
			int i = 0 ;
			while ( i < count) {
				Node node = userLinkList.findUser(i) ;
				
				node.getInput().close();
				node.getOutput().close();
				node.getSocket().close();
				
				i++;
			}
			
			mainFrame.stopServer.setEnabled(false);
			mainFrame.stopItem.setEnabled(false);
			mainFrame.startServer.setEnabled(true);
			mainFrame.startItem.setEnabled(true);
			mainFrame.portSet.setEnabled(true);
			mainFrame.portItem.setEnabled(true);
			mainFrame.sysMessage.setEnabled(false);
			
			mainFrame.messageShow.append("���������Ѿ��ر�\n");
			
			mainFrame.combobox.removeAllItems();
			mainFrame.combobox.addItem("������");
			
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}
	
	/**
	 * �������˷��ͷ������رյ���Ϣ
	 */
	public void sendStopToAll() {
		int count = userLinkList.getCount();
		
		int i = 0;
		while (i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			
			try {
				node.getOutput().writeObject("����ر�");
				node.getOutput().flush();
			} catch (Exception e) {
//				e.printStackTrace();
			}
			
			i++;
		}
	}
	
	/**
	 * �������˷�����Ϣ
	 */
	public void sendMsgToAll(String msg) {
		int count = userLinkList.getCount();	//�û�����
		
		int i = 0;
		while(i < count) {
			Node node = userLinkList.findUser(i);
			if (node == null) {
				i++;
				continue;
			}
			
			try {
				node.getOutput().writeObject("ϵͳ��Ϣ");
				node.getOutput().flush();
				node.getOutput().writeObject(msg);
				node.getOutput().flush();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			i++;
		}
		
		mainFrame.sysMessage.setText("");
	}
	
	/**
	 * ��ͻ����û�������Ϣ
	 */
	public void sendSystemMessage() {
		String toSomebody = mainFrame.combobox.getSelectedItem().toString();
		String message = mainFrame.sysMessage.getText() + "\n";
		
		mainFrame.messageShow.append(message);
		
		if (toSomebody.equalsIgnoreCase("������")) {
			//�������˷�����Ϣ
			sendMsgToAll(message);
		} else {
			//��ĳ���û�������Ϣ
			Node node = userLinkList.findUser(toSomebody);
			
			try {
				node.getOutput().writeObject("ϵͳ��Ϣ");
				node.getOutput().flush();
				node.getOutput().writeObject(message);
				node.getOutput().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainFrame.sysMessage.setText(""); //��������Ϣ������Ϣ���
		}
	}
}
