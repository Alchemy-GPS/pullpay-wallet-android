package org.payio.wallet.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.customview.CtrlScrollViewPager;
import org.payio.navigation.BottomNavigationLayout;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragmentActivity;
import org.payio.wallet.dialog.AppDownloadProgressDialog;
import org.payio.wallet.dialog.AppUpdateDialog;
import org.payio.wallet.model.event.AppCheckEvent;
import org.payio.wallet.model.event.RedownloadEvent;
import org.payio.wallet.model.event.ViewPagerChangeEvent;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.io.UpdateAppResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.EventBusUtil;

import static org.payio.wallet.params.TransParam.LAUNCH_RXTRA_BUNDLE;
import static org.payio.wallet.params.TransParam.OTC_PAGE_URL;

public class MainActivity extends BaseFragmentActivity<MainPresenter> implements MainView, View.OnClickListener {
    private BottomNavigationLayout mBottomNavigationLayout;
    private CtrlScrollViewPager mViewPager;

    private String downloadUrl;
    private AppUpdateDialog mUpdateDialog;
    private AppDownloadProgressDialog mProgressDialog;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusUtil.register(this);

        mViewPager = findViewById(R.id.main_viewpager);
        mBottomNavigationLayout = findViewById(R.id.main_navigation);

        MvpPre.checkAppUpdate(this, false);

        mAdapter = new MainAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setScroll(false);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(mAdapter);

        mBottomNavigationLayout.setViewPager(mViewPager);

        MvpPre.setPushALias(this);

        Bundle bundle = getIntent().getBundleExtra(LAUNCH_RXTRA_BUNDLE);
        if (bundle != null) {
            checkBunble(bundle);
        }
    }

    @Override
    protected MainPresenter bindPresenter() {
        return new MainPresenter(this);
    }

    private void checkBunble(@NonNull Bundle bundle) {
        String url = bundle.getString(OTC_PAGE_URL);
        Intent intent = new Intent(this, OTCWebActivity.class);
        intent.putExtra(TransParam.OTC_PAGE_URL, url);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        if (!AppUtil.isServiceRunning(this, "org.payio.wallet.service.MessageService")) {
            Intent intent = new Intent(this, MessageService.class);
            startService(intent);
        }

        if (!AppUtil.isServiceRunning(this, "org.payio.wallet.service.ProtectService")) {
            Intent intent = new Intent(this, ProtectService.class);
            startService(intent);
        }

        EventBusUtil.post(new WebsocketEvent(true, WsManager.CHECK_WEBSOCKET));
        */
    }

    @Override
    public void showUpdate(UpdateAppResponse response) {
        downloadUrl = response.getUrl();
        if (mUpdateDialog == null) {
            mUpdateDialog = new AppUpdateDialog(this);
        }
        mUpdateDialog.setUpdateInfo(response);
        mUpdateDialog.setUpdatekListener(new AppUpdateDialog.OnUpdatekClickListener() {
            @Override
            public void onUpdatekClick() {
                MvpPre.download(downloadUrl, MainActivity.this);
                showDownloadProgress();
            }
        });
        mUpdateDialog.show();
    }

    @Override
    public void showDownloadProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new AppDownloadProgressDialog(this);
        }

        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceive(ViewPagerChangeEvent event) {
        if (event != null) {
            mViewPager.setCurrentItem(event.getPage(), false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void needRedownload(RedownloadEvent event) {
        if (event != null && event.isRedownload()) {
            mProgressDialog = null;
            MvpPre.download(downloadUrl, MainActivity.this);
            showDownloadProgress();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void checkAppUpdate(AppCheckEvent event) {
        if (event != null) {
            MvpPre.checkAppUpdate(this, event.isCheck());
        }
    }
}
