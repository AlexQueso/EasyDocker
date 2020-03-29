package com.include.easydocker.security;


import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        modelAndView.addObject("token", token.getToken());
    }
}
