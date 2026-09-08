package com.example.dienmayxanh.features.promotions.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.promotions.data.Promotion;
import java.util.ArrayList;
import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private List<Promotion> promotionList = new ArrayList<>();
    private final OnPromotionClickListener listener;

    public interface OnPromotionClickListener {
        void onEditClick(Promotion promotion);
        void onDeleteClick(Promotion promotion);
    }

    public PromotionAdapter(OnPromotionClickListener listener) {
        this.listener = listener;
    }

    public void setPromotions(List<Promotion> newPromotions) {
        this.promotionList.clear();
        if (newPromotions != null) {
            this.promotionList.addAll(newPromotions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promo = promotionList.get(position);

        holder.tvPromoCode.setText(promo.getCode());
        holder.tvPromoStatus.setText(promo.getStatus());
        holder.tvPromoDesc.setText(promo.getDescription());
        holder.tvPromoDiscount.setText("Giảm: " + promo.getDiscount());

        // Bấm vào dấu 3 chấm để hiện Menu Sửa / Xóa
        holder.tvOptions.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.tvOptions);
            popup.getMenu().add("Sửa");
            popup.getMenu().add("Xóa");

            popup.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Sửa")) {
                    if (listener != null) listener.onEditClick(promo);
                } else if (item.getTitle().equals("Xóa")) {
                    if (listener != null) listener.onDeleteClick(promo);
                }
                return true;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() { return promotionList != null ? promotionList.size() : 0; }

    static class PromotionViewHolder extends RecyclerView.ViewHolder {
        TextView tvPromoCode, tvPromoDiscount, tvPromoStatus, tvPromoDesc, tvOptions;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPromoCode = itemView.findViewById(R.id.tvPromoCode);
            tvPromoDiscount = itemView.findViewById(R.id.tvPromoDiscount);
            tvPromoStatus = itemView.findViewById(R.id.tvPromoStatus);
            tvPromoDesc = itemView.findViewById(R.id.tvPromoDesc);
            tvOptions = itemView.findViewById(R.id.tvOptions);
        }
    }
}