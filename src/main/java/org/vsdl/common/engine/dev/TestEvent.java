package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.Event;
import org.vsdl.common.engine.event.EventSource;

public class TestEvent extends Event {
    private static long nextIndex = 0L;

    private final long index;

    public TestEvent(EventSource eventSource) {
        super(eventSource);
        index = nextIndex++;
    }

    public long getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Event #" + getIndex();
    }
}
