package com.example.dienmayxanh.features.products.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.products.data.Product;

public class AddEditProductFragment extends Fragment {
    private ProductViewModel viewModel;
    private Product currentProduct;

    private EditText edtName, edtPrice, edtStock, edtCategoryId, edtSupplierId;
    private Switch swActive;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        edtName = view.findViewById(R.id.edtProductName);
        edtPrice = view.findViewById(R.id.edtProductPrice);
        edtStock = view.findViewById(R.id.edtProductStock);
        edtCategoryId = view.findViewById(R.id.edtCategoryId);
        edtSupplierId = view.findViewById(R.id.edtSupplierId);
        swActive = view.findViewById(R.id.swActive);
        btnSave = view.findViewById(R.id.btnSaveProduct);
        Button btnBack = view.findViewById(R.id.btnBack);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Bắt sự kiện click
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Lệnh này đóng Fragment hiện tại và đưa bạn trở về màn hình trước đó (Danh sách)
                getParentFragmentManager().popBackStack();
            });
        }
        // Nhận dữ liệu nếu là chức năng Sửa
        if (getArguments() != null && getArguments().containsKey("PRODUCT_DATA")) {
            currentProduct = (Product) getArguments().getSerializable("PRODUCT_DATA");
            if (currentProduct != null) {
                edtName.setText(currentProduct.getName());
                edtPrice.setText(String.valueOf(currentProduct.getPrice()));
                edtStock.setText(String.valueOf(currentProduct.getStock()));
                edtCategoryId.setText(currentProduct.getCategoryId());
                edtSupplierId.setText(currentProduct.getSupplierId());
                swActive.setChecked(currentProduct.isActive());
            }
        }

        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        btnSave.setEnabled(false);
                        btnSave.setText("Đang lưu...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), resource.data, Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        getParentFragmentManager().popBackStack(); // Quay lại màn hình trước
                        break;
                    case ERROR:
                        btnSave.setEnabled(true);
                        btnSave.setText("Lưu Sản Phẩm");
                        Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnSave.setOnClickListener(v -> saveProduct());

        return view;
    }

    private void saveProduct() {
        String name = edtName.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        String stockStr = edtStock.getText().toString().trim();
        String categoryId = edtCategoryId.getText().toString().trim();
        String supplierId = edtSupplierId.getText().toString().trim();
        boolean isActive = swActive.isChecked();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ tên, giá và số lượng", Toast.LENGTH_SHORT).show();
            return;
        }

        long price = Long.parseLong(priceStr);
        int stock = Integer.parseInt(stockStr);

        if (currentProduct == null) {
            Product newProduct = new Product(name, price, stock, "", categoryId, supplierId, isActive);
            viewModel.addProduct(newProduct);
        } else {
            currentProduct.setName(name);
            currentProduct.setPrice(price);
            currentProduct.setStock(stock);
            currentProduct.setCategoryId(categoryId);
            currentProduct.setSupplierId(supplierId);
            currentProduct.setActive(isActive);
            viewModel.updateProduct(currentProduct);
        }
    }
}