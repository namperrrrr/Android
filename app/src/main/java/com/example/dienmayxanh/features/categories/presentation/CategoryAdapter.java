package com.example.dienmayxanh.features.categories.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.categories.data.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Category category);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    // Hàm này hỗ trợ xóa mượt trên giao diện mà không cần load lại toàn bộ list
    public void removeCategory(Category category) {
        int position = categories.indexOf(category);
        if (position != -1) {
            categories.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category currentCategory = categories.get(position);

        holder.tvName.setText(currentCategory.getName());
        holder.tvDesc.setText(currentCategory.getDescription());

        // Xử lý màu sắc trạng thái
        if (currentCategory.isActive()) {
            holder.tvStatus.setText("Đang hoạt động");
            holder.tvStatus.setTextColor(0xFF388E3C);
        } else {
            holder.tvStatus.setText("Ngừng hoạt động");
            holder.tvStatus.setTextColor(0xFFD32F2F);
        }

        // THAY ĐỔI: Gán sự kiện click vào nút Hình Cây Bút để Sửa
        holder.ivEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentCategory);
            }
        });

        // Sự kiện click vào Hình Thùng Rác để Xóa
        holder.ivDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(currentCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvStatus;
        ImageView ivDelete, ivEdit; // Thêm ivEdit

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryName);
            tvDesc = itemView.findViewById(R.id.tvCategoryDesc);
            tvStatus = itemView.findViewById(R.id.tvCategoryStatus);
            ivDelete = itemView.findViewById(R.id.ivDeleteCategory);
            ivEdit = itemView.findViewById(R.id.ivEditCategory); // Ánh xạ nút bút chì
        }
    }
}