package com.example.dienmayxanh.features.customers.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.databinding.FragmentAddEditCustomerBinding;
import com.example.dienmayxanh.features.customers.data.Customer;

public class AddEditCustomerFragment extends Fragment {

    private FragmentAddEditCustomerBinding binding;
    private CustomerViewModel viewModel;
    private boolean isAddMode = true;
    private String currentCustomerId = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        checkModeAndLoadData();
        setupEvents();
        observeViewModel();
    }

    private void checkModeAndLoadData() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("CUSTOMER_DATA")) {
            isAddMode = false;
            Customer customerToEdit = (Customer) args.getSerializable("CUSTOMER_DATA");

            if (customerToEdit != null) {
                currentCustomerId = customerToEdit.getId();
                binding.tvCustomerFormTitle.setText("CẬP NHẬT KHÁCH HÀNG");
                binding.btnSaveCustomer.setText("LƯU THAY ĐỔI");

                binding.edtCustomerName.setText(customerToEdit.getName());
                binding.edtCustomerPhone.setText(customerToEdit.getPhone());
                binding.edtCustomerAddress.setText(customerToEdit.getAddress());
            }
        } else {
            isAddMode = true;
            binding.tvCustomerFormTitle.setText("THÊM MỚI KHÁCH HÀNG");
        }
    }

    private void observeViewModel() {
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.btnSaveCustomer.setEnabled(false);
                        binding.btnSaveCustomer.setText("ĐANG XỬ LÝ...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        requireActivity().getSupportFragmentManager().popBackStack(); // Quay về danh sách
                        break;
                    case ERROR:
                        binding.btnSaveCustomer.setEnabled(true);
                        binding.btnSaveCustomer.setText(isAddMode ? "LƯU THÔNG TIN" : "LƯU THAY ĐỔI");
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void setupEvents() {
        // Nút lưu
        binding.btnSaveCustomer.setOnClickListener(v -> {
            String name = binding.edtCustomerName.getText().toString().trim();
            String phone = binding.edtCustomerPhone.getText().toString().trim();
            String address = binding.edtCustomerAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập Tên và Số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            Customer customer = new Customer(name, phone, address);

            if (isAddMode) {
                viewModel.addCustomer(customer);
            } else {
                customer.setId(currentCustomerId);
                viewModel.updateCustomer(customer);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}