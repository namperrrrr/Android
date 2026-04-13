package com.example.dienmayxanh.features.suppliers.presentation;

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
import com.example.dienmayxanh.features.suppliers.data.Supplier;

public class AddEditSupplierFragment extends Fragment {
    private SupplierViewModel viewModel;
    private Supplier currentSupplier;

    private EditText edtName, edtPhone, edtAddress;
    private Switch swActive;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_supplier, container, false);

        edtName = view.findViewById(R.id.edtSupplierName);
        edtPhone = view.findViewById(R.id.edtSupplierPhone);
        edtAddress = view.findViewById(R.id.edtSupplierAddress);
        swActive = view.findViewById(R.id.swActive);
        btnSave = view.findViewById(R.id.btnSaveSupplier);
        Button btnBack = view.findViewById(R.id.btnBack);

        viewModel = new ViewModelProvider(this).get(SupplierViewModel.class);

        if (btnBack != null) btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        if (getArguments() != null && getArguments().containsKey("SUPPLIER_DATA")) {
            currentSupplier = (Supplier) getArguments().getSerializable("SUPPLIER_DATA");
            if (currentSupplier != null) {
                edtName.setText(currentSupplier.getName());
                edtPhone.setText(currentSupplier.getPhone());
                edtAddress.setText(currentSupplier.getAddress());
                swActive.setChecked(currentSupplier.isActive());
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
                        getParentFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        btnSave.setEnabled(true);
                        btnSave.setText("Lưu Nhà Cung Cấp");
                        Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnSave.setOnClickListener(v -> saveSupplier());
        return view;
    }

    private void saveSupplier() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        boolean isActive = swActive.isChecked();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập tên và SĐT", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentSupplier == null) {
            viewModel.addSupplier(new Supplier(name, phone, address, isActive));
        } else {
            currentSupplier.setName(name);
            currentSupplier.setPhone(phone);
            currentSupplier.setAddress(address);
            currentSupplier.setActive(isActive);
            viewModel.updateSupplier(currentSupplier);
        }
    }
}