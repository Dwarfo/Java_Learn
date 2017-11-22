
public class Clock 
{
	private long currentTime;
	public Clock(long startTime) {
		this.currentTime = startTime;
	}
	
	public void setTime(long time) {
		this.currentTime = time;
	}
	public long getTime() {
		return System.currentTimeMillis() - this.currentTime;
	}
}
