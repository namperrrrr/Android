package com.example.dienmayxanh.features.auth.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dienmayxanh.databinding.FragmentChangePasswordBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Áp dụng View Binding
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupEvents();
    }

    private void setupEvents() {
        // Nút Back trên thanh Header
        binding.btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Bắt sự kiện bấm nút Cập nhật
        binding.btnSavePassword.setOnClickListener(v -> handlePasswordChange());
    }

    private void handlePasswordChange() {
        String currentPass = binding.edtCurrentPassword.getText().toString().trim();
        String newPass = binding.edtNewPassword.getText().toString().trim();
        String confirmPass = binding.edtConfirmPassword.getText().toString().trim();

        // 1. Kiểm tra rỗng
        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Kiểm tra độ dài mật khẩu mới
        if (newPass.length() < 6) {
            Toast.makeText(getContext(), "Mật khẩu mới phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Kiểm tra mật khẩu xác nhận
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khóa nút bấm và hiện chữ đang xử lý để tránh user bấm nhiều lần
        binding.btnSavePassword.setEnabled(false);
        binding.btnSavePassword.setText("ĐANG XỬ LÝ...");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.getEmail() != null) {
            // 4. Bước quan trọng: Xác thực lại bằng mật khẩu cũ (Re-authenticate)
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Nếu mật khẩu cũ đúng -> Cho phép đổi mật khẩu mới
                    user.updatePassword(newPass).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                            // Quay lại màn hình Profile
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            binding.btnSavePassword.setEnabled(true);
                            binding.btnSavePassword.setText("CẬP NHẬT MẬT KHẨU");
                            Toast.makeText(getContext(), "Lỗi: " + updateTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // Nếu mật khẩu cũ sai
                    binding.btnSavePassword.setEnabled(true);
                    binding.btnSavePassword.setText("CẬP NHẬT MẬT KHẨU");
                    Toast.makeText(getContext(), "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Lỗi: Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            binding.btnSavePassword.setEnabled(true);
            binding.btnSavePassword.setText("CẬP NHẬT MẬT KHẨU");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Chống rò rỉ bộ nhớ
    }
}
