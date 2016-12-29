package com.distinktiv.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by distinktiv on 2016-12-26.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              ModelAndView model,
                              HttpServletRequest request){

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addObject("_csrf", csrfToken);
        }

        error = getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION");

        if (!StringUtils.isEmpty(error)) {
            model.addObject("error", error);
        }

        model.setViewName("login");

        return model;

    }

    private String getErrorMessage(javax.servlet.http.HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = exception.getMessage();
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        }
        return error;
    }
}
