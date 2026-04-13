package com.example.dienmayxanh.features.categories.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.dienmayxanh.R;
import com.example.dienmayxanh.features.categories.data.Category;

public class AddEditCategoryFragment extends Fragment {
    private CategoryViewModel viewModel;
    private Category currentCategory;

    private EditText edtName, edtDesc;
    private Switch swActive;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_category, container, false);

        edtName = view.findViewById(R.id.edtCategoryName);
        edtDesc = view.findViewById(R.id.edtCategoryDesc);
        swActive = view.findViewById(R.id.swActive);
        btnSave = view.findViewById(R.id.btnSaveCategory);
        Button btnBack = view.findViewById(R.id.btnBack);

        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        if (getArguments() != null && getArguments().containsKey("CATEGORY_DATA")) {
            currentCategory = (Category) getArguments().getSerializable("CATEGORY_DATA");
            if (currentCategory != null) {
                edtName.setText(currentCategory.getName());
                edtDesc.setText(currentCategory.getDescription());
                swActive.setChecked(currentCategory.isActive());
            }
        }

        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        btnSave.setEnabled(false);
                        btnSave.setText("Đang lưu...");
                        break;
                    case SUCCESS:
                        Toast.makeText(getContext(), resource.data, Toast.LENGTH_SHORT).show();
                        viewModel.clearActionState();
                        getParentFragmentManager().popBackStack();
                        break;
                    case ERROR:
                        btnSave.setEnabled(true);
                        btnSave.setText("Lưu Danh Mục");
                        Toast.makeText(getContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnSave.setOnClickListener(v -> saveCategory());

        return view;
    }

    private void saveCategory() {
        String name = edtName.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        boolean isActive = swActive.isChecked();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentCategory == null) {
            Category newCategory = new Category(name, desc, isActive);
            viewModel.addCategory(newCategory);
        } else {
            currentCategory.setName(name);
            currentCategory.setDescription(desc);
            currentCategory.setActive(isActive);
            viewModel.updateCategory(currentCategory);
        }
    }
}