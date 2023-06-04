package com.bn.library.service.impl;

import com.bn.clients.constant.RoleData;
import com.bn.library.model.Role;
import com.bn.library.repository.RoleRepository;
import com.bn.library.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getOrCreateRole(RoleData role) {
        return roleRepository.findRoleByName(role.getDBRoleName())
                .orElseGet(() -> roleRepository.save(new Role(role)));
    }
}
