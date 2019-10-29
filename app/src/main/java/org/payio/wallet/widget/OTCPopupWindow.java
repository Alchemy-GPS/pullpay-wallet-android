package org.payio.wallet.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.Log;

import static org.payio.wallet.Constants.OTC_PUTUP_ORDER;
import static org.payio.wallet.Constants.OTC_USER_ORDER;

public class OTCPopupWindow implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private Context mContext;
    private int statusBarHeight;

    public OTCPopupWindow(Context mContext) {
        this.mContext = mContext;

        statusBarHeight = getStatusBarHeight(mContext);

        if (mPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.otc_popup_window, null);

            TextView mCreateOrder = view.findViewById(R.id.otc_popup_create_trade);
            TextView mMyOrder = view.findViewById(R.id.otc_popup_my_trade);

            mCreateOrder.setOnClickListener(this);
            mMyOrder.setOnClickListener(this);

            mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        }
    }

    public void showAtLocation(View mainView, int yOffset) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(mainView, Gravity.RIGHT | Gravity.TOP, 30, statusBarHeight + yOffset);
        }
    }

    public int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.otc_popup_create_trade) {
            Log.i("点击挂单");
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }

            Intent intent = new Intent(mContext, OTCWebActivity.class);
            intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + OTC_PUTUP_ORDER);
            mContext.startActivity(intent);
        } else if (v.getId() == R.id.otc_popup_my_trade) {
            Log.i("我的挂单");
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }

            Intent intent = new Intent(mContext, OTCWebActivity.class);
            intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + OTC_USER_ORDER);
            mContext.startActivity(intent);
        }
    }
}
