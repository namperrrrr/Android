package com.example.dienmayxanh.features.auth.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dienmayxanh.databinding.AuthFragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private AuthFragmentRegisterBinding binding;
    private AuthViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout bằng ViewBinding (tên class tự sinh từ auth_fragment_register.xml)
        binding = AuthFragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sử dụng requireActivity() để dùng chung instance ViewModel với LoginFragment
        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        setupListeners();
        observeViewModel();
    }

    private void setupListeners() {
        // Nút Đăng ký
        binding.btnRegister.setOnClickListener(v -> {
            String fullName = binding.edtFullName.getText().toString().trim();
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            String confirmPass = binding.edtConfirmPassword.getText().toString().trim();

            // Gọi hàm đăng ký trong ViewModel
            viewModel.register(email, password, confirmPass, fullName);
        });

        // Nút Quay lại đăng nhập
        binding.tvBackToLogin.setOnClickListener(v -> {
            // Chỉ cần rút bức tranh này ra khỏi khung, màn hình Login bên dưới sẽ tự hiện ra
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void observeViewModel() {
        // Quan sát trạng thái actionState (dùng chung cho Đăng ký và Quên mật khẩu)
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnRegister.setEnabled(false);
                    break;

                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    // Thông báo cho người dùng
                    Toast.makeText(getContext(), "Đăng ký thành công! Mời bạn đăng nhập.", Toast.LENGTH_SHORT).show();

                    // Sau khi đăng ký xong, tự động quay về màn hình Login
                    requireActivity().getSupportFragmentManager().popBackStack();
                    break;

                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnRegister.setEnabled(true);
                    // Hiển thị lỗi từ Firebase hoặc lỗi validate từ UseCase
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}