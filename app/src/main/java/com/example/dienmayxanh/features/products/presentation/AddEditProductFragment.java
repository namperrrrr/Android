package com.example.dienmayxanh.features.products.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.categories.data.Category;
import com.example.dienmayxanh.features.products.data.Product;
import com.example.dienmayxanh.features.suppliers.data.Supplier;

import java.util.ArrayList;
import java.util.List;

public class AddEditProductFragment extends Fragment {
    private ProductViewModel viewModel;
    private Product currentProduct;

    private EditText edtName, edtPrice, edtStock, edtImage;
    private Spinner spinnerCategory, spinnerSupplier;
    private Switch swActive;
    private Button btnSave;
    private ImageView imgPreview;
    // Dữ liệu cho Spinners
    private List<Category> categoryList = new ArrayList<>();
    private List<Supplier> supplierList = new ArrayList<>();
    private ArrayAdapter<Category> categoryAdapter;
    private ArrayAdapter<Supplier> supplierAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        edtName = view.findViewById(R.id.edtProductName);
        edtPrice = view.findViewById(R.id.edtProductPrice);
        edtStock = view.findViewById(R.id.edtProductStock);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerSupplier = view.findViewById(R.id.spinnerSupplier);
        swActive = view.findViewById(R.id.swActive);
        btnSave = view.findViewById(R.id.btnSaveProduct);

        Button btnBack = view.findViewById(R.id.btnBack);

        edtImage = view.findViewById(R.id.edtProductImage);
        imgPreview = view.findViewById(R.id.imgProductPreview);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        setupSpinners();
        observeDropdownData();

        // Gọi lệnh lấy data cho Spinner
        viewModel.fetchCategoriesForDropdown();
        viewModel.fetchSuppliersForDropdown();

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        if (getArguments() != null && getArguments().containsKey("PRODUCT_DATA")) {
            currentProduct = (Product) getArguments().getSerializable("PRODUCT_DATA");
            if (currentProduct != null) {
                edtName.setText(currentProduct.getName());
                edtPrice.setText(String.valueOf(currentProduct.getPrice()));
                edtStock.setText(String.valueOf(currentProduct.getStock()));
                swActive.setChecked(currentProduct.isActive());

                // Hiển thị link và ảnh cũ nếu có
                edtImage.setText(currentProduct.getImage());
                com.github.bumptech.glide.Glide.with(this)
                        .load(currentProduct.getImage())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(imgPreview);
            }
        }
        edtImage.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Khi người dùng thoát khỏi ô nhập liệu
                String url = edtImage.getText().toString().trim();
                if (!url.isEmpty()) {
                    com.github.bumptech.glide.Glide.with(this)
                            .load(url)
                            .error(android.R.drawable.stat_notify_error)
                            .into(imgPreview);
                }
            }
        });

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
                        getParentFragmentManager().popBackStack();
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

    private void setupSpinners() {
        // Cài đặt adapter giao diện mặc định của Android cho Spinner
        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        supplierAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, supplierList);
        supplierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSupplier.setAdapter(supplierAdapter);
    }

    private void observeDropdownData() {
        // Lắng nghe dữ liệu Danh mục
        viewModel.getCategoriesState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == Resource.Status.SUCCESS && resource.data != null) {
                categoryList.clear();
                categoryList.addAll(resource.data);
                categoryAdapter.notifyDataSetChanged();

                // Nếu đang SỬA sản phẩm, tự động chọn đúng Danh mục cũ
                if (currentProduct != null && currentProduct.getCategoryId() != null) {
                    for (int i = 0; i < categoryList.size(); i++) {
                        if (categoryList.get(i).getId().equals(currentProduct.getCategoryId())) {
                            spinnerCategory.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        // Lắng nghe dữ liệu Nhà cung cấp
        viewModel.getSuppliersState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == Resource.Status.SUCCESS && resource.data != null) {
                supplierList.clear();
                supplierList.addAll(resource.data);
                supplierAdapter.notifyDataSetChanged();

                // Tự động chọn đúng Nhà cung cấp khi Đang sửa
                if (currentProduct != null && currentProduct.getSupplierId() != null) {
                    for (int i = 0; i < supplierList.size(); i++) {
                        if (supplierList.get(i).getId().equals(currentProduct.getSupplierId())) {
                            spinnerSupplier.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void saveProduct() {
        String name = edtName.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        String stockStr = edtStock.getText().toString().trim();
        String imageUrl = edtImage.getText().toString().trim();
        boolean isActive = swActive.isChecked();

        // Ép kiểu ép trực tiếp Object từ Spinner về Class tương ứng để lấy ID
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        Supplier selectedSupplier = (Supplier) spinnerSupplier.getSelectedItem();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ tên, giá và số lượng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategory == null || selectedSupplier == null) {
            Toast.makeText(getContext(), "Vui lòng tạo Danh mục và Nhà cung cấp trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        String categoryId = selectedCategory.getId();
        String supplierId = selectedSupplier.getId();

        long price = Long.parseLong(priceStr);
        int stock = Integer.parseInt(stockStr);

        if (currentProduct == null) {
            // Lưu ý: Đối số thứ 4 là imageUrl (thay cho chuỗi rỗng trước đây)
            Product newProduct = new Product(name, price, stock, imageUrl, categoryId, supplierId, isActive);
            viewModel.addProduct(newProduct);
        } else {
            currentProduct.setName(name);
            currentProduct.setPrice(price);
            currentProduct.setStock(stock);
            currentProduct.setImage(imageUrl); // Cập nhật URL ảnh mới
            currentProduct.setCategoryId(categoryId);
            currentProduct.setSupplierId(supplierId);
            currentProduct.setActive(isActive);
            viewModel.updateProduct(currentProduct);
        }
    }
    }
}