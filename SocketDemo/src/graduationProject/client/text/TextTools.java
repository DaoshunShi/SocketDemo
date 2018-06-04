package graduationProject.client.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextTools {
	
	
	/**
	 * 清空已有的文件内容，以便下次重新写入新的内容
	 * @param fileName
	 */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 在已有的文件后面追加信息
	 * @param fileName 写入文件目录
	 * @param info 最佳信息
	 */
	public static void appendInfoToFile(String fileName, String info) {
		File file =new File(fileName);
        try {

            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWriter =new FileWriter(file, true);
            info =info +System.getProperty("line.separator");
            fileWriter.write(info);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 读取文件内容，文件中的内容为String
	 * @param fileName 文件路径
	 * @return
	 */
	public static List<String> readInfoFromFile(String fileName) {
        File file =new File(fileName);
        if(!file.exists()) {
            return null;
        }
        List<String> resultStr =new ArrayList<String>();
        try {
            BufferedReader bufferedReader =new BufferedReader(new FileReader(file));
            String str =null;
            while(null !=(str=bufferedReader.readLine())) {
                resultStr.add(str);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return resultStr;
    }
	
	/**
	 * 判断文件及目录是否存在，若不存在则创建文件及目录
	 * @param filepath 文件路径
	 * @return
	 * @throws Exception
	 */
	public static File checkExist(String filepath) throws Exception{
		File file = new File(filepath);
		if (file.exists()) {// 判断文件目录的存在
			System.out.println("文件夹存在！");
			if (file.isDirectory()) {// 判断文件的存在性
				System.out.println("文件存在！");
			} else {
				file.createNewFile();// 创建文件
				System.out.println("文件不存在，创建文件成功！");
			}
		} else {
			System.out.println("文件夹不存在！");
			File file2 = new File(file.getParent());
			file2.mkdirs();
			System.out.println("创建文件夹成功！");
			if (file.isDirectory()) {
				System.out.println("文件存在！");
			} else {
				file.createNewFile();// 创建文件
				System.out.println("文件不存在，创建文件成功！");
			}
		}
		return file;
	}
	
}
