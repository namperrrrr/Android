package com.example.dienmayxanh.features.roles.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienmayxanh.databinding.ItemRoleBinding;
import com.example.dienmayxanh.features.roles.data.Role;

import java.util.ArrayList;
import java.util.List;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.RolesViewHolder> {
    private List<Role> roleList = new ArrayList<>();
    private final OnRoleClickListener listener;
    public interface OnRoleClickListener {
        void onEditClick(Role role);
        void onDeleteClick(Role role);
    }
    public RolesAdapter(OnRoleClickListener listener) {
        this.listener = listener;
    }
    public void setRoles(List<Role> newRoles) {
        this.roleList.clear();
        if (newRoles != null) {
            this.roleList.addAll(newRoles);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RolesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoleBinding binding = ItemRoleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RolesViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull RolesViewHolder holder, int position) {
        Role currentRole = roleList.get(position);

        // Gọi View trực tiếp từ biến binding (Cực nhanh và không bao giờ sợ null)
        holder.binding.tvRoleName.setText(currentRole.getName());

        holder.binding.imgEditRole.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(currentRole);
        });

        holder.binding.imgDeleteRole.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(currentRole);
        });
    }
    @Override
    public int getItemCount() {
        return roleList != null ? roleList.size() : 0;
    }
    static class RolesViewHolder extends RecyclerView.ViewHolder {
        ItemRoleBinding binding;
        public RolesViewHolder(@NonNull ItemRoleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
