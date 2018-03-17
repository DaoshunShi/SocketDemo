package icq.client.frame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import icq.client.listener.ClientListener;

/**
 * �ͻ���������
 * @author DossShi
 *
 */
public class MainFrame extends JFrame {
	
	ClientListener clientListener;
	
	public JComboBox combobox;	//ѡ������Ϣ�Ľ�����
	public JTextArea messageShow;	//�ͻ��˵���Ϣ��ʾ
	JScrollPane messageScrollPane;	//��Ϣ��ʾ�Ĺ�����
	
	JLabel express, sendToLabel, messageLabel;
	
	public JTextField clientMessage;	//�ͻ�����Ϣ�ķ���
	public JCheckBox checkbox;	//���Ļ�
	public JComboBox actionlist;	//����ѡ��
	public JButton clientMessageButton;	//������Ϣ
	public JTextField showStatus;	//��ʾ�û�����״̬
	
	//�����˵���
	JMenuBar jMenuBar = new JMenuBar();
	//�����˵���
	JMenu operateMenu = new JMenu("����");
	//�����˵���
	public JMenuItem loginItem = new JMenuItem("�û���½");
	public JMenuItem logoffItem = new JMenuItem("�û�ע��");
	public JMenuItem exitItem = new JMenuItem("�˳�");
	
	JMenu conMenu = new JMenu("����");
	public JMenuItem userItem = new JMenuItem("�û�����");
	public JMenuItem connectItem = new JMenuItem("��������");
	
	JMenu helpMenu = new JMenu("����");
	public JMenuItem helpItem = new JMenuItem("����");
	
	//����������
	JToolBar toolBar = new JToolBar();
	//�����������еİ�ť���
	public JButton loginButton;	//�û���½
	public JButton logoffButton;	//�û�ע��
	public JButton userButton;	//�û���Ϣ������
	public JButton connectButton;	//��������
	public JButton exitButton;	//�˳���ť
	
	//��ܵĴ�С
	Dimension faceSize = new Dimension(400, 600);
	
	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;
	
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
		this.setTitle(clientListener.userName); 	//���ñ���
	}
	
	/**
	 * �����ʼ������
	 */
	public void init() {
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//��Ӳ˵���
		operateMenu.add(loginItem);
		operateMenu.add(logoffItem);
		operateMenu.add(exitItem);
		jMenuBar.add(operateMenu);
		conMenu.add(userItem);
		conMenu.add(connectItem);
		jMenuBar.add(conMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar(jMenuBar);
		
		//��ʼ����ť
		loginButton = new JButton("��½");
		logoffButton = new JButton("ע��");
		userButton = new JButton("�û�����");
		connectButton = new JButton("��������");
		exitButton = new JButton("�˳�");
		//����������ʾ��Ϣ
		loginButton.setToolTipText("���ӵ�ָ���ķ�����");
		logoffButton.setToolTipText("��������Ͽ�����");
		userButton.setToolTipText("�����û���Ϣ");
		connectButton.setToolTipText("������Ҫ���ӵ��ķ�������Ϣ");
		//����ť��ӵ�������
		toolBar.add(userButton);
		toolBar.add(connectButton);
		toolBar.addSeparator();	//��ӷָ���
		toolBar.add(loginButton);	
		toolBar.add(logoffButton);
		toolBar.addSeparator();//��ӷָ���
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		checkbox = new JCheckBox("���Ļ�");
		checkbox.setSelected(false);
		
		actionlist = new JComboBox();
		actionlist.addItem("΢Ц��");
		actionlist.addItem("���˵�");
		actionlist.addItem("�����");
		actionlist.addItem("������");
		actionlist.addItem("С�ĵ�");
		actionlist.addItem("�����");
		actionlist.setSelectedIndex(0);
		
		//��ʼ��
		loginButton.setEnabled(true);
		logoffButton.setEnabled(false);
		
		combobox = new JComboBox();
		combobox.insertItemAt("������", 0);
		combobox.setSelectedIndex(0);
		
		messageShow = new JTextArea();
		messageShow.setEditable(false);
		//��ӹ�����
		messageScrollPane = new JScrollPane(messageShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();
		
		clientMessage = new JTextField(23);
		clientMessage.setEnabled(false);
		clientMessageButton = new JButton();
		clientMessageButton.setText("����");
		
		sendToLabel = new JLabel("��������");
		express = new JLabel("       ���飺   ");
		messageLabel = new JLabel("������Ϣ��");
		downPanel = new JPanel();
		girdBag = new GridBagLayout();
		downPanel.setLayout(girdBag);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 0;
		girdBagCon.gridwidth = 5;
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
//		girdBagCon.ipadx = 5;
//		girdBagCon.ipady = 5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
		girdBag.setConstraints(combobox, girdBagCon);
		downPanel.add(combobox);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 2;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_END;
		girdBag.setConstraints(express, girdBagCon);
		downPanel.add(express);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 3;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
//		girdBagCon.insets = new Insets(1, 0, 0, 0);
//		girdBagCon.ipadx = 5;
//		girdBagCon.ipady = 5;
		girdBag.setConstraints(actionlist, girdBagCon);
		downPanel.add(actionlist);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 4;
		girdBagCon.gridy = 2;
		girdBagCon.insets = new Insets(1, 0, 0, 0);
//		girdBagCon.ipadx = 5;
//		girdBagCon.ipady = 5;
		girdBag.setConstraints(checkbox, girdBagCon);
		downPanel.add(checkbox);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 3;
		girdBagCon.gridwidth = 3;
		girdBagCon.gridheight = 1;
		girdBag.setConstraints(clientMessage, girdBagCon);
		downPanel.add(clientMessage);
		
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 4;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(clientMessageButton, girdBagCon);
		downPanel.add(clientMessageButton);
		
		showStatus = new JTextField(35);
		showStatus.setEditable(false);
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy=  5;
		girdBagCon.gridwidth = 5;
		girdBag.setConstraints(showStatus, girdBagCon);
		downPanel.add(showStatus);
		
		contentPane.add(messageScrollPane, BorderLayout.CENTER);
		contentPane.add(downPanel, BorderLayout.SOUTH);
		
		clientListener = new ClientListener(this);
		
		//�رճ���ʱ�Ĳ���
		this.addWindowListener(  
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if (clientListener.type == 1) {
							clientListener.DisConnect();
						}
						System.exit(0);
					}
				});
		
		
		
	}

	public int showConfirmDialog(String title, String msg) {
		int j = JOptionPane.showConfirmDialog(this,  title, msg, JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (j == JOptionPane.YES_OPTION) {
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
	}
}
