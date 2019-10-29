package org.payio.wallet.model.event;

public class ViewPagerChangeEvent {
    private int page;

    public ViewPagerChangeEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
