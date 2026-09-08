package com.example.dienmayxanh.features.promotions.presentation;

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
import com.example.dienmayxanh.databinding.FragmentAddEditPromotionBinding;
import com.example.dienmayxanh.features.promotions.data.Promotion;

public class AddEditPromotionFragment extends Fragment {

    private FragmentAddEditPromotionBinding binding;
    private PromotionViewModel viewModel;
    private boolean isAddMode = true;
    private String currentPromoId = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditPromotionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PromotionViewModel.class);

        setupDropdownMenu(); // Cài đặt ô xổ xuống
        checkModeAndLoadData();
        setupEvents();
        observeViewModel();
    }

    private void setupDropdownMenu() {
        String[] statuses = new String[]{"Còn hạn", "Hết hạn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                statuses
        );
        binding.edtPromoStatus.setAdapter(adapter);
    }

    private void checkModeAndLoadData() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("PROMO_DATA")) {
            isAddMode = false;
            Promotion promo = (Promotion) args.getSerializable("PROMO_DATA");
            if (promo != null) {
                currentPromoId = promo.getId();
                binding.tvPromoFormTitle.setText("CẬP NHẬT KHUYẾN MÃI");
                binding.btnSavePromo.setText("LƯU THAY ĐỔI");
                binding.edtPromoCode.setText(promo.getCode());
                binding.edtPromoDiscount.setText(promo.getDiscount());
                binding.edtPromoDesc.setText(promo.getDescription());
                binding.edtPromoStatus.setText(promo.getStatus(), false);
            }
        } else {
            isAddMode = true;
            binding.tvPromoFormTitle.setText("THÊM MỚI KHUYẾN MÃI");
            binding.edtPromoStatus.setText("Còn hạn", false);
        }
    }

    private void observeViewModel() {
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.btnSavePromo.setEnabled(false);
                        binding.btnSavePromo.setText("ĐANG XỬ LÝ...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        requireActivity().getSupportFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        binding.btnSavePromo.setEnabled(true);
                        binding.btnSavePromo.setText(isAddMode ? "LƯU KHUYẾN MÃI" : "LƯU THAY ĐỔI");
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void setupEvents() {
        binding.btnSavePromo.setOnClickListener(v -> {
            String code = binding.edtPromoCode.getText().toString().trim().toUpperCase();
            String discount = binding.edtPromoDiscount.getText().toString().trim();
            String desc = binding.edtPromoDesc.getText().toString().trim();
            String status = binding.edtPromoStatus.getText().toString().trim();

            if (code.isEmpty() || discount.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập Mã và Mức giảm!", Toast.LENGTH_SHORT).show();
                return;
            }

            Promotion promo = new Promotion(code, discount, desc, status);
            if (isAddMode) {
                viewModel.addPromotion(promo);
            } else {
                promo.setId(currentPromoId);
                viewModel.updatePromotion(promo);
            }
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}