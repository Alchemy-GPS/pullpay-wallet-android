package org.payio.wallet.model.event;

public class OrderChangedEvent {
    private boolean isChanged;
    private volatile static OrderChangedEvent event = new OrderChangedEvent(true);

    private OrderChangedEvent(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public static OrderChangedEvent getInstance() {
        if (event == null) {
            synchronized (OrderChangedEvent.class) {
                if (event == null) {
                    event = new OrderChangedEvent(true);
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
