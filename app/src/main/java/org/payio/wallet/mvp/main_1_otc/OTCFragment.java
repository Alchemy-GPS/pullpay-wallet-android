package org.payio.wallet.mvp.main_1_otc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.customview.CtrlScrollViewPager;
import org.payio.customview.TitleTextView;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.model.event.OTCChangedEvent;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.widget.OTCPopupWindow;

public class OTCFragment extends BaseFragment<OTCPresenter> implements OTCView, View.OnClickListener {

    private LinearLayout mMainView;
    private TitleTextView mBuyCurrency;
    private TitleTextView mSellCurrency;
    private RelativeLayout mOTCRadioLayout;
    private TextView mOTCRadioContent;
    private RelativeLayout mOTCTitle;
    private OTCPopupWindow popupWindow;

    private CtrlScrollViewPager mViewPager;
    private OTCFragmentAdapter mAdapter;

    public static OTCFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        OTCFragment fragment = new OTCFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected OTCPresenter bindPresenter() {
        return new OTCPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_otc, container, false);
        initView(mainView);
        EventBusUtil.register(this);
        return mainView;
    }

    private void initView(View mainView) {
        mMainView = mainView.findViewById(R.id.otc_fragment_main);
        mOTCTitle = mainView.findViewById(R.id.otc_fragment_title);
        RelativeLayout mOTCScan = mainView.findViewById(R.id.otc_title_scan_layout);
        RelativeLayout mOTCAdd = mainView.findViewById(R.id.otc_title_plus_layout);
        mOTCRadioLayout = mainView.findViewById(R.id.otc_radio_layout);
        mOTCRadioContent = mainView.findViewById(R.id.otc_radio_content);
        mBuyCurrency = mainView.findViewById(R.id.otc_title_buy);
        mSellCurrency = mainView.findViewById(R.id.otc_title_sell);

        mOTCScan.setOnClickListener(this);
        mOTCAdd.setOnClickListener(this);

        mBuyCurrency.setOnClickListener(this);
        mSellCurrency.setOnClickListener(this);

        mViewPager = mainView.findViewById(R.id.fragment_otc_viewpager);

        mAdapter = new OTCFragmentAdapter(getSelfActivity().getSupportFragmentManager(),getSelfActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setScroll(false);
        mViewPager.addOnPageChangeListener(mAdapter);
    }

    public void onPageSelected() {
        if (mViewPager != null) {
            int current = mViewPager.getCurrentItem();
            mAdapter.onPageSelected(current);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceive(OTCChangedEvent event) {
        Log.e("OTCChangedEvent received");
        if (event != null && event.isChanged()) {
            onPageSelected();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.otc_title_scan_layout) {
            SmartToast.warning(getString(R.string.function_not_developed));
        } else if (v.getId() == R.id.otc_title_plus_layout) {
            if (popupWindow == null) {
                popupWindow = new OTCPopupWindow(getSelfActivity());
            }
            popupWindow.showAtLocation(mMainView, mOTCTitle.getHeight());
        } else if (v.getId() == R.id.otc_title_buy) {
            //买币
            mSellCurrency.setTextColor(getSelfActivity().getResources().getColor(R.color.main_tab_text_ed));
            mBuyCurrency.setTextColor(getSelfActivity().getResources().getColor(R.color.white));

            mViewPager.setCurrentItem(0, false);
        } else if (v.getId() == R.id.otc_title_sell) {
            //卖币
            mBuyCurrency.setTextColor(getSelfActivity().getResources().getColor(R.color.main_tab_text_ed));
            mSellCurrency.setTextColor(getSelfActivity().getResources().getColor(R.color.white));
            mViewPager.setCurrentItem(1, false);
        }
    }
}
