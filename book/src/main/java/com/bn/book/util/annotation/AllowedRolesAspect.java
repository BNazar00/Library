package com.bn.book.util.annotation;

import com.bn.book.constant.RoleData;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.NotYetImplementedFor6Exception;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AllowedRolesAspect {
    @Around("@annotation(com.bn.book.util.annotation.AllowedRoles)")
    public Object doSomething(ProceedingJoinPoint jp) throws Throwable {
        try {
            Set<RoleData> roles = Arrays
                    .stream(((MethodSignature) jp.getSignature()).getMethod().getAnnotation(AllowedRoles.class).value())
                    .collect(Collectors.toSet());
            //todo
            //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //for (RoleData role : roles) {
            //    if (user.getAuthorities().contains(new SimpleGrantedAuthority(role.getDBRoleName()))) {
            //        return jp.proceed();
            //    }
            //}
        } catch (ClassCastException e) {
            //throw new UserPermissionException();
            throw new NotYetImplementedFor6Exception();
        }
        throw new NotYetImplementedFor6Exception();

        //throw new UserPermissionException();
    }
}
