package File.fileScannerAndNio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.nio.Varry;

public class Client {

	public static void main(String[] args) throws IOException {
		
		List<File> fileList = new ArrayList<File>();
		fileList = FileScanner.getFiles(Varry.FILE_INPUT_PATH);
		System.out.println("----------Client----------------------");
		System.out.println(fileList.size());
		System.out.println("--------------------------------------");
		for (File file : fileList) {
//			System.out.println(file.getPath());
//			NIOClient.init(file.getPath());
//			ClientHandle client = new ClientHandle(file.getPath());
			new Thread(new ClientHandle(file.getPath())).start();
		}
	}

}
