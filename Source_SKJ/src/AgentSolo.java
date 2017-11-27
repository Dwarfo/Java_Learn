import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class AgentSolo {
	
	private final int portNumber;
	private final String host;
	public final String agentName;
	private final Adress agentAdr;
	private boolean isCounting = false;
	
	private Runnable sender;
	private AgentMessageListener receiver;
	
	Clock agentClock;
	
	ServerSocket AgentSocket;
	
	//private HashMap<String, String> addressBook;
	private long toAverage = 0;
	private int avgKoef = 0;
	private List<Adress> addressBook = new ArrayList<Adress>();
	
	//Initial agent
	public AgentSolo(String host, int port) throws Exception{
		this.portNumber = port;
		this.host = host;
		this.agentClock = new Clock(System.currentTimeMillis());
		this.agentName = host.toString() +":"+ port;
		this.AgentSocket = new ServerSocket(this.portNumber);
		this.agentAdr = new Adress(host, String.valueOf(port));
		
		this.receiver = new AgentMessageListener(this);
		//this.sender = new AgentSendMessage(this ,);
		new Thread(this.getListener()).start();
		//startListening();
		System.out.println("Created agent: " + this.agentName);
		
	}
	
	public AgentSolo(AgentSolo agent,String host, int port) throws Exception {
		this(host,port);
		this.addressBook.add(agent.getAdress());
		System.out.println(host + ":"+ port + " remembered adress: " + host + ":" + String.valueOf(port));
	}
	
	public void startListening() throws Exception {
		
		System.out.println(this.getHost() + ":" + String.valueOf(this.getPort()) + " Is waiting for message");
		boolean open = true;
		Socket newSocket = null;
		String answer = null;
		while(open) {
		try {
			//this.AgentSocket = new ServerSocket(this.portNumber);
				newSocket = AgentSocket.accept();

            	BufferedReader in = new BufferedReader(
            		new InputStreamReader(newSocket.getInputStream()));
            	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(newSocket.getOutputStream()));
            	String message = null;
            	message = in.readLine();
            	
            	String[] line = message.split("\\s+");
            	if(line[0].equals("close")) {
            		AgentSocket.close();
            		out.flush();
            		break;
            	}
            	
            	switch(line[0]){
        		case "CLK": 
        			answer = String.valueOf(this.agentClock.getTime());
        			break;
        		case "NET":
        			Adress newAdress = new Adress(line[1], line[2]);
        			netCommandHandler(out,in);
        			answer = "Adresses sended";
        			
        			if(!this.addressBook.contains(newAdress)) {
        				this.addressBook.add(newAdress);
        			}
        			break;
        		case "FRG":
        			frgCommandHandler(line);
        			answer = "Adresses removed";
        			break;
        		case "AVG":
        			avgCommandHandler();
        			break;
        		case "AVA":
        			this.agentClock.setTime(Long.valueOf(line[1]));
        			break;
        		default:
        			System.out.println("Unknown comand");
        		}
            	
            	//System.out.println(line);
            	out.write(answer);
            	out.newLine();
            	out.flush();
                
            	}
        	catch (IOException e) {
            	e.printStackTrace();
        	}
		}
		// connectedToAgent.add(newSocket);
	}
		
	private void frgCommandHandler(String[] adr) throws IOException {

		Adress adrToForget = new Adress(adr[1],adr[2]);
		addressBook.remove(adrToForget);
		
	}
	
	private void netCommandHandler(BufferedWriter out,BufferedReader in) throws IOException, InterruptedException {
		
		out.write("GTN");
		out.newLine();
    	out.flush();
    	Thread.sleep(10);
		
    	out.write(String.valueOf(this.addressBook.size()));
    	out.newLine();
    	out.flush();
    	Thread.sleep(10);
    	
    	for(Iterator<Adress> i = this.addressBook.iterator();i.hasNext(); ) {
    		Adress adress = i.next();
    		out.write(adress.host + " " + adress.port);
    		out.newLine();
        	out.flush();
    		Thread.sleep(10);
    	}
    	//out.write("OOC");
    	out.newLine();
    	out.flush();
	}
	
	private void gtnCommandHandler(BufferedWriter out, BufferedReader in) throws IOException, InterruptedException {
		
		out.write(this.host + " " + this.portNumber);
		out.newLine();
		out.flush();
		Thread.sleep(10);
		
		int listen = Integer.valueOf(in.readLine());
		System.out.println("listen = " + listen);
		while(listen > 0) {
			String adress = in.readLine();
			String[] adressPart = adress.split("\\s+");
			Adress newAdress = new Adress(adressPart[0], adressPart[1]);
			
			if(!(this.addressBook.contains(newAdress))) {
				this.addressBook.add(newAdress);
				this.addressBook.remove(this.agentAdr);
			}
			listen--;
		}
	}
	
	private void avgCommandHandler() throws IOException, InterruptedException {
		
    	this.isCounting = true;
    	
    	AgentSolo.sendMessageToAll("CLK", this);
    	
    	while(this.isCounting) {
    		System.out.println("counting, please standBy");
    	}
    	
    	String avaMsg = "AVA " + String.valueOf(this.toAverage/this.avgKoef);
    	
    	AgentSolo.sendMessageToAll(avaMsg, this);
		this.agentClock.setTime(this.toAverage/this.avgKoef);	
		}
		
		
		
	
	
	public static void sendMessage(String msg, AgentSolo agentReceiver, AgentSolo agentSender) {
		Adress adress = agentReceiver.getAdress();
		System.out.println(adress.host + adress.port);
		
		if(agentSender.addressBook.contains(adress)) {
			agentSender.sender = new AgentSendMessage(msg,agentReceiver, agentSender);
			Thread sendThread = new Thread(agentSender.sender);
			sendThread.start();
		}
		else
			System.out.println("Unknown adress");
	}
	
	public static void sendMessageToAll(String msg, AgentSolo agentSender) {
		
		agentSender.sender = new AgentSendMessageToAll(msg, agentSender);
		Thread sendThread = new Thread(agentSender.sender);
		sendThread.start();
		
	}
	
	public void sendMessageThread(Adress receiver, String testMsg) throws IOException, InterruptedException {
		//Socket senderReceiver = connectionsToServer.get(serverAgentName);
		String answer = "nothing happened";
		System.out.println("Send method in: " + receiver.host +":"+ receiver.port); 
		//Socket senderReceiver = new Socket(receiver.getHost(), receiver.getPort());
		Socket senderReceiver = new Socket(receiver.host, Integer.valueOf(receiver.port));
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(senderReceiver.getInputStream()));  
		BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(senderReceiver.getOutputStream()));
		
		System.out.println(testMsg);
		if(testMsg.equals("NET")|| testMsg.equals("FRG")) {
			testMsg += " " + this.getHost() + " " + this.getPort(); 
		}
		
		out.write(testMsg);
		out.newLine();
        out.flush();
        
     
		System.out.println("Mesage sended");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		answer = inFromServer.readLine();
		
		if(isCounting) {
			toAverage += -1*(Long.valueOf(answer) - System.currentTimeMillis()); 
			avgKoef++;
			System.out.println(this.addressBook.size());
			if(avgKoef == this.addressBook.size())
				isCounting = false;
		}
		
		
		if(answer.equals("GTN")) {
			gtnCommandHandler(out, inFromServer);
		}
		
        System.out.println(answer);
        senderReceiver.close();
	}
	
	

	
	public void showAllNetMembers() {
		for(Iterator<Adress> i = this.addressBook.iterator();i.hasNext(); ) {
    		Adress adress = i.next();
    		System.out.println(adress.host + " " + adress.port);
    	}
	}
	
	public AgentMessageListener getListener() {
		return receiver;
	}
	
	public int getPort() {
		return this.portNumber;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public boolean adrExists(String[] adr) {
		return this.addressBook.contains(adr);
	}
	
	public List<Adress> getAdressBook() {
		return this.addressBook;
	}
	
	public Adress getAdress() {
		return this.agentAdr;
	}
		
}
	
	
	
	
	
	
	
	
	/*send command
	 * agentOutputStream.println("message");
	 * 
	 * receive command
	 * String message = agentInputStream("answer");
	 * */
	

	/*
	public void writeMessage() throws IOException {
		this.outputSentence = agentInputStream.readLine();
	}
	
	public void sendMessage() throws IOException {
		this.agentOutputStream.writeBytes(outputSentence + '\n');
	}
	
	public void receiveMessage() throws IOException {
		String inputSentence = agentInputStream.readLine();
		
	}
	
	public void closeAgent() throws IOException {
		clientAgentSocket.close();
	}
	
	public void setNet(List<Agent> netMembers){
		this.netMembers = netMembers;
	}
	//Method for listening to mesages and answering accordinglyWhat does it return?
	public receiveMessage(Agent sender, String message) {
		
	}
	*/
	//Method for receiving information about all other members




