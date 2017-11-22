import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;
    private Agent serverAgent;

    public EchoThread(Socket clientSocket, Agent serverAgent) {
        this.socket = clientSocket;
        this.serverAgent = serverAgent;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        
        String line;
        boolean a = true;
        while (a) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                }// All the functional should be here
                else {
                    out.writeBytes(line + "1100 \n\r");
                    out.flush();
                    //a = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}