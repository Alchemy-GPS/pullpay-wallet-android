package org.payio.wallet.mvp.main_1_otc.content;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.io.OTCTradeResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.Log;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {
    private Context mContext;
    private List<OTCTradeResponse.TradeInfo> mList;

    public TradeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<OTCTradeResponse.TradeInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addDatas(List<OTCTradeResponse.TradeInfo> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TradeAdapter.TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_otc_trade, parent, false);
        return new TradeAdapter.TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeAdapter.TradeViewHolder holder, int position) {
        final OTCTradeResponse.TradeInfo mTradeInfo = mList.get(position);

        if (!TextUtils.isEmpty(mTradeInfo.getHeadImage())) {
            Glide.with(mContext).load(mTradeInfo.getHeadImage()).into(holder.mUserIcon);
        }

        if (TextUtils.equals(mTradeInfo.getUserType(), "S")) {
            holder.mSuperSeller.setVisibility(View.VISIBLE);
        } else {
            holder.mSuperSeller.setVisibility(View.GONE);
        }

        holder.mUserName.setText(mTradeInfo.getName());

        if (TextUtils.isEmpty(mTradeInfo.getTradeRate())) {
            holder.mTradeNumber.setText("0");
        }else {
            holder.mTradeNumber.setText(mTradeInfo.getDoneAmount());
        }

        if (TextUtils.isEmpty(mTradeInfo.getTradeRate())) {
            holder.mTradeRate.setText("0%");
        } else {
            holder.mTradeRate.setText(mTradeInfo.getTradeRate().concat("%"));
        }

        holder.mPrice.setText(mTradeInfo.getOrderPrice());
        if (mTradeInfo.getType().equalsIgnoreCase("IN")) {
            holder.mButton.setText(mContext.getString(R.string.otc_trade_sell));
        } else if (mTradeInfo.getType().equalsIgnoreCase("OUT")) {
            holder.mButton.setText(mContext.getString(R.string.otc_trade_buy));
        }

        holder.mOrderAmountLimit.setText(mTradeInfo.getOrderAmountLimit());
        holder.mOrderAmountMax.setText(mTradeInfo.getOrderAmountMax());

        holder.mOrderQuantityLimit.setText(mTradeInfo.getOrderQuantityLimit());
        holder.mOrderQuantityMax.setText(mTradeInfo.getOrderQuantityMax());
        holder.mOrderCryptoUnit.setText(mTradeInfo.getCurrencyType());

        if (mTradeInfo.getAlipay().equals("1")) {
            holder.mAlipay.setVisibility(View.VISIBLE);
        } else {
            holder.mAlipay.setVisibility(View.GONE);
        }
        if (mTradeInfo.getWechat().equals("1")) {
            holder.mWechat.setVisibility(View.VISIBLE);
        } else {
            holder.mWechat.setVisibility(View.GONE);
        }
        if (mTradeInfo.getCard().equals("1")) {
            holder.mBank.setVisibility(View.VISIBLE);
        } else {
            holder.mBank.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OTCWebActivity.class);
                intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet" + mTradeInfo.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class TradeViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        ImageView mUserIcon;
        ImageView mSuperSeller;
        TextView mUserName;

        TextView mTradeNumber;
        TextView mTradeRate;

        TextView mPrice;

        TextView mButton;

        TextView mOrderQuantityLimit;
        TextView mOrderQuantityMax;
        TextView mOrderCryptoUnit;
        TextView mOrderAmountLimit;
        TextView mOrderAmountMax;

        ImageView mAlipay;
        ImageView mWechat;
        ImageView mBank;

        TradeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mUserIcon = itemView.findViewById(R.id.item_otc_trade_head);
            mSuperSeller = itemView.findViewById(R.id.item_otc_trade_super);
            mUserName = itemView.findViewById(R.id.item_otc_trade_username);

            mTradeNumber = itemView.findViewById(R.id.item_otc_trade_close_number);
            mTradeRate = itemView.findViewById(R.id.item_otc_trade_close_rate);

            mPrice = itemView.findViewById(R.id.item_otc_trade_unit_price);
            mButton = itemView.findViewById(R.id.item_otc_trade_button);

            mOrderAmountLimit = itemView.findViewById(R.id.item_otc_trade_currency_limit);
            mOrderAmountMax = itemView.findViewById(R.id.item_otc_trade_currency_max);

            mOrderQuantityLimit = itemView.findViewById(R.id.item_otc_trade_crypto_limit);
            mOrderQuantityMax = itemView.findViewById(R.id.item_otc_trade_crypto_max);
            mOrderCryptoUnit = itemView.findViewById(R.id.item_otc_trade_crypto_unit);

            mAlipay = itemView.findViewById(R.id.item_otc_trade_receipt_alipay);
            mWechat = itemView.findViewById(R.id.item_otc_trade_receipt_wechat);
            mBank = itemView.findViewById(R.id.item_otc_trade_receipt_bank);
        }
    }
}