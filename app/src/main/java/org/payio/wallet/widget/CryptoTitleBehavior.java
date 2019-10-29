package org.payio.wallet.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.payio.wallet.R;

public class CryptoTitleBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public CryptoTitleBehavior() {
    }

    public CryptoTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof SmartRefreshLayout;
    }

    //被观察的view发生改变时回调
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        RelativeLayout mTitleBar = parent.findViewById(R.id.currency_transaction_title_layout);

        if (deltaY == 0) {
            deltaY = dependency.getY() - mTitleBar.getHeight() - child.getHeight();
        }

        float dy = dependency.getY() - mTitleBar.getHeight()- child.getHeight();
        dy = dy < 0 ? 0 : dy;

//        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(mTitleBar.getHeight());

        float alpha = 1 - (dy / deltaY);

        mTitleBar.setBackgroundColor(Color.argb((int) (alpha * 255), 87, 120, 214));

        child.setAlpha(alpha);

        return true;
    }
}
