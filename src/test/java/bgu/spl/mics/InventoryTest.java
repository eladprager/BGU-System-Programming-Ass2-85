//package bgu.spl.mics;
//
//import bgu.spl.mics.application.passiveObjects.Inventory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class InventoryTest {
//    Inventory check;
//    @BeforeEach
//    public void setUp(){
//        check = new Inventory();
//        System.out.println("Running test...");
//    }
//
//    @Test
//    void load() {
//        String [] k = {"Sky Hook"};
//        check.load(k);
//        Assertions.assertTrue(check.getItem("Sky Hook"),"Should be true");
//    }
//
//    @Test
//    void getItem() {
//        String [] k = {"Sky Hook"};
//        check.load(k);
//        Assertions.assertTrue(check.getItem("Sky Hook"), "Should be true");
//        Assertions.assertFalse(check.getItem("Sky Hook"),"Should be false");
//    }
//
//}
