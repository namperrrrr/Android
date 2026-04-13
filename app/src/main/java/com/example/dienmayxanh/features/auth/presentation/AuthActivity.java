package com.example.dienmayxanh.features.auth.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dienmayxanh.R;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lưu ý: Nhớ tạo 1 file res/layout/activity_auth.xml có duy nhất 1 cái FrameLayout id là fragmentContainer nhé
        setContentView(R.layout.auth_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment())
                    .commit();
        }
    }
}
