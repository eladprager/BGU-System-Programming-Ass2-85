package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Message;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.MTerminated;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsOverBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {

	private int mbt;
	private Inventory Qinv;

	public Q() {
		super("Q");
			Qinv = Inventory.getInstance();
			mbt = 1;
	}


	@Override
	protected void initialize() {
			System.out.println("Listener " + getName() + " started");
			subscribeBroadcast(TickBroadcast.class, (callback) -> {
				mbt++;
			});
			subscribeBroadcast(TimeIsOverBroadcast.class, (callback) -> {
					this.terminate();
					System.out.println("Listener " + getName() + " terminating.");
			});
			subscribeEvent(GadgetAvailableEvent.class, (callback) -> {
				System.out.println("Event Handler " + getName() + " got a new event from " + "M" + "! (mbt: " + mbt + ")");
				boolean available = Qinv.getItem(callback.getName());
				int time;
				if (available)
					complete(callback, mbt);
				else
					complete(callback, null);
			});
	}
}


