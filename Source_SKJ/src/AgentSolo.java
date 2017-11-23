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
	
	String outputSentence;
	ServerSocket AgentSocket;
	
	Map<String,Socket> connectionsToServer = new HashMap<>();
	List<Socket> connectedToAgent = new ArrayList<>();
	
	//Initial agent
	public AgentSolo(String host, int port) throws Exception{
		this.portNumber = port;
		this.host = host;
		this.agentClock = new Clock( System.currentTimeMillis());
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
		this.createConnection(agent);
	}
	
	public void createConnection(AgentSolo agent) {
		
	}
	
	public void startListening() throws Exception {
		
		System.out.println("Waiting for message");
		boolean open = true;
		String message;
		Socket newSocket = null;
		while(open) {
		try {
			//this.AgentSocket = new ServerSocket(this.portNumber);
				newSocket = AgentSocket.accept();

            	BufferedReader in = new BufferedReader(
            		new InputStreamReader(newSocket.getInputStream()));
            	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(newSocket.getOutputStream()));
            	String line = null;
            	line = in.readLine();
            	if(line.equals("close")) {
            		AgentSocket.close();
            		break;
            	}
            	
            	System.out.println(line);
            	out.write(line + " Message answered!");
            	out.newLine();
            	out.flush();
                
            	}
        	catch (IOException e) {
            	e.printStackTrace();
        	}
		}
		// connectedToAgent.add(newSocket);
	}
		
	public void receiveMessage() {
		Thread sendThread = new Thread(this.receiver);
		sendThread.start();
	}
	
	
	public static void sendMessage(String msg, AgentSolo agentReceiver, AgentSolo agentSender) {
		agentSender.sender = new AgentSendMessage(msg,agentReceiver, agentSender);
		Thread sendThread = new Thread(agentSender.sender);
		sendThread.start();
		
	}
	
	public void sendMessageThread(AgentSolo receiver, String testMsg) throws IOException {
		//Socket senderReceiver = connectionsToServer.get(serverAgentName);
		String answer = "nothing happened";
		System.out.println("Send method in: " + receiver.getHost() +":"+ receiver.getPort()); 
		//Socket senderReceiver = new Socket(receiver.getHost(), receiver.getPort());
		Socket senderReceiver = new Socket(receiver.getHost(), receiver.getPort());
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(senderReceiver.getInputStream()));  
		BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(senderReceiver.getOutputStream()));
		
		System.out.println(testMsg);
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
		
        System.out.println(answer);
        senderReceiver.close();
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




