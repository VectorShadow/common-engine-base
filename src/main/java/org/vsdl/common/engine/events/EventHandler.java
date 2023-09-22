package org.vsdl.common.engine.events;

public interface EventHandler {
    EngineEvent handle(EngineEvent event, long engineTime);
}
