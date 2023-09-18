package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventScheduler;
import org.vsdl.common.engine.event.EventSource;

import java.security.SecureRandom;

public class TestScheduler extends EventScheduler implements EventSource {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final int minimumEventsPerTurn;
    private final int maximumEventsPerTurn;


    public TestScheduler(EventEngine eventEngine, int turnLengthInEngineFrames) {
        this(eventEngine, turnLengthInEngineFrames, 0, 99);
    }

    public TestScheduler(EventEngine eventEngine, int turnLengthInEngineFrames, int minimumEventsPerTurn, int maximumEventsPerTurn) {
        super(eventEngine, turnLengthInEngineFrames);
        this.minimumEventsPerTurn = minimumEventsPerTurn;
        this.maximumEventsPerTurn = maximumEventsPerTurn;
    }

    @Override
    public void run() {
        startRunning();
        do {
            int numberOfEventsThisTurn = minimumEventsPerTurn + secureRandom.nextInt(maximumEventsPerTurn);
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
