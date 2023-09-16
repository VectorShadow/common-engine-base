package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventScheduler;
import org.vsdl.common.engine.event.EventSource;

import java.security.SecureRandom;

public class TestScheduler extends EventScheduler implements EventSource {

    private static final SecureRandom secureRandom = new SecureRandom();

    public TestScheduler(EventEngine eventEngine, int turnLengthInEngineFrames) {
        super(eventEngine, turnLengthInEngineFrames);
    }

    @Override
    public void run() {
        startRunning();
        do {
            int numberOfEventsThisTurn = secureRandom.nextInt(99);
            System.out.println("Scheduling " + numberOfEventsThisTurn + " events this turn.");
            for (int i = 0; i < numberOfEventsThisTurn; ++i) {
                scheduleEvent(new TestEvent(this), i / getTurnLengthInEngineFrames());
            }
            long now = System.currentTimeMillis();
            try {
                Thread.sleep(now % getMillisecondsPerTurn());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while(isRunning());
    }
}
