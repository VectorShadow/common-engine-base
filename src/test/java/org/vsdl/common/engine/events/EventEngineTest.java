package org.vsdl.common.engine.events;

import org.junit.jupiter.api.Test;
import org.vsdl.common.engine.events.fixtures.TestHandler;
import org.vsdl.common.engine.events.fixtures.TestSource;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventEngineTest {

    private Engine engine;

    private long testDuration;

    @Test
    void testTiming_LightLoad_ShortRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 5000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("\nTest - Light Load / Short Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_HeavyLoad_ShortRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 5000L;
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
        System.out.println("\nTest - Heavy Load / Short Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_LightLoad_LongRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 30000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        do {
            Thread.sleep(50);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("\nTest - Light Load / Long Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testTiming_HeavyLoad_LongRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 30000L;
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
        System.out.println("\nTest - Heavy Load / Long Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testRegisterSourcesMidRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 15000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        engine.start();
        engine.registerEventSource(new TestSource(), 10);
        do {
            Thread.sleep(255);
            engine.registerEventSource(new TestSource(), 10);
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("\nTest - Register Sources Mid Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testUnregisterSourcesMidRun() throws InterruptedException {
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 15000L;
        long start = System.currentTimeMillis();
        engine.registerEventSource(new TestSource(), 10);
        Queue<EventSource> registrationQueue = new ArrayDeque<>();
        for (int i = 0; i < 10; ++i) {
            EventSource eventSource = new TestSource();
            registrationQueue.add(eventSource);
            engine.registerEventSource(eventSource, 10);
        }
        engine.start();
        do {
            Thread.sleep(50);
            if (!registrationQueue.isEmpty()) {
                EventSource eventSource = registrationQueue.remove();
                eventSource.setRegistered(false);
            }
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("\nTest - Unregister Sources Mid Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }

    @Test
    void testExtendedRegisterUnregisterRun() throws InterruptedException {
        Random random = new Random();
        TestHandler handler = new TestHandler();
        engine = Engine.getNewEngine(handler, true);
        testDuration = 300000L;
        long start = System.currentTimeMillis();
        Queue<EventSource> registrationQueue = new ArrayDeque<>();
        for (int i = 0; i < 256; ++i) {
            EventSource eventSource = new TestSource();
            registrationQueue.add(eventSource);
            engine.registerEventSource(eventSource, random.nextInt(90) + 10);
        }
        engine.start();
        int eventSourcesAddedAfterStart = 0;
        int eventSourcesRemovedAfterStart = 0;
        do {
            Thread.sleep(50);
            if (random.nextBoolean() && !registrationQueue.isEmpty()) {
                EventSource eventSource = registrationQueue.remove();
                eventSource.setRegistered(false);
                eventSourcesRemovedAfterStart++;
            } else {
                EventSource eventSource = new TestSource();
                registrationQueue.add(eventSource);
                engine.registerEventSource(eventSource, random.nextInt(90) + 10);
                eventSourcesAddedAfterStart++;
            }
        } while(System.currentTimeMillis() < start + testDuration);
        engine.halt();
        long stop = System.currentTimeMillis();
        long elapsed = stop - start;
        System.out.println("\nTest - Extended Register/Unregister Run");
        System.out.println("Events handled: " + handler.getEventCount());
        System.out.println("Expected duration: " + testDuration);
        System.out.println("Elapsed duration: " + elapsed);
        System.out.println("Event sources added after run start: " + eventSourcesAddedAfterStart);
        System.out.println("Event sources removed after run start: " + eventSourcesRemovedAfterStart);
        assertTrue((double) Math.abs(testDuration - elapsed) < (double)testDuration * 0.05);
    }
}
