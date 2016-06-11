import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSingleTon {
	 private final static MessageSingleTon instance = new MessageSingleTon();

	    private final Map<String, BlockingQueue<Object>> channels = new HashMap<>();


	    public static MessageSingleTon getInstance() {
	        return instance;
	    }

	    public BlockingQueue<Object> getOrCreateChannel(String channel) {
	        synchronized (channels) {
	            BlockingQueue<Object> queue = channels.get(channel);
	            if (queue == null) {
	                queue = new LinkedBlockingQueue<>();
	                channels.put(channel, queue);
	            }
	            return queue;
	        }
	    }

	    public Object waitForChannel(String channel) throws InterruptedException {
	    	Object temp=getOrCreateChannel(channel).take();
	    	System.err.println(channel);
	    	try{
	    		System.err.println(temp.toString());
	    	}catch(Exception ignored){
	    		System.err.println(ignored);
	    	}
	        return temp;
	    }
}
