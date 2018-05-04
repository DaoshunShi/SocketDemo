package Connect.Greeting.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//需使用cmd手动编译执行
public class GreetingClient {
	public static void main(String[] args) {
//		String serverName = args[0];
//		int port = Integer.parseInt(args[1]);
		String serverName = "localhost";
		int port = 6066;
		
		try {
			System.out.println("连接到主机 ： " + serverName + " , 端口号 ： " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("远程主机地址 ： " + client.getRemoteSocketAddress());
			
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("Hello from " + client.getLocalSocketAddress());
			
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("服务器相应 ： " + in.readUTF());
			client.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
