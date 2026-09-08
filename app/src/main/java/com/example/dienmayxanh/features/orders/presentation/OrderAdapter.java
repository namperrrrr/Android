package com.example.dienmayxanh.features.orders.presentation;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.orders.data.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList = new ArrayList<>();
    private final OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onEditClick(Order order);
        void onDeleteClick(Order order);
    }

    public OrderAdapter(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void setOrders(List<Order> newOrders) {
        this.orderList.clear();
        if (newOrders != null) {
            this.orderList.addAll(newOrders);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order currentOrder = orderList.get(position);

        holder.tvCustomerName.setText(currentOrder.getCustomerName());
        holder.tvProductName.setText(currentOrder.getItems());
        holder.tvOrderTotal.setText(currentOrder.getTotal() + " đ");
        holder.tvOrderStatus.setText(currentOrder.getStatus());

        String fullId = currentOrder.getId();
        String shortId = "N/A";
        if (fullId != null) {
            shortId = (fullId.length() >= 4) ? fullId.substring(fullId.length() - 4).toUpperCase() : fullId.toUpperCase();
        }
        holder.tvOrderId.setText("MDH: #" + shortId);

        String voucher = currentOrder.getVoucher();
        if (voucher == null || voucher.isEmpty() || voucher.equalsIgnoreCase("Không dùng Voucher")) {
            holder.tvOrderVoucher.setText("Không có mã");
            holder.tvOrderVoucher.setTextColor(Color.parseColor("#9E9E9E"));
        } else {
            holder.tvOrderVoucher.setText("Voucher: " + voucher);
            holder.tvOrderVoucher.setTextColor(Color.parseColor("#388E3C"));
        }

        holder.btnEditOrder.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(currentOrder);
        });

        holder.btnDeleteOrder.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(currentOrder);
        });
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderStatus, tvCustomerName, tvProductName, tvOrderTotal, tvOrderVoucher;
        Button btnEditOrder, btnDeleteOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderVoucher = itemView.findViewById(R.id.tvOrderVoucher);
            btnEditOrder = itemView.findViewById(R.id.btnEditOrder);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }
}