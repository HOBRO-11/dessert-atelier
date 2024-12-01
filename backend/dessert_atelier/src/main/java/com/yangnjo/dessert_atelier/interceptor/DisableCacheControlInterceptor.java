package com.yangnjo.dessert_atelier.interceptor;

import java.lang.annotation.Annotation;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yangnjo.dessert_atelier.common.annotation.DisableCacheControl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DisableCacheControlInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        if (isSupport(handler) == false) {
            return;
        }

        response.setHeader("Cache-Control", "public, max-age=31536000");
        response.setHeader("Last-Modified", LocalDate.now().toString());
        response.setHeader("Pragma", "");
        response.setHeader("Expires", "");
    }

    private boolean isSupport(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Annotation[] annotations = hm.getMethod().getAnnotations();

            for (Annotation annotation : annotations) {
                if (methodAnnotationIsBrowserCache(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean methodAnnotationIsBrowserCache(Annotation annotation) {
        return annotation.annotationType().equals(DisableCacheControl.class);
    }

}
