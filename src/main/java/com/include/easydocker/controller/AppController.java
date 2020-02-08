package com.include.easydocker.controller;

import com.include.easydocker.classes.User;
import com.include.easydocker.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/user-overview")
    public String userPage(Model model) {

        if(usersService.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersService.getUser().getName());

        return "app";
    }

    @GetMapping("/user-temporal")
    public String temporalPage(Model model) {

        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());

        usersService.setUser(unknownUser);

        return "redirect:/user-overview";

    }


}
