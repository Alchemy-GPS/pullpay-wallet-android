package org.payio.wallet.mvp.main_3_wallet;

import android.content.Context;

import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.model.data.Wallet;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.network.web3j.BaseSubscriber;
import org.payio.wallet.network.web3j.Web3JService;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.FileUtils;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.web3j.crypto.Credentials;

import java.io.File;
import java.util.List;

public class WalletPresenter extends BasePresenter<WalletView> {
    private Context mContext;
    private Wallet mWallet;
    private List<Cryptocurrency> mCryptocurrencies;

    public WalletPresenter(WalletView view) {
        super(view);
    }

    public void getWalletInfo(Context mContext) {
        this.mContext = mContext;
        final Wallet wallet = WalletManager.getInstance().queryWalletByAddress(CommonUtil.getWalletAddress());

        if (wallet != null) {
            MvpRef.get().setWalletAddress(wallet.getWalletAddress());
            mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();

            MvpRef.get().setCoinInfo(mCryptocurrencies);

            RetrofitUtil.getInstance().getWalletBalnce(wallet.getWalletAddress(), new Basesubscriber<WalletInfoResponse>() {
                @Override
                public void onResponse(WalletInfoResponse response) {

                    Log.i(GsonUtil.objectToJson(response));

                    if (response != null && response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                        CryptocurrencyManger.getInstance().updateCryptocurrencies(response.getData(), wallet.getWalletAddress());
                    }

                    mCryptocurrencies = CryptocurrencyManger.getInstance().queryAllCryptocurrency();
                    MvpRef.get().setCoinInfo(mCryptocurrencies);
                    stopRefresh();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    stopRefresh();
                }
            });
        } else {
            if (mCryptocurrencies != null) {
                mCryptocurrencies.clear();
            }
            MvpRef.get().setCoinInfo(mCryptocurrencies);
            MvpRef.get().setWalletAddress("");
            stopRefresh();
//            加载默认钱包文件
//            loadDefaultWallet();
        }
    }

    public void loadDefaultWallet() {
        File mWalletFile;

        mWalletFile = FileUtils.createFileInFiles(mContext, "seller.json");
        FileUtils.copyFilesFromAssets(mContext, "seller.json", mWalletFile.getAbsolutePath());

//        mWalletFile = FileUtils.createFileInFiles(mContext, "buyer.json");
//        FileUtils.copyFilesFromAssets(mContext, "buyer.json", mWalletFile.getAbsolutePath());

        String password = "123456";
        mWallet = new Wallet();
        mWallet.setCreateTime(String.valueOf(System.currentTimeMillis()));
        mWallet.setKeystore(FileUtils.getFileContent(mWalletFile));
        mWallet.setWalletName("WALLET");
        mWallet.setWalletPassword(CommonUtil.MD5(password));
        mWallet.setWalletPath(mWalletFile.getAbsolutePath());

        Web3JService.loadWallet(password, mWalletFile.getAbsolutePath(), new BaseSubscriber<Credentials>() {
            @Override
            public void onSuccess(Credentials credentials) {
                //转为16进制私钥


                String privatekey = credentials.getEcKeyPair().getPrivateKey().toString(16);
                String address = credentials.getAddress();
                mWallet.setPrivateKey(privatekey);
                mWallet.setWalletAddress(address);
                CommonUtil.setWalletAddress(address);
                WalletManager.getInstance().insertWallet(mWallet);
                addUSDT(address);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void addUSDT(String address) {
        Cryptocurrency cryptocurrency = new Cryptocurrency();

        cryptocurrency.setId(1111);
        cryptocurrency.setAddress(address);
        cryptocurrency.setBalance("0.00000000");
        cryptocurrency.setContract(Constants.CONTRACT_ADDRESS_RTT);
        cryptocurrency.setLogo("" + R.mipmap.bi_gusd);
        cryptocurrency.setName("USDT");
        cryptocurrency.setProxy(Constants.TUNNEL_PROXY_ADDRESS);
        cryptocurrency.setUnit("USDT");

        CryptocurrencyManger.getInstance().insertCryptocurrency(cryptocurrency);
    }

    public void addUSDC(String address) {
        Cryptocurrency cryptocurrency = new Cryptocurrency();

        cryptocurrency.setId(1112);
        cryptocurrency.setAddress(address);
        cryptocurrency.setBalance("0.00000000");
        cryptocurrency.setContract(Constants.CONTRACT_ADDRESS_ACHPAY);
        cryptocurrency.setLogo("" + R.mipmap.bi_usdc);
        cryptocurrency.setName("USDC");
        cryptocurrency.setProxy(Constants.TUNNEL_PROXY_ADDRESS);
        cryptocurrency.setUnit("USDC");

        CryptocurrencyManger.getInstance().insertCryptocurrency(cryptocurrency);
    }

    public void addToken(Context mContext) {

    }

    private void refreshData(String address) {

    }

    private void stopRefresh() {
        if (MvpRef != null) {
            MvpRef.get().stopRefresh();
        }
    }
}
