package icq.server.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class Help extends JDialog{
	
	JPanel titlePanel = new JPanel();
	JPanel contentPanel = new JPanel();
	JPanel closePanel = new JPanel();
	
	JButton close = new JButton();
	JLabel title = new JLabel("�����ҷ���˰���");
	JTextArea help = new JTextArea();
	
	Color bg = new Color(255, 255, 255);
	
	public Help(JFrame frame) {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//��������λ�ã�ʹ�Ի������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - 400)/2, (int)(screenSize.height - 320)/2);
		this.setResizable(false);
	}
	
	private void jbInit() throws Exception {
		this.setSize(new Dimension(400, 200));
		this.setTitle("����");
		
		titlePanel.setBackground(bg);
		contentPanel.setBackground(bg);
		closePanel.setBackground(bg);
		
		titlePanel.add(new Label("          "));
		titlePanel.add(title);
		titlePanel.add(new Label("          "));
		
		help.setText("1�����÷������˵������˿� ��Ĭ�϶˿�Ϊ8888����\n" + 
				"2��������������񡱰�ť�����ָ���Ķ˿��������� \n" + 
				"3��ѡ����Ҫ������Ϣ���û�������Ϣ����д����Ϣ��֮���ɷ�����Ϣ��\n" +
				"4����Ϣ״̬������ʾ��������ǰ��������ֹͣ״̬��" + 
				"�û����͵���Ϣ��\n	�������˷��͵�ϵͳ��Ϣ��"  );
		help.setEditable(false);
		
		contentPanel.add(help);

		close.setText("�ر�");
		closePanel.add(new Label("            "));
		closePanel.add(close);
		closePanel.add(new Label("            "));
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(titlePanel, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(closePanel, BorderLayout.SOUTH);
		
		//�¼�����
		close.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
		});
	}

}
