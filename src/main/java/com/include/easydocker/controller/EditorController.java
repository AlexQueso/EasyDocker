package com.include.easydocker.controller;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.services.EditorService;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EditorController {

    public EditorService editorService;

    @Autowired
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping(value = "/new-volume/{idTemplate}")
    public String createVolume(@PathVariable long idTemplate, Volume volume) {
        editorService.createVolume(idTemplate, volume);
        return Utils.redirectTo("/template/" + idTemplate);
    }

    @GetMapping(value = "/new-network/{idTemplate}")
    public String createNetwork(@PathVariable long idTemplate, Network network) {
        editorService.createNetwork(idTemplate, network);
        return Utils.redirectTo("/template/" + idTemplate);
    }

    @GetMapping(value = "/new-service/{idTemplate}")
    public String createService(@PathVariable long idTemplate, Service service) {
        editorService.createService(idTemplate, service);
        return Utils.redirectTo("/template/" + idTemplate);
    }
}
