import java.io.IOException;

public class Sender implements Runnable {
	
	private Agent agentSender;
	private Adress agentReceiver;
	private String message;
	

	
	public Sender(String message, Adress agentReceiver, Agent agentSender) {
		
		this.agentSender = agentSender;
		this.message = message;
		this.agentReceiver = agentReceiver;
		
	}
	
	@Override
	public void run() {
		try {
			agentSender.sendMessage(message, agentReceiver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
