package com.example.dienmayxanh.features.auth.presentation;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.auth.domain.AuthUseCases;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {

    private AuthUseCases useCases = new AuthUseCases();

    private MutableLiveData<Resource<FirebaseUser>> loginState = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();

    public LiveData<Resource<FirebaseUser>> getLoginState() { return loginState; }
    public LiveData<Resource<String>> getActionState() { return actionState; }

    public void login(String email, String password) {
        // Bật trạng thái Loading trước khi gọi UseCase để giao diện quay vòng xoay
        loginState.setValue(Resource.loading());
        useCases.login.execute(email, password, resource -> loginState.setValue(resource));
    }

    public void register(String email, String password, String confirmPassword, String name) {
        actionState.setValue(Resource.loading());
        useCases.register.execute(email, password, confirmPassword, name, resource -> actionState.setValue(resource));
    }


    }