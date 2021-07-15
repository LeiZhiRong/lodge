package com.shgs.lodge.servlet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;


public class SessionScopeMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (parameter.hasParameterAnnotation(SessionScope.class))
      return true;
    else return parameter.getMethodAnnotation(SessionScope.class) != null;
  }

  // 解析请求中的参数为某个函数的参数对象
  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    String annoVal = null;

    if (parameter.getParameterAnnotation(SessionScope.class) != null) {
      annoVal = Objects.requireNonNull(parameter.getParameterAnnotation(SessionScope.class)).value();
    } else if (parameter.getMethodAnnotation(SessionScope.class) != null) {
      annoVal = parameter.getMethodAnnotation(SessionScope.class) != null ? StringUtils.defaultString(Objects.requireNonNull(parameter.getMethodAnnotation(SessionScope.class)).value()) : StringUtils.EMPTY;
    }
    if (webRequest.getAttribute(Objects.requireNonNull(annoVal), RequestAttributes.SCOPE_SESSION) != null) {
      return webRequest.getAttribute(annoVal, RequestAttributes.SCOPE_SESSION);
    } else
      return null;
  }

}

