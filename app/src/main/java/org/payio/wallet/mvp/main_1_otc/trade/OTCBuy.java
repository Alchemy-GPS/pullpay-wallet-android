package org.payio.wallet.mvp.main_1_otc.trade;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.payio.customview.SlidingTabLayout;
import org.payio.wallet.R;
import org.payio.wallet.base.presenter.IPresenter;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.utils.Log;

import java.util.List;

public class OTCBuy extends BaseFragment {

    //初始化一些参数，完成懒加载和数据只加载一次的效果
    private boolean isInit = false;//Fragment初始化

    private boolean isLoadOver = false;//Fragment没有加载过

    private boolean isVisible = false;//Fragment可见

    private View mainView;

    private Context mContext;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private OTCContentAdapter mAdapter;
    private List<Cryptocurrency> mCryptocurrencies;

    public static OTCBuy newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        OTCBuy fragment = new OTCBuy();
        fragment.setArguments(bundle);
        return fragment;
    }

    //界面可见时再加载数据(该方法在onCreate()方法之前执行。)
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisible = isVisibleToUser;
        setData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_otc_buy, container, false);
            isInit = true;
            initView(mainView);
            setData();
        }
        return mainView;
    }

    private void initView(View mainView) {

        mContext = getSelfActivity();

        mTabLayout = mainView.findViewById(R.id.fragment_otc_buy_customlayout);

        mViewPager = mainView.findViewById(R.id.fragment_otc_buy_viewpager);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setData() {
        if (isInit && !isLoadOver && isVisible) {
            isLoadOver = true;

            mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();

            if (mCryptocurrencies == null || mCryptocurrencies.size() == 0) {
                mViewPager.setOffscreenPageLimit(0);
                return;
            }

            mViewPager.setOffscreenPageLimit(mCryptocurrencies.size());
            mAdapter = new OTCContentAdapter(getSelfActivity().getSupportFragmentManager(), "OUT");
            mViewPager.setAdapter(mAdapter);
        }
    }

    public void onPageSelected() {
        Log.e("OTCBuy PageSelected");
        int current = mViewPager.getCurrentItem();
        if (mAdapter != null) {
            mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();

            if (mCryptocurrencies.size() != mAdapter.getCount()) {
                //币种发生了变化
                mAdapter = null;
                mViewPager.removeAllViews();

                mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();
                if (mCryptocurrencies == null || mCryptocurrencies.size() == 0) {
                    mViewPager.setOffscreenPageLimit(0);
                    return;
                }

                mViewPager.setOffscreenPageLimit(mCryptocurrencies.size());
                mAdapter = new OTCContentAdapter(getSelfActivity().getSupportFragmentManager(), "OUT");
                mViewPager.setAdapter(mAdapter);
            } else {
                mAdapter.onPageSelected(current);
            }
        }
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }
}
