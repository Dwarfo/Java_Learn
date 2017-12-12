import java.io.IOException;

public class Counter implements Runnable {

		private long currentTime;
		private Agent countingAgent;
		
		public Counter(long startTime, Agent countingAgent) {
			this.currentTime = startTime;
			this.countingAgent = countingAgent;
		}
		
		public void setTime(long time) {
			this.currentTime = time;
		}
		public long getTime() {
			return System.currentTimeMillis() - this.currentTime;
		}
		
		public long getCurrentTime() {
			return this.currentTime;
		}

		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					try {
						countingAgent.actualizeTime();
						countingAgent.showTime();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	

