package org.payio.wallet.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Basesubscriber<T> implements Observer<T> {
    private Disposable mDisposable;

    @Override
    public void onNext(T t) {
        if (t != null) {
            this.onResponse(t);
        } else {
            onError(new NullPointerException("Response Content is NULL"));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onComplete() {

    }

    public abstract void onResponse(T t);

    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
