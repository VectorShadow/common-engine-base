package org.vsdl.common.engine.dev;

import org.vsdl.common.engine.event.Event;
import org.vsdl.common.engine.event.EventHandler;

public class UserInputHandler extends EventHandler {
    @Override
    protected void handleEvent(Event event) {
        System.out.println("Handling event " + event);
        if (event instanceof UserInputEvent) {
            lockEnginePendingUserInput();
            System.out.println("Handling user event!");
        }
    }
}
