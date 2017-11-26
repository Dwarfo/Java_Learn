import java.io.IOException;

public class AgentSendMessage implements Runnable{

	private AgentSolo agentSender;
	private AgentSolo agentReceiver;
	private String message;
	
	public AgentSendMessage(String message, AgentSolo agentReceiver, AgentSolo agentSender) {
		
		this.agentSender = agentSender;
		this.message = message;
		this.agentReceiver = agentReceiver;
		
	}

	@Override
	public void run() {
		try {
			agentSender.sendMessageThread(this.agentReceiver, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
