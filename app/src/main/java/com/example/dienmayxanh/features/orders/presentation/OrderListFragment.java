package com.example.dienmayxanh.features.orders.presentation;

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
import com.example.dienmayxanh.databinding.FragmentOrderListBinding;
import com.example.dienmayxanh.features.orders.data.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {

    private OrderViewModel viewModel;
    private OrderAdapter adapter;
    private FragmentOrderListBinding binding;


    private List<Order> fullOrderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderListBinding.inflate(inflater, container, false);
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
        adapter = new OrderAdapter(new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onEditClick(Order order) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER_DATA", order);

                AddEditOrderFragment fragment = new AddEditOrderFragment();
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onDeleteClick(Order order) {

                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa giao dịch của khách hàng " + order.getCustomerName() + " không?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("XÓA", (dialogInterface, which) -> {
                            viewModel.deleteOrder(order.getId());
                        })
                        .setNegativeButton("HỦY", (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                        })
                        .create();


                dialog.show();


                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.parseColor("#D32F2F"));
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(android.graphics.Color.parseColor("#757575"));
            }
        });

        binding.recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewOrders.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Lắng nghe danh sách tải về
        viewModel.getOrderListState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        binding.progressBarOrders.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBarOrders.setVisibility(View.GONE);
                        // 2. Lưu lại bản gốc khi tải thành công từ Firebase
                        if (resource.data != null) {
                            fullOrderList = resource.data;
                        }
                        adapter.setOrders(fullOrderList);
                        break;
                    case ERROR:
                        binding.progressBarOrders.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Lỗi: " + resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == com.example.dienmayxanh.core.network.Resource.Status.SUCCESS) {
                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                viewModel.clearActionState();
                viewModel.fetchOrders(); // Tải lại danh sách
            }
        });

        viewModel.fetchOrders();
    }

    private void setupEvents() {
        binding.fabAddOrder.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditOrderFragment())
                    .addToBackStack(null)
                    .commit();
        });


        binding.edtSearchOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterOrders(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 4. Hàm xử lý logic lọc danh sách
    private void filterOrders(String text) {
        List<Order> filteredList = new ArrayList<>();
        String searchKeyword = text.toLowerCase().trim();

        for (Order order : fullOrderList) {
            // Lấy ra các trường cần tìm kiếm và đưa hết về chữ thường
            String id = order.getId() != null ? order.getId().toLowerCase() : "";
            String customer = order.getCustomerName() != null ? order.getCustomerName().toLowerCase() : "";
            String items = order.getItems() != null ? order.getItems().toLowerCase() : "";

            // Kiểm tra xem từ khóa tìm kiếm có nằm trong Tên, Sản phẩm, hoặc Mã hay không
            if (customer.contains(searchKeyword) ||
                    items.contains(searchKeyword) ||
                    id.contains(searchKeyword)) {

                filteredList.add(order);
            }
        }

        // Đổ danh sách đã lọc ra màn hình
        adapter.setOrders(filteredList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}