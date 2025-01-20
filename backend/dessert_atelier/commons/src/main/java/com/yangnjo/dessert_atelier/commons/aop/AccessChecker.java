package com.yangnjo.dessert_atelier.commons.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AccessChecker {
    
    /**
     * parameter name of memberId
     * @return
     */
    String memberId() default "memberId";

    /**
     * parameter name of entityId
     * @return
     */
    String entityId();
}
