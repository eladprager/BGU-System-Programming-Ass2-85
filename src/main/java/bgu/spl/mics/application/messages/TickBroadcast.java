package bgu.spl.mics.application.messages;


import bgu.spl.mics.Broadcast;

import java.util.concurrent.atomic.AtomicInteger;

public class TickBroadcast implements Broadcast {

    int ticker;


    private String senderId;

    public TickBroadcast(String senderId,int ticker) {
        this.senderId = senderId;
        this.ticker = ticker;
    }

    public int getTicker() {
        return ticker;
    }



    public String getSenderId() {
        return senderId;
    }

}
