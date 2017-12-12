import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Agent a1 = new Agent(5000);
		Agent a2 = new Agent(6000,a1);
		a1.debugAddAdr();
		Adress adr = new Adress(5000,InetAddress.getByName("localhost"));
		
		
		Thread listenThread = new Thread(a1.listenThread);
		listenThread.start();
		Thread listenThread2 = new Thread(a2.listenThread);
		listenThread2.start();
		Thread.sleep(1000);
		//a2.sendMessage("CLK",adr);
		a2.showAdresses();
		a1.showAdresses();
		
	}

}
