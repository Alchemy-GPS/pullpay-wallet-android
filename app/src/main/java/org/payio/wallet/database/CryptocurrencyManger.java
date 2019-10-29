package org.payio.wallet.database;

import org.greenrobot.greendao.query.QueryBuilder;
import org.payio.wallet.R;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class CryptocurrencyManger {
    private static final String TAG = "CryptocurrencyManger";
    private DaoManager mManager;
    private volatile static CryptocurrencyManger manager = new CryptocurrencyManger();

    private CryptocurrencyManger() {
        mManager = DaoManager.getInstance();
    }

    public static CryptocurrencyManger getInstance() {
        if (manager == null) {
            synchronized (CryptocurrencyManger.class) {
                if (manager == null) {
                    manager = new CryptocurrencyManger();
                }
            }
        }
        return manager;
    }

    /**
     * 完成cryptocurrency记录的插入，如果表未创建，先创建Cryptocurrency表
     *
     * @param cryptocurrency
     * @return
     */
    public boolean insertCryptocurrency(Cryptocurrency cryptocurrency) {
        return mManager.getDaoSession().getCryptocurrencyDao().insertOrReplace(cryptocurrency) != -1;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param mList
     * @return
     */
    /*
    public void insertCryptocurrencys(List<CoinTypeResponse.Cryptocurrencis> mList) {
        CryptocurrencyDao mCryptocurrencyDao = mManager.getDaoSession().getCryptocurrencyDao();

        List<Cryptocurrency> mCryptocurrencys = new ArrayList<>();

        for (CoinTypeResponse.Cryptocurrencis cryptocurrencis : mList) {
            Cryptocurrency cryptocurrency = new Cryptocurrency();
            cryptocurrency.setCryptoCurrency(cryptocurrencis.getCryptoCurrency());
            cryptocurrency.setCryptoCurrencyDesc(cryptocurrencis.getCryptoCurrencyDesc());
            cryptocurrency.setCryptoName(cryptocurrencis.getCryptoName());
            cryptocurrency.setCryptoProtocol(cryptocurrencis.getCryptoProtocol());
            cryptocurrency.setListlogoUrl(cryptocurrencis.getListlogoUrl());
            cryptocurrency.setCryptoCoin(cryptocurrencis.getCryptoCoin());

            mCryptocurrencys.add(cryptocurrency);
        }
        mCryptocurrencyDao.insertOrReplaceInTx(mCryptocurrencys,false);
    }
    */

    /**
     * 根据服务器信息返回
     *
     * @param mCryptoInfos
     * @return
     */
    public void updateCryptocurrencies(List<WalletInfoResponse.CryptoInfo> mCryptoInfos, String walletAddress) {

        if (mCryptoInfos == null || mCryptoInfos.size() == 0) return;

        //如果服务器返回的币种减少了,则先清空数据库,再重新添加币种
        if (mCryptoInfos.size() < queryAllCryptocurrency().size()) {
            deleteAll();
        }

        CryptocurrencyDao mCryptocurrencyDao = mManager.getDaoSession().getCryptocurrencyDao();

        List<Cryptocurrency> mCryptocurrencys = new ArrayList<>();

        for (WalletInfoResponse.CryptoInfo mCryptoInfo : mCryptoInfos) {
            Cryptocurrency cryptocurrency = new Cryptocurrency();
            cryptocurrency.setId(Integer.parseInt(mCryptoInfo.getTokenId()));

            cryptocurrency.setLogo(mCryptoInfo.getIconUrl());

            cryptocurrency.setBalance(NumberUtils.formatDouble2(mCryptoInfo.getChannelAmount()));
            cryptocurrency.setNeedAmount(NumberUtils.formatDouble2(mCryptoInfo.getNeedAmount()));
            cryptocurrency.setFreezeAmount(NumberUtils.formatDouble2(mCryptoInfo.getFreezeAmount()));

            cryptocurrency.setName(mCryptoInfo.getName());
            cryptocurrency.setUnit(mCryptoInfo.getName());
            cryptocurrency.setProxy(mCryptoInfo.getProxyAddress());
            cryptocurrency.setContract(mCryptoInfo.getTokenAddress());
            cryptocurrency.setAddress(walletAddress);

            mCryptocurrencys.add(cryptocurrency);
        }
        mCryptocurrencyDao.insertOrReplaceInTx(mCryptocurrencys);
    }

    /**
     * 修改一条数据
     *
     * @param cryptocurrency
     * @return
     */
    public boolean updateCryptocurrency(Cryptocurrency cryptocurrency) {
        boolean flag = false;
        try {
            mManager.getDaoSession().getCryptocurrencyDao().update(cryptocurrency);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * (通过执行SQL语句)根据合约地址更新余额
     *
     * @param balance
     * @param contract
     */
    public void update(String balance, String contract) {
        Cryptocurrency cryptocurrency = queryCryptocurrencyByContract(contract);
        cryptocurrency.setBalance(balance);
        updateCryptocurrency(cryptocurrency);
    }

    /**
     * 删除单条记录
     *
     * @param cryptocurrency
     * @return
     */
    public boolean deleteCryptocurrency(Cryptocurrency cryptocurrency) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().getCryptocurrencyDao().delete(cryptocurrency);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(Cryptocurrency.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据Id查询币种
     *
     * @return
     */
    public Cryptocurrency queryCryptocurrencyById(long id) {
        return mManager.getDaoSession().getCryptocurrencyDao().load(id);
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<Cryptocurrency> queryAllCryptocurrency() {
        return mManager.getDaoSession().getCryptocurrencyDao().loadAll();
    }

    /**
     * 根据钱包地址查询该钱包下的币种列表
     *
     * @param address 钱包地址
     * @return
     */
    public List<Cryptocurrency> queryCryptocurrencyByAddress(String address) {
        //清除缓存
        mManager.getDaoSession().getCryptocurrencyDao().detachAll();
        QueryBuilder<Cryptocurrency> queryBuilder = mManager.getDaoSession().getCryptocurrencyDao().queryBuilder();
        return queryBuilder.where(CryptocurrencyDao.Properties.Address.eq(address)).list();
    }

    /**
     * 根据钱包地址查询该钱包下的币种列表
     *
     * @param contract 合约地址
     * @return
     */
    public Cryptocurrency queryCryptocurrencyByContract(String contract) {
        //清除缓存
        mManager.getDaoSession().getCryptocurrencyDao().detachAll();
        QueryBuilder<Cryptocurrency> queryBuilder = mManager.getDaoSession().getCryptocurrencyDao().queryBuilder();
        return queryBuilder.where(CryptocurrencyDao.Properties.Contract.eq(contract)).unique();
    }
}
