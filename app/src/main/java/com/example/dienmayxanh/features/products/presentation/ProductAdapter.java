package com.example.dienmayxanh.features.products.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.products.data.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener; // Thêm listener cho nút Xóa

    // Interface cho nút Sửa (Cây bút)
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    // Interface cho nút Xóa (Thùng rác)
    public interface OnDeleteClickListener {
        void onDeleteClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    // Thêm hàm này để xóa item sản phẩm trực tiếp trên giao diện
    public void removeProduct(Product product) {
        int position = productList.indexOf(product); // Tìm vị trí của sản phẩm trong danh sách
        if (position != -1) {
            productList.remove(position); // Xóa khỏi danh sách tạm
            notifyItemRemoved(position); // Báo cho giao diện chạy hiệu ứng biến mất
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("Giá: " + product.getPrice() + " VNĐ");
        holder.tvStock.setText("Tồn kho: " + product.getStock());

        // Bắt sự kiện ấn vào nút Sửa
        holder.imgEdit.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(product);
        });

        // Bắt sự kiện ấn vào nút Xóa
        holder.imgDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDeleteClick(product);
        });
    }

    @Override
    public int getItemCount() { return productList.size(); }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStock;
        ImageView imgEdit, imgDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvStock = itemView.findViewById(R.id.tvProductStock);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}