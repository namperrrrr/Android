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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dienmayxanh.R;
// Import View Binding tự động sinh ra từ file fragment_roles_list.xml
import com.example.dienmayxanh.databinding.FragmentRolesListBinding;
import com.example.dienmayxanh.features.roles.data.Role;

public class RolesListFragment extends Fragment {

    private RolesViewModel viewModel;
    private RolesAdapter adapter;

    // Khai báo duy nhất 1 biến binding ôm trọn toàn bộ giao diện
    private FragmentRolesListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Khởi tạo View Binding
        binding = FragmentRolesListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupViewModel();
        setupEvents();
    }

    private void setupRecyclerView() {
        adapter = new RolesAdapter(new RolesAdapter.OnRoleClickListener() {
            @Override
            public void onEditClick(Role role) {
                // Gói data chuyển sang màn hình Sửa
                Bundle bundle = new Bundle();
                bundle.putSerializable("ROLE_DATA", role);

                AddEditRoleFragment fragment = new AddEditRoleFragment();
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onDeleteClick(Role role) {
                // Gọi ViewModel để xóa chức danh
                viewModel.deleteRole(role.getId());
            }
        });

        // Gọi RecyclerView thông qua binding cực gọn
        binding.recyclerViewRoles.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewRoles.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RolesViewModel.class);

        // 1. Quan sát danh sách Chức danh
        viewModel.getRoleListState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.progressBarRoles.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBarRoles.setVisibility(View.GONE);
                        adapter.setRoles(resource.data);
                        break;
                    case ERROR:
                        binding.progressBarRoles.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi tải data: " + resource.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // 2. Quan sát trạng thái hành động (Xóa/Thêm/Sửa)
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.progressBarRoles.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBarRoles.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Thao tác thành công!", Toast.LENGTH_SHORT).show();

                        viewModel.clearActionState();
                        viewModel.fetchRoles(); // Load lại danh sách
                        break;
                    case ERROR:
                        binding.progressBarRoles.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        break;
                }
            }
        });

        // Gọi dữ liệu lần đầu khi mở màn hình
        viewModel.fetchRoles();
    }

    private void setupEvents() {
        binding.fabAddRole.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditRoleFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    // Dọn dẹp bộ nhớ khi tắt Fragment
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}