
public class Clockv2 implements Runnable {

	private long currentTime;
	
	public Clockv2() {
		this.currentTime = 0;
	}
	
	public long getTime() {
		return this.currentTime;
	}
	
	public void setTime(long time) {
		this.currentTime = time;
	}
	
	@Override
	public void run() {
		int i = 1000000;
		
		while (true) {
			i--;
			if(i== 0)
					this.currentTime++;
		}
		
	}
}
