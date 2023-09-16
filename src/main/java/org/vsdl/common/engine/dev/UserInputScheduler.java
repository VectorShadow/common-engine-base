package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.EventEngine;
import org.vsdl.common.engine.event.EventScheduler;
import org.vsdl.common.engine.event.EventSource;

import java.security.SecureRandom;

public class UserInputScheduler extends EventScheduler implements EventSource {

    private static final SecureRandom secureRandom = new SecureRandom();

    private long userInputCount = 0;
    public UserInputScheduler(EventEngine eventEngine, int turnLengthInEngineFrames) {
        super(eventEngine, turnLengthInEngineFrames);
    }

    public long getUserInputCount() {
        return userInputCount;
    }

    public void scheduleUserInputEvent() {
        userInputCount++;
        scheduleEvent(new UserInputEvent(this), 0, true);
    }

    @Override
    public void run() {
        startRunning();
        scheduleUserInputEvent();
        do {
            int numberOfEventsThisTurn = secureRandom.nextInt(10);
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
