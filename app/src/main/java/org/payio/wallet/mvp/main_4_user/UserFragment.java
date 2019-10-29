package org.payio.wallet.mvp.main_4_user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.customview.CircleImageView;
import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseFragment;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.dialog.EditUsernameDialog;
import org.payio.wallet.model.data.Wallet;
import org.payio.wallet.model.event.AppCheckEvent;
import org.payio.wallet.model.event.LogoutEvent;
import org.payio.wallet.model.event.WebsocketEvent;
import org.payio.wallet.mvp.clip.ClipActivity;
import org.payio.wallet.mvp.login.LoginActivity;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.mvp.setting.AboutActivity;
import org.payio.wallet.network.websocket.WsManager;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.SharedPreference;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static org.payio.wallet.params.TransParam.CLIP_HEAD_IMAGE_PATH;
import static org.payio.wallet.params.TransParam.CLIP_HEAD_IMAGE_REQUESTCODE;
import static org.payio.wallet.params.TransParam.GET_HEAD_IMAGE_REQUESTCODE;

public class UserFragment extends BaseFragment<UserPresenter> implements UserView, View.OnClickListener, EditUsernameDialog.OnConfirmClickListener {

    private TextView mUserId;
    private CircleImageView mHeader;
    private TextView mWalletName;
    private KProgressHUD progressHUD;
    private AlertDialog.Builder mDialog;
    private EditUsernameDialog mUsernameDialog;
    private Context mContext;

    public static UserFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected UserPresenter bindPresenter() {
        return new UserPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getSelfActivity();
        View mainView = inflater.inflate(R.layout.fragment_user, container, false);
        initView(mainView);
        EventBusUtil.register(this);
        return mainView;
    }

    private void initView(View mainView) {
        mUserId = mainView.findViewById(R.id.mine_user_id);
        RelativeLayout mReceiptType = mainView.findViewById(R.id.user_receipt_type);
        RelativeLayout mUpdate = mainView.findViewById(R.id.mine_update_layout);
        RelativeLayout mSetting = mainView.findViewById(R.id.mine_setting_layout);
        RelativeLayout mAbout = mainView.findViewById(R.id.mine_about_layout);
        RelativeLayout mLogout = mainView.findViewById(R.id.user_logout_layout);
        RelativeLayout mEditName = mainView.findViewById(R.id.user_edit_name);
        mWalletName = mainView.findViewById(R.id.user_wallet_name);
        mHeader = mainView.findViewById(R.id.mine_user_head);

        mHeader.setOnClickListener(this);
        mReceiptType.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mEditName.setOnClickListener(this);

        setUserId();
        setWalletName();

        getUserInfo();
    }

