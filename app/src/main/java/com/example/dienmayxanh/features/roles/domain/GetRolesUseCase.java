package com.example.dienmayxanh.features.roles.domain;

import com.example.dienmayxanh.features.roles.data.Role;
import com.example.dienmayxanh.features.roles.data.RolesRepository;

import java.util.List;

public class GetRolesUseCase {
    private RolesRepository repository = new RolesRepository();
    public void execute(RolesRepository.RoleCallback<List<Role>> callback) {
        repository.getRoles(callback);
    }
}
