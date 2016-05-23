import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Broadcast extends Thread{
	  
	final static int DISCOVERY_PORT = 13787;
    final static int INTERVAL = 1000;
    private String message;
	
    public Broadcast(String message) {
		// TODO Auto-generated constructor stub
    	super("broadcast");
    	this.message=message;
	}
    
    public Broadcast(ThreadGroup threadGroup,String name,String message) {
		// TODO Auto-generated constructor stub
    	super(threadGroup,name);
    	this.message=message;
	}
    
    private void sendBroadcast(DatagramSocket socket, byte[] data, InetAddress address)
    {
        System.err.println("Sending broadcast to " + address);
        try {
            socket.send(new DatagramPacket(data, data.length, address, DISCOVERY_PORT));
        } catch (Exception ex) {
        	System.err.println(ex);
        	System.err.println("Fail to send broadcast to " + address);
        }
    }
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	try (DatagramSocket socket = new DatagramSocket();) {
            socket.setBroadcast(true);
            byte[] data = message.getBytes("UTF-8");
            while (!this.isInterrupted()) {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                try {
                    this.sendBroadcast(socket, data, InetAddress.getByName("255.255.255.255"));
                } catch (UnknownHostException ignored) {

                }
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isUp()) {
                        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                            InetAddress broadcast = interfaceAddress.getBroadcast();
                            if (broadcast != null) {
                                if (this.isInterrupted())
                                    return;
                                this.sendBroadcast(socket, data, broadcast);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        } catch (SocketException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
