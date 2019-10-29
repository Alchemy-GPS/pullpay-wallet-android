package org.payio.wallet.model.event;

public class WalletChangedEvent {
    private boolean isChanged;
    private volatile static WalletChangedEvent event = new WalletChangedEvent(true);

    private WalletChangedEvent(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public static WalletChangedEvent getInstance() {
        if (event == null) {
            synchronized (WalletChangedEvent.class) {
                if (event == null) {
                    event = new WalletChangedEvent(true);
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
