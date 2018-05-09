package text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextReader {

	public static void main(String[] args) throws Exception {
 
		String inputPath = "D:\\Project\\SocketTest\\text\\input\\input1.txt";
		String outputPath = "D:\\Project\\SocketTest\\text\\output\\李强2.txt";
		
//		readAndWrite(inputPath, outputPath);
//		appendInfoToFile(outputPath, "hahaha");
//		List<String> list = readInfoFromFile(inputPath);
//		for (String str : list) {
//			System.out.println(str);
//		}
		
		File res = checkExist(outputPath);
//		appendInfoToFile(outputPath, "再次编写");
		for (int i= 0; i<100; i++) {
			String temp = "李强2  "  + i + " *** " + i + i + i + i + i + i + "---";
			appendInfoToFile(outputPath, temp);
		}
//		System.out.println(res.exists());
	}
	
	//文件读取与写入（放弃）
	public static void readAndWrite(String inputPath, String outPath) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
      	  
            /* 读入TXT文件 */  
//            String pathname = "D:\\Project\\SocketTest\\text\\input\\input1.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(inputPath); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
            	System.out.println(line);
                line = br.readLine(); // 一次读入一行数据  
            }  
  
            /* 写入Txt文件 */  
//            File writename = new File("D:\\Project\\SocketTest\\text\\output\\output1.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
            File writename = new File(outPath);
            if (!writename.exists()) {
            	writename.createNewFile(); // 创建新文件 
            }
            
            //将信息写入输出文件
            reader = new InputStreamReader(new FileInputStream(filename));
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            br = new BufferedReader(reader);
            line = br.readLine();
            while (line != null) {
            	out.write(line);
            	out.newLine();
            	line = br.readLine();
            }
            out.write("我会写入文件啦1\r\n"); // \r\n即为换行  
            out.flush(); // 把缓存区内容压入文件  
            out.close(); // 最后记得关闭文件  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
	
	//清空已有的文件内容，以便下次重新写入新的内容
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
	
    // 在已有的文件后面追加信息
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
	
	//读取文件内容，文件中的内容为String
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
	
	//判断文件及目录是否存在，若不存在则创建文件及目录
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