    private void getUserInfo() {
        if (MvpPre != null) {
            MvpPre.getUserInfo(getSelfActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.user_logout_layout) {
            if (mDialog == null) {
                mDialog = new AlertDialog.Builder(getSelfActivity())
                        .setTitle(mContext.getString(R.string.tips))
                        .setNegativeButton(mContext.getString(R.string.cancel), null);
            }
            mDialog.setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (MvpPre != null) {
                        MvpPre.logout(getSelfActivity());
                    }
                }
            });
            mDialog.setMessage(mContext.getString(R.string.user_logout_message));
            mDialog.show();
        } else if (v.getId() == R.id.user_receipt_type) {
            //收款方式
            Intent intent = new Intent(getSelfActivity(), OTCWebActivity.class);
            intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet/user/make");
            startActivity(intent);
        } else if (v.getId() == R.id.mine_setting_layout) {
            //设置
            Intent intent = new Intent(getSelfActivity(), OTCWebActivity.class);
            intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet/user/setting");
            startActivity(intent);
        } else if (v.getId() == R.id.mine_update_layout) {
            //更新
            EventBusUtil.post(AppCheckEvent.getInstance());
        } else if (v.getId() == R.id.mine_about_layout) {
            //关于
            Intent intent = new Intent(getSelfActivity(), AboutActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.user_edit_name) {
            //修改昵称
            mUsernameDialog = new EditUsernameDialog(getSelfActivity());

            mUsernameDialog.show();
            mUsernameDialog.setOnConfirmClickListener(this);
        } else if (v.getId() == R.id.mine_user_head) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (AndPermission.hasPermissions(mContext, Permission.READ_EXTERNAL_STORAGE)) {
                                Intent picIntent = new Intent(Intent.ACTION_PICK);
                                picIntent.setType("image/*");
                                startActivityForResult(picIntent, GET_HEAD_IMAGE_REQUESTCODE);
                            } else {
                                SmartToast.warning(mContext.getString(R.string.no_storage_permission));
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            SmartToast.warning(mContext.getString(R.string.no_storage_permission));
                        }
                    })
                    .start();
        }
    }

    public void onPageSelected() {
        getUserInfo();
    }

    @Override
    public void showLoading(String msg) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(getSelfActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setSize(110, 110)
                    .setCancellable(false);
        }

        if (!TextUtils.isEmpty(msg)) {
            progressHUD.setLabelSize(15);
            progressHUD.setLabel(msg);
        }
        progressHUD.show();
    }

    @Override
    public void dismiss() {
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }

    @Override
    public void finish() {
        finish();
    }


    private void setWalletName() {
        Wallet wallet = WalletManager.getInstance().queryWalletByAddress(CommonUtil.getWalletAddress());
        if (wallet != null) {
            mWalletName.setText(wallet.getWalletName());
        }
    }

    @Override
    public void setUserId() {
        mUserId.setText(CommonUtil.getUserName());
    }

    @Override
    public void setUserInfo(String userName, String headImage) {
        mUserId.setText(CommonUtil.getUserName());
        Glide.with(mContext)
                .load(headImage)
                .into(mHeader);
    }

    @Override
    public void updateUserNameError(String error) {
        dismiss();
        mUsernameDialog = null;
        SmartToast.error(error);
    }

    @Override
    public void updateUserNameSuccess(String userName) {
        dismiss();
        mUsernameDialog = null;
        SmartToast.success(mContext.getString(R.string.user_edit_name_success));
    }

    @Override
    public void onEditUserNameConfirmClick(String userName) {

        if (CommonUtil.stringLength(userName) >= 16) {
            if (mUsernameDialog != null) {
                mUsernameDialog.showTips(mContext.getString(R.string.user_edit_name_too_long));
            }
            return;
        }

        if (mUsernameDialog != null) {
            mUsernameDialog.dismiss();
        }
        showLoading("");
        if (MvpPre != null) {
            MvpPre.updateName(mContext, userName);
        }
    }

    @Override
    public void updateHeadImageError(String error) {

    }

    @Override
    public void updateHeadImageSuccess(String headImage) {
        Glide.with(mContext)
                .load(headImage)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        dismiss();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        dismiss();
                        SmartToast.success(mContext.getString(R.string.user_head_image_update_success));
                        return false;
                    }
                })
                .into(mHeader);
    }

    @Override
    public void clearUserData() {
        WalletManager.getInstance().deleteAll();
        CryptocurrencyManger.getInstance().deleteAll();

        //清楚webview的cookie
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getSelfActivity());
        CookieManager.getInstance().removeAllCookie();
        cookieSyncManager.sync();

        SharedPreference.get(mContext).putStringValue(User.USER_COOKIE, "");

        //登录状态改为false
        SharedPreference.get(getSelfActivity()).putBooleanValue(User.USER_IS_LOGIN, false);

        JPushInterface.deleteAlias(mContext, 9999);

        CommonUtil.setUserName("");
        CommonUtil.setUserId("");
        CommonUtil.setOrderStatus("");

        EventBusUtil.post(new WebsocketEvent(false, WsManager.CLIENT_CLOSE_WEBSOCKET));

        dismiss();

        //跳转到登录界面
        Intent intent = new Intent(getSelfActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getSelfActivity().finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceive(LogoutEvent event) {
        if (event != null && event.isLogout()) {
            clearUserData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == GET_HEAD_IMAGE_REQUESTCODE) {
                Intent intent = new Intent();
                intent.setClass(getSelfActivity(), ClipActivity.class);
                intent.setData(data.getData());
                startActivityForResult(intent, CLIP_HEAD_IMAGE_REQUESTCODE);
            } else if (requestCode == CLIP_HEAD_IMAGE_REQUESTCODE) {
                String clipImagePath = data.getStringExtra(CLIP_HEAD_IMAGE_PATH);
                Log.e("CLIP_HEAD_IMAGE_PATH == " + clipImagePath);

                showLoading(getString(R.string.user_head_image_uploading));

                File mHeadImage = new File(clipImagePath);
                MvpPre.uploadHeadImage(getSelfActivity(), mHeadImage);
            }
        }
    }
}
