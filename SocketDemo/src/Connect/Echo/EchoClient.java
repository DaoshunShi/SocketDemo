package Connect.Echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

	private int port = 8000;
	private String host = "localhost";
//	private String host = "192.168.0.6";
	private Socket socket;

	public EchoClient() {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public BufferedReader getBufferedReader(Socket socket) throws IOException {
		InputStream socketR = socket.getInputStream();// �ֽ���
		return new BufferedReader(new InputStreamReader(socketR));
	}

	/**
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public PrintWriter getBufferedWriter(Socket socket) throws IOException {
		OutputStream socketW = socket.getOutputStream();
		return new PrintWriter(socketW, true);
	}

	/**
	 * @throws IOException
	 */
	public void talk() throws IOException {
		try {
			BufferedReader br = getBufferedReader(socket);
			PrintWriter printWriter = getBufferedWriter(socket);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while ((msg = bufferedReader.readLine()) != null) {
//				printWriter.println(msg+"��ӡ�ڿͻ��˿���̨��"); //����ᱻ��������ȡ��
				printWriter.write(msg + "��ӡ�ڿͻ��˿���̨");
				printWriter.println();
				// System.out.println("\n"+br.readLine()+"����socket���ᱻ��������ȡ��\n");
				System.out.println(br.readLine()); // ����ǿͻ��Լ������Լ�����Ķ��������� һ��Ҫ�����
				if (msg.equals("bye")) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if (socket != null && socket.isConnected() && (!socket.isClosed()))
				socket.close();
		}
	}

	public static void main(String[] args) {
		try {
			new EchoClient().talk();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}