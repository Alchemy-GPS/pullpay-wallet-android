package org.payio.wallet.model.event;

public class PayMessageEvent {
    private boolean isChanged;
    private volatile static PayMessageEvent event = new PayMessageEvent(true);

    private PayMessageEvent(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public static PayMessageEvent getInstance() {
        if (event == null) {
            synchronized (PayMessageEvent.class) {
                if (event == null) {
                    event = new PayMessageEvent(true);
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
