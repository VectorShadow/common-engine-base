package org.vsdl.common.engine.event;

public abstract class Event {

    private final EventSource eventSource;

    private EventScheduler scheduledBy;

    public Event(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    protected EventSource getSource() {
        return eventSource;
    }

    protected void setScheduledBy(EventScheduler scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    protected EventScheduler getScheduledBy() {
        return scheduledBy;
    }
}
