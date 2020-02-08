package com.include.easydocker.controller;

import com.include.easydocker.classes.User;
import com.include.easydocker.services.UsersService;
import com.include.easydocker.utils.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;


@Controller
public class HomeController {

    @Autowired
    private UsersService usersService;

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
    public String signUp(Model model, @RequestParam String user, @RequestParam String password ) {

        String hashedPwd = Hasher.hash(password);
        User newUser = checkUser(user, hashedPwd);

        if (newUser == null) {

            newUser = new User(user, hashedPwd, new LinkedList<>());

            // TODO: Registrar el usuario en la base de datos.

            usersService.setUser(newUser);

            return "redirect:/user-overview";
        }

        model.addAttribute("sign-up", true);

        return "home";
    }

    @PostMapping(value = "/signing_in")
    public String signIn(Model model, @RequestParam String user, @RequestParam String password ) {

        User welcomeUser = checkUser(user, Hasher.hash(password));

        if (welcomeUser != null) {
            usersService.setUser(welcomeUser);
            return "redirect:/user-overview";
        }

        model.addAttribute("sign-in", true);

        return "home";
    }

    private User checkUser(String user, String hash) {
        // Todo: buscar en la base de datos y devover el user, o null si no se encuentra.
        return null;
    }
}
