package org.payio.wallet.model.event;

public class AppCheckEvent {
    private boolean isCheck;

    private volatile static AppCheckEvent event = new AppCheckEvent(true);

    private AppCheckEvent(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public static AppCheckEvent getInstance() {
        if (event == null) {
            synchronized (AppCheckEvent.class) {
                if (event == null) {
                    event = new AppCheckEvent(true);
                }
            }
        }
        return event;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
