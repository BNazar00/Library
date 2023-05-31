package com.bn.library.service;

import com.bn.library.constant.RoleData;
import com.bn.library.model.Role;

public interface RoleService {
    Role getOrCreateRole(RoleData role);
}
