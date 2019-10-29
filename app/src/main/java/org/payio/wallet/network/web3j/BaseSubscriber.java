package org.payio.wallet.network.web3j;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> implements OnSuccess<T>{
    @Override
    public void onCompleted() {
        unsubscribe();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}
