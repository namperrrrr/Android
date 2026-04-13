package com.example.dienmayxanh.features.roles.domain;

import com.example.dienmayxanh.features.roles.data.RolesRepository;

public class DeleteRolesUseCase {
    private RolesRepository repository = new RolesRepository();
    public void execute(String roleId, RolesRepository.RoleCallback<String> callback) {
        repository.deleteRoles(roleId, callback);
    }
}
