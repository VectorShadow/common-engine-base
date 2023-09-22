package org.vsdl.common.engine.events;

import org.junit.jupiter.api.Test;
import org.vsdl.common.engine.events.fixtures.TestHandler;
import org.vsdl.common.engine.events.fixtures.TestSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

//todo - performance tests are far out of expected bounds! Investigate and fix.
public class EventEngineTest {

    private Engine engine;

    private long testDuration;

    @Test
    void testTiming_LightLoad_ShortRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 1000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_HeavyLoad_ShortRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 1000L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 256; ++i) {
            engine.registerEventSource(new TestSource(), 10 + i);
        }
            engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_LightLoad_LongRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 60000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_HeavyLoad_LongRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 60000L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 256; ++i) {
            engine.registerEventSource(new TestSource(), 10 + i);
        }
        engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testRegisterSourcesMidRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 1000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        engine.registerEventSource(new TestSource(), 10);
        do {
            Thread.sleep(64);
            engine.registerEventSource(new TestSource(), 10);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }
}
