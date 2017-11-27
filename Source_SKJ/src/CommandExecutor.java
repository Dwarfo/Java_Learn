import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {
	
	//All possible commands
	private final String addCommand = "addAgent";
	private final String removeCommand = "removeAgent";
	private final String sendMessageToFrom = "send";
	private final String sendMessageFromToAll = "sendAll";
	private final String synchronize = "sync";
	private final String showAgentTime = "showTime";
	private final String showallTime = "showAll";
	private final String showAllNet = "showNetFor";
	private final String stahp = "exit";
	private final String allPosibleCommands = addCommand +" "+ addCommand +" "+ sendMessageToFrom +" "+ synchronize +
			" "+ showAgentTime +" "+ showallTime +" "+ stahp;
	
	boolean reading = true;
	private ArrayList<AgentSolo> Net;
	private HashMap<String, AgentSolo> addressBook;
	
	private BufferedReader commandReader;
	public Creator agentCreator = new Creator();
	
	
	public CommandExecutor() throws Exception {
		commandReader = new BufferedReader(new InputStreamReader(System.in));
		Net = agentCreator.makeNet();
		addressBook = new HashMap<String, AgentSolo>();
		AgentSolo agent = new AgentSolo("localhost", 2000);
		Net.add(agent);
		addressBook.put(String.valueOf(agent.getPort()), agent);
		System.out.println("All posible commands " + allPosibleCommands);
		System.out.println("Type command");
		
		this.readCommands();
		
	}
	public void readCommands() throws IOException {
		while(reading) {
			String command = commandReader.readLine();
			
			executeCommand(command);
		}
		
	}
	
	public void executeCommand(String command) {
	
		String[] commandPart = command.split("\\s+");
		
		switch(commandPart[0]) {
		case addCommand: 
			AgentSolo agent = agentCreator.addAgent(commandPart[1],Net.get(Net.size() -1));
			Net.add(agent);
			addressBook.put(String.valueOf(agent.getPort()) , agent);
			break;
		case sendMessageToFrom:
			String receiver = commandPart[2];
			String sender = commandPart[3];
			
			AgentSolo.sendMessage(commandPart[1],addressBook.get(receiver), addressBook.get(sender));
			break;
		case sendMessageFromToAll:
			String senderToAll = commandPart[2];
			
			AgentSolo.sendMessageToAll(commandPart[1], addressBook.get(senderToAll));
			break;
		case showAllNet:
			String showAgent = commandPart[1];
			AgentSolo toShow = addressBook.get(showAgent);
			toShow.showAllNetMembers();
			break;
		case showallTime:
			for(Iterator<AgentSolo> i = this.Net.iterator();i.hasNext(); ) {
	    		AgentSolo timeAgent = i.next();
	    		System.out.println(timeAgent.getHost() + ":" + timeAgent.getPort() + " time= " + timeAgent.agentClock.getTime() + "ms");
	    	}
			break;
		case removeCommand:
			AgentSolo agentToRemove = addressBook.get(commandPart[1]);
			Net.remove(agentToRemove);
		default:
			System.out.println("Unknown comand");
		}
		
	}
}
