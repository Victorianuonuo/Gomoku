import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

public class ServerThread extends Thread{
	
	public static final int GAME_PORT = 13788;
	
    public ServerThread(ThreadGroup group, String name) {
		// TODO Auto-generated constructor stub
    	   super(group, name);
	}
    
    public ServerThread() {
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	BlockingQueue<Object> in = MessageSingleTon.getInstance().getOrCreateChannel("IN");
        BlockingQueue<Object> out = MessageSingleTon.getInstance().getOrCreateChannel("OUT");
        try (ServerSocket serverSocket = new ServerSocket(GAME_PORT);
             Socket socket = serverSocket.accept();){
        	 in.put("CONNECTED");
        	 while(!this.isInterrupted()){
        		 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
                 while (!this.isInterrupted()) {
                     String line = reader.readLine();

                     if (line == null) {
                         throw new IOException("end of stream");
                     }

                     if (!line.equals("WAIT")) {
                         in.put(line);
                     }
                     String data = (String) out.poll();
                     if (data == null)
                         data = "OK";
                     writer.write(data);
                     writer.newLine();
                     writer.flush();
                     if (!line.equals("WAIT") || !data.equals("OK"))
                         System.err.println(line + " / " + data);
                 }
        	 }
        }catch (IOException e) {
            System.err.println(e);
            try {
                in.put(e);
            } catch (InterruptedException ignored) {

            }
        } catch (InterruptedException ignored) {

        }
        
        
        
    }
    
}
