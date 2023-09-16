package org.vsdl.common.engine.event;

import java.util.Objects;

public abstract class EventHandler {

    private EventEngine linkedEngine;

    protected abstract void handleEvent(Event event);

    public void linkToEngine(EventEngine engine) {
        if (Objects.nonNull(linkedEngine)) {
            throw new IllegalStateException("Attempted to link a new engine to a handler which is already linked!");
        }
        linkedEngine = engine;
    }

    protected void lockEnginePendingUserInput() {
        linkedEngine.setUserInputLock(true);
    }
}
