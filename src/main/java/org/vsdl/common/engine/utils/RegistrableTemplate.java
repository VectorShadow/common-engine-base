package org.vsdl.common.engine.utils;

/**
 * Java does not support multiple inheritance.
 * However, all classes implementing Registrable should use this class as a template.
 */
public abstract class RegistrableTemplate implements Registrable {
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
