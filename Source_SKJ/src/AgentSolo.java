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
	
	private AgentSendMessage sender;
	private AgentMessageListener receiver;
	
	Clock agentClock;
	
	ServerSocket AgentSocket;
	
	//private HashMap<String, String> addressBook;
	private List<String[]> addressBook = new ArrayList<String[]>();
	
	//Initial agent
	public AgentSolo(String host, int port) throws Exception{
		this.portNumber = port;
		this.host = host;
		this.agentClock = new Clock(System.currentTimeMillis());
		this.agentName = host.toString() +":"+ port;
		this.AgentSocket = new ServerSocket(this.portNumber);
		
		this.receiver = new AgentMessageListener(this);
		//this.sender = new AgentSendMessage(this ,);
		new Thread(this.getListener()).start();
		//startListening();
		System.out.println("Created agent: " + this.agentName);
		
	}
	
	public AgentSolo(AgentSolo agent,String host, int port) throws Exception {
		this(host,port);
		String[] adress = {agent.getHost(),String.valueOf(agent.getPort())};
		this.addressBook.add(adress);
		System.out.println(host + ":"+ port + " remembered adress: " + adress[0] + ":" + adress[1]);
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
        			String[] newAdress = new String[]{line[1], line[2]};
        			netCommandHandler(out,in);
        			answer = "Adresses sended";
        			
        			if(!this.addressBook.contains(newAdress)) {
        				this.addressBook.add(newAdress);
        			}
        			break;
        		/*case "GTN":
        			gtnCommandHandler(in);
        			answer = "Adresses received";
        			break;*/
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
		
	private void netCommandHandler(BufferedWriter out,BufferedReader in) throws IOException, InterruptedException {
		
		out.write("GTN");
		out.newLine();
    	out.flush();
    	Thread.sleep(10);
		
    	out.write(String.valueOf(this.addressBook.size()));
    	out.newLine();
    	out.flush();
    	Thread.sleep(10);
    	
    	for(Iterator<String[]> i = this.addressBook.iterator();i.hasNext(); ) {
    		String[] adress = i.next();
    		out.write(adress[0] + " " + adress[1]);
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
			String[] newAdress = new String[]{adressPart[0], adressPart[1]};
			
			if(!this.addressBook.contains(newAdress)) {
				this.addressBook.add(newAdress);
			}
			listen--;
		}
	}
	
	public static void sendMessage(String msg, AgentSolo agentReceiver, AgentSolo agentSender) {
		agentSender.sender = new AgentSendMessage(msg,agentReceiver, agentSender);
		Thread sendThread = new Thread(agentSender.sender);
		sendThread.start();
		
	}
	
	public void sendMessageThread(AgentSolo receiver, String testMsg) throws IOException, InterruptedException {
		//Socket senderReceiver = connectionsToServer.get(serverAgentName);
		String answer = "nothing happened";
		System.out.println("Send method in: " + receiver.getHost() +":"+ receiver.getPort()); 
		//Socket senderReceiver = new Socket(receiver.getHost(), receiver.getPort());
		Socket senderReceiver = new Socket(receiver.getHost(), receiver.getPort());
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(senderReceiver.getInputStream()));  
		BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(senderReceiver.getOutputStream()));
		
		System.out.println(testMsg);
		if(testMsg.equals("NET")) {
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
		
		if(answer.equals("GTN")) {
			gtnCommandHandler(out, inFromServer);
		}
		
        System.out.println(answer);
        senderReceiver.close();
	}

	
	public void showAllNetMembers() {
		for(Iterator<String[]> i = this.addressBook.iterator();i.hasNext(); ) {
    		String[] adress = i.next();
    		System.out.println(adress[0] + " " + adress[1]);
    	}
	}

	public AgentSendMessage getSender() {
		return sender;
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




