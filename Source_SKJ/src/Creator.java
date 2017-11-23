import java.util.ArrayList;
import java.util.List;

public class Creator {
	
	public AgentSolo addAgent(String port, AgentSolo agent) {
		AgentSolo newAgent = null;
		try {
			newAgent =  new AgentSolo(agent, "localhost", Integer.parseInt(port));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newAgent;
	}
	
	public ArrayList<AgentSolo> makeNet() throws Exception {
		
		ArrayList<AgentSolo> newNet = new ArrayList<AgentSolo>();
		
		
		return newNet;
	}
}
