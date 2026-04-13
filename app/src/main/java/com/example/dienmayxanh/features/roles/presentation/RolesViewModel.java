package com.example.dienmayxanh.features.roles.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dienmayxanh.core.network.Resource;
import com.example.dienmayxanh.features.roles.data.Role;
import com.example.dienmayxanh.features.roles.domain.RolesUseCase;

import java.util.List;


public class RolesViewModel extends ViewModel {
    private RolesUseCase useCase = new RolesUseCase();
    private MutableLiveData<Resource<List<Role>>> roleListState = new MutableLiveData<>();
    public LiveData<Resource<List<Role>>> getRoleListState() { return roleListState; }
    private MutableLiveData<Resource<String>> actionState = new MutableLiveData<>();
    public LiveData<Resource<String>> getActionState() { return actionState; }
    public void fetchRoles() {
        roleListState.setValue(Resource.loading());
        useCase.getRolesUseCase.execute(resource -> roleListState.setValue(resource));
    }
    public void addRole(Role role) {
        actionState.setValue(Resource.loading());
        useCase.addRoles.execute(role, resource -> actionState.setValue(resource));
    }
    public void updateRole(Role role) {
        actionState.setValue(Resource.loading());
        useCase.updateRoles.execute(role, resource -> actionState.setValue(resource));
    }
    public void deleteRole(String roleId) {
        actionState.setValue(Resource.loading());
        useCase.deleteRolesUseCase.execute(roleId, resource -> actionState.setValue(resource));
    }
    public void clearActionState() {
        actionState.setValue(null);
    }
}
