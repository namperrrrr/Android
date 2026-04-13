package com.example.dienmayxanh.features.suppliers.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.suppliers.data.Supplier;
import java.util.ArrayList;
import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {
    private List<Supplier> suppliers = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public interface OnItemClickListener { void onItemClick(Supplier supplier); }
    public interface OnDeleteClickListener { void onDeleteClick(Supplier supplier); }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }
    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) { this.deleteListener = deleteListener; }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
        notifyDataSetChanged();
    }

    public void removeSupplier(Supplier supplier) {
        int position = suppliers.indexOf(supplier);
        if (position != -1) {
            suppliers.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supplier, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {
        Supplier sup = suppliers.get(position);

        holder.tvName.setText(sup.getName());
        holder.tvPhone.setText("SĐT: " + sup.getPhone());
        holder.tvAddress.setText(sup.getAddress());

        if (sup.isActive()) {
            holder.tvStatus.setText("Đang hợp tác");
            holder.tvStatus.setTextColor(0xFF388E3C);
        } else {
            holder.tvStatus.setText("Ngừng hợp tác");
            holder.tvStatus.setTextColor(0xFFD32F2F);
        }

        holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onItemClick(sup); });
        holder.ivDelete.setOnClickListener(v -> { if (deleteListener != null) deleteListener.onDeleteClick(sup); });
    }

    @Override
    public int getItemCount() { return suppliers != null ? suppliers.size() : 0; }

    class SupplierViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvAddress, tvStatus;
        ImageView ivDelete;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSupplierName);
            tvPhone = itemView.findViewById(R.id.tvSupplierPhone);
            tvAddress = itemView.findViewById(R.id.tvSupplierAddress);
            tvStatus = itemView.findViewById(R.id.tvSupplierStatus);
            ivDelete = itemView.findViewById(R.id.ivDeleteSupplier);
        }
    }
}