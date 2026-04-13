package com.example.dienmayxanh.features.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.MainActivity;
import com.example.dienmayxanh.R; // Thêm import R chung của project
// Import class Binding với tên được tự động chuyển đổi từ auth_fragment_login.xml
import com.example.dienmayxanh.databinding.AuthFragmentLoginBinding;

public class LoginFragment extends Fragment {

    // 1. Sửa lại đúng tên Class Binding
    private AuthFragmentLoginBinding binding;
    private AuthViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 2. Gọi hàm inflate từ đúng tên Class
        binding = AuthFragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel (Dùng requireActivity() để share chung 1 ViewModel cho cả 3 Fragment)
        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        setupListeners();
        observeViewModel();
    }

    private void setupListeners() {
        // Nút Đăng nhập
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            viewModel.login(email, password);
        });

        // Nút chuyển sang Đăng ký
        binding.tvGoToRegister.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new RegisterFragment())
                    .addToBackStack(null) // Cho phép bấm nút Back để quay lại Login
                    .commit();
        });


    }

    private void observeViewModel() {
        viewModel.getLoginState().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnLogin.setEnabled(false); // Khóa nút không cho bấm nhiều lần
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Bay thẳng vào trang chủ
                    startActivity(new Intent(requireActivity(), MainActivity.class));
                    requireActivity().finish(); // Hủy toàn bộ luồng Auth để không quay lại được
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnLogin.setEnabled(true); // Nhả nút ra cho bấm lại
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh rò rỉ bộ nhớ
    }
}