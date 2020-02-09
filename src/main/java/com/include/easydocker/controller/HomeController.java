package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import com.include.easydocker.repositories.ProjectRepository;
import com.include.easydocker.repositories.TemplateRepository;
import com.include.easydocker.repositories.UserRepository;
import com.include.easydocker.services.UsersService;
import com.include.easydocker.utils.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;


@Controller
public class HomeController {

    private final UsersService usersService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;

    @Autowired
    public HomeController(UsersService usersService,
                          UserRepository userRepository,
                          ProjectRepository projectRepository,
                          TemplateRepository templateRepository) {

        this.usersService = usersService;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
    }


    @PostConstruct
    public void init() {
        User user = new User("user1", Hasher.hash("1234"));
        userRepository.save(user);

        Project project = new Project("project example");
        project.setUser(user);
        projectRepository.save(project);

        Template template = new Template("template example");
        template.setProject(project);
        templateRepository.save(template);
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
    public String signUp(Model model, @RequestParam String user, @RequestParam String password ) {

        String hashedPwd = Hasher.hash(password);
        User newUser = checkUser(user, hashedPwd);

        if (newUser == null) {

            newUser = new User(user, hashedPwd);

            userRepository.save(newUser);
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
        return userRepository.findByNameAndPassword(user, hash);
    }
}
