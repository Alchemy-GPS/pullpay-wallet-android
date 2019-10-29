package org.payio.wallet.mvp.main_3_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.model.OnlineQRCode;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.model.event.WalletChangedEvent;
import org.payio.wallet.mvp.qrcode.QrcodeScanActivity;
import org.payio.wallet.mvp.transaction.receive.ReceiveCurrencyActivity;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.UrlQrcodeParser;

import java.net.URISyntaxException;
import java.util.List;

public class WalletFragment extends BaseFragment<WalletPresenter> implements WalletView, View.OnClickListener, OnRefreshListener {

    private CryptocurrencyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private Activity mContext;
    private TextView mWalletAddress;
    private TextView mWalletBalance;
    private KProgressHUD progressHUD;
    private ImageView mCopy;
    private String walletAddress;

    public static WalletFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected WalletPresenter bindPresenter() {
        return new WalletPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getSelfActivity();
        View mainView = inflater.inflate(R.layout.fragment_wallet, container, false);
        initView(mainView);
        EventBusUtil.register(this);
        return mainView;
    }

    private void initView(View mainView) {
        LinearLayout mWalletInfo = mainView.findViewById(R.id.home_wallet_info);

        RelativeLayout mScanQrcode = mainView.findViewById(R.id.wallet_scan_qrcode);

        RelativeLayout mAddToken = mainView.findViewById(R.id.home_add_token);

        mWalletAddress = mainView.findViewById(R.id.home_wallet_address);
        mCopy = mainView.findViewById(R.id.home_wallet_address_copy);

        mWalletBalance = mainView.findViewById(R.id.home_asset_currency);

        mRefreshLayout = mainView.findViewById(R.id.fragment_home_refresh);

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = mainView.findViewById(R.id.fragment_home_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getSelfActivity()));

        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new CryptocurrencyAdapter(getSelfActivity());

        mRecyclerView.setAdapter(mAdapter);

        mWalletInfo.setOnClickListener(this);
        mScanQrcode.setOnClickListener(this);
        mAddToken.setOnClickListener(this);
        mCopy.setOnClickListener(this);

        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_wallet_info) {
            if (!TextUtils.isEmpty(walletAddress)) {
                Intent intent = new Intent(mContext, ReceiveCurrencyActivity.class);
                intent.putExtra(TransParam.WALLET_ADDRESS, walletAddress);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.wallet_scan_qrcode) {
            AndPermission.with(getSelfActivity())
                    .runtime()
                    .permission(Permission.CAMERA)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (AndPermission.hasPermissions(WalletFragment.this, Permission.CAMERA)) {
                                Intent intent = new Intent(mContext, QrcodeScanActivity.class);
                                startActivityForResult(intent, TransParam.QRCODE_REQUEST_CODE);
                            } else {
                                SmartToast.warning(getString(R.string.no_camera_permission));
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            SmartToast.warning(getString(R.string.no_camera_permission));
                        }
                    })
                    .start();
        } else if (v.getId() == R.id.home_add_token) {
            if (MvpPre != null) {
                MvpPre.addToken(getSelfActivity());
            }
        }else if (v.getId() == R.id.home_wallet_address_copy) {
            if (!TextUtils.isEmpty(walletAddress)) {
                AppUtil.copyAddress(mContext, walletAddress);
                SmartToast.show(getString(R.string.copy_success));
            }
        }
    }

    @Override
    public void setCoinInfo(List<Cryptocurrency> coinTypeList) {
        mAdapter.setDatas(coinTypeList);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getWalletInfo();
    }

    @Override
    public void stopRefresh() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
        mWalletAddress.setText(walletAddress);
    }

    @Override
    public void setWalletBalance(String walletBalance) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onPageSelected() {
        getWalletInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onWalletChanged(WalletChangedEvent event) {
        if (event != null && event.isChanged()) {
            getWalletInfo();
        }
    }

    private void getWalletInfo() {
        if (MvpPre != null) {
            MvpPre.getWalletInfo(getSelfActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    private void setSendInfo(String result) {
        if (!TextUtils.isEmpty(result)) {
            OnlineQRCode onlineQRCode = (OnlineQRCode) GsonUtil.jsonToBean(result, OnlineQRCode.class);
            UrlQrcodeParser.Parameter parameter = UrlQrcodeParser.parseString(result);

            if (onlineQRCode != null && !TextUtils.isEmpty(onlineQRCode.getAgentCode()) && !TextUtils.isEmpty(onlineQRCode.getOutFlowNo())) {
                String agentCode = onlineQRCode.getAgentCode();
                String outFlowNo = onlineQRCode.getOutFlowNo();
                String payio = CommonUtil.setPaySchemaParams(agentCode, outFlowNo);
                try {
                    Intent intent = Intent.parseUri(payio, Intent.URI_INTENT_SCHEME);
                    startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (parameter != null && !TextUtils.isEmpty(parameter.getAgentCode()) && !TextUtils.isEmpty(parameter.getOutFlowNo())) {
                String agentCode = parameter.getAgentCode();
                String outFlowNo = parameter.getOutFlowNo();
                String payio = CommonUtil.setPaySchemaParams(agentCode, outFlowNo);
                try {
                    Intent intent = Intent.parseUri(payio, Intent.URI_INTENT_SCHEME);
                    startActivity(intent);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                SmartToast.error(getString(R.string.send_address_error));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TransParam.QRCODE_REQUEST_CODE && resultCode == TransParam.QRCODE_RESULT_CODE) {
            if (data != null) {
                String result = data.getStringExtra(TransParam.QRCODE_RESULT);
                Log.e("result == " + result);
                setSendInfo(result);
            }
        }
    }
}
