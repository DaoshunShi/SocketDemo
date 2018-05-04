package HelloWorld;

import java.io.File;
import java.util.List;

import File.fileScanner.FileScanner;

public class Hello {

	public static void main(String[] args) {
		System.out.println("Hello Java Socket Demo");
		
		List<File> fileList = FileScanner.getFiles("D:\\Project\\SocketTest\\FileInput");
		System.out.println(fileList.size());
	}

}
