package chatAndTrans.server.frame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import chatAndTrans.server.listener.ServerListener;

public class MainFrame extends JFrame{
	
	ServerListener sendInfo;
	
	public JComboBox combobox;	//ѡ������Ϣ�Ľ����ߣ��б���ʾ
	public JTextArea messageShow;	//�������˵���Ϣ��ʾ
	JScrollPane messageScrollPane;	//��Ϣ��ʾ�Ĺ�����
	public JTextField showStatus;	//��ʾ�û�����״̬����������
	JLabel sendToLabel, messageLabel;
	public JTextField sysMessage;	//����������Ϣ�ķ��ͣ�д��Ϣ
	public JButton sysMessageButton;	//����������Ϣ�ķ��Ͱ�ť
	
	//�����˵���
	JMenuBar jMenuBar = new JMenuBar();
	//�����˵���
	JMenu serviceMenu = new JMenu("����");
	//�����˵���
	public JMenuItem portItem = new JMenuItem("�˿�����");
	public JMenuItem startItem = new JMenuItem("��������");
	public JMenuItem stopItem = new JMenuItem("ֹͣ����");
	public JMenuItem exitItem = new JMenuItem("�˳�");
	
	JMenu helpMenu = new JMenu("����");
	public JMenuItem helpItem = new JMenuItem("����");
	
	//����������
	JToolBar toolBar = new JToolBar();
	
	//�����������еİ�ť���
	public JButton portSet;	//��������������
	public JButton startServer;	//����������������
	public JButton stopServer;	//�رշ�����������
	public JButton exitButton;	//�˳���ť
	
	//��ܵĴ�С
	Dimension faceSize = new Dimension(400, 600);
	
	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;
	
	/*
	 * ���캯��
	 */
	public MainFrame() {
		init();	//��ʼ������
		
		//��ӿ�ܵĹر��¼�����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//���ÿ�ܵĴ�С
		this.setSize(faceSize);
		this.setVisible(true);
		//��������ʱ���ڵ�λ��
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-faceSize.getWidth())/2, (int)(screenSize.height-faceSize.getHeight())/2);
		this.setResizable(false);
		
		this.setTitle("�����ҷ����");//���ñ���
		
	}
	
	/**
	 * �����ʼ������
	 */
	public void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//��Ӳ˵���
		serviceMenu.add(portItem);
		serviceMenu.add(startItem);
		serviceMenu.add(stopItem);
		serviceMenu.add(exitItem);
		jMenuBar.add(serviceMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar (jMenuBar);
		
		//��ʼ����ť
		portSet = new JButton("�˿�����");
		startServer = new JButton("��������");
		stopServer = new JButton("ֹͣ����");
		exitButton = new JButton("�˳�");
		//����ť��ӵ�������
		toolBar.add(portSet);
		toolBar.addSeparator();//��ӷָ���
		toolBar.add(startServer);
		toolBar.add(stopServer);
		toolBar.addSeparator();	//��ӷָ���
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		//��ʼʱ����ֹͣ����ť������
		stopServer.setEnabled(false);
		stopItem.setEnabled(false);
		
		combobox = new JComboBox();
		combobox.insertItemAt("������", 0);
		combobox.setSelectedIndex(0);
		
		messageShow = new JTextArea();
		messageShow.setEditable(false);
		//��ӹ�����
		messageScrollPane = new JScrollPane(messageShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();
		
		showStatus = new JTextField(35);
		showStatus.setEditable(false);
		
		sysMessage = new JTextField(24);
		sysMessage.setEnabled(false);
		sysMessageButton = new JButton();
		sysMessageButton.setText("����");
		
		sendToLabel = new JLabel("��������");
		messageLabel = new JLabel("������Ϣ��");
		downPanel = new JPanel();
		girdBag = new GridBagLayout();
		downPanel.setLayout(girdBag);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 0;
		girdBagCon.gridwidth = 3;
		girdBagCon.gridheight = 2;
		girdBagCon.ipadx = 5;
		girdBagCon.ipady = 5;
		JLabel none = new JLabel("    ");
		girdBag.setConstraints(none, girdBagCon);
		downPanel.add(none);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 2;
		girdBagCon.insets = new Insets(1, 0, 0, 0);
		girdBagCon.ipadx = 5;
		girdBagCon.ipady = 5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
		girdBag.setConstraints(combobox, girdBagCon);
		downPanel.add(combobox);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(sysMessage, girdBagCon);
		downPanel.add(sysMessage);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 2;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(sysMessageButton, girdBagCon);
		downPanel.add(sysMessageButton);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 4;
		girdBagCon.gridwidth = 3;
		girdBag.setConstraints(showStatus, girdBagCon);
		downPanel.add(showStatus);
		
		contentPane.add(messageScrollPane, BorderLayout.CENTER);
		contentPane.add(downPanel, BorderLayout.SOUTH);
		
		sendInfo = new ServerListener(this);
		
		//�رճ���ʱ�Ĳ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sendInfo.stopService();
				System.exit(0);
			}
		});
		
	}
	
	public int showConfirmDialog (String title, String msg) {
		int j = JOptionPane.showConfirmDialog(this, title, msg, JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (j == JOptionPane.YES_OPTION) {
			return 1;
		}
		return 0;
		
	}
	
	public static void main (String[] args) {
		MainFrame mainFrame = new MainFrame();
	}

}
