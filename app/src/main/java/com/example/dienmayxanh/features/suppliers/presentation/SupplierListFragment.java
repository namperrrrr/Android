package com.example.dienmayxanh.features.suppliers.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.suppliers.data.Supplier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SupplierListFragment extends Fragment {
    private SupplierViewModel viewModel;
    private SupplierAdapter adapter;
    private ProgressBar progressBar;
    private Supplier currentSupplierToDelete = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_list, container, false);

        RecyclerView rvSuppliers = view.findViewById(R.id.rvSuppliers);
        progressBar = view.findViewById(R.id.progressBar);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddSupplier);

        rvSuppliers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SupplierAdapter();
        rvSuppliers.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SupplierViewModel.class);

        viewModel.getSupplierListState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING: progressBar.setVisibility(View.VISIBLE); break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.data != null) adapter.setSuppliers(resource.data);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        fabAdd.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, new AddEditSupplierFragment())
                .addToBackStack(null).commit());

        adapter.setOnItemClickListener(supplier -> {
            AddEditSupplierFragment fragment = new AddEditSupplierFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("SUPPLIER_DATA", supplier);
            fragment.setArguments(bundle);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, fragment)
                    .addToBackStack(null).commit();
        });

        adapter.setOnDeleteClickListener(supplier -> {
            currentSupplierToDelete = supplier;
            viewModel.deleteSupplier(supplier.getId());
        });

        viewModel.getDeleteSupplierState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING: progressBar.setVisibility(View.VISIBLE); break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Đã xóa nhà cung cấp!", Toast.LENGTH_SHORT).show();
                    if (currentSupplierToDelete != null) {
                        adapter.removeSupplier(currentSupplierToDelete);
                        currentSupplierToDelete = null;
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Lỗi xóa: " + resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.fetchSuppliers();
        return view;
    }
}