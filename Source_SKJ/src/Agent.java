import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Agent {
	
	private final int portNumber;
	private final InetAddress host;
	private final String agentName;
	
	String outputSentence;
	Clock agentClock;
	ServerSocket AgentSocket;
	
	Map<String,Socket> connectionsToServer = new HashMap<>();
	List<Socket> connectedToAgent = new ArrayList<>();
	
	//Initial agent
	public Agent(InetAddress host, int port) throws Exception{
		this.portNumber = port;
		this.host = host;
		this.agentClock = new Clock( System.currentTimeMillis());
		this.agentName = host.toString() +":"+ port;
		try {
		this.AgentSocket = new ServerSocket(this.portNumber);}
		catch (IOException e) {
	            e.printStackTrace();
	        }
		//startListening();
		System.out.println("Created agent: " + this.agentName);
		
	}
	
	public Agent(Agent agent,InetAddress host, int port) throws Exception {
		this(host,port);
		this.createConnection(agent.agentName, agent.host, agent.portNumber);
	}
	
	public void createConnection(String agentName, InetAddress host, int port) {
		try {
			connectionsToServer.put(agentName ,new Socket(host, port));
			System.out.print(" connected to " + agentName);
		} catch (IOException e) {
		}
	}
	
	public void startListening() {
		Socket newSocket = null;
		boolean a = true;
		 while (a) {
	            try {
	                newSocket = this.AgentSocket.accept();
	                //a = false;
	            } catch (IOException e) {
	                System.out.println("I/O error: " + e);
	            }
	            // new thread for a client where all the logic is specified
	            new EchoThread(newSocket,this).start();
	        }
		// connectedToAgent.add(newSocket);
	}
	
	public void sendMessage(String serverAgentName, String testMsg) throws IOException {
		//Socket senderReceiver = connectionsToServer.get(serverAgentName);
		String answer = "nothing happened";
		Socket senderReceiver = new Socket("localhost", 9998);
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(senderReceiver.getInputStream()));  
		DataOutputStream outToServer;
		try {
		outToServer = new DataOutputStream(senderReceiver.getOutputStream());
		
		outToServer.writeBytes(testMsg);
		answer = inFromServer.readLine();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println(answer);
        senderReceiver.close();
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


}
