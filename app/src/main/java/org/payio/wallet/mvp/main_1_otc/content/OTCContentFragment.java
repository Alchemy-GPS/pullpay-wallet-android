package org.payio.wallet.mvp.main_1_otc.content;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.network.io.OTCTradeResponse;
import org.payio.wallet.utils.Log;

import java.util.List;

import static org.payio.wallet.params.User.TRADE_TYPE;

public class OTCContentFragment extends BaseFragment<OTCContentPresenter> implements OTCContentView, OnRefreshListener, OnLoadMoreListener {

    private boolean isFirstInit = false;//Fragment初始化

    private String title;//标题就是币种
    private String type;//交易类型
    private Context mContext;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int currentPage;
    private TradeAdapter mAdapter;

    public static OTCContentFragment newInstance(String title, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putString(TRADE_TYPE, type);
        OTCContentFragment fragment = new OTCContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected OTCContentPresenter bindPresenter() {
        return new OTCContentPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OTCContentFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("OTCContentFragment onCreateView");
        Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString(BUNDLE_TITLE);
            type = arguments.getString(TRADE_TYPE);
        }
        mContext = getSelfActivity();
        View mainView = inflater.inflate(R.layout.fragment_otc_content, container, false);
        initView(mainView);
        return mainView;
    }

    private void initView(View mainView) {
        mRecyclerView = mainView.findViewById(R.id.otc_content_recyclerview);
        mRefreshLayout = mainView.findViewById(R.id.otc_content_refresh_layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter = new TradeAdapter(mContext);

        mRecyclerView.setAdapter(mAdapter);

        currentPage = 1;
        isFirstInit = true;

        loadFirstPage();
    }

    public void onPageSelected(int position) {
        Log.e("OTCContentFragment Page " + position + " Selected title == " + title + " type == " + type);

        if (MvpPre != null) {
            if (mRecyclerView != null) {
                mRecyclerView.scrollToPosition(0);
            }
            loadFirstPage();
        }
    }

    private void loadFirstPage() {
        if (MvpPre != null) {
            MvpPre.getOTCTrade(mContext, type, title, 1);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRefreshLayout.setNoMoreData(false);
        stopLoading(true);
        loadFirstPage();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mRecyclerView.stopScroll();
        if (MvpPre != null) {
            MvpPre.getMoreOTCTrade(mContext, type, title, currentPage + 1);
        }
    }

    @Override
    public void setOTCTrade(List<OTCTradeResponse.TradeInfo> mTradeInfos) {
        mAdapter.setDatas(mTradeInfos);
        mRefreshLayout.finishRefresh();
        currentPage = 1;
    }

    @Override
    public void addOTCTrade(List<OTCTradeResponse.TradeInfo> mTradeInfos) {
        mRefreshLayout.finishLoadMore(0);
        currentPage = currentPage + 1;
        mAdapter.addDatas(mTradeInfos);
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

}