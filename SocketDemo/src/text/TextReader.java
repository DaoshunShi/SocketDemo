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
		String outputPath = "D:\\Project\\SocketTest\\text\\output\\��ǿ2.txt";
		
//		readAndWrite(inputPath, outputPath);
//		appendInfoToFile(outputPath, "hahaha");
//		List<String> list = readInfoFromFile(inputPath);
//		for (String str : list) {
//			System.out.println(str);
//		}
		
		File res = checkExist(outputPath);
//		appendInfoToFile(outputPath, "�ٴα�д");
		for (int i= 0; i<100; i++) {
			String temp = "��ǿ2  "  + i + " *** " + i + i + i + i + i + i + "---";
			appendInfoToFile(outputPath, temp);
		}
//		System.out.println(res.exists());
	}
	
	//�ļ���ȡ��д�루������
	public static void readAndWrite(String inputPath, String outPath) {
        try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
      	  
            /* ����TXT�ļ� */  
//            String pathname = "D:\\Project\\SocketTest\\text\\input\\input1.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��  
            File filename = new File(inputPath); // Ҫ��ȡ����·����input��txt�ļ�  
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
            	System.out.println(line);
                line = br.readLine(); // һ�ζ���һ������  
            }  
  
            /* д��Txt�ļ� */  
//            File writename = new File("D:\\Project\\SocketTest\\text\\output\\output1.txt"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
            File writename = new File(outPath);
            if (!writename.exists()) {
            	writename.createNewFile(); // �������ļ� 
            }
            
            //����Ϣд������ļ�
            reader = new InputStreamReader(new FileInputStream(filename));
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            br = new BufferedReader(reader);
            line = br.readLine();
            while (line != null) {
            	out.write(line);
            	out.newLine();
            	line = br.readLine();
            }
            out.write("�һ�д���ļ���1\r\n"); // \r\n��Ϊ����  
            out.flush(); // �ѻ���������ѹ���ļ�  
            out.close(); // ���ǵùر��ļ�  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
	
	//������е��ļ����ݣ��Ա��´�����д���µ�����
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
	
    // �����е��ļ�����׷����Ϣ
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
	
	//��ȡ�ļ����ݣ��ļ��е�����ΪString
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
	
	//�ж��ļ���Ŀ¼�Ƿ���ڣ����������򴴽��ļ���Ŀ¼
	public static File checkExist(String filepath) throws Exception{
		File file = new File(filepath);
		if (file.exists()) {// �ж��ļ�Ŀ¼�Ĵ���
			System.out.println("�ļ��д��ڣ�");
			if (file.isDirectory()) {// �ж��ļ��Ĵ�����
				System.out.println("�ļ����ڣ�");
			} else {
				file.createNewFile();// �����ļ�
				System.out.println("�ļ������ڣ������ļ��ɹ���");
			}
		} else {
			System.out.println("�ļ��в����ڣ�");
			File file2 = new File(file.getParent());
			file2.mkdirs();
			System.out.println("�����ļ��гɹ���");
			if (file.isDirectory()) {
				System.out.println("�ļ����ڣ�");
			} else {
				file.createNewFile();// �����ļ�
				System.out.println("�ļ������ڣ������ļ��ɹ���");
			}
		}
		return file;
	}


}
