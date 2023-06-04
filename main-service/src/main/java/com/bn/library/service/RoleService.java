package com.bn.library.service;

import com.bn.clients.constant.RoleData;
import com.bn.library.model.Role;

public interface RoleService {
    Role getOrCreateRole(RoleData role);
}
