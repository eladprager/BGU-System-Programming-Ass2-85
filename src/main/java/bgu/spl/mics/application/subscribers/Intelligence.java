package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MTerminated;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsOverBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {

	private int mbt;
	private List<MissionInfo> missions;
	private int num=0;
	private Map<Integer,MissionInfo> MissionsByTimeIssued;

	public Intelligence() {
		super("I");
			MissionsByTimeIssued = new HashMap<>();
	}

	public void setMissions(List<MissionInfo> missions){
			this.missions = missions;
	}

	public List<MissionInfo> getMissions(){
			return missions;
	}

	@Override
	protected void initialize() {
			System.out.println("Listener " + getName() + " started");
			for (int i = 0; i < getMissions().size(); i++) {
				MissionsByTimeIssued.put(getMissions().get(i).getTimeIssued(), getMissions().get(i));
			}
			mbt = 1;
			subscribeBroadcast(TickBroadcast.class, (callback) -> {
				mbt++;
				if (MissionsByTimeIssued.containsKey(mbt)) {
					System.out.println("Sender " + getName() + " publish a MissionReceivedEvent event");
					getSimplePublisher().sendEvent(new MissionReceivedEvent(MissionsByTimeIssued.get(mbt))); //ULAY LIMHOK
				}
			});
			subscribeBroadcast(TimeIsOverBroadcast.class, (callback) -> {
					this.terminate();
					System.out.println("Listener " + getName() + " terminating.");
			});
	}
}


