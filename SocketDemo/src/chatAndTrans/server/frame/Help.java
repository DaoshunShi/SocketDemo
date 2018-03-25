package chatAndTrans.server.frame;

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
	JLabel title = new JLabel("聊天室服务端帮助");
	JTextArea help = new JTextArea();
	
	Color bg = new Color(255, 255, 255);
	
	public Help(JFrame frame) {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//设置运行位置，使对话框居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - 400)/2, (int)(screenSize.height - 320)/2);
		this.setResizable(false);
	}
	
	private void jbInit() throws Exception {
		this.setSize(new Dimension(400, 200));
		this.setTitle("帮助");
		
		titlePanel.setBackground(bg);
		contentPanel.setBackground(bg);
		closePanel.setBackground(bg);
		
		titlePanel.add(new Label("          "));
		titlePanel.add(title);
		titlePanel.add(new Label("          "));
		
		help.setText("1、设置服务器端的侦听端口 （默认端口为8888）。\n" + 
				"2、点击“启动服务”按钮便可在指定的端口启动服务。 \n" + 
				"3、选择需要接收消息的用户，在消息栏中写入消息，之后便可发送消息。\n" +
				"4、信息状态栏中显示服务器当前的启动与停止状态、" + 
				"用户发送的消息和\n	服务器端发送的系统消息。"  );
		help.setEditable(false);
		
		contentPanel.add(help);

		close.setText("关闭");
		closePanel.add(new Label("            "));
		closePanel.add(close);
		closePanel.add(new Label("            "));
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(titlePanel, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(closePanel, BorderLayout.SOUTH);
		
		//事件处理
		close.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
		});
	}

}
