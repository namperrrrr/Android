package com.example.dienmayxanh.features.customers.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.dienmayxanh.databinding.FragmentCustomerListBinding;
import com.example.dienmayxanh.features.customers.data.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListFragment extends Fragment {

    private CustomerViewModel viewModel;
    private CustomerAdapter adapter;
    private FragmentCustomerListBinding binding;

    // Biến lưu danh sách gốc để Tìm kiếm
    private List<Customer> fullCustomerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCustomerListBinding.inflate(inflater, container, false);
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
        adapter = new CustomerAdapter(new CustomerAdapter.OnCustomerClickListener() {
            @Override
            public void onEditClick(Customer customer) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("CUSTOMER_DATA", customer);

                AddEditCustomerFragment fragment = new AddEditCustomerFragment();
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onDeleteClick(Customer customer) {
                // Hộp thoại xác nhận xóa (Fix lỗi mờ nút)
                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa khách hàng " + customer.getName() + " không?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("XÓA", (dialogInterface, which) -> {
                            viewModel.deleteCustomer(customer.getId());
                        })
                        .setNegativeButton("HỦY", (dialogInterface, which) -> dialogInterface.dismiss())
                        .create();

                dialog.show();
                // Ép màu nút
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.parseColor("#D32F2F"));
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(android.graphics.Color.parseColor("#757575"));
            }
        });

        binding.recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCustomers.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        viewModel.getCustomerListState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        // Nếu XML bạn chưa có progressBar thì comment dòng này lại
                        // binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        // binding.progressBar.setVisibility(View.GONE);
                        if (resource.data != null) {
                            fullCustomerList = resource.data;
                        }
                        adapter.setCustomers(fullCustomerList);
                        break;
                    case ERROR:
                        // binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == com.example.dienmayxanh.core.network.Resource.Status.SUCCESS) {
                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                viewModel.clearActionState();
                viewModel.fetchCustomers();
            }
        });

        viewModel.fetchCustomers();
    }

    private void setupEvents() {
        binding.fabAddCustomer.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditCustomerFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Xử lý sự kiện gõ tìm kiếm
        binding.edtSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCustomers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Hàm Lọc Khách hàng
    private void filterCustomers(String text) {
        List<Customer> filteredList = new ArrayList<>();
        String searchKeyword = text.toLowerCase().trim();

        for (Customer customer : fullCustomerList) {
            String name = customer.getName() != null ? customer.getName().toLowerCase() : "";
            String phone = customer.getPhone() != null ? customer.getPhone() : "";

            if (name.contains(searchKeyword) || phone.contains(searchKeyword)) {
                filteredList.add(customer);
            }
        }
        adapter.setCustomers(filteredList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}