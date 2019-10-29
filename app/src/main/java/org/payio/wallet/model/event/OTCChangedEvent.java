package org.payio.wallet.model.event;

public class OTCChangedEvent {
    private boolean isChanged;
    private volatile static OTCChangedEvent event = new OTCChangedEvent(true);

    private OTCChangedEvent(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public static OTCChangedEvent getInstance() {
        if (event == null) {
            synchronized (OTCChangedEvent.class) {
                if (event == null) {
                    event = new OTCChangedEvent(true);
                }
            }
        }
        return event;
    }
    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
