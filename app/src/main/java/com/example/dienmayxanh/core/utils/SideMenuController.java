package com.example.dienmayxanh.core.utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.employees.presentation.EmployeeListFragment;
import com.example.dienmayxanh.features.roles.presentation.RolesListFragment;
// Đã import thêm Fragment Sản phẩm của bạn
import com.example.dienmayxanh.features.products.presentation.ProductListFragment;

public class SideMenuController {

    private final AppCompatActivity activity;
    private final DrawerLayout drawerLayout;
    private final TextView tvToolbarTitle;

    public SideMenuController(AppCompatActivity activity, DrawerLayout drawerLayout, TextView tvToolbarTitle) {
        this.activity = activity;
        this.drawerLayout = drawerLayout;
        this.tvToolbarTitle = tvToolbarTitle;
    }

    public void setupMenuEvents() {
        //Sửa code
        // ---------------------------------------------------------
        // 1. ÁNH XẠ VIEW & CÀI ĐẶT HIỆU ỨNG XỔ XUỐNG (ACCORDION)
        // ---------------------------------------------------------

        // Nhóm Quản lý nhân sự
        TextView navGroupHr = activity.findViewById(R.id.nav_group_hr);
        LinearLayout layoutHrItems = activity.findViewById(R.id.layout_hr_items);
        TextView navStaff = activity.findViewById(R.id.nav_staff);
        TextView navRoles = activity.findViewById(R.id.nav_roles);

        // THÊM ĐOẠN CODE NÀY VÀO ĐỂ XỬ LÝ SỰ KIỆN XỔ XUỐNG/THU LẠI
        navGroupHr.setOnClickListener(v -> {
            if (layoutHrItems.getVisibility() == View.GONE) {
                // Nếu đang ẩn thì hiện ra
                layoutHrItems.setVisibility(View.VISIBLE);
                navGroupHr.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
            } else {
                // Nếu đang hiện thì ẩn đi
                layoutHrItems.setVisibility(View.GONE);
                navGroupHr.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
            }
        });

        navStaff.setOnClickListener(v -> {
            tvToolbarTitle.setText("Danh Sách Nhân Viên");
            drawerLayout.closeDrawer(GravityCompat.START);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new EmployeeListFragment())
                    .commit();
        });

        navRoles.setOnClickListener(v -> {
            tvToolbarTitle.setText("Quản Lý Chức Danh");
            drawerLayout.closeDrawer(GravityCompat.START);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new RolesListFragment())
                    .commit();
        });

        // ---------------------------------------------------------
        // 2. PHẦN THÊM MỚI CHO QUẢN LÝ SẢN PHẨM CỦA BẠN
        // ---------------------------------------------------------

        // Lưu ý: Tôi đang giả định ID nút bấm trong file layout_side_menu.xml là nav_products
        // Nếu file XML của nhóm trưởng đặt tên ID khác, bạn chỉ cần sửa lại R.id.nav_products cho khớp nhé.
        TextView navProducts = activity.findViewById(R.id.nav_products);

        if (navProducts != null) {
            navProducts.setOnClickListener(v -> {
                tvToolbarTitle.setText("Quản Lý Sản Phẩm");
                drawerLayout.closeDrawer(GravityCompat.START);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, new ProductListFragment())
                        .commit();
            });
        }
    }
}