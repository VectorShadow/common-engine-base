package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventScheduler;

public class TestDriver {
    public static void main(String[] args) throws InterruptedException {
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
}
