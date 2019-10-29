package org.payio.wallet.mvp.transaction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.io.CryptoHistoryResponse;
import org.payio.wallet.network.io.RaidenHistoryResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.NumberUtils;

import java.util.List;

import static org.payio.wallet.Constants.OTC_ORDER_HOST_4;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.HistoryViewHolder> {

    private Context mContext;

    List<CryptoHistoryResponse.Data.CryptoHistory> mList;

    public TransactionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<CryptoHistoryResponse.Data.CryptoHistory> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addDatas(List<CryptoHistoryResponse.Data.CryptoHistory> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_transaction, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder itemHolder, int position) {
        final CryptoHistoryResponse.Data.CryptoHistory mOrder = mList.get(position);

        String amount = mOrder.getAmount();

        if (amount.contains("-")) {
            itemHolder.orderAmount.setTextColor(mContext.getResources().getColor(R.color.pink_theme));
        } else {
            itemHolder.orderAmount.setTextColor(mContext.getResources().getColor(R.color.green_theme));
        }

        itemHolder.orderAmount.setText(mOrder.getAmount());

        itemHolder.orderTime.setText(mOrder.getTime());

        itemHolder.orderStatus.setText(mOrder.getName());

        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder.getType() == 1) {//OTC
                    Intent intent = new Intent(mContext, OTCWebActivity.class);
                    intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + OTC_ORDER_HOST_4 + mOrder.getOrderId());
                    mContext.startActivity(intent);
                } else if (mOrder.getType() == 2) {//交易记录

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView orderAmount;
        private TextView orderTime;
        private TextView orderStatus;

        HistoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            orderAmount = itemView.findViewById(R.id.item_transaction_amount);
            orderTime = itemView.findViewById(R.id.item_transaction_time);
            orderStatus = itemView.findViewById(R.id.item_transaction_status);
        }
    }
}
