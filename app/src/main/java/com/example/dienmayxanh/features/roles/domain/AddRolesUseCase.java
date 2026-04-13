package com.example.dienmayxanh.features.roles.domain;

import com.example.dienmayxanh.features.roles.data.Role;
import com.example.dienmayxanh.features.roles.data.RolesRepository;

public class AddRolesUseCase {
    private RolesRepository repository = new RolesRepository();
    public void execute(Role role, RolesRepository.RoleCallback<String> callback) {
        repository.addRoles(role, callback);
    }
}
