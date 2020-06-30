//package bgu.spl.mics;
//
//import bgu.spl.mics.application.passiveObjects.Agent;
//import bgu.spl.mics.application.passiveObjects.Squad;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class SquadTest {
//    Squad check;
//    @BeforeEach
//    public void setUp(){
//        check = new Squad();
//        System.out.println("Running test...");
//    }
//
//
//    @Test
//    void load() {
//        Agent a = new Agent("007","James Bond");
//        Agent b = new Agent("002","Bill Fairbanks");
//        Agent c = new Agent("0012","Sam Johnston");
//        Agent [] d = {a,b,c};
//        check.load(d);
//        Assertions.assertEquals(check.getAgentsMap().containsKey("007"),true);
//    }
//
//    @Test
//    void releaseAgents() {
//        Agent a = new Agent("007","James Bond");
//        a.acquire();
//        Agent b = new Agent("002","Bill Fairbanks");
//        b.acquire();
//        Agent c = new Agent("0012","Sam Johnston");
//        Agent [] d = {a,b,c};
//        check.load(d);
//        List<String> serials = Arrays.asList(a.getSerialNumber(),b.getSerialNumber());
//        check.releaseAgents(serials);
//        Assertions.assertTrue(a.isAvailable(),"Should be true");
//        Assertions.assertTrue(b.isAvailable(),"Should be true");
//
//    }
//
//    @Test
//    void sendAgents() {
//
//    }
//
//    @Test
//    void getAgents1() {
//        Agent a = new Agent("007","James Bond");
//        Agent b = new Agent("002","Bill Fairbanks");
//        Agent c = new Agent("0012","Sam Johnston");
//        Agent [] d = {a,b,c};
//        check.load(d);
//        List<String> serials = Arrays.asList("007","008","002");
//        Assertions.assertFalse(check.getAgents(serials),"Should be false");
//    }
//
//    @Test
//    void getAgents2() {
//        Agent a = new Agent("007","James Bond");
//        Agent b = new Agent("002","Bill Fairbanks");
//        Agent c = new Agent("0012","Sam Johnston");
//        Agent [] d = {a,b,c};
//        check.load(d);
//        List<String> serials = Arrays.asList("007");
//        Assertions.assertTrue(check.getAgents(serials),"Should be true");
//        Assertions.assertFalse(check.getAgentsMap().get("007").isAvailable(),"Should be false");
//    }
//
//    @Test
//    void getAgentsNames() {
//        Agent a = new Agent("007","James Bond");
//        Agent b = new Agent("002","Bill Fairbanks");
//        Agent c = new Agent("0012","Sam Johnston");
//        Agent [] d = {a,b,c};
//        check.load(d);
//        List<String> Expected = Arrays.asList(a.getName(),b.getName(),c.getName());
//        List<String> serials = Arrays.asList(a.getSerialNumber(),b.getSerialNumber(),c.getSerialNumber());
//        Assertions.assertTrue(check.getAgentsNames(serials).containsAll(Expected),"Should be true");
//    }
//}
