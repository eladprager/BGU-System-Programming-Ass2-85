package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Message;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;
import bgu.spl.mics.example.messages.ExampleEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	private Diary diary;
	private int numOfM;
	private int mbt;

	public M(int num) {
		super("M" + num);
			this.numOfM = num;
			diary = Diary.getInstance();
	}

	@Override
	protected void initialize() {
			System.out.println("Listener " + getName() + " started");
			this.mbt = 1;
			subscribeBroadcast(TickBroadcast.class, (callback) -> {
				mbt++;
			});
			subscribeBroadcast(TimeIsOverBroadcast.class, (callback) -> {
				System.out.println("Listener " + getName() + " terminating.");
				this.terminate();
			});
			subscribeEvent(MissionReceivedEvent.class, (callback) -> {
				System.out.println("Listener " + getName() + " got a new message from " + "Intelligence" + "! (mbt: " + mbt + ")");
				diary.incrementTotal();
				MissionInfo missionInfo = callback.getMissionInfo();
				AgentsAvailableEvent available = new AgentsAvailableEvent(missionInfo.getDuration(), missionInfo.getSerialAgentsNumbers());
				System.out.println("Sender " + getName() + " publish an AgentsAvailableEvent event");
				Future<List<String>> futureobj = getSimplePublisher().sendEvent(available);
				if (futureobj != null && futureobj.get() != null) {
					GadgetAvailableEvent availableGadget = new GadgetAvailableEvent(callback.getMissionInfo().getGadget());
					System.out.println("Sender " + getName() + " publish a GadgetAvailableEvent event");
					Future<Integer> futureint = getSimplePublisher().sendEvent(availableGadget);
					if (futureint != null && futureint.get() != null) {
						if (futureint.get() < missionInfo.getTimeExpired()) {
							Report report = new Report(missionInfo.getMissionName());
							int result = Integer.parseInt(String.valueOf(futureobj.get().get(0)).substring(2));
							report.setMoneypenny(result);
							futureobj.get().remove(0);
							report.setAgentsNames(futureobj.get());
							report.setAgentsSerialNumbers(missionInfo.getSerialAgentsNumbers());
							report.setGadgetName(missionInfo.getGadget());
							report.setM(this.numOfM);
							report.setQTime(futureint.get());
							report.setTimeIssued(missionInfo.getTimeIssued());
							report.setTimeCreated(mbt);
							diary.addReport(report);
							System.out.println("Mission Completed");
						} else {
							System.out.println("Mission Aborted (TimePassed)");
						}
					} else {
						System.out.println("Mission Aborted (NoGadget)");
					}
				} else {
					System.out.println("Mission Aborted (NoAgent)");
				}
			});
	}
}