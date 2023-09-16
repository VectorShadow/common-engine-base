package org.vsdl.common.engine.event;

import java.util.Objects;

public class ScheduledEvent implements Comparable<ScheduledEvent> {

    private final Event event;
    private final Long executionFrame;

    public ScheduledEvent(Event event, Long executionFrame) {
        this.event = event;
        this.executionFrame = executionFrame;
    }

    public Event getEvent() {
        return event;
    }

    public Long getExecutionFrame() {
        return executionFrame;
    }

    @Override
    public int compareTo(ScheduledEvent o) {
        return Objects.isNull(o) ? this.executionFrame.intValue() : this.executionFrame.compareTo(o.getExecutionFrame());
    }
}
