package org.payio.wallet.database;


import org.greenrobot.greendao.query.QueryBuilder;
import org.payio.wallet.model.data.Wallet;

import java.util.List;

public class WalletManager {
    private static final String TAG = "WalletManager";
    private DaoManager mManager;
    private volatile static WalletManager manager = new WalletManager();

    private WalletManager() {
        mManager = DaoManager.getInstance();
    }

    public static WalletManager getInstance(){
        if(manager==null){
            synchronized (WalletManager.class){
                if(manager==null){
                    manager=new WalletManager();
                }
            }
        }
        return manager;
    }

    /**
     * 完成wallet记录的插入，如果表未创建，先创建Wallet表
     *
     * @param wallet
     * @return
     */
    public boolean insertWallet(Wallet wallet) {
        return mManager.getDaoSession().getWalletDao().insertOrReplace(wallet) != -1;
    }

    /**
     * 修改一条数据
     *
     * @param wallet
     * @return
     */
    public boolean updateWallet(Wallet wallet) {
        boolean flag = false;
        try {
            mManager.getDaoSession().getWalletDao().update(wallet);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param wallet
     * @return
     */
    public boolean deleteWallet(Wallet wallet) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().getWalletDao().delete(wallet);
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
            mManager.getDaoSession().deleteAll(Wallet.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据主键id查询记录
     *
     * @param walletAddress
     * @return
     */
    public Wallet queryWalletById(String walletAddress) {
        return mManager.getDaoSession().getWalletDao().load(walletAddress);
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<Wallet> queryAllWallet() {
        return mManager.getDaoSession().getWalletDao().loadAll();
    }

    /**
     * 根据钱包地址查询记录
     *
     * @param address
     * @return
     */
    public Wallet queryWalletByAddress(String address) {
        QueryBuilder<Wallet> queryBuilder = mManager.getDaoSession().getWalletDao().queryBuilder();
        return queryBuilder.where(WalletDao.Properties.WalletAddress.eq(address)).unique();
    }

    /**
     * 根据钱包路径查询记录
     *
     * @param walletPath
     * @return
     */
    public Wallet queryWalletByPath(String walletPath) {
        QueryBuilder<Wallet> queryBuilder = mManager.getDaoSession().getWalletDao().queryBuilder();
        return queryBuilder.where(WalletDao.Properties.WalletPath.eq(walletPath)).unique();
    }
}
