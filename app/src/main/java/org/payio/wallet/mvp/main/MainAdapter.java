package org.payio.wallet.mvp.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.payio.wallet.R;
import org.payio.wallet.mvp.main_1_otc.trade.OTCBuy;
import org.payio.wallet.mvp.main_2_order.OrderFragment;
import org.payio.wallet.mvp.main_1_otc.OTCFragment;
import org.payio.wallet.mvp.main_4_user.UserFragment;
import org.payio.wallet.mvp.main_3_wallet.WalletFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragments = new ArrayList<>();

    public MainAdapter(FragmentManager fm, Context mContext) {
        super(fm);
//        fragments.add(OTCFragment.newInstance(mContext.getString(R.string.main_otc)));
//        fragments.add(OrderFragment.newInstance(mContext.getString(R.string.main_order)));
        fragments.add(WalletFragment.newInstance(mContext.getString(R.string.main_wallet)));
        fragments.add(UserFragment.newInstance(mContext.getString(R.string.main_user)));
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
        /*
        if (position == 0) {
            ((OTCFragment) fragments.get(0)).onPageSelected();
        } else if (position == 1) {
            ((OrderFragment) fragments.get(1)).onPageSelected();
        }else if (position == 2) {
            ((WalletFragment) fragments.get(2)).onPageSelected();
        }else if (position == 3) {
            ((UserFragment) fragments.get(3)).onPageSelected();
        }
        */


        if (position == 0) {
            ((WalletFragment) fragments.get(0)).onPageSelected();
        }else if (position == 1) {
            ((UserFragment) fragments.get(1)).onPageSelected();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
