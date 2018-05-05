package File.fileScannerAndNio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;  
import java.util.Set;  

/** 
 * NIO¿Í»§¶Ë 
 * @author yangtao__anxpp.com 
 * @version 1.0 
 */  
public class ClientHandle implements Runnable{  
    private String filePath = "";
  
    public ClientHandle(String filePath) {  
        this.filePath = filePath;
    }  
 
    @Override  
    public void run() {  
    	try {
			NIOClient.init(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  

}  