package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.Event;
import org.vsdl.common.engine.event.EventSource;

public class UserInputEvent extends Event {
    public UserInputEvent(EventSource eventSource) {
        super(eventSource);
    }
}
