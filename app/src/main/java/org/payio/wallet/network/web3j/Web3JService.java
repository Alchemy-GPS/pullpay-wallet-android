package org.payio.wallet.network.web3j;

import org.payio.wallet.Constants;
import org.payio.wallet.OTCApplication;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Web3JService {
    private static final Web3j web3j = Web3jFactory.build(new HttpService(Constants.WEB3JURL));
    private static final BigInteger gasLimit = BigInteger.valueOf(210000L);

    private static TransactionReceipt receipt;

    private static String banlance;

    public static Web3j getInstance() {
        return web3j;
    }

    private Web3JService() {
    }

    public static void loadWallet(final String password, final String walletPath, BaseSubscriber<Credentials> subcriber) {

        Observable.create(new Observable.OnSubscribe<Credentials>() {
            @Override
            public void call(Subscriber<? super Credentials> subscriber) {
                try {
                    Credentials credentials = WalletUtils.loadCredentials(password, walletPath);
                    subscriber.onNext(credentials);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CipherException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subcriber);
    }

    public static void transaction(Credentials mCredentials, String toAddress, double amount, BaseSubscriber<TransactionReceipt> subscriber) {
        try {
            Transfer.sendFunds(web3j, mCredentials, toAddress, BigDecimal.valueOf(amount), Convert.Unit.ETHER)
                    .observable()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } catch (InterruptedException | IOException | TransactionException e) {
            e.printStackTrace();
        }
    }

    public static void getBalance(String address, BaseSubscriber<EthGetBalance> subscriber) {
        web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getGas(BaseSubscriber<EthGasPrice> subscriber) {
        web3j.ethGasPrice()
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getNonce(String walletAddress, BaseSubscriber<EthGetTransactionCount> subscriber) {
        web3j.ethGetTransactionCount(walletAddress, DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void sendRawTransaction(Credentials credentials, RawTransaction rawTransaction, BaseSubscriber<EthSendTransaction> subscriber) {
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String signedTransactionData = Numeric.toHexString(signedMessage);
        web3j.ethSendRawTransaction(signedTransactionData)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void createWallet(final String password, BaseSubscriber<String> subcriber) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                File path = OTCApplication.APPLICATION.getFilesDir();

                try {
                    String fileName = WalletUtils.generateLightNewWalletFile(password, new File(String.valueOf(path)));
                    subscriber.onNext(path + "/" + fileName);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (CipherException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subcriber);
    }

    /*
    public static void getTokenBalance(String walletAddress, String contractAddress, TokenBalanceSubscriber subscriber) {

        Function function = new Function("balanceOf",
                Arrays.<Type>asList(new Address(walletAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));

        subscriber.setFunction(function);

        web3j.ethCall(
                Transaction.createEthCallTransaction(walletAddress, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getTokenDecimals(String walletAddress, String contractAddress, TokenDecimalsSubscriber subscriber) {
        Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));

        subscriber.setFunction(function);

        web3j.ethCall(
                Transaction.createEthCallTransaction(walletAddress, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getTokenName(String walletAddress, String contractAddress, TokenNameSubscriber subscriber) {
        Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));

        subscriber.setFunction(function);

        web3j.ethCall(
                Transaction.createEthCallTransaction(walletAddress, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getTokenSymbol(String walletAddress, String contractAddress, TokenSymbolSubscriber subscriber) {
        Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));

        subscriber.setFunction(function);

        web3j.ethCall(
                Transaction.createEthCallTransaction(walletAddress, contractAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameterName.LATEST)
                .observable()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }*/
}