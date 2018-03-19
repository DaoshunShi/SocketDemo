package File.FileChooser;

import java.awt.event.*;
import java.io.File;
import java.util.List;

import javax.swing.*;

/**
 * 文件选择框多选文件
 * @author DossShi
 * 
 * 使用FileChooser.setMultiSelectEnabled(true)，将FileChooser的可多选设置为true，即可实现多选文件。
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
		jfc.showDialog(new JLabel(), "选择");
		File[] files = jfc.getSelectedFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				System.out.println("文件夹：" + files[i].getAbsolutePath());
			} else if (files[i].isFile()) {
				System.out.println("文件：" + files[i].getAbsolutePath());
			}
			System.out.println(files[i].getName());
		}
	}
	
	public static void main(String[] args) {
		new FileChooserMultiSelect();
	}
}
