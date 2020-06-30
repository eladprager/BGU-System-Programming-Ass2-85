package bgu.spl.mics.application.messages;


import bgu.spl.mics.Event;

public class GadgetAvailableEvent<T> implements Event<T> {
    private String name;

    public GadgetAvailableEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
