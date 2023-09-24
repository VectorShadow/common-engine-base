package org.vsdl.common.engine.utils;

public abstract class ManagedThread extends Thread {
    private boolean isRunning = false;

    @Override
    public abstract void run();

    protected void doStart() {
        isRunning = true;
    }

    public void halt() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
