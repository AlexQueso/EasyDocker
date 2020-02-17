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

        model.addAttribute("idTemplate", n.getTemplate().getId());
        model.addAttribute("idNetwork", n.getId());
        model.addAttribute("nameNetwork", n.getName());
        model.addAttribute("network", true);
        model.addAttribute("services", n.getServices());

        return "editor";
    }

    @GetMapping("/volume/{id}")
    public String volumeProperties(@PathVariable long id, Model model) {
        showLoggedInfoOrTemporal(model);

        Volume v = editorService.volumeProperties(id);

        model.addAttribute("idTemplate", v.getTemplate().getId());
        model.addAttribute("idVolume", v.getId());
        model.addAttribute("nameVolume", v.getName());
        model.addAttribute("volume", true);
        model.addAttribute("services", v.getServices());

        return "editor";
    }

    @PostMapping(value = "/add-service-to-network/{id}")
    public String addServiceToNetwork(@PathVariable long id, String name){
        Service s = editorService.getService(name);
        if (s != null)
            editorService.addServiceToNetwork(s, id);
        return Utils.redirectTo("/network/" + id);
    }

    @PostMapping(value = "/add-service-to-volume/{id}")
    public String addServiceToVolume(@PathVariable long id, String name){
        Service s = editorService.getService(name);
        if (s != null)
            editorService.addServiceToVolume(s, id);
        return Utils.redirectTo("/volume/" + id);
    }

    @GetMapping("/delete-from/{idTemplate}/{eliminate}/{id}")
    public String deleteFromTemplate(@PathVariable long id, @PathVariable long idTemplate,
                                     @PathVariable String eliminate) {
        switch (eliminate) {
            case "network": editorService.deleteNetwork(id);
                break;
            case "volume": editorService.deleteVolume(id);
                break;
            case "service": editorService.deleteService(id);
                break;
        }

        return Utils.redirectTo("/template/"+ idTemplate);
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
