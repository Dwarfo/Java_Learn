import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Agent {
	
	DatagramSocket agentSocket;
	Listener listenThread;
	Counter counter;
	Adress adr;
	int numberOfAnswers = 0;
	long toCount;


	List<Adress> adresses = new ArrayList<Adress>();
	
	
	public Agent(int port) throws SocketException, UnknownHostException {
		
		counter = new Counter(System.currentTimeMillis(),this);
		agentSocket = new DatagramSocket(port, InetAddress.getByName("localhost"));
		listenThread = new Listener(this);
		adr = new Adress(port,InetAddress.getByName("localhost"));
		
		Thread countThread = new Thread(this.counter);
		countThread.start();
		toCount = this.counter.getCurrentTime();
	
	}
	
	public Agent(int port, Agent agent) throws SocketException, UnknownHostException {
		
		this(port);
		this.adresses.add(agent.getAdress());
	}
	
	public void receiveMessage() throws IOException {
		
		byte[] receiveData = new byte[32];

		
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			agentSocket.receive(receivePacket);
			
        	String mesage = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
        	
        	String[] line = mesage.split("\\s+");
        
        	//System.out.println(mesage);
        
        	InetAddress IPAddress = receivePacket.getAddress();
        	int port = receivePacket.getPort();
        
        	Adress newAdr = new Adress(port, IPAddress);
        	if(!adresses.contains(newAdr))
        		adresses.add(newAdr);
        
        	
        	
        	if(line[0].equals("NAS")){
        		Adress anotherAdress = new Adress(Integer.valueOf(line[2]),InetAddress.getByName(line[1]));
        		if(!adresses.contains(anotherAdress))
        			this.adresses.add(anotherAdress);
        	}
        	
        	if(line[0].equals("NET")){
        		//System.out.println("Got net");
        		Sender sendMsg = new Sender("NAS " + String.valueOf(this.getAdress().ip) + " " + String.valueOf(this.getAdress().port),newAdr,this);
        		Thread sendThread = new Thread(sendMsg);
        		sendThread.start();
        	}
        	
        	if(line[0].equals("ANS")){
        		toCount += Long.valueOf(line[1]);
        		numberOfAnswers++;
        		//System.out.println("Count " + line[1]);
        		if(numberOfAnswers >= adresses.size()) {
        			System.out.println(numberOfAnswers + " size " + toCount + " toCount");
        			this.counter.setTime(toCount/(numberOfAnswers + 1));
        			numberOfAnswers = 0;
        			this.toCount = this.counter.getCurrentTime();
        		}
        	}
        	
        	if(line[0].equals("CLK")){
        		//System.out.println("Got clk");
        		Sender sendMsg = new Sender("ANS " + String.valueOf(this.counter.getCurrentTime()),newAdr,this);
        		Thread sendThread = new Thread(sendMsg);
        		sendThread.start();
        	}
		}
	}
	
	public void sendMessage(String message,Adress adr) throws IOException {
		
		//System.out.println("Message " + message +  " sended");
		byte[] sendData = new byte[1024];
		
		String answer = message;
        sendData = answer.getBytes();
        DatagramPacket sendPacket =  new DatagramPacket(sendData, sendData.length, adr.ip, adr.port);
        agentSocket.send(sendPacket);
	}
	
	public void actualizeTime() throws IOException {
		
		//System.out.println("actualizeTime");
		for(Iterator<Adress> i = adresses.iterator(); i.hasNext();) {
			Adress toSend = i.next();
			
			this.sendMessage("CLK", toSend);
		}
	}

	
	public Adress getAdress() {
		return this.adr;
	}
	
	public void showTime() {
		System.out.println(this.counter.getTime());
	}
	public void showAdresses() {
		for(Iterator<Adress> i = adresses.iterator(); i.hasNext();) {
			Adress toSend = i.next();
			
			System.out.println("Adress " + toSend.ip + ":" + toSend.port);
		}
	}
	public void debugAddAdr() throws UnknownHostException {
		Adress toSend = new Adress(6000,InetAddress.getByName("localhost") );
		adresses.add(toSend);
	}
}
