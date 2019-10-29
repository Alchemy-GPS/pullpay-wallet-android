package org.payio.wallet.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.payio.wallet.R;

import java.util.List;

public class OTCOrderFilterAdapter extends RecyclerView.Adapter<OTCOrderFilterAdapter.OTCOrderFilterViewHolder> {

    private List<String> mOTCOrderStatus;
    private List<String> mOTCOrderStatusType;

    private int selected = 0;

    public OTCOrderFilterAdapter(List<String> mOTCOrderStatus,List<String> mOTCOrderStatusType) {
        this.mOTCOrderStatus = mOTCOrderStatus;
        this.mOTCOrderStatusType = mOTCOrderStatusType;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    public int getSelection() {
        return selected;
    }

    @NonNull
    @Override
    public OTCOrderFilterAdapter.OTCOrderFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otc_order_filter, parent, false);
        return new OTCOrderFilterAdapter.OTCOrderFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OTCOrderFilterAdapter.OTCOrderFilterViewHolder holder, int position) {

        if (position == mOTCOrderStatus.size() - 1) {
            holder.mGap.setVisibility(View.GONE);
        }

        String statusType = mOTCOrderStatusType.get(position);
        holder.mStatusType.setText(statusType);

        if (selected == position) {
            holder.mSelected.setChecked(true);
        } else {
            holder.mSelected.setChecked(false);
        }

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mOTCOrderStatus.size();
    }

    private OTCOrderFilterAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OTCOrderFilterAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    class OTCOrderFilterViewHolder extends RecyclerView.ViewHolder {
        TextView mStatusType;
        CheckBox mSelected;
        View mGap;

        public OTCOrderFilterViewHolder(View itemView) {
            super(itemView);
            mStatusType = itemView.findViewById(R.id.item_order_filter_tv);
            mSelected = itemView.findViewById(R.id.item_order_filter_cb);
            mGap = itemView.findViewById(R.id.item_order_filter_bottom);
        }
    }
}
