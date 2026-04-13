package com.example.dienmayxanh;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.dienmayxanh.core.utils.SideMenuController;
import com.example.dienmayxanh.features.auth.presentation.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupHeaderLogic();

        // GỌI CLASS "QUẢN GIA" ĐỂ XỬ LÝ MENU CHO SẠCH CODE
        SideMenuController menuController = new SideMenuController(this, drawerLayout, tvToolbarTitle);
        menuController.setupMenuEvents();

        // --- ĐÃ THÊM: MẶC ĐỊNH HIỂN THỊ PROFILE FRAGMENT KHI VÀO APP ---
        if (savedInstanceState == null) {
            // Đổi luôn tiêu đề cho khớp với màn hình hiển thị
            tvToolbarTitle.setText("Hồ Sơ Cá Nhân");

            // Đổ ProfileFragment vào container (Không dùng addToBackStack cho màn hình gốc)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new ProfileFragment())
                    .commit();
        }
        // -------------------------------------------------------------

        // XỬ LÝ NÚT BACK CHUẨN ANDROID MỚI NHẤT
        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
    }

    private void setupHeaderLogic() {
        ImageView imgMenu = findViewById(R.id.imgMenu);
        ImageView imgProfile = findViewById(R.id.imgProfile);

        imgMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // ĐÃ BẮT SỰ KIỆN: Bấm icon Profile góc phải sẽ mở Fragment Hồ sơ
        imgProfile.setOnClickListener(v -> {
            // 1. Đổi text ở title của header
            if (tvToolbarTitle != null) {
                tvToolbarTitle.setText("Hồ Sơ Cá Nhân");
            }

            // 2. Chuyển sang màn hình Profile
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new ProfileFragment())
                    .addToBackStack(null) // Giúp bấm nút Back để quay lại màn hình cũ
                    .commit();
        });
    }
}