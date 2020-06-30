package bgu.spl.mics.application.messages;


import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

import java.util.List;

public class AgentsAvailableEvent implements Event {

    private int finish;
    private List<String> serials;

    public AgentsAvailableEvent(int finish,List<String> serials) {
        this.serials=serials;
        this.finish=finish;

    }

    public int getFinish(){ return this.finish; }
    public List<String> getSerials(){ return this.serials; }

}
