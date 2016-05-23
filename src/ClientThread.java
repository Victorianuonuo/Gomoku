import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ClientThread extends Thread{
          
	public static final int GAME_PORT = 13788;
    private InetAddress address;

    public ClientThread(String name, InetAddress address) {
        super(name);
        this.address = address;
    }
    
    public ClientThread() {
		// TODO Auto-generated constructor stub
	}
	
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BlockingQueue<Object> out = MessageBus.getMessageBus().getOrCreateChannel("OUT");
        BlockingQueue<Object> in = MessageBus.getMessageBus().getOrCreateChannel("IN");
        try (Socket socket = new Socket(address, GAME_PORT);) {
            in.put("SUCCESSFUL");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
            while (!this.isInterrupted()) {
                String data = (String) out.poll();
                if (data == null)
                    data = "TICK";
                writer.write(data);
                writer.newLine();
                writer.flush();

                String line = reader.readLine();
                if (line == null)
                    throw new IOException("end of stream");
                if (!line.equals("OK"))
                    in.put(line);
                if (!line.equals("OK") || !data.equals("TICK"))
                    System.err.println(data + " / " + line);
                Thread.sleep(100);
            }
        } catch (IOException e) {
            try {
                in.put(e);
            } catch (InterruptedException e1) {

            }
        } catch (InterruptedException ignored) {

        } 
	}
}
