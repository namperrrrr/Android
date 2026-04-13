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

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.databinding.FragmentProfileBinding;// Import màn hình đổi mật khẩu lúc nãy
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Áp dụng View Binding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel (Nếu bạn dùng AuthViewModel để quản lý state)
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loadAdminData();
        setupEvents();
    }

    private void loadAdminData() {
        // Lấy thông tin user đang đăng nhập trực tiếp từ Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Đổ dữ liệu thật lên giao diện
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            // Nếu tên trống (do lúc đăng ký chưa set) thì hiển thị mặc định
            if (displayName == null || displayName.isEmpty()) {
                binding.tvAdminName.setText("Quản trị viên");
            } else {
                binding.tvAdminName.setText(displayName);
            }

            binding.tvAdminEmail.setText(email != null ? email : "Chưa có email");
        } else {
            // Trả về mặc định nếu không load được
            binding.tvAdminName.setText("Tài khoản khách");
            binding.tvAdminEmail.setText("Vui lòng đăng nhập lại");
        }
    }

    private void setupEvents() {

        // 2. Chuyển sang màn hình Đổi Mật Khẩu
        binding.layoutChangePassword.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new ChangePasswordFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 3. Xử lý Đăng xuất
        binding.layoutLogout.setOnClickListener(v -> {
            // Đăng xuất khỏi Firebase
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển hướng người dùng về lại màn hình Đăng nhập (AuthActivity)
            // Đồng thời xóa sạch lịch sử màn hình cũ (tránh bấm nút Back bị quay lại app)
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Chống rò rỉ bộ nhớ
    }
}