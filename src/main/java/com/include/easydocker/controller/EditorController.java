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

    @GetMapping("/service/{id}")
    public String serviceOverview(Model model, @PathVariable long id) {
        //TODO
        return "properties";
    }

    @GetMapping("/network/{id}")
    public String networkOverview(Model model, @PathVariable long id) {
        //TODO
        return "properties";
    }

    @GetMapping("/volume/{id}")
    public String volumeOverview(Model model, @PathVariable long id) {
        //TODO
        return "properties";
    }
}
