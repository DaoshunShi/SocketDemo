package File.FileChooser;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChoose_FileFilter {

	public static void main(String[] args) {
//		fileChoose();
		fileFilter();
	}
	
	public static void fileChoose() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getName());
	}
	
	public static void fileFilter() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getName());
	}

}
