import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vsdl.common.engine.dev.TestHandler;
import org.vsdl.common.engine.dev.TestScheduler;
import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventScheduler;

import static org.junit.jupiter.api.Assertions.assertTrue;

//todo - performance tests are far out of expected bounds! Investigate and fix.
public class EventEngineTest {

    private EventEngine engine;

    private long testDuration;

    @BeforeEach
    void setup() {
        engine = EventEngine.getNewEngine();
        testDuration = 0L;
    }

    @Test
    void testTiming_LightLoad_ShortRun() throws InterruptedException {
        testDuration = 64L;
        engine.attachHandler(new TestHandler());
        EventScheduler scheduler = new TestScheduler(engine, 10, 0, 4);
        long start = System.currentTimeMillis();
        engine.start();
        scheduler.start();
        do {
            Thread.sleep(50);
        } while(engine.getCurrentFrame() < testDuration);
        long stop = System.currentTimeMillis();
        long expected = engine.getFramerate() * testDuration;
        long elapsed = stop - start;
        assertTrue(Math.abs(expected - elapsed) < testDuration);
    }

    @Test
    void testTiming_HeavyLoad_ShortRun() throws InterruptedException {
        testDuration = 64L;
        engine.attachHandler(new TestHandler());
        EventScheduler scheduler = new TestScheduler(engine, 10, 16384, 32767);
        long start = System.currentTimeMillis();
        engine.start();
        scheduler.start();
        do {
            Thread.sleep(50);
        } while(engine.getCurrentFrame() < testDuration);
        long stop = System.currentTimeMillis();
        long expected = engine.getFramerate() * testDuration;
        long elapsed = stop - start;
        assertTrue(Math.abs(expected - elapsed) < testDuration);
    }
}
