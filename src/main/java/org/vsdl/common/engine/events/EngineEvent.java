package org.vsdl.common.engine.events;

public class EngineEvent implements Comparable<EngineEvent> {

    private final Long executeAtTime;
    private final EventSource source;

    public EngineEvent(Long executeAtTime, EventSource source) {
        this.executeAtTime = executeAtTime;
        this.source = source;
    }

    public Long getExecuteAtTime() {
        return executeAtTime;
    }

    public EventSource getSource() {
        return source;
    }

    @Override
    public int compareTo(EngineEvent o) {
        return getExecuteAtTime().compareTo(o.getExecuteAtTime());
    }
}
