package bgu.spl.mics.application;

import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
//import sun.awt.image.ImageWatched;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) throws IOException {
            Input input = JsonInputReader.getInputFromJson(Paths.get(args[0]).toString());
            Inventory.getInstance().load(input.getInventory());
            Squad.getInstance().load(input.getSquad());
            List<String> serials = new LinkedList<>();
            for (int i = 0; i < input.getSquad().length; i++){
                serials.add(input.getSquad()[i].getSerialNumber());
            }
            Squad.getInstance().releaseAgents(serials);
            Services services = input.getServices();
////        Intelligence[] intelligence = input.getServices().getIntelligence();
//        List<MissionInfo> missions = new LinkedList<>();
//        for (int i = 0; i < input.getServices().getIntelligence().length; i++){
//            for (int j = 0; j < input.getServices().getIntelligence()[i].getMission().size(); j++){
//                missions.add(input.getServices().getIntelligence()[i].getMission().get(j));
//            }

            int M = services.getM();
            int MP = services.getMoneypenny();
            int I = services.getIntelligence().length;
            int Time = services.getTime();

            try {
//        ExecutorService manager = Executors.newFixedThreadPool(M+MP+I+2);
                List<Thread> runnable = new ArrayList<>();

                for (int i = 0; i < M; i++) {
                    runnable.add(new Thread(new M(i),"M"));
                }
                for (int i = 0; i < MP; i++) {
                    runnable.add(new Thread(new Moneypenny(i),"MP"));
                }
                for (int i = 0; i < I; i++) {
                    runnable.add(new Thread(services.getIntelligence()[i],"I"));
                }
                runnable.add(new Thread(new Q(),"Q"));
                runnable.add(new Thread(new TimeService(Time),"Ticker"));


                for (int i = 0; i < runnable.size(); i++) {
                    runnable.get(i).start();
                }

                for (int i = 0; i < runnable.size(); i++) {
                    runnable.get(i).join();
                }
                Thread.sleep(Time * 100);

                for (int i = 0; i < runnable.size(); i++) {
                    runnable.get(i).interrupt();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Inventory k = Inventory.getInstance();
            k.printToFile(args[1]);
            Diary d = Diary.getInstance();
            d.printToFile(args[2]);
        }
    }
