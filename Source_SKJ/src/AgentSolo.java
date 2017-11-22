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
	public final String host;
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
		
		//startListening();
		System.out.println("Created agent: " + this.agentName);
		
	}
	
	public AgentSolo(AgentSolo agent,String host, int port) throws Exception {
		this(host,port);
		this.createConnection(agent.agentName, agent.host, agent.portNumber);
	}
	
	public void createConnection(String agentName, String host, int port) {
		try {
			connectionsToServer.put(agentName ,new Socket(host, port));
			System.out.print(" connected to " + agentName);
		} catch (IOException e) {
		}
	}
	
	public void startListening() throws Exception {
		
		String message;
		Socket newSocket = null;
		try {
			//this.AgentSocket = new ServerSocket(this.portNumber);

            newSocket = AgentSocket.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(newSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(newSocket.getOutputStream()));
            String line = null;
            line = in.readLine();
            System.out.println(line);
            out.write(line + "1100");
            out.newLine();
            out.flush();
                
            }
        catch (IOException e) {
            e.printStackTrace();
        }
		// connectedToAgent.add(newSocket);
	}
		
	
	public void sendMessage(String msg) {
		this.sender = new AgentSendMessage(this,msg);
	}
	
	public void sendMessageThread(String serverAgentName, String testMsg, int port) throws IOException {
		//Socket senderReceiver = connectionsToServer.get(serverAgentName);
		String answer = "nothing happened";
		System.out.println("Sen method in: " + serverAgentName +":"+ port); 
		Socket senderReceiver = new Socket(serverAgentName, port);
		
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




