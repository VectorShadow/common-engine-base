package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.Event;
import org.vsdl.common.engine.event.EventHandler;

public class TestHandler extends EventHandler {

    private long eventCount = 0;
    @Override
    public void handleEvent(Event event) {
        ++eventCount;
        System.out.println("handling event: " + event);
    }

    public long getEventCount() {
        return eventCount;
    }
}
