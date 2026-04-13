package com.example.dienmayxanh.features.roles.presentation;

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
// Import View Binding của file fragment_add_edit_role.xml
import com.example.dienmayxanh.databinding.FragmentAddEditRoleBinding;
import com.example.dienmayxanh.features.roles.data.Role;

public class AddEditRoleFragment extends Fragment {

    // Khai báo View Binding và ViewModel
    private FragmentAddEditRoleBinding binding;
    private RolesViewModel viewModel;

    private boolean isAddMode = true;
    private String currentRoleId = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Khởi tạo View Binding cực gọn
        binding = FragmentAddEditRoleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();
        checkModeAndLoadData();
        setupEvents();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RolesViewModel.class);

        // Lắng nghe kết quả từ ViewModel (Lưu thành công hay thất bại)
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.btnSaveRole.setEnabled(false);
                        binding.btnSaveRole.setText("ĐANG LƯU...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), "Lưu chức danh thành công!", Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();

                        // Lưu xong thì tự động quay về màn hình danh sách
                        requireActivity().getSupportFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        binding.btnSaveRole.setEnabled(true);
                        binding.btnSaveRole.setText(isAddMode ? "TẠO CHỨC DANH" : "LƯU THAY ĐỔI");
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_LONG).show();
                        viewModel.clearActionState();
                        break;
                }
            }
        });
    }

    private void checkModeAndLoadData() {
        Bundle args = getArguments();

        // Nếu có data truyền sang -> Chế độ SỬA
        if (args != null && args.containsKey("ROLE_DATA")) {
            isAddMode = false;
            Role roleToEdit = (Role) args.getSerializable("ROLE_DATA");

            if (roleToEdit != null) {
                currentRoleId = roleToEdit.getId();
                binding.tvFormTitle.setText("CẬP NHẬT CHỨC DANH");
                binding.btnSaveRole.setText("LƯU THAY ĐỔI");

                binding.edtRoleName.setText(roleToEdit.getName());

                // (Lưu ý: Nếu bạn đã xóa edtRoleDescription trong XML thì hãy xóa dòng dưới này đi nhé)
                binding.edtRoleDescription.setText(roleToEdit.getDescription());
            }
        } else {
            // Không có data -> Chế độ THÊM MỚI
            isAddMode = true;
            binding.tvFormTitle.setText("THÊM CHỨC DANH MỚI");
            binding.btnSaveRole.setText("TẠO CHỨC DANH");
        }
    }

    private void setupEvents() {
        binding.btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        binding.btnSaveRole.setOnClickListener(v -> saveRoleData());
    }

    private void saveRoleData() {
        String name = binding.edtRoleName.getText().toString().trim();

        // (Lưu ý: Nếu bạn đã xóa edtRoleDescription trong XML thì đổi dòng dưới thành String description = ""; nhé)
        String description = binding.edtRoleDescription.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập tên chức danh!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đóng gói dữ liệu
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        // Gọi ViewModel để xử lý lưu Database
        if (isAddMode) {
            viewModel.addRole(role);
        } else {
            role.setId(currentRoleId);
            viewModel.updateRole(role);
        }
    }

    // Chống tràn bộ nhớ
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}