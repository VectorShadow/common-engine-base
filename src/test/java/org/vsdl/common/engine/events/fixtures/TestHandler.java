package org.vsdl.common.engine.events.fixtures;

import org.vsdl.common.engine.events.EngineEvent;
import org.vsdl.common.engine.events.EventHandler;
import org.vsdl.common.engine.events.EventSource;

import java.util.Random;

public class TestHandler implements EventHandler {

    private static final Random random = new Random();

    private int eventCount = 0;
    @Override
    public EngineEvent handle(EngineEvent event, long engineTime) {
        EventSource source = event.getSource();
        ++eventCount;
        return new EngineEvent(engineTime + 5 + random.nextLong(25), source);
    }

    public int getEventCount() {
        return eventCount;
    }
}
