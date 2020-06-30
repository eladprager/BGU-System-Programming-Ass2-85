package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsOverBroadcast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class  TimeService extends Publisher {

	private int duration;
	private int T;
	private long period;


	public TimeService(int duration) {
		super("TimeService");
			this.duration = duration;
	}




	@Override
	protected void initialize() {
			T = 1;
			period = TimeUnit.MILLISECONDS.toMillis(100);
	}

	@Override
	public void run() {
			initialize();
			while (T <= duration) {
				getSimplePublisher().sendBroadcast(new TickBroadcast("TimeService", T));
				System.out.println("Sender TimeService" + " publish a broadcast number " + T);
				T++;
				try {
					TimeUnit.MILLISECONDS.sleep(period);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			getSimplePublisher().sendBroadcast(new TimeIsOverBroadcast());
	}
}
