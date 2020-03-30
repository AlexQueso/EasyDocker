package com.include.easydocker.controller;

import com.include.easydocker.utils.Utils;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Override
    public String getErrorPath() {
        return Utils.redirectTo("/log-out");
    }
}
