package com.example.dienmayxanh.features.auth.domain;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.auth.data.AuthRepository;

public class RegisterUseCase {
    private AuthRepository repository;
    public RegisterUseCase() {
        this.repository = new AuthRepository();
    }
    public void execute(String email, String password, String confirmPassword, String fullName, AuthRepository.AuthCallback<String> callback) {
        if (fullName == null || fullName.trim().isEmpty()) {
            callback.onCompleted(Resource.error("Họ tên không được để trống!"));
            return;
        }

        if (!password.equals(confirmPassword)) {
            callback.onCompleted(Resource.error("Mật khẩu xác nhận không khớp!"));
            return;
        }

        // Đẩy xuống Repository nếu qua hết vòng kiểm tra
        repository.register(email, password, fullName, callback);
    }
}
