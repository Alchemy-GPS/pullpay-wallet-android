package org.payio.wallet.mvp.main_2_order;

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
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.params.TransParam;

import java.util.List;

import static org.payio.wallet.Constants.OTC_ORDER_HOST_2;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context mContext;
    private List<OTCOrderResponse.OrderDetail> mList;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<OTCOrderResponse.OrderDetail> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addDatas(List<OTCOrderResponse.OrderDetail> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_otc_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final OTCOrderResponse.OrderDetail mOtcOrder = mList.get(position);

        if (position == 0) {
            holder.itemDec.setVisibility(View.GONE);
        } else {
            holder.itemDec.setVisibility(View.VISIBLE);
        }

        holder.orderNumber.setText(mOtcOrder.getSysFlowNo());
        holder.orderCryptoAmount.setText(mOtcOrder.getOrderQuantity());
        //虚拟币单位
        holder.orderCryptoUnit.setText(mOtcOrder.getCurrencyType());
        holder.orderCurrencyAmount.setText(mOtcOrder.getOrderAmount());
        //1是买单,2是卖单

        if ("1".equals(mOtcOrder.getUserType())) {
            holder.orderType.setText(mContext.getString(R.string.otc_order_buy));
        } else if ("2".equals(mOtcOrder.getUserType())) {
            holder.orderType.setText(mContext.getString(R.string.otc_order_sell));
        }

        //TODO status转换为汉字
        holder.orderStatus.setText(mOtcOrder.getOrderStatusMsg());

        holder.orderTime.setText(mOtcOrder.getCreateTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OTCWebActivity.class);
                intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + OTC_ORDER_HOST_2 + mOtcOrder.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView orderNumber;
        private TextView orderCryptoAmount;
        private TextView orderCryptoUnit;
        private TextView orderCurrencyAmount;
        private TextView orderType;
        private TextView orderStatus;
        private TextView orderTime;
        private View itemDec;

        OrderViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemDec = itemView.findViewById(R.id.otc_order_item_dec);
            orderNumber = itemView.findViewById(R.id.otc_order_number);
            orderCryptoAmount = itemView.findViewById(R.id.otc_order_crypto_amount);
            orderCryptoUnit = itemView.findViewById(R.id.otc_order_crypto_amount_unit);
            orderCurrencyAmount = itemView.findViewById(R.id.otc_order_currency_amount);
            orderType = itemView.findViewById(R.id.otc_order_type);
            orderStatus = itemView.findViewById(R.id.otc_order_status);
            orderTime = itemView.findViewById(R.id.otc_order_time);

        }
    }
}
