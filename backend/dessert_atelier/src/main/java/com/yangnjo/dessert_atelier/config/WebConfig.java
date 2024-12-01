package com.yangnjo.dessert_atelier.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yangnjo.dessert_atelier.common.argument_resolver.CreateFormResolver;
import com.yangnjo.dessert_atelier.common.argument_resolver.MemberIdResolver;
import com.yangnjo.dessert_atelier.common.formatter.LocalDateTimeFormatter;
import com.yangnjo.dessert_atelier.interceptor.DisableCacheControlInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberIdResolver memberIdResolver;
    private final CreateFormResolver createFormResolver;
    private final DisableCacheControlInterceptor disableCacheableInterceptor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdResolver);
        resolvers.add(createFormResolver);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(disableCacheableInterceptor);
	}

    
}
