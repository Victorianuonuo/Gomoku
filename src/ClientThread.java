import java.net.InetAddress;
import java.util.logging.Logger;

public class ClientThread extends Thread{
          
	public static final int GAME_PORT = 13788;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private InetAddress target;

    public ClientThread(String name, InetAddress target) {
        super(name);
        this.target = target;
    }
    
    public ClientThread() {
		// TODO Auto-generated constructor stub
	}
	
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
