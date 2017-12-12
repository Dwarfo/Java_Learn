
public class Listener implements Runnable{
	
	private Agent agentListener;
	//private ServerSocket serverSocket;
	
	public Listener(Agent agent) {
		
		this.agentListener = agent;
		
	}

	@Override
	public void run() {
		try {
			agentListener.receiveMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
