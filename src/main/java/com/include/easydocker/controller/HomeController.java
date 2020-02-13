package com.include.easydocker.controller;

import com.include.easydocker.services.HomeService;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    public final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("selection", true);
        return "home";
    }

    @GetMapping("/sign_in")
    public String changeToSignInView(Model model){
        model.addAttribute("sign-in", true);
        return "home";
    }

    @GetMapping("/sign_up")
    public String changeToSignUpView(Model model){
        model.addAttribute("sign-up", true);
        return "home";
    }

    @PostMapping(value = "/signing_up")
    public String signUp(Model model, @RequestParam String user, @RequestParam String password) {

        if (homeService.signedUpSuccessfully(user, password))
            return Utils.redirectTo("/user-overview");

        model.addAttribute("sign-up", true);

        return "home";
    }

    @PostMapping(value = "/signing_in")
    public String signIn(Model model, @RequestParam String user, @RequestParam String password) {

        if(homeService.signedInSuccessfully(user, password))
            return Utils.redirectTo("/user-overview");

        model.addAttribute("sign-in", true);

        return "home";
    }

    @GetMapping("/user-temporal")
    public String createUserTemporal() {
        homeService.createUserTemporal();
        return Utils.redirectTo("/user-overview");
    }
}
