package bgu.spl.mics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {

    Future<String> a;
    Future<Boolean> b;
    Future<Integer> c;

    @BeforeEach
    public void setUp(){
        a = new Future();
        b = new Future();
        c = new Future();
    }

    @Test
    void get1() {
        String d = "Hatul Katom";
        a.resolve(d);
        Assertions.assertEquals(a.get(),"Hatul Katom");
    }

    @Test
    void get2() {
        Boolean d = true;
        b.resolve(d);
        Assertions.assertEquals(b.get(),true);
    }
    @Test
    void get3() {
        Integer d = 666;
        c.resolve(d);
        Assertions.assertEquals(c.get(),666);
    }

    @Test
    void resolve() {
        String d = "Hatul Katom";
        a.resolve(d);
        Assertions.assertEquals(a.isDone(),true);
    }

    @Test
    void isDone() {
        Assertions.assertEquals(a.isDone(),false);
    }

    @Test
    void testGet1() {
        Assertions.assertNull(a.get(100, TimeUnit.MINUTES));
    }

    @Test
    void testGet2() {
        Assertions.assertNull(a.get(1000, TimeUnit.MILLISECONDS));
    }
}
