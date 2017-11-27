import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgentSendMessageToAll implements Runnable{

	private AgentSolo agentSender;
	private String message;
	
	List<Adress> senderNet;
	
	public AgentSendMessageToAll(String message, AgentSolo agentSender) {
		
		this.agentSender = agentSender;
		this.message = message;
		this.senderNet = agentSender.getAdressBook();
	}

	@Override
	public void run() {
		try {
			for(Iterator<Adress> i = senderNet.iterator();i.hasNext(); ) {
	    		Adress adress = i.next();
	    		agentSender.sendMessageThread(adress, message);
	    	}
			//agentSender.sendMessageThread(this.agentReceiver, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
