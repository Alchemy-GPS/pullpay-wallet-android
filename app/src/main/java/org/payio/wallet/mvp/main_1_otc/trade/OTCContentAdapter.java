package org.payio.wallet.mvp.main_1_otc.trade;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.mvp.main_1_otc.content.OTCContentFragment;
import org.payio.wallet.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class OTCContentAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener{
    private List<Cryptocurrency> mCryptocurrencies;
    private List<Fragment> fragments = new ArrayList<>();
    private String tradeType;
    FragmentManager fragmentManager;

    public OTCContentAdapter(FragmentManager fm,String tradeType) {
        super(fm);
        this.tradeType = tradeType;
        this.fragmentManager = fm;

        mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();

        for (Cryptocurrency cryptocurrency : mCryptocurrencies) {
            fragments.add(OTCContentFragment.newInstance(cryptocurrency.getName(), tradeType));
        }
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
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCryptocurrencies.get(position).getName().toUpperCase();
    }

    @Override
    public void onPageSelected(int position) {
        ((OTCContentFragment) fragments.get(position)).onPageSelected(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}