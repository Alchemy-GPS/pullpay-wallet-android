package org.payio.wallet.mvp.main_3_wallet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.payio.wallet.R;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.mvp.transaction.CryptoTransactionActivity;
import org.payio.wallet.params.TransParam;

import java.util.List;

public class CryptocurrencyAdapter extends RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrencyViewHolder> {
    private Context mContext;
    private List<Cryptocurrency> mList;

    public CryptocurrencyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CryptocurrencyAdapter(Context mContext, List<Cryptocurrency> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setDatas(List<Cryptocurrency> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptocurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_crypto, parent, false);
        return new CryptocurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptocurrencyViewHolder holder, int position) {

        final Cryptocurrency cryptocurrency = mList.get(position);

        final String cryptoName = cryptocurrency.getName();

        holder.cryptoName.setText(cryptoName);

        Glide.with(mContext).load(cryptocurrency.getLogo()).into(holder.cryptoIcon);

        holder.cryptoAmount.setText(cryptocurrency.getBalance());
        holder.cryptoUnit.setText(cryptocurrency.getUnit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CryptoTransactionActivity.class);
                intent.putExtra(TransParam.CRYPTOCURRENCY_ID, cryptocurrency.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class CryptocurrencyViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView cryptoIcon;
        private TextView cryptoName;
        private TextView cryptoUnit;
        private TextView cryptoAmount;

        CryptocurrencyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cryptoIcon = itemView.findViewById(org.payio.wallet.R.id.item_crypto_icon);
            cryptoName = itemView.findViewById(org.payio.wallet.R.id.item_crypto_name);
            cryptoUnit = itemView.findViewById(org.payio.wallet.R.id.item_crypto_unit);
            cryptoAmount = itemView.findViewById(org.payio.wallet.R.id.item_crypto_amount);
        }
    }

}
