package File.FileChooser;

import java.awt.event.*;
import java.io.File;
import java.util.List;

import javax.swing.*;

/**
 * �ļ�ѡ����ѡ�ļ�
 * @author DossShi
 * 
 * ʹ��FileChooser.setMultiSelectEnabled(true)����FileChooser�Ŀɶ�ѡ����Ϊtrue������ʵ�ֶ�ѡ�ļ���
 *
 */
public class FileChooserMultiSelect extends JFrame implements ActionListener {
	
	JButton open = null;
	
	public FileChooserMultiSelect() {
		open = new JButton("open");
		this.add(open);
		this.setBounds(400, 200, 100, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		open.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setMultiSelectionEnabled(true);
		jfc.showDialog(new JLabel(), "ѡ��");
		File[] files = jfc.getSelectedFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				System.out.println("�ļ��У�" + files[i].getAbsolutePath());
			} else if (files[i].isFile()) {
				System.out.println("�ļ���" + files[i].getAbsolutePath());
			}
			System.out.println(files[i].getName());
		}
	}
	
	public static void main(String[] args) {
		new FileChooserMultiSelect();
	}
}
