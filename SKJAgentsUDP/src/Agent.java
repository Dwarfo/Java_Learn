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
import java.util.Random;

public class Agent {
	
	DatagramSocket agentSocket;
	Listener listenThread;
	Sender sendThread;
	Counter counter;
	Adress adr;
	Random rand = new Random();
	int numberOfAnswers = 1;
	long toCount;
	int actualization = 0;
	//boolean sended = false;


	List<Adress> adresses = new ArrayList<Adress>();
	
	/*public Agent(int port,long startTime,long quant,String adr) throws SocketException, UnknownHostException {
		
		counter = new Counter(startTime, this, quant);
		agentSocket = new DatagramSocket(port, InetAddress.getByName(adr));
		listenThread = new Listener(this);
		//adres = new Adress(port,InetAddress.getByName(adr));
		
		Thread countThread = new Thread(this.counter);
		countThread.start();
		toCount = this.counter.getCurrentTime();
	
	}*/
	
	public Agent(int port,long startTime,long quant) throws SocketException, UnknownHostException {
		
		counter = new Counter(startTime, this, quant);
		agentSocket = new DatagramSocket(port, InetAddress.getByName("localhost"));
		listenThread = new Listener(this);
		adr = new Adress(port,InetAddress.getByName("localhost"));
		
		Thread countThread = new Thread(this.counter);
		countThread.start();
		toCount = this.counter.getCurrentTime();
	
	}
	
	public Agent(int port,long startTime,long quant, Agent agent) throws SocketException, UnknownHostException {
		
		this(port,startTime,quant);
		this.adresses.add(agent.getAdress());
	}
	
	public void receiveMessage() throws IOException, InterruptedException {
		
		byte[] receiveData = new byte[32];

		
		while(true) {
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			agentSocket.receive(receivePacket);
			
        	String mesage = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
        	
        	String[] line = mesage.split("\\s+");
        
        	//System.out.println(mesage + " " + this.getAdress().adrString());
        	
        	
        	InetAddress IPAddress = receivePacket.getAddress();
        	int port = receivePacket.getPort();
        	
        	
        	Adress newAdr = new Adress(port, IPAddress);
        	if(!adresses.contains(newAdr))
        		adresses.add(newAdr);
        	Thread.sleep(100);
        	workMessage(line,newAdr);
        	//System.out.println(mesage + " " + newAdr.adrString());
		}
	}
	
	private void clkCommand(Adress adr) throws InterruptedException  {
		String message = "ANS " + String.valueOf(this.counter.getCurrentTime());
		
		
		sendThread = new Sender(message,adr,this);
		Thread toSend = new Thread(sendThread);
		toSend.start();
		toSend.join();
		
	}
	
	private void ansCommand(String time) {
		
		toCount += Long.valueOf(time);
		numberOfAnswers++;
		if(numberOfAnswers >= adresses.size()+1) {
			System.out.println(this.adr.adrString() + " Got " + numberOfAnswers + " answers");
			this.counter.setTime(toCount/(numberOfAnswers));
			toCount = this.counter.getCurrentTime();
			numberOfAnswers = 1;
		}
		
	}
	
	public void sendMessage(String message,Adress adr) throws IOException {
		
		//System.out.println("Message " + message +  " sended from " + this.getAdress().adrString() );
		byte[] sendData = new byte[32];
		
		String answer = message;
        sendData = answer.getBytes();
        DatagramPacket sendPacket =  new DatagramPacket(sendData, sendData.length, adr.ip, adr.port);
        agentSocket.send(sendPacket);
        
        //System.out.println(counter.getTime() + ": " + answer + " sended from " + this.adr.adrString() + " to " + adr.adrString());
	}
	
	public void actualizeTime() throws IOException, InterruptedException {
		
		//System.out.println("actualizeTime");
		for(Iterator<Adress> i = adresses.iterator(); i.hasNext();) {
			Adress toSend = i.next();
			
			
			sendThread = new Sender("CLK",toSend,this);
			Thread sender = new Thread(sendThread);
			sender.start();
			sender.join();

		}
	}

	private void workMessage(String[] line, Adress senderAdr) throws IOException, InterruptedException {
		
		if(line[0].equals("ANS")){
			ansCommand(line[1]);
    		/*toCount += Long.valueOf(line[1]);
    		numberOfAnswers++;
    		//System.out.println("Count " + line[1]);
    		if(numberOfAnswers >= adresses.size()) {
    			for(Iterator<Adress> i = adresses.iterator();i.hasNext();) {
    				this.counter.setTime(toCount/(numberOfAnswers + 1));
        			this.toCount = this.counter.getCurrentTime();
        			numberOfAnswers = 0;
    			}
    			System.out.println("Poschital");
    		}*/
    	}
    	
    	if(line[0].equals("CLK")){
    		//System.out.println("Got clk");
    		clkCommand(senderAdr);
    	}	
	}
	
	public void addAdress(List<Adress> net) {
		
		ArrayList<Adress> newAdrs = new ArrayList<Adress>();
		for(Iterator<Adress> i = net.iterator();i.hasNext();) {
			Adress toAdd = i.next();
			if(!adresses.contains(toAdd))
				adresses.add(toAdd);
			
		}
		adresses.remove(this.getAdress());
	}
	
	public Adress getAdress() {
		return this.adr;
	}
	
	public void showTime() {
		System.out.println(this.getAdress().adrString() +": "+ this.counter.getTime()+"ms");
	}
	public void showAdresses() {
		for(Iterator<Adress> i = adresses.iterator(); i.hasNext();) {
			Adress toSend = i.next();
			
			System.out.println("Adress " + toSend.ip + ":" + toSend.port);
		}
	}

}
