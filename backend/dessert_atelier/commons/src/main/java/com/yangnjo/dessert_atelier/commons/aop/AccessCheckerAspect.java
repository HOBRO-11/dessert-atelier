package com.yangnjo.dessert_atelier.commons.aop;

import java.lang.reflect.Parameter;
import java.nio.file.AccessDeniedException;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccessCheckerAspect {

    @Before("@annotation(com.yangnjo.dessert_atelier.commons.aop.AccessChecker)")
    public void check(MethodInvocationProceedingJoinPoint joinPoint) throws AccessDeniedException {
        AccessCheckable clazz = null;

        // 클래스의 AccessCheckable 인스턴스를 얻는다.
        if (joinPoint.getTarget() instanceof AccessCheckable) {
            clazz = (AccessCheckable) joinPoint.getTarget();
        } else {
            throw new IllegalArgumentException("target class must implements AccessCheckable");
        }

        // 해당 인스턴스 메소드에 AccessChecker 어노테이션의 값을들 확인한다.
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AccessChecker annotation = signature.getMethod().getAnnotation(AccessChecker.class);
        String memberIdName = annotation.memberId();
        String entityIdName = annotation.entityId();

        Parameter[] parameters = signature.getMethod().getParameters();

        Long memberId = null;
        Long entityId = null;

        // 해당 인스턴스 메소드의 파라미터에 memberId 또는 entityId 값이 있는지 확인한다.
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals(memberIdName)) {
                memberId = (Long) joinPoint.getArgs()[i];
            }

            else if (parameters[i].getName().equals(entityIdName)) {
                entityId = (Long) joinPoint.getArgs()[i];
            }
        }

        if (memberId == null || entityId == null) {
            throw new IllegalArgumentException("memberId or entityId is null");
        }

        // aop logic 을 실행한다.
        clazz.check(memberId, entityId);

    }

}
