package com.example.dienmayxanh.features.categories.presentation;

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
import com.example.dienmayxanh.features.categories.data.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryListFragment extends Fragment {
    private CategoryViewModel viewModel;
    private CategoryAdapter adapter;
    private ProgressBar progressBar;
    private Category currentCategoryToDelete = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        RecyclerView rvCategories = view.findViewById(R.id.rvCategories);
        progressBar = view.findViewById(R.id.progressBar);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddCategory);

        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoryAdapter();
        rvCategories.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        viewModel.getCategoryListState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.data != null) adapter.setCategories(resource.data);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // Chuyển sang màn hình Thêm
        fabAdd.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, new AddEditCategoryFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Sửa danh mục
        adapter.setOnItemClickListener(category -> {
            AddEditCategoryFragment fragment = new AddEditCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CATEGORY_DATA", category);
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Xóa 1 chạm
        adapter.setOnDeleteClickListener(category -> {
            currentCategoryToDelete = category;
            viewModel.deleteCategory(category.getId());
        });

        // Lắng nghe kết quả xóa
        viewModel.getDeleteCategoryState().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Đã xóa danh mục thành công!", Toast.LENGTH_SHORT).show();
                    if (currentCategoryToDelete != null) {
                        adapter.removeCategory(currentCategoryToDelete);
                        currentCategoryToDelete = null;
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Lỗi khi xóa: " + resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.fetchCategories();
        return view;
    }
}