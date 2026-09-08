package com.example.dienmayxanh.features.orders.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.databinding.FragmentAddEditOrderBinding;
import com.example.dienmayxanh.features.orders.data.Order;
import com.example.dienmayxanh.features.promotions.data.Promotion;
import com.example.dienmayxanh.features.promotions.data.PromotionRepository;

import java.util.ArrayList;
import java.util.List;

public class AddEditOrderFragment extends Fragment {

    private FragmentAddEditOrderBinding binding;
    private OrderViewModel viewModel;
    private boolean isAddMode = true;
    private String currentOrderId = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        setupSpinner();
        loadVouchersIntoDropdown();
        checkModeAndLoadData();
        setupEvents();
        observeViewModel();
    }

    private void setupSpinner() {
        String[] statuses = new String[]{"Chờ thanh toán", "Đã thanh toán", "Đang giao", "Hoàn thành", "Đã hủy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        binding.spinnerOrderStatus.setAdapter(adapter);
    }

    private void loadVouchersIntoDropdown() {
        PromotionRepository promoRepo = new PromotionRepository();
        promoRepo.getPromotions(resource -> {
            if (resource.status == com.example.dienmayxanh.core.network.Resource.Status.SUCCESS && resource.data != null) {
                List<String> voucherList = new ArrayList<>();
                voucherList.add("Không dùng Voucher");

                for (Promotion promo : resource.data) {
                    if ("Còn hạn".equals(promo.getStatus())) {
                        voucherList.add(promo.getCode() + " (" + promo.getDiscount() + ")");
                    }
                }

                if (getContext() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            voucherList
                    );
                    binding.edtOrderVoucher.setAdapter(adapter);
                }
            }
        });
    }

    private void checkModeAndLoadData() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("ORDER_DATA")) {
            isAddMode = false;
            Order orderToEdit = (Order) args.getSerializable("ORDER_DATA");

            if (orderToEdit != null) {
                currentOrderId = orderToEdit.getId();
                binding.tvOrderFormTitle.setText("CẬP NHẬT GIAO DỊCH");
                binding.btnSaveOrder.setText("LƯU THAY ĐỔI");

                binding.edtOrderCustomer.setText(orderToEdit.getCustomerName());
                binding.edtOrderItems.setText(orderToEdit.getItems());
                binding.edtOrderTotal.setText(orderToEdit.getTotal());
                binding.edtOrderVoucher.setText(orderToEdit.getVoucher(), false);
            }
        } else {
            isAddMode = true;
            binding.tvOrderFormTitle.setText("THÊM MỚI GIAO DỊCH");
        }
    }

    private void observeViewModel() {
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.btnSaveOrder.setEnabled(false);
                        binding.btnSaveOrder.setText("ĐANG XỬ LÝ...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        requireActivity().getSupportFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        binding.btnSaveOrder.setEnabled(true);
                        binding.btnSaveOrder.setText(isAddMode ? "LƯU THÔNG TIN" : "LƯU THAY ĐỔI");
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void setupEvents() {
        binding.btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.btnSaveOrder.setOnClickListener(v -> {
            String customer = binding.edtOrderCustomer.getText().toString().trim();
            String items = binding.edtOrderItems.getText().toString().trim();
            String total = binding.edtOrderTotal.getText().toString().trim();
            String status = binding.spinnerOrderStatus.getSelectedItem().toString();
            String voucher = binding.edtOrderVoucher.getText().toString().trim();

            if (customer.isEmpty() || items.isEmpty() || total.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = new Order(customer, items, total, status, voucher);

            if (isAddMode) {
                viewModel.addOrder(order);
            } else {
                order.setId(currentOrderId);
                viewModel.updateOrder(order);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}