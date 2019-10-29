package org.payio.wallet.model.event;

public class WebsocketEvent {

    public boolean connect;
    public String messge;
    public int code;

    public WebsocketEvent(boolean connect) {
        this.connect = connect;
    }

    public WebsocketEvent(boolean connect, int code) {
        this.connect = connect;
        this.code = code;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
