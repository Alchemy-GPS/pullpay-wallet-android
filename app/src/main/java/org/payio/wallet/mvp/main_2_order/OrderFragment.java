package org.payio.wallet.mvp.main_2_order;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.customview.EditTextHintIcon;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.model.event.OrderChangedEvent;
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.widget.OTCOrderFilterDialog;

import java.util.List;

public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderView, OnRefreshListener, OnLoadMoreListener, EditTextHintIcon.OnFocusListener, View.OnClickListener, TextView.OnEditorActionListener, DialogInterface.OnDismissListener, OTCOrderFilterDialog.OnItemClickLitener {
    private Activity mContext;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int currentPage;
    private OrderAdapter mAdapter;
    private TextView mSearchCancel;
    private EditTextHintIcon mSearchEdit;
    private RelativeLayout mOrderTypeLayout;
    private TextView mOrderType;
    private LinearLayout mSearchLayout;
    private OTCOrderFilterDialog mFilterDialog;
    private String[] mOTCOrderStatusType;
    private String[] mOTCOrderStatus;
    private String mOrderStatus = "-1";
    private int mOrderStatusPosition;

    public static OrderFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected OrderPresenter bindPresenter() {
        return new OrderPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_orders, container, false);
        mContext = getSelfActivity();
        initView(mainView);
        EventBusUtil.register(this);
        return mainView;
    }

    private void initView(View mainView) {
        mSearchLayout = mainView.findViewById(R.id.order_search_full_layout);
        mOrderTypeLayout = mainView.findViewById(R.id.order_status_type_layout);
        mOrderType = mainView.findViewById(R.id.order_status_type);

        mSearchCancel = mainView.findViewById(R.id.order_search_cancel);
        mSearchEdit = mainView.findViewById(R.id.order_search_edit);


        mRecyclerView = mainView.findViewById(R.id.otc_order_recyclerview);
        mRefreshLayout = mainView.findViewById(R.id.otc_order_refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mSearchEdit.setOnFocusListener(this);
        mSearchCancel.setOnClickListener(this);
        mOrderTypeLayout.setOnClickListener(this);
        mSearchEdit.setOnEditorActionListener(this);
        mAdapter = new OrderAdapter(mContext);

        mRecyclerView.setAdapter(mAdapter);

        currentPage = 1;
        mOTCOrderStatusType = mContext.getResources().getStringArray(R.array.otc_order_status_type);
        mOTCOrderStatus = mContext.getResources().getStringArray(R.array.otc_order_status);

        loadFirstPage();
    }

    private void loadFirstPage() {
        if (MvpPre != null) {
            MvpPre.getOTCOrder(getSelfActivity(), mOrderStatus, 1);
        }
    }

    public void onPageSelected() {
        loadFirstPage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceive(OrderChangedEvent event) {
        Log.e("OrderChangedEvent received");
        if (event != null && event.isChanged()) {
            loadFirstPage();
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
            MvpPre.getMoreOTCOrder(mContext, mOrderStatus, currentPage + 1);
        }
    }

    @Override
    public void setOTCOrders(List<OTCOrderResponse.OrderDetail> mDetails) {
        mAdapter.setDatas(mDetails);
        mRefreshLayout.finishRefresh();
        currentPage = 1;
    }

    @Override
    public void addOTCOrders(List<OTCOrderResponse.OrderDetail> mDetails) {
        mRefreshLayout.finishLoadMore(0);
        currentPage = currentPage + 1;
        mAdapter.addDatas(mDetails);
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mSearchCancel.setVisibility(View.VISIBLE);
        } else {
            mSearchCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.order_status_type_layout) {
            //弹出选择状态的弹窗
            if (mFilterDialog == null) {
                mFilterDialog = new OTCOrderFilterDialog(mContext);
            }
            mFilterDialog.setOnItemClickLitener(this);
            mFilterDialog.setSelection(mOrderStatusPosition);
            mFilterDialog.show();
            mFilterDialog.setOnDismissListener(this);

        } else if (v.getId() == R.id.order_search_cancel) {
            mSearchEdit.setText("");
            mSearchEdit.clearFocus();
            //隐藏键盘
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
            }
            // TODO: 2018/12/17 搜索功能
            Log.e("搜索内容 == " + mSearchEdit.getText().toString().trim());
            return true;
        }
        return false;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mFilterDialog = null;
    }

    /**
     * 弹出的dialog点击条目
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Log.e("positon == " + position);
        mOrderType.setText(mOTCOrderStatusType[position]);
        mOrderStatus = mOTCOrderStatus[position];
        mOrderStatusPosition = position;
        loadFirstPage();
    }
}
