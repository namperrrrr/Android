package com.example.dienmayxanh.features.auth.domain;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.auth.data.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginUseCase {
    private AuthRepository repository;
    public LoginUseCase() {
        this.repository = new AuthRepository();
    }
    public void execute(String email, String password, AuthRepository.AuthCallback<FirebaseUser> callback) {
        // Business Logic: Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            callback.onCompleted(Resource.error("Email không hợp lệ hoặc để trống!"));
            return;
        }

        if (password == null || password.trim().isEmpty() || password.length() < 6) {
            callback.onCompleted(Resource.error("Mật khẩu phải có ít nhất 6 ký tự!"));
            return;
        }

        // Nếu dữ liệu ngon lành, mới gọi xuống tầng Data (Repository)
        repository.login(email, password, callback);
    }
}
