import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBus {
	 private final static MessageBus messageBus = new MessageBus();

	    private final Map<String, BlockingQueue<Object>> channels = new HashMap<>();


	    public static MessageBus getMessageBus() {
	        return messageBus;
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
	        return getOrCreateChannel(channel).take();
	    }
}
