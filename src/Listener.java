import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Listener extends Thread{
	
	final static int DISCOVERY_PORT = 13787;
	
	
    public Listener() {
		// TODO Auto-generated constructor stub
	}
    
    public Listener(ThreadGroup threadGroup,String name){
    	super(threadGroup,name);
    }
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(DISCOVERY_PORT, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);
            socket.setSoTimeout(1000);
            DatagramPacket packet = new DatagramPacket(new byte[8192], 8192);
            while (true) {
                try {
                    socket.receive(packet);
                    if (this.isInterrupted())
                        return;
                    String data = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    if (data.startsWith("Start the game by ")) {
                        String[] parts = data.split(" ",18);
                        if (parts.length > 1)
                            MessageBus.getInstance().getOrCreateChannel("DISCOVERY").put(new Rivals());
                    }
                } catch (SocketTimeoutException ignored) {

                } catch (InterruptedException e) {
                    return;
                }
                if (this.isInterrupted())
                    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
