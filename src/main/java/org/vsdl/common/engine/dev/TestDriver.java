package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventHandler;
import org.vsdl.common.engine.event.EventScheduler;

import java.io.IOException;

public class TestDriver {
    public static void main(String[] args) throws InterruptedException, IOException {
        turnBasedEngine();
    }

    private static void realtimeEngine() throws InterruptedException {
        EventEngine eventEngine = EventEngine.getNewEngine();
        TestHandler handler = new TestHandler();
        eventEngine.attachHandler(handler);
        EventScheduler eventScheduler = new TestScheduler(eventEngine, 8);
        eventEngine.start();
        eventScheduler.start();
        do {
            System.out.println(handler.getEventCount());
            Thread.sleep(100);
        } while (handler.getEventCount() < 1024);
        eventScheduler.stopRunning();
        Thread.sleep(100);
        eventEngine.stopRunning();
    }

    private static void turnBasedEngine() throws IOException, InterruptedException {
        EventEngine engine = EventEngine.getNewEngine();
        EventHandler handler = new UserInputHandler();
        engine.attachHandler(handler);
        UserInputScheduler scheduler = new UserInputScheduler(engine, 32);
        engine.start();
        scheduler.start();
        do {
            System.out.println("User Inputs: " + scheduler.getUserInputCount());
            System.in.read();
            scheduler.scheduleUserInputEvent();
        } while (scheduler.getUserInputCount() < 10);
        scheduler.stopRunning();
        Thread.sleep(100);
        engine.stopRunning();
    }
}
