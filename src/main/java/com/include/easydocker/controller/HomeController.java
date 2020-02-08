package com.include.easydocker.controller;

import com.include.easydocker.utils.Hasher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("selection", true);
        return "home";
    }

    @RequestMapping("/sign_in")
    public String changeToSignInView(Model model){
        model.addAttribute("sign-in", true);
        return "home";
    }

    @RequestMapping("/sign_up")
    public String changeToSignUpView(Model model){
        model.addAttribute("sign-up", true);
        return "home";
    }

    @RequestMapping("/signing_up")
    public String signUp(Model model, @RequestParam String user, @RequestParam String password) {

        if (!checkUser(user, Hasher.hash(password)))
            return "app";

        model.addAttribute("sign-up", true);

        return "home";
    }

    @RequestMapping("/signing_in")
    public String signIn(Model model, @RequestParam String user, @RequestParam String password) {

        if (checkUser(user, Hasher.hash(password)))
            return "app";

        model.addAttribute("sign-in", true);

        return "home";
    }

    private boolean checkUser(String user, String hash) {
        // Todo: buscar en la base de datos y devover true si está y falso si no.
        return false;
    }
}
