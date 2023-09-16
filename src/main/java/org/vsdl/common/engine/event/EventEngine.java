package org.vsdl.common.engine.event;

import java.util.*;

public class EventEngine extends EventThread {

    private static final int DEFAULT_FRAMERATE = 16;

    private int framerate;

    private long currentFrame;

    private boolean executionLock;
    private boolean scheduleLock;
    private boolean userInputLock;

    private final Queue<ScheduledEvent> scheduledEvents = new PriorityQueue<>();

    private final List<EventHandler> handlers = new ArrayList<>();

    public static EventEngine getNewEngine() {
        return new EventEngine();
    }

    private EventEngine() {
        setFramerate(DEFAULT_FRAMERATE);
    }

    public void attachHandler(EventHandler eventHandler) {
        if (isRunning()) {
            throw new IllegalStateException("Event Handlers may not be attached while Engine is running!");
        }
        handlers.add(eventHandler);
        eventHandler.linkToEngine(this);
    }

    public void run() {
        startRunning();
        currentFrame = 0L;
        executionLock = false;
        userInputLock = false;
        do {
            scheduleLock = true;
            while (userInputLock) {
                try {
                    Thread.sleep(framerate / 4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (executionLock) {
                try {
                    Thread.sleep(framerate / 4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (!scheduledEvents.isEmpty() && scheduledEvents.peek().getExecutionFrame() <= currentFrame) {
                ScheduledEvent scheduledEvent = scheduledEvents.poll();
                for (EventHandler handler : handlers) {
                    handler.handleEvent(scheduledEvent.getEvent());
                }
            }
            ++currentFrame;
            scheduleLock = false;
            try {
                Thread.sleep(System.currentTimeMillis() % framerate);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (isRunning());
    }

    public synchronized void scheduleEvent(ScheduledEvent eventToSchedule) {
        if (!isRunning()) {
            throw new IllegalStateException("events may not be scheduled while engine is not running!");
        }
        while (scheduleLock) {
            try {
                Thread.sleep(framerate / 4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executionLock = true;
        scheduledEvents.add(eventToSchedule);
        executionLock = false;
    }

    public int getFramerate() {
        return framerate;
    }

    public void setFramerate(int framerate) {
        if (isRunning()) {
            throw new IllegalStateException("framerate may not be changed while EventThread is running!");
        }
        this.framerate = framerate;
    }

    long getCurrentFrame() {
        return currentFrame;
    }

    /**
     * For a turn based engine. Allow execution to be paused indefinitely while waiting for the user's next command.
     * Handler is responsible for locking, Scheduler for unlocking.
     * @param isLockedForUserInput true on user input handled, false on user input scheduled
     */
    void setUserInputLock(boolean isLockedForUserInput) {
        userInputLock = isLockedForUserInput;
    }
}
