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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
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
    private SwitchCompat swActive;
    private Button btnSave;
    private ImageView imgPreview;

    private final List<Category> categoryList = new ArrayList<>();
    private final List<Supplier> supplierList = new ArrayList<>();
    private ArrayAdapter<Category> categoryAdapter;
    private ArrayAdapter<Supplier> supplierAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        // 1. Ánh xạ View
        edtName = view.findViewById(R.id.edtProductName);
        edtPrice = view.findViewById(R.id.edtProductPrice);
        edtStock = view.findViewById(R.id.edtProductStock);
        edtImage = view.findViewById(R.id.edtProductImage);
        imgPreview = view.findViewById(R.id.imgProductPreview);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerSupplier = view.findViewById(R.id.spinnerSupplier);
        swActive = view.findViewById(R.id.swActive);
        btnSave = view.findViewById(R.id.btnSaveProduct);
        Button btnBack = view.findViewById(R.id.btnBack);

        // 2. Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // 3. Cài đặt Spinner và Quan sát dữ liệu
        setupSpinners();
        observeDropdownData();

        viewModel.fetchCategoriesForDropdown();
        viewModel.fetchSuppliersForDropdown();

        // 4. Xử lý nút quay lại
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // 5. Đổ dữ liệu nếu là chế độ Sửa (Edit)
        if (getArguments() != null && getArguments().containsKey("PRODUCT_DATA")) {
            currentProduct = (Product) getArguments().getSerializable("PRODUCT_DATA");
            if (currentProduct != null) {
                edtName.setText(currentProduct.getName());
                edtPrice.setText(String.valueOf(currentProduct.getPrice()));
                edtStock.setText(String.valueOf(currentProduct.getStock()));
                swActive.setChecked(currentProduct.isActive());
                edtImage.setText(currentProduct.getImageUrl());

                Glide.with(this)
                        .load(currentProduct.getImageUrl())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .fitCenter()
                        .into(imgPreview);
            }
        }

        // 6. Preview ảnh khi nhập URL
        edtImage.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String url = edtImage.getText().toString().trim();
                if (!url.isEmpty()) {
                    Glide.with(this)
                            .load(url)
                            .error(android.R.drawable.stat_notify_error)
                            .fitCenter()
                            .into(imgPreview);
                }
            }
        });

        // 7. Quan sát trạng thái lưu (Success/Error)
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
                        btnSave.setText("LƯU THÔNG TIN");
                        Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnSave.setOnClickListener(v -> saveProduct());

        return view;
    }

    private void setupSpinners() {
        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        supplierAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, supplierList);
        supplierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSupplier.setAdapter(supplierAdapter);
    }

    private void observeDropdownData() {
        viewModel.getCategoriesState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == Resource.Status.SUCCESS && resource.data != null) {
                categoryList.clear();
                categoryList.addAll(resource.data);
                categoryAdapter.notifyDataSetChanged();

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

        viewModel.getSuppliersState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == Resource.Status.SUCCESS && resource.data != null) {
                supplierList.clear();
                supplierList.addAll(resource.data);
                supplierAdapter.notifyDataSetChanged();

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

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        Supplier selectedSupplier = (Supplier) spinnerSupplier.getSelectedItem();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategory == null || selectedSupplier == null) {
            Toast.makeText(getContext(), "Vui lòng chọn Danh mục và Nhà cung cấp", Toast.LENGTH_SHORT).show();
            return;
        }

        long price = Long.parseLong(priceStr);
        int stock = Integer.parseInt(stockStr);
        String categoryId = selectedCategory.getId();
        String supplierId = selectedSupplier.getId();

        if (currentProduct == null) {
            Product newProduct = new Product(name, price, stock, imageUrl, categoryId, supplierId, isActive);
            viewModel.addProduct(newProduct);
        } else {
            currentProduct.setName(name);
            currentProduct.setPrice(price);
            currentProduct.setStock(stock);
            currentProduct.setImageUrl(imageUrl);
            currentProduct.setCategoryId(categoryId);
            currentProduct.setSupplierId(supplierId);
            currentProduct.setActive(isActive);
            viewModel.updateProduct(currentProduct);
        }
    }
}
