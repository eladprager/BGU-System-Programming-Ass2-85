package bgu.spl.mics;

import bgu.spl.mics.application.messages.MissionReceivedEvent;

/**
 * The SimplePublisher is a class that any publisher in the system
 * stores. The SimplePublisher class is responsible to send
 * messages to the singleton {@link MessageBroker} instance.
 * <p>
 *
 * Only private fields and methods may be added to this class.
 * <p>
 */
public final class SimplePublisher {
    private MessageBroker s1 = MessageBrokerImpl.getInstance();
    /**
     * Sends the event {@code e} using the MessageBroker and receive a {@link Future<T>}
     * object that may be resolved to hold a result. This method must be Non-Blocking since
     * there may be events which do not require any response and resolving.
     * <p>
     * @param <T>       The type of the expected result of the request
     *                  {@code e}
     * @param e         The event to send
     * @return  		{@link Future<T>} object that may be resolved later by a different
     *         			subscriber processing this event.
     * 	       			null in case no Subscriber has subscribed to {@code e.getClass()}.
     */
    public final <T> Future<T> sendEvent(Event<T> e) {
            if(e instanceof MissionReceivedEvent)
                System.out.print("");
            Future<T> t1 = s1.sendEvent(e);
            return t1;
    }

    /**
     * A Publisher calls this method in order to send the broadcast message {@code b} using the MessageBroker
     * to all the subscribers subscribed to it.
     * <p>
     * @param b The broadcast message to send
     */
    public final void sendBroadcast(Broadcast b) {
      //  synchronized (this) {
            s1.sendBroadcast(b);
      //  }
    }
}
