import java.io.IOException;
import java.util.Random;

public class Sender implements Runnable {
	
	private Agent agentSender;
	private Adress agentReceiver;
	private String message;
	Random rand = new Random();

	
	public Sender(String message, Adress agentReceiver, Agent agentSender) {
		
		this.agentSender = agentSender;
		this.message = message;
		this.agentReceiver = agentReceiver;
		
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(rand.nextInt(80) + 30);
			agentSender.sendMessage(message, agentReceiver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}

}
