package org.payio.wallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.payio.wallet.R;

import java.util.Arrays;
import java.util.List;

public class OTCOrderFilterDialog extends Dialog implements OTCOrderFilterAdapter.OnItemClickLitener {
    private Context mContext;
    private List<String> mOTCOrderStatus;
    private List<String> mOTCOrderStatusType;
    private OTCOrderFilterAdapter mAdapter;


    public OTCOrderFilterDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public OTCOrderFilterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private void init() {
        View layoutView = LayoutInflater.from(mContext).inflate(R.layout.dialog_otc_order_filter, null);

        RecyclerView mRecyclerView = layoutView.findViewById(R.id.otc_order_filter_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        String[] OTCOrderStatus = mContext.getResources().getStringArray(R.array.otc_order_status);
        String[] OTCOrderStatusType = mContext.getResources().getStringArray(R.array.otc_order_status_type);

        mOTCOrderStatus = Arrays.asList(OTCOrderStatus);
        mOTCOrderStatusType = Arrays.asList(OTCOrderStatusType);

        mAdapter = new OTCOrderFilterAdapter(mOTCOrderStatus, mOTCOrderStatusType);
        mAdapter.setOnItemClickLitener(this);

        mRecyclerView.setAdapter(mAdapter);

        this.setContentView(layoutView);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mOnItemClickLitener != null) {
            mOnItemClickLitener.onItemClick(view, position);
            setSelection(position);
            this.dismiss();
        }
    }

    public void setSelection(int position) {
        mAdapter.setSelection(position);
    }

    public int getSelection() {
        return mAdapter.getSelection();
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
