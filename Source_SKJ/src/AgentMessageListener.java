import java.net.ServerSocket;

public class AgentMessageListener implements Runnable {
	
	private AgentSolo agentListener;
	//private ServerSocket serverSocket;
	
	public AgentMessageListener(AgentSolo agent) {
		
		this.agentListener = agent;
		
	}

	@Override
	public void run() {
		try {
			agentListener.startListening();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
