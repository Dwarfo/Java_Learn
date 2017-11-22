import java.io.IOException;

public class AgentSendMessage implements Runnable{

	private AgentSolo agentSender;
	private String message;
	
	public AgentSendMessage(AgentSolo agent, String message) {
		
		this.agentSender = agent;
		this.message = message;
		
	}

	@Override
	public void run() {
		try {
			agentSender.sendMessageThread(agentSender.host, message, 60010);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
