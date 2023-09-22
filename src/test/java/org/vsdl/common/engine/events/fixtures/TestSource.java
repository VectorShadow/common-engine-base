package org.vsdl.common.engine.events.fixtures;

import org.vsdl.common.engine.events.EventSource;

public class TestSource implements EventSource {
    private boolean isRegistered = false;
    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

    @Override
    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
