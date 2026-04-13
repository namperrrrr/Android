package com.example.dienmayxanh.features.auth.domain;

public class AuthUseCases {
    public final LoginUseCase login;
    public final RegisterUseCase register;
    public AuthUseCases() {
        this.login = new LoginUseCase();
        this.register = new RegisterUseCase();

    }
}
