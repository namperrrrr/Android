package com.example.dienmayxanh.features.customers.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.customers.data.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList = new ArrayList<>();
    private final OnCustomerClickListener listener;

    public interface OnCustomerClickListener {
        void onEditClick(Customer customer);
        void onDeleteClick(Customer customer);
    }

    public CustomerAdapter(OnCustomerClickListener listener) {
        this.listener = listener;
    }

    public void setCustomers(List<Customer> newCustomers) {
        this.customerList.clear();
        if (newCustomers != null) {
            this.customerList.addAll(newCustomers);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer currentCustomer = customerList.get(position);

        holder.tvCustomerName.setText(currentCustomer.getName());
        holder.tvCustomerPhone.setText(currentCustomer.getPhone());
        holder.tvCustomerAddress.setText(currentCustomer.getAddress());

        holder.imgEditCustomer.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(currentCustomer);
        });

        holder.imgDeleteCustomer.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(currentCustomer);
        });
    }

    @Override
    public int getItemCount() {
        return customerList != null ? customerList.size() : 0;
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvCustomerPhone, tvCustomerAddress;
        ImageView imgEditCustomer, imgDeleteCustomer;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhone = itemView.findViewById(R.id.tvCustomerPhone);
            tvCustomerAddress = itemView.findViewById(R.id.tvCustomerAddress);
            imgEditCustomer = itemView.findViewById(R.id.imgEditCustomer);
            imgDeleteCustomer = itemView.findViewById(R.id.imgDeleteCustomer);
        }
    }
}