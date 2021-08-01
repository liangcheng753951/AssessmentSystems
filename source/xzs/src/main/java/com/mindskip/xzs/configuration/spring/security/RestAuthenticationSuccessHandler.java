package com.mindskip.xzs.configuration.spring.security;

import com.mindskip.xzs.base.SystemCode;
import com.mindskip.xzs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login successful return
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public RestAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Object object = authentication.getPrincipal();
        if (null != object) {
            User springUser = (User) object;
            com.mindskip.xzs.domain.User user = userService.getUserByUserName(springUser.getUsername());
            if (null != user) {
                com.mindskip.xzs.domain.User newUser = new com.mindskip.xzs.domain.User();
                newUser.setUserName(user.getUserName());
                newUser.setImagePath(user.getImagePath());
                RestUtil.response(response, SystemCode.OK.getCode(), SystemCode.OK.getMessage(), newUser);
            }
        } else {
            RestUtil.response(response, SystemCode.UNAUTHORIZED.getCode(), SystemCode.UNAUTHORIZED.getMessage());
        }
    }
}
