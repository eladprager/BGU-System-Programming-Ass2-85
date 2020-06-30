//package bgu.spl.mics;
//
//import bgu.spl.mics.application.subscribers.M;
//import bgu.spl.mics.application.subscribers.Q;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class MessageBrokerTest {
//    private MessageBroker checkMessageBroker;
//    @BeforeEach
//    public void setUp(){
//        checkMessageBroker = MessageBrokerImpl.getInstance();
//    }
//
//    @Test
//    public void test(){
//        MessageBroker s1 = MessageBrokerImpl.getInstance();
//        if (!(s1 == checkMessageBroker)) fail("MassegeBroker is a singlton!");
//        M m = new M();
//        checkMessageBroker.register(m);
//        TestEvent event = new TestEvent();
//        Future<String> future = checkMessageBroker.sendEvent(event);
//        if (future != null) fail("m is not subscribed");
//        checkMessageBroker.subscribeEvent(event.getClass(),m);
//        Future<String> future2 =  checkMessageBroker.sendEvent(event);
//        try {
//            Message message = checkMessageBroker.awaitMessage(m);
//            Assertions.assertEquals(event,message);
//        }
//        catch (Exception c){
//
//        }
//        if (future2 == null) fail("Subscribe is not working!");
//        checkMessageBroker.complete(event,"Hatul Katom");
//        Assertions.assertEquals(future.get(),"Hatul Katom");
//
//
//        TestBroadcast broadcast = new TestBroadcast();
//        Q q = new Q();
//        checkMessageBroker.subscribeBroadcast(broadcast.getClass(), m);
//        checkMessageBroker.subscribeBroadcast(broadcast.getClass(), q);
//        checkMessageBroker.sendBroadcast(broadcast);
//        try {
//            Message mes1 = checkMessageBroker.awaitMessage(m);
//            Message mes2 = checkMessageBroker.awaitMessage(q);
//            Assertions.assertEquals(mes1, mes2);
//            Assertions.assertEquals(mes2, broadcast);
//        }
//        catch (Exception e) {}
//        checkMessageBroker.unregister(m);
//        try {
//            Message mes1 = checkMessageBroker.awaitMessage(m);
//            fail("unregister did not work!");
//        }
//        catch (Exception e) {
//        }
//    }
//
//    class TestEvent implements Event<String> {
//        public TestEvent() {}
//    }
//
//    class TestBroadcast implements  Broadcast {
//        public TestBroadcast() {}
//    }
//}
