import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.util.List;

public class AgentSolo {
	
	private final int portNumber;
	private final String host;
	public final String agentName;
	private final Adress agentAdr;
	private boolean isCounting = false;
	private boolean receivingAdresses = false;
	private boolean ATN = false;
	
	private Runnable sender;
	private AgentMessageListener receiver;
	
	Clock agentClock;
	
	ServerSocket AgentSocket;
	
	//private HashMap<String, String> addressBook;
	private long toAverage;
	private int avgKoef = 1;
	private List<Adress> addressBook = new ArrayList<Adress>();
	
	//Initial agent
	public AgentSolo(String host, int port) throws Exception{
		this.portNumber = port;
		this.host = host;
		this.agentClock = new Clock(System.currentTimeMillis());
		//this.toAverage = this.agentClock.getCurrentTime();
		this.agentName = host.toString() +":"+ port;
		this.AgentSocket = new ServerSocket(this.portNumber);
		this.agentAdr = new Adress(host, String.valueOf(port));
		
		this.receiver = new AgentMessageListener(this);
		//this.sender = new AgentSendMessage(this ,);
		new Thread(this.getListener()).start();
		//new Thread(this.agentClock).start();
		//startListening();
		System.out.println("Created agent: " + this.agentName);
		
	}
	
	public AgentSolo(AgentSolo agent,String host, int port) throws Exception {
		this(host,port);
		this.addressBook.add(agent.getAdress());
		System.out.println(host + ":"+ port + " remembered adress: " + host + ":" + String.valueOf(port));
		
		AgentSolo.sendMessage("NET", agent.getAdress(), this);
		Thread.sleep(100);//this is chosen impirically, so that no additional boolean is needed
		getInitMsg("NET");
		Thread.sleep(100);
		
		Thread.sleep(500);
		//setTime();
		//AgentSolo.sendMessage("SYN", this.getAdress(), agent);
		Thread.sleep(500);
		initialSyn();
		Thread.sleep(500);
		this.avgKoef = 0;
		this.toAverage = 0;
		//synCommandHandler();
		getInitMsg("AVA");
		Thread.sleep(500);
		synCommandHandler();
		//initialSyn();
		setTime();
		
		
	}
	
	private void initialSyn() throws InterruptedException, IOException {
		
		for(Iterator<Adress> i = addressBook.iterator();i.hasNext();) {
			this.ATN = false;
			Adress adress = i.next();
    		AgentSolo.sendMessage("SYN", adress, this);
    		Thread.sleep(20);
    		while(!this.ATN) {
        		System.out.print("");
        	}
    		
		}
	}
	
	private void getInitMsg(String msg) throws InterruptedException {
		int a = 0;
		for(Iterator<Adress> i = addressBook.iterator();i.hasNext();) {
			Adress adress = i.next();
    		AgentSolo.sendMessage(msg, adress, this);
    		Thread.sleep(20);
    		while(this.receivingAdresses) {
        		System.out.print("");
        	}
    		a++;
		}
		//Thread.sleep(a*20);
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
        			answer = String.valueOf(this.agentClock.getCurrentTime());
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
        		case "SYN":
        			toAverage = this.agentClock.getCurrentTime();
        			synCommandHandler();
        			
        			answer = "SYF";
        			break;
        		case "AVA":
        			//this.agentClock.setTime(Long.valueOf(line[1]));
        			setTime();
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
		
		this.receivingAdresses = true;
		
		out.write(this.host + " " + this.portNumber);
		out.newLine();
		out.flush();
		Thread.sleep(10);
		
		List<Adress> newAdresses = new ArrayList<Adress>();
		
		int listen = Integer.valueOf(in.readLine());
		System.out.println("listen = " + listen);
		while(listen > 0) {
			String adress = in.readLine();
			String[] adressPart = adress.split("\\s+");
			Adress newAdress = new Adress(adressPart[0], adressPart[1]);
			
			if(!(this.addressBook.contains(newAdress))) {
				newAdresses.add(newAdress);
			}
			listen--;
		}
		newAdresses.remove(this.agentAdr);
		this.addressBook.addAll(newAdresses);
		this.receivingAdresses = false;
	}
	
	private void synCommandHandler() throws IOException, InterruptedException {
		
    	this.isCounting = true;
    	//toAverage = this.agentClock.getCurrentTime();
    	AgentSolo.sendMessageToAll("CLK", this);
    	
    	while(this.isCounting) {
    		System.out.print("");
    	}
    	
    	/*String avaMsg = "AVA " + String.valueOf(this.toAverage/this.avgKoef);
    	
    	AgentSolo.sendMessageToAll(avaMsg, this);*/
    	
    	System.out.println("Summa "+ toAverage + " Koef " + avgKoef);
		//this.agentClock.setTime( (this.toAverage /this.avgKoef) + System.currentTimeMillis() );
		//this.toAverage = this.agentClock.getTime();
		//this.avgKoef = 0;
    	//Thread.sleep(50);
    	//AgentSolo.sendMessageToAll("AVA", this);
    	this.isCounting = false;
		}
		
	private void setTime() {
		this.agentClock.setTime( (this.toAverage /this.avgKoef) );
		//this.toAverage = this.agentClock.getTime();
		this.avgKoef = 1;
	}	
		
	
	
	public static void sendMessage(String msg, Adress agentReceiver, AgentSolo agentSender) {
		//Adress adress = agentReceiver.getAdress();
		System.out.println(agentReceiver.host + agentReceiver.port);
		
		if(agentSender.addressBook.contains(agentReceiver)) {
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
			System.out.println("Answer: "+ Long.valueOf(answer));
			System.out.println("toAverage1 " + toAverage);
			toAverage += Long.valueOf(answer); 
			System.out.println("toAverage2 " + toAverage);
			avgKoef++;
			System.out.println("Booksize " + this.addressBook.size());
			if(avgKoef >= this.addressBook.size())
				isCounting = false;
		}
		
		
		if(answer.equals("GTN")) {
			gtnCommandHandler(out, inFromServer);
		}
		
		if(answer.equals("SYF")) {
			this.ATN = true;
			
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




