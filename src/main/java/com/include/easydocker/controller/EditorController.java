package com.include.easydocker.controller;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.repositories.*;
import com.include.easydocker.services.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditorController {

    private final TemplateRepository templateRepository;
    private final VolumesRepository volumesRepository;
    private final ServiceRepository serviceRepository;
    private final NetworkRepository networkRepository;

    @Autowired
    public EditorController(TemplateRepository templateRepository,
                            VolumesRepository volumesRepository,
                            ServiceRepository serviceRepository,
                            NetworkRepository networkRepository) {

        this.templateRepository = templateRepository;
        this.volumesRepository = volumesRepository;
        this.serviceRepository = serviceRepository;
        this.networkRepository = networkRepository;
    }

    @GetMapping(value = "/new-volume/{idTemplate}")
    public String createVolume(@PathVariable long idTemplate, Volume volume) {

        volume.setTemplate(templateRepository.findById(idTemplate));
        volumesRepository.save(volume);

        return redirect(idTemplate);
    }

    private String redirect(long idTemplate) {
        return "redirect:/template/" + idTemplate;
    }

    @GetMapping(value = "/new-network/{idTemplate}")
    public String createNetwork(@PathVariable long idTemplate, Network network) {

        network.setTemplate(templateRepository.findById(idTemplate));
        networkRepository.save(network);

        return redirect(idTemplate);
    }

    @GetMapping(value = "/new-service/{idTemplate}")
    public String createService(@PathVariable long idTemplate, Service service) {

        service.getTemplates().add(templateRepository.findById(idTemplate));
        serviceRepository.save(service);

        return redirect(idTemplate);
    }

}
