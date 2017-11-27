
public class Adress {
	public String port;
	public String host;

	@Override
	public boolean equals(Object other){
		boolean result = false;
	    if (other == null || other.getClass() != getClass()) {
	        result = false;
	    } else {
	        Adress employee = (Adress) other;
	        if (this.host.equals(employee.host) && this.port.equals(employee.port)) {
	            result = true;
	        }
	    }
	    return result;
	}

	public Adress(String host,String port) {
		this.port = port;
		this.host = host;
	}
	
}
