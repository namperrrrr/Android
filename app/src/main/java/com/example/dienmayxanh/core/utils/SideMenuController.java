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
    }
}