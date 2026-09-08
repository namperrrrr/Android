package com.example.dienmayxanh.core.utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.categories.presentation.Category_List_Fragment;
import com.example.dienmayxanh.features.employees.presentation.EmployeeListFragment;
import com.example.dienmayxanh.features.roles.presentation.RolesListFragment;
// Đã import thêm Fragment Sản phẩm của bạn
import com.example.dienmayxanh.features.products.presentation.Product_List_Fragment;
import com.example.dienmayxanh.features.suppliers.presentation.Supplier_List_Fragment;

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

        // quan li ban hang
        TextView navGroupSales = activity.findViewById(R.id.nav_group_hr1);
        LinearLayout layoutSalesItems = activity.findViewById(R.id.layout_hr_items1);
        TextView navTransaction = activity.findViewById(R.id.nav_staff_1); // Quản lý giao dịch
        TextView navCustomer = activity.findViewById(R.id.nav_roles_1);    // Quản lý khách hàng
        TextView navVoucher = activity.findViewById(R.id.nav_roles_2);     // Quản lý voucher

        navGroupSales.setOnClickListener(v -> {
            if (layoutSalesItems.getVisibility() == View.VISIBLE) {

                layoutSalesItems.setVisibility(View.GONE);


                navGroupSales.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
            } else {
                layoutSalesItems.setVisibility(View.VISIBLE);
                navGroupSales.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
            }
        });

        navTransaction.setOnClickListener(v -> {
            tvToolbarTitle.setText("Quản lý giao dịch");
            drawerLayout.closeDrawer(GravityCompat.START);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new com.example.dienmayxanh.features.orders.presentation.OrderListFragment())
                    .commit();

        });

        navCustomer.setOnClickListener(v -> {
            tvToolbarTitle.setText("Quản lý khách hàng");
            drawerLayout.closeDrawer(GravityCompat.START);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new com.example.dienmayxanh.features.customers.presentation.CustomerListFragment())
                    .commit();

        });

        navVoucher.setOnClickListener(v -> {
            tvToolbarTitle.setText("Quản lý voucher");
            drawerLayout.closeDrawer(GravityCompat.START);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new com.example.dienmayxanh.features.promotions.presentation.PromotionListFragment())
                    .commit();


        });

        // ---------------------------------------------------------
        // PHẦN QUẢN LÝ SẢN PHẨM
        // ---------------------------------------------------------

        TextView navGroupProduct = activity.findViewById(R.id.nav_group_product);
        LinearLayout layoutProductItems = activity.findViewById(R.id.layout_product_items);
        TextView navProductList = activity.findViewById(R.id.nav_product_list);
        TextView navProductCategory = activity.findViewById(R.id.nav_product_category);
        TextView navSupplier = activity.findViewById(R.id.nav_supplier);

        // Xử lý sự kiện xổ xuống / thu lại
        if (navGroupProduct != null) {
            navGroupProduct.setOnClickListener(v -> {
                if (layoutProductItems.getVisibility() == View.GONE) {
                    layoutProductItems.setVisibility(View.VISIBLE);
                    navGroupProduct.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                } else {
                    layoutProductItems.setVisibility(View.GONE);
                    navGroupProduct.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                }
            });
        }

        // Chuyển hướng màn hình Danh sách sản phẩm (chức năng bạn đã làm)
        if (navProductList != null) {
            navProductList.setOnClickListener(v -> {
                tvToolbarTitle.setText("Danh Sách Sản Phẩm");
                drawerLayout.closeDrawer(GravityCompat.START);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, new Product_List_Fragment())
                        .commit();
            });
        }

        // (Tùy chọn) Gắn sự kiện cho Danh mục và Nhà cung cấp sau này khi bạn làm xong
        if (navProductCategory != null) {
            navProductCategory.setOnClickListener(v -> {
                tvToolbarTitle.setText("Danh Mục");
                drawerLayout.closeDrawer(GravityCompat.START);

                // Mở comment và gọi CategoryListFragment
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, new Category_List_Fragment())
                        .commit();
            });
        }

        if (navSupplier != null) {
            navSupplier.setOnClickListener(v -> {
                tvToolbarTitle.setText("Nhà Cung Cấp");
                drawerLayout.closeDrawer(GravityCompat.START);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, new Supplier_List_Fragment())
                        .commit();
            });
        }
    }



}