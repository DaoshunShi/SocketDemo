package Echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {

	private Socket socket;

	public ServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("�����ӵ��IP��" + socket.getInetAddress() + "�˿�Ϊ:" + socket.getPort());
			BufferedReader br = getBufferedReader(socket);
			PrintWriter printWriter = getBufferedWriter(socket);
			String str = null;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				printWriter.println(str);
				if (str.equals("bye")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	private BufferedReader getBufferedReader(Socket socket) throws IOException {
		InputStream socketR = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketR));
	}

	/**
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	private PrintWriter getBufferedWriter(Socket socket) throws IOException {
		OutputStream socketW = socket.getOutputStream();
		return new PrintWriter(socketW, true);
	}

}