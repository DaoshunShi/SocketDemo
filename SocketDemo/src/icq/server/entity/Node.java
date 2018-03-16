package icq.server.entity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  用户链表的结点类
 */
public class Node {

	
	String username = null;
	Socket socket = null;
	ObjectOutputStream output = null;
	ObjectInputStream input = null;
	
	Node next = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}
	
	
}
