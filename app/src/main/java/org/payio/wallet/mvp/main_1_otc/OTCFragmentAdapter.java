package org.payio.wallet.mvp.main_1_otc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.payio.wallet.R;
import org.payio.wallet.mvp.main_1_otc.trade.OTCBuy;
import org.payio.wallet.mvp.main_1_otc.trade.OTCSell;

import java.util.ArrayList;
import java.util.List;

public class OTCFragmentAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragments = new ArrayList<>();

    public OTCFragmentAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        fragments.add(OTCBuy.newInstance(mContext.getString(R.string.otc_buy)));

        fragments.add(OTCSell.newInstance(mContext.getString(R.string.otc_sell)));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            ((OTCBuy) fragments.get(0)).onPageSelected();
        } else if (position == 1) {
            ((OTCSell) fragments.get(1)).onPageSelected();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}