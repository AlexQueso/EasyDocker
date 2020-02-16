package com.include.easydocker.controller;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.services.EditorService;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditorController {

    public EditorService editorService;

    @Autowired
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @PostMapping(value = "/new-volume/{idTemplate}")
    public String createVolume(@PathVariable long idTemplate, Volume volume) {
        editorService.createVolume(idTemplate, volume);
        return Utils.redirectTo("/template/" + idTemplate);
    }

    @PostMapping(value = "/new-network/{idTemplate}")
    public String createNetwork(@PathVariable long idTemplate, Network network) {
        editorService.createNetwork(idTemplate, network);
        return Utils.redirectTo("/template/" + idTemplate);
    }

    @PostMapping(value = "/new-service/{idTemplate}")
    public String createService(@PathVariable long idTemplate, Service service) {
        editorService.createService(idTemplate, service);
        return Utils.redirectTo("/template/" + idTemplate);
    }

    @GetMapping("/network/{id}")
    public String networkProperties(@PathVariable long id, Model model) {
        showLoggedInfoOrTemporal(model);

        Network n = editorService.networkProperties(id);

//        model.addAttribute("idNetwork", n.getId());
        model.addAttribute("idTemplate", n.getTemplate().getId());
        model.addAttribute("nameNetwork", n.getName());
        model.addAttribute("network", true);
        model.addAttribute("services", n.getTemplate().getServices());

        return "editor";
    }

    private void showLoggedInfoOrTemporal(Model model) {
        if(editorService.getUsersSession().isLogged())
            model.addAttribute("user-logged", true);
        else
            model.addAttribute("user-temporal", true);

        model.addAttribute("username", editorService.getUsersSession()
                .getUser().getName());
    }
}
