package com.bn.library.util.annotation;

import com.bn.clients.constant.RoleData;
import com.bn.library.exception.UserPermissionException;
import com.bn.library.model.User;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AllowedRolesAspect {
    @Around("@annotation(com.bn.library.util.annotation.AllowedRoles)")
    public Object doSomething(ProceedingJoinPoint jp) throws Throwable {
        try {
            Set<RoleData> roles = Arrays
                    .stream(((MethodSignature) jp.getSignature()).getMethod().getAnnotation(AllowedRoles.class).value())
                    .collect(Collectors.toSet());
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for (RoleData role : roles) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority(role.getDBRoleName()))) {
                    return jp.proceed();
                }
            }
        } catch (ClassCastException e) {
            throw new UserPermissionException();
        }
        throw new UserPermissionException();
    }
}
