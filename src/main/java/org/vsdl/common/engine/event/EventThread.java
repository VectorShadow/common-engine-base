package org.vsdl.common.engine.event;

public abstract class EventThread extends Thread {

    private boolean isRunning = false;

    protected boolean isRunning() {
        return isRunning;
    }
    protected void startRunning() {
        isRunning = true;
    }

    public void stopRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("EventThread was not running!");
        }
        isRunning = false;
    }

    public abstract void run();
}
