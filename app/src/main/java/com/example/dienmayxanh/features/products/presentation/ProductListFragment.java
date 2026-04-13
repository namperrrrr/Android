package com.example.dienmayxanh.features.products.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.products.data.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListFragment extends Fragment {
    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    private ProgressBar progressBar;
    private Product currentProductToDelete = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        RecyclerView rvProducts = view.findViewById(R.id.rvProducts);
        progressBar = view.findViewById(R.id.progressBar);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddProduct);

        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter();
        rvProducts.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        viewModel.getProductListState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.data != null) adapter.setProducts(resource.data);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // Xử lý chuyển sang màn hình Thêm
        fabAdd.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditProductFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // --- ĐOẠN CODE CŨ CỦA BẠN (Sửa sản phẩm) ---
        adapter.setOnItemClickListener(product -> {
            AddEditProductFragment fragment = new AddEditProductFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("PRODUCT_DATA", product);
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // --- BẮT SỰ KIỆN XÓA (Đồng bộ với nhóm trưởng: Xóa 1 chạm) ---
        adapter.setOnDeleteClickListener(product -> {
            // 1. Lưu lại sản phẩm đang bị xóa để tẹo nữa xóa khỏi giao diện
            currentProductToDelete = product;

            // 2. Gọi lệnh xóa trực tiếp lên server luôn, bỏ qua bước hiển thị Dialog hỏi đáp
            viewModel.deleteProduct(product.getId());
        });

        // --- ĐOẠN CODE LẮNG NGHE KẾT QUẢ XÓA ---
        // Thêm LiveData quan sát trạng thái xóa từ ViewModel (nếu bạn có cấu hình phần này)
        viewModel.getDeleteProductState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Đã xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    if (currentProductToDelete != null) {
                        adapter.removeProduct(currentProductToDelete);
                        currentProductToDelete = null; // Xóa xong thì reset lại biến
                    }
//                    viewModel.fetchProducts(); // Tải lại danh sách sau khi xóa
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Lỗi khi xóa: " + resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.fetchProducts();
        return view;
    }
}