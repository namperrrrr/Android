package com.example.dienmayxanh.features.promotions.presentation;

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
import com.example.dienmayxanh.databinding.FragmentPromotionListBinding;
import com.example.dienmayxanh.features.promotions.data.Promotion;
import java.util.ArrayList;
import java.util.List;

public class PromotionListFragment extends Fragment {

    private PromotionViewModel viewModel;
    private PromotionAdapter adapter;
    private FragmentPromotionListBinding binding;
    private List<Promotion> fullPromoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPromotionListBinding.inflate(inflater, container, false);
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
        adapter = new PromotionAdapter(new PromotionAdapter.OnPromotionClickListener() {
            @Override
            public void onEditClick(Promotion promotion) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROMO_DATA", promotion);
                AddEditPromotionFragment fragment = new AddEditPromotionFragment();
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onDeleteClick(Promotion promotion) {
                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa mã giảm giá " + promotion.getCode() + " không?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("XÓA", (d, w) -> viewModel.deletePromotion(promotion.getId()))
                        .setNegativeButton("HỦY", (d, w) -> d.dismiss())
                        .create();
                dialog.show();
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.parseColor("#D32F2F"));
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(android.graphics.Color.parseColor("#757575"));
            }
        });
        binding.recyclerViewPromotions.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPromotions.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PromotionViewModel.class);
        viewModel.getPromotionListState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == com.example.dienmayxanh.core.network.Resource.Status.SUCCESS) {
                if (resource.data != null) fullPromoList = resource.data;
                adapter.setPromotions(fullPromoList);
            }
        });
        viewModel.getActionState().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == com.example.dienmayxanh.core.network.Resource.Status.SUCCESS) {
                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                viewModel.clearActionState();
                viewModel.fetchPromotions();
            }
        });
        viewModel.fetchPromotions();
    }

    private void setupEvents() {
        binding.fabAddPromotion.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, new AddEditPromotionFragment()).addToBackStack(null).commit());

        binding.edtSearchPromo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase().trim();
                List<Promotion> filtered = new ArrayList<>();
                for (Promotion p : fullPromoList) {
                    if ((p.getCode() != null && p.getCode().toLowerCase().contains(keyword)) ||
                            (p.getDescription() != null && p.getDescription().toLowerCase().contains(keyword))) {
                        filtered.add(p);
                    }
                }
                adapter.setPromotions(filtered);
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}