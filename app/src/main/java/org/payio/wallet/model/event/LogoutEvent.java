package org.payio.wallet.model.event;

public class LogoutEvent {
    private boolean isLogout;

    private volatile static LogoutEvent event = new LogoutEvent(true);

    private LogoutEvent(boolean isLogout) {
        this.isLogout = isLogout;
    }

    public static LogoutEvent getInstance() {
        if (event == null) {
            synchronized (LogoutEvent.class) {
                if (event == null) {
                    event = new LogoutEvent(true);
                }
            }
        }
        return event;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }
}
