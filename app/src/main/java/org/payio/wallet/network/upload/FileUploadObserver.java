package org.payio.wallet.network.upload;

import java.io.IOException;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

public abstract class FileUploadObserver<T> extends DefaultObserver<T> {
    @Override
    public void onNext(T t) {
        if (t instanceof ResponseBody) {
            try {
                onUpLoadSuccess(((ResponseBody) t).string());
            } catch (IOException e) {
                e.printStackTrace();
                onUpLoadFail(e);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onUpLoadFail(e);
    }

    @Override
    public void onComplete() {

    }

    //监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }

    //上传成功的回调
    public abstract void onUpLoadSuccess(String response);

    //上传失败回调
    public abstract void onUpLoadFail(Throwable e);

    //上传进度回调
    public abstract void onProgress(int progress);

}
