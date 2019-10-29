package org.payio.wallet.mvp.transaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseActivity;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.model.event.WalletChangedEvent;
import org.payio.wallet.mvp.transaction.receive.ReceiveCurrencyActivity;
import org.payio.wallet.mvp.transaction.send.SendCurrencyActivity;
import org.payio.wallet.network.io.CryptoHistoryResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.Log;

import java.util.List;

public class CryptoTransactionActivity extends BaseActivity<TransactionPresenter> implements TransactionView, View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private TransactionAdapter mTransactionAdapter;
    private String address;
    private String token;
    private long currencyId;
    private String lastOrderTime;
    private TextView mEnabledAmount;
    private TextView mSubEnabledAmount;
    private TextView mFreezedAmount;
    private TextView mSubFreezedAmount;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarLayout.LayoutParams mAppBarParams;
    private Handler mHandler;

    private int mScrollY = 0;
    private RelativeLayout mSubAmountLayout;
    private RelativeLayout mTitleBarLayout;
    private LinearLayout mAmountDesLayout;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_transaction);

        RelativeLayout mTitleBack = findViewById(R.id.currency_transaction_title_back);
        TextView mSend = findViewById(R.id.currency_transaction_send);
        TextView mReceive = findViewById(R.id.currency_transaction_receive);

        currencyId = getIntent().getLongExtra(TransParam.CRYPTOCURRENCY_ID, 0);

        mRecyclerView = findViewById(R.id.currency_transaction_recyclerview);
        mRefreshLayout = findViewById(R.id.currency_transaction_refresh_layout);
        mEnabledAmount = findViewById(R.id.transaction_enabled_amount);
        mSubEnabledAmount = findViewById(R.id.transaction_sub_enabled_amount);

        mFreezedAmount = findViewById(R.id.transaction_freezed_amount);
        mSubFreezedAmount = findViewById(R.id.transaction_sub_freezed_amount);

        mScrollView = findViewById(R.id.scrollview);

        mSubAmountLayout = findViewById(R.id.transaction_sub_amount_layout);
        mTitleBarLayout = findViewById(R.id.currency_transaction_title_layout);
        mAmountDesLayout = findViewById(R.id.transaction_amount_des_layout);

        TextView mEnabledAmountUnit = findViewById(R.id.transaction_enabled_unit);
        TextView mFreezedAmountUnit = findViewById(R.id.transaction_freezed_unit);
        TextView mSubEnabledAmountUnit = findViewById(R.id.transaction_sub_enabled_unit);
        TextView mSubFreezedAmountUnit = findViewById(R.id.transaction_sub_freezed_unit);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        EventBusUtil.register(this);

        Cryptocurrency cryptocurrency = CryptocurrencyManger.getInstance().queryCryptocurrencyById(currencyId);

        if (cryptocurrency != null) {
            address = cryptocurrency.getAddress();
            token = cryptocurrency.getContract();

            setEnabledAmount(cryptocurrency.getNeedAmount());
            setFreezedAmount(cryptocurrency.getFreezeAmount());

            mEnabledAmountUnit.setText(cryptocurrency.getUnit());
            mFreezedAmountUnit.setText(cryptocurrency.getUnit());
            mSubEnabledAmountUnit.setText(cryptocurrency.getUnit());
            mSubFreezedAmountUnit.setText(cryptocurrency.getUnit());

            mTransactionAdapter = new TransactionAdapter(this);
            mRecyclerView.setAdapter(mTransactionAdapter);
        }

        mSend.setOnClickListener(this);
        mReceive.setOnClickListener(this);
        mTitleBack.setOnClickListener(this);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mRefreshLayout.autoRefresh();

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(50);

            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == 0) {
                    mSubAmountLayout.setAlpha(0);
                    mAmountDesLayout.setAlpha(1F);
                    mTitleBarLayout.setBackgroundColor(Color.TRANSPARENT);
                }

                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;

                    float alpha = 1f * mScrollY / h;

                    mSubAmountLayout.setAlpha(alpha);

                    mAmountDesLayout.setAlpha(1F - alpha);

                    mTitleBarLayout.setBackgroundColor(Color.argb((int) (alpha * 255), 87, 120, 214));
                }
                lastScrollY = scrollY;
            }
        });
    }

    @Override
    protected TransactionPresenter bindPresenter() {
        return new TransactionPresenter(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.currency_transaction_send) {
            Intent intent = new Intent(this, SendCurrencyActivity.class);
            intent.putExtra(TransParam.CRYPTOCURRENCY_ID, currencyId);
            startActivity(intent);
        } else if (v.getId() == R.id.currency_transaction_title_back) {
            this.finish();
        } else if (v.getId() == R.id.currency_transaction_receive) {
            if (!TextUtils.isEmpty(address)) {
                Intent intent = new Intent(this, ReceiveCurrencyActivity.class);
                intent.putExtra(TransParam.WALLET_ADDRESS, address);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (MvpPre != null && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(address)) {
            MvpPre.getTransaction(this, token, address);
        }
        mRefreshLayout.setNoMoreData(false);
        stopLoading(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mRecyclerView.stopScroll();
        if (MvpPre != null && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(address)) {
            MvpPre.getMoreTransaction(this, token, address, lastOrderTime);
        }
    }

    @Override
    public void setTransactionHistory(List<CryptoHistoryResponse.Data.CryptoHistory> mList) {
        mRefreshLayout.finishRefresh();
        if (mList != null && mList.size() != 0) {
            lastOrderTime = mList.get(mList.size() - 1).getTime();
        }

        mTransactionAdapter.setDatas(mList);
    }

    @Override
    public void addTransactionHistory(final List<CryptoHistoryResponse.Data.CryptoHistory> mList) {
        mRefreshLayout.finishLoadMore(0);
        if (mList != null && mList.size() != 0) {
            lastOrderTime = mList.get(mList.size() - 1).getTime();
        }
        mTransactionAdapter.addDatas(mList);
    }

    @Override
    public void stopLoading(boolean haveMore) {
        if (!haveMore) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void stopRefresh() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void setEnabledAmount(String enabledAmount) {
        mEnabledAmount.setText(enabledAmount);
        mSubEnabledAmount.setText(enabledAmount);
    }

    @Override
    public void setFreezedAmount(String freezedAmount) {
        mFreezedAmount.setText(freezedAmount);
        mSubFreezedAmount.setText(freezedAmount);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onWalletChanged(WalletChangedEvent event) {
        if (event != null && event.isChanged()) {
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(address) && MvpPre != null) {
                MvpPre.getTransaction(this, token, address);
            }
        }
    }
}
