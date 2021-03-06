package org.payio.wallet.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import org.payio.wallet.model.data.Wallet;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WALLET".
*/
public class WalletDao extends AbstractDao<Wallet, String> {

    public static final String TABLENAME = "WALLET";

    /**
     * Properties of entity Wallet.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property WalletAddress = new Property(0, String.class, "walletAddress", true, "WALLET_ADDRESS");
        public final static Property WalletPath = new Property(1, String.class, "walletPath", false, "WALLET_PATH");
        public final static Property WalletName = new Property(2, String.class, "walletName", false, "WALLET_NAME");
        public final static Property WalletPassword = new Property(3, String.class, "walletPassword", false, "WALLET_PASSWORD");
        public final static Property CreateTime = new Property(4, String.class, "createTime", false, "CREATE_TIME");
        public final static Property PrivateKey = new Property(5, String.class, "privateKey", false, "PRIVATE_KEY");
        public final static Property Keystore = new Property(6, String.class, "keystore", false, "KEYSTORE");
    }


    public WalletDao(DaoConfig config) {
        super(config);
    }
    
    public WalletDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WALLET\" (" + //
                "\"WALLET_ADDRESS\" TEXT PRIMARY KEY NOT NULL ," + // 0: walletAddress
                "\"WALLET_PATH\" TEXT," + // 1: walletPath
                "\"WALLET_NAME\" TEXT," + // 2: walletName
                "\"WALLET_PASSWORD\" TEXT," + // 3: walletPassword
                "\"CREATE_TIME\" TEXT," + // 4: createTime
                "\"PRIVATE_KEY\" TEXT," + // 5: privateKey
                "\"KEYSTORE\" TEXT);"); // 6: keystore
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WALLET\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Wallet entity) {
        stmt.clearBindings();
 
        String walletAddress = entity.getWalletAddress();
        if (walletAddress != null) {
            stmt.bindString(1, walletAddress);
        }
 
        String walletPath = entity.getWalletPath();
        if (walletPath != null) {
            stmt.bindString(2, walletPath);
        }
 
        String walletName = entity.getWalletName();
        if (walletName != null) {
            stmt.bindString(3, walletName);
        }
 
        String walletPassword = entity.getWalletPassword();
        if (walletPassword != null) {
            stmt.bindString(4, walletPassword);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(5, createTime);
        }
 
        String privateKey = entity.getPrivateKey();
        if (privateKey != null) {
            stmt.bindString(6, privateKey);
        }
 
        String keystore = entity.getKeystore();
        if (keystore != null) {
            stmt.bindString(7, keystore);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Wallet entity) {
        stmt.clearBindings();
 
        String walletAddress = entity.getWalletAddress();
        if (walletAddress != null) {
            stmt.bindString(1, walletAddress);
        }
 
        String walletPath = entity.getWalletPath();
        if (walletPath != null) {
            stmt.bindString(2, walletPath);
        }
 
        String walletName = entity.getWalletName();
        if (walletName != null) {
            stmt.bindString(3, walletName);
        }
 
        String walletPassword = entity.getWalletPassword();
        if (walletPassword != null) {
            stmt.bindString(4, walletPassword);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(5, createTime);
        }
 
        String privateKey = entity.getPrivateKey();
        if (privateKey != null) {
            stmt.bindString(6, privateKey);
        }
 
        String keystore = entity.getKeystore();
        if (keystore != null) {
            stmt.bindString(7, keystore);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Wallet readEntity(Cursor cursor, int offset) {
        Wallet entity = new Wallet( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // walletAddress
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // walletPath
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // walletName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // walletPassword
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // createTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // privateKey
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // keystore
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Wallet entity, int offset) {
        entity.setWalletAddress(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setWalletPath(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWalletName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setWalletPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreateTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrivateKey(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setKeystore(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Wallet entity, long rowId) {
        return entity.getWalletAddress();
    }
    
    @Override
    public String getKey(Wallet entity) {
        if(entity != null) {
            return entity.getWalletAddress();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Wallet entity) {
        return entity.getWalletAddress() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
