import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static List<Adress> Net = new ArrayList<Adress>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//Agent test = new Agent(5000,0,5000,"www.informit.com");
		
		Agent a1 = new Agent(5000,0,5000);
		Net.add(a1.adr);
		Thread.sleep(842);
		Agent a2 = new Agent(6000,0,6000);
		Net.add(a2.adr);
		Thread.sleep(733);
		Agent a3 = new Agent(7000,0,7000);
		Net.add(a3.adr);
		Thread.sleep(733);
		Agent a4 = new Agent(8000,0,3000);
		Net.add(a4.adr);
		Thread.sleep(623);
		a1.addAdress(Net);
		a2.addAdress(Net);
		a3.addAdress(Net);
		a4.addAdress(Net);

		
		
		
		Thread listenThread = new Thread(a1.listenThread);
		listenThread.start();
		Thread listenThread2 = new Thread(a2.listenThread);
		listenThread2.start();
		Thread listenThread3 = new Thread(a3.listenThread);
		listenThread3.start();
		Thread listenThread4 = new Thread(a4.listenThread);
		listenThread4.start();
		
		//a2.sendMessage("CLK",adr);
		a4.showAdresses();
		while(true) {
			a1.showTime();
			a2.showTime();
			a3.showTime();
			a4.showTime();
			System.out.println("///////////////////");
			Thread.sleep(5000);
		}
		
		
		
	}

}
