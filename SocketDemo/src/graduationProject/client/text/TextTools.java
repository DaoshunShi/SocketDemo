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
	 * ������е��ļ����ݣ��Ա��´�����д���µ�����
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
	 * �����е��ļ�����׷����Ϣ
	 * @param fileName д���ļ�Ŀ¼
	 * @param info �����Ϣ
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
	 * ��ȡ�ļ����ݣ��ļ��е�����ΪString
	 * @param fileName �ļ�·��
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
	 * �ж��ļ���Ŀ¼�Ƿ���ڣ����������򴴽��ļ���Ŀ¼
	 * @param filepath �ļ�·��
	 * @return
	 * @throws Exception
	 */
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
