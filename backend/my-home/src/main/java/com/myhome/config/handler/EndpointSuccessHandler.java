package com.myhome.config.handler;

import com.myhome.config.core.GlobalResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * response Body 변환 전 공통 작업 처리
 */
@RestControllerAdvice
public class EndpointSuccessHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // TODO : 어떤 경우에만 해당 Advice탈지 결정해야됨 - 일단 모든 경우에대해서 다 탈수 있도록 처리
        return !returnType.getParameterType().equals(GlobalResponse.error.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        
        HttpServletRequest oldRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Long startTime = Optional.ofNullable((Long)oldRequest.getAttribute("beforeStartTime")).orElseGet(() -> -1L);
        
        GlobalResponse.success<?> returnClass = new GlobalResponse.success<>(body);
        returnClass.setPath(request.getURI().getPath());

        return returnClass;
    }

}
