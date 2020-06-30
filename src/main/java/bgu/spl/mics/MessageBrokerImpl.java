package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
	private static class SingleHolder {
		private static MessageBroker s1=new MessageBrokerImpl();
	}
	private ConcurrentHashMap<Subscriber,List<Class<? extends Message>>> topicMap;
    private ConcurrentHashMap<Subscriber,ConcurrentLinkedQueue<Message>> messageMap;
	private ConcurrentHashMap<Class<? extends Message>,ConcurrentLinkedQueue<Subscriber>> eventMap;
    private ConcurrentHashMap<Event,Future> mapEvFu;

    /**
     * Retrieves the single instance of this class.
     */
	private MessageBrokerImpl(){
	  //  synchronized (this) {
            topicMap = new ConcurrentHashMap<>();
            messageMap = new ConcurrentHashMap<>();
            eventMap = new ConcurrentHashMap<>();
            mapEvFu = new ConcurrentHashMap<>();
      //  }
    }
    public static MessageBroker getInstance() {
        return SingleHolder.s1;
    }

    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
        synchronized (topicMap.get(m)) {
            topicMap.get(m).add(type);
        }
        eventMap.putIfAbsent(type, new ConcurrentLinkedQueue<>());
        synchronized (eventMap.get(type)){
                        eventMap.get(type).add(m);
                   }
    }

    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
	    synchronized (topicMap.get(m)) {
            topicMap.get(m).add(type);
        }
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
            mapEvFu.get(e).resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
//	    synchronized (topicMap) {
            for (ConcurrentHashMap.Entry<Subscriber, List<Class<? extends Message>>> entry : topicMap.entrySet()) {
                synchronized (messageMap.get(entry.getKey())) {
//                    if (entry.getValue().contains(b.getClass())) {
                    messageMap.get(entry.getKey()).add(b);
//                        notifyAll();
                    messageMap.get(entry.getKey()).notify();
                }
            }
//        }
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
            Future<T> f = new Future<T>();
            for (ConcurrentHashMap.Entry<Subscriber,List<Class<? extends Message>>> entry : topicMap.entrySet()) {
                synchronized (messageMap.get(entry.getKey())) {
                    if (entry.getValue().contains(e.getClass())) {
                        if(eventMap.get(e.getClass()).peek() == entry.getKey()) {
                            messageMap.get(entry.getKey()).add(e);
                            messageMap.get(entry.getKey()).notify();
                            Subscriber sub = eventMap.get(e.getClass()).poll();
                            eventMap.get(e.getClass()).add(sub);
                            mapEvFu.put(e, f);
                            return f;
                        }
                    }
                }
            }
            return null;
    }

    @Override
    public void register(Subscriber m) {
            messageMap.putIfAbsent(m, new ConcurrentLinkedQueue<>());
            topicMap.putIfAbsent(m, new ArrayList<>());
    }

    @Override
    public void unregister(Subscriber m) {
	    synchronized (messageMap.get(m)) {
	        while (!messageMap.get(m).isEmpty()){
	            if (messageMap.get(m).peek() instanceof Event){
	                Message tmp = messageMap.get(m).poll();
	                mapEvFu.get(tmp).resolve(null);
                }
            }
            messageMap.remove(m);
            topicMap.remove(m);
            ConcurrentLinkedQueue<Subscriber> tmp = new ConcurrentLinkedQueue<>();
            for (ConcurrentHashMap.Entry<Class<? extends Message>, ConcurrentLinkedQueue<Subscriber>> entry : eventMap.entrySet()) {
                synchronized (entry.getValue()){
                while (!entry.getValue().isEmpty()) {
                    if (entry.getValue().peek() != null && entry.getValue().peek() != m)
                        tmp.add(entry.getValue().poll());
                    else entry.getValue().poll();
                }
                entry.setValue(tmp);
            }
        }
        }
    }

    @Override
    public Message awaitMessage(Subscriber m) throws InterruptedException {
        synchronized (messageMap.get(m)) {
            while (messageMap.get(m).isEmpty()) {
                try {
                    messageMap.get(m).wait();
                } catch (InterruptedException e) {
                }
            }
            return (messageMap.get(m).poll());
        }
    }
}
