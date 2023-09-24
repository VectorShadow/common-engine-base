package org.vsdl.common.engine.events;

import org.vsdl.common.engine.utils.ManagedThread;
import org.vsdl.common.engine.utils.ProtectedPriorityQueue;

public class Engine extends ManagedThread {
    private static final long DEFAULT_PEEK_INTERVAL = 10L;
    private final EventHandler eventHandler;
    private final ProtectedPriorityQueue<EngineEvent> eventQueue = new ProtectedPriorityQueue<>();
    private long internalTime;
    private final boolean isRealtime;
    private final long peekInterval;

    private Engine(final EventHandler eventHandler, final boolean isRealtime, final long peekInterval) {
        this.eventHandler = eventHandler;
        this.isRealtime = isRealtime;
        this.peekInterval = peekInterval;
    }

    public static Engine getNewEngine(final EventHandler eventHandler, final boolean isRealtime, final long peekInterval) {
        return new Engine(eventHandler, isRealtime, peekInterval);
    }

    public static Engine getNewEngine(final EventHandler eventHandler, final boolean isRealtime) {
        return getNewEngine(eventHandler, isRealtime, DEFAULT_PEEK_INTERVAL);
    }

    public long getInternalTime() {
        return internalTime;
    }

    public boolean isRealtime() {
        return isRealtime;
    }

    public synchronized void registerEventSource(EventSource eventSource, int millisBeforeFirstEvent) {
        eventQueue.alter(
                new EngineEvent(
                        isRealtime()
                                ? System.currentTimeMillis() + peekInterval + millisBeforeFirstEvent
                                : getInternalTime() + millisBeforeFirstEvent,
                        eventSource
                )
        );
        eventSource.setRegistered(true);
    }

    @Override
    public void run() {
        doStart();
        if (isRealtime) {
            internalTime = 0L;
        }
        long now;
        do {
            while ((!isRealtime && isRunning()) || eventQueue.peek().getExecuteAtTime() < System.currentTimeMillis()) {
                now = System.currentTimeMillis();
                EngineEvent nextEvent = eventQueue.alter(null);
                if (nextEvent.getSource().isRegistered()) {
                    eventQueue.alter(eventHandler.handle(nextEvent, isRealtime() ? now : internalTime));
                }
                if (!isRealtime) {
                    internalTime = eventQueue.peek().getExecuteAtTime();
                }
            }
            if (isRealtime) {
                now = System.currentTimeMillis();
                long sleepDuration = Math.min(peekInterval, Math.max(1L, eventQueue.peek().getExecuteAtTime() - now));
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } while (isRunning());
    }
}
