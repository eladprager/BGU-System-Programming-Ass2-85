package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Message;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.MTerminated;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsOverBroadcast;
import bgu.spl.mics.application.passiveObjects.Squad;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {

	private Integer num;
	private int mbt;
	public Moneypenny(int num) {
		super("MP"+num);
			this.num = new Integer(num);
	}

	@Override
	protected void initialize() {
			System.out.println("Listener " + getName() + " started");
			mbt=1;
			Squad MPsquad = Squad.getInstance();
			subscribeBroadcast(TickBroadcast.class,(callback)->{
				mbt++;
			});

			subscribeBroadcast(TimeIsOverBroadcast.class, (callback) -> {
					this.terminate();
					System.out.println("Listener " + getName() + " terminating.");
			});


			subscribeEvent(AgentsAvailableEvent.class, (callback) -> {
				System.out.println("Listener " + getName() + " got a new message from " + "M" + "! (mbt: " + mbt + ")");
				boolean available = MPsquad.getAgents(callback.getSerials());
				List<String> names = new LinkedList<>();
				names.add(this.getName());
				if (available) {
					for (int i=0;i<MPsquad.getAgentsNames(callback.getSerials()).size();i++){
					names.add(MPsquad.getAgentsNames(callback.getSerials()).get(i));
					}
					complete(callback, names);
					int finish = callback.getFinish();
					MPsquad.sendAgents(callback.getSerials(), finish);
				} else {
					complete(callback, null);
				}
			});
	}
}
