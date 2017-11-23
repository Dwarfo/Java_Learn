import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Main {

	public static class MyThread extends Thread {
		@Override
		public void run() {
			AgentSolo a;
			try {
				a = new AgentSolo("localhost", 60010);
				a.startListening();
				//a_listener.run();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}/*
			ServerSocket ss;
            try {
                ss = new ServerSocket(60010);

                Socket s = ss.accept();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(s.getInputStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
			
	}
	}*/
	
	public static class MyThread2 extends Thread {
		@Override
		public void run() {
			AgentSolo b;
			try {
				b = new AgentSolo("localhost", 60000);
				//b.sendMessage("It's time to work");
				//AgentSendMessage b_sender = b.getSender();
				//b.sendMessageThread("localhost","Its ALive!",60010);
				//b_sender.run();
				AgentSolo.sendMessage("Message", new AgentSolo("localhost", 60110), b);
				//AgentSolo.sendMessage("close", new AgentSolo("localhost", 60111), b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}/*
			try {
                Socket s = new Socket("localhost", 60010);
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(s.getOutputStream()));

                while (true) {
                    out.write("Hello World!");
                    out.newLine();
                    out.flush();

                    Thread.sleep(200);
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}*/
	
	
	public static void main(String[] args) throws UnknownHostException, Exception {
		// TODO Auto-generated method stub
		/*AgentSolo a = new AgentSolo("localhost", 60010);
		AgentSolo a2 = new AgentSolo("localhost", 60012);
		AgentSolo a3 = new AgentSolo("localhost", 60013);
		AgentSolo b = new AgentSolo(a,"localhost", 60000);
		
		
		b.sendMessage("Message", a, b);
		AgentSolo.sendMessage("Message to 13", a3, b);
		AgentSolo.sendMessage("Message to 13 number 2", a3, b);
		AgentSolo.sendMessage("Message to b number 3", b, a3);*/
		
		CommandExecutor ce = new CommandExecutor();
		
		//new Thread(a.getListener()).start();
		//new Thread(b.getSender()).start();
		
		/*MyThread thread1 = new MyThread();
		thread1.start();
		MyThread2 thread2 = new MyThread2();
		thread2.start();*/
		
		
		
		//a.sendMessage("localhost:9998", "Please work!");
		
		//b.sendMessage("localhost:9998", "Please work!");
		//a.startListening();
		//System.out.println("");
		//System.out.println(a.agentName );
		
		/*net.add(new Agent(net));
		Agent a = net.get(0);
		a.showAllMembers();
		System.out.println("//////");
		net.add(new Agent(net));
		net.add(new Agent(net));
		net.add(new Agent(net));
		a.showAllMembers();
		/*
		long a = System.currentTimeMillis();
		long i = 0;
		while (i<1000000)i++;
		long b = System.currentTimeMillis();
		System.out.println(b - a);*/
	}
}



