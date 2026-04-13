package com.example.dienmayxanh.features.employees.presentation;

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
// Import View Binding của file fragment_employee_list.xml
import com.example.dienmayxanh.databinding.FragmentEmployeeListBinding;
import com.example.dienmayxanh.features.employees.data.Employee;

public class EmployeeListFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private EmployeeAdapter adapter;

    // Khai báo duy nhất 1 biến binding thay vì cả tá View
    private FragmentEmployeeListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Khởi tạo View Binding
        binding = FragmentEmployeeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Không cần hàm initViews() với findViewById rườm rà nữa!
        setupRecyclerView();
        setupViewModel();
        setupEvents();
    }

    private void setupRecyclerView() {
        adapter = new EmployeeAdapter(new EmployeeAdapter.OnEmployeeClickListener() {
            @Override
            public void onEditClick(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("EMPLOYEE_DATA", employee);

                AddEditEmployeeFragment fragment = new AddEditEmployeeFragment();
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onDeleteClick(Employee employee) {
                viewModel.deleteEmployee(employee.getId());
            }
        });

        // Gọi trực tiếp RecyclerView thông qua binding
        binding.recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewEmployees.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        viewModel.getEmployeeListState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBar.setVisibility(View.GONE);
                        adapter.setEmployees(resource.data);
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi tải data: " + resource.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Thao tác thành công!", Toast.LENGTH_SHORT).show();

                        viewModel.clearActionState();
                        viewModel.fetchEmployees();
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        break;
                }
            }
        });

        viewModel.fetchEmployees();
    }

    private void setupEvents() {
        binding.fabAddEmployee.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditEmployeeFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    // Best Practice: Hủy binding khi Fragment bị đóng để tránh rò rỉ bộ nhớ (Memory Leak)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}