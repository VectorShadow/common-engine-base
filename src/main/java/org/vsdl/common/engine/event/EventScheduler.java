package org.vsdl.common.engine.event;

public abstract class EventScheduler extends EventThread {

    private final EventEngine eventEngine;

    private final int turnLengthInEngineFrames;
    private final int millisecondsPerTurn;

    public EventScheduler(EventEngine eventEngine, int turnLengthInEngineFrames) {
        this.eventEngine = eventEngine;
        this.turnLengthInEngineFrames = turnLengthInEngineFrames;
        this.millisecondsPerTurn = this.turnLengthInEngineFrames * eventEngine.getFramerate();
    }

    protected int getTurnLengthInEngineFrames() {
        return turnLengthInEngineFrames;
    }

    protected int getMillisecondsPerTurn() {
        return millisecondsPerTurn;
    }

    protected void scheduleEvent(Event event, int schedulingPassThisTurn) {
        event.setScheduledBy(this);
        eventEngine.scheduleEvent(new ScheduledEvent(event, eventEngine.getCurrentFrame() + schedulingPassThisTurn + 1));
    }
}
