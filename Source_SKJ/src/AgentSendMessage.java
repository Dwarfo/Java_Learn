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
			Adress adress = agentReceiver.getAdress();
			agentSender.sendMessageThread(adress, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}