import java.io.IOException;

public class AgentSendMessage implements Runnable{

	private AgentSolo agentSender;
	private Adress agentReceiver;
	private String message;
	

	
	public AgentSendMessage(String message, Adress agentReceiver, AgentSolo agentSender) {
		
		this.agentSender = agentSender;
		this.message = message;
		this.agentReceiver = agentReceiver;
		
	}

	@Override
	public void run() {
		//Adress adress = agentReceiver.getAdress();
		try {
			agentSender.sendMessageThread(agentReceiver, message);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	
}