package com.mindskip.xzs.configuration.spring.security;

import com.mindskip.xzs.base.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    @Autowired
    public RestLogoutSuccessHandler() {
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // could do something, but nothing will be done in this system
        RestUtil.response(response, SystemCode.OK);
    }
}
