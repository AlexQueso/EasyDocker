package com.include.easydocker.session;

import com.include.easydocker.classes.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TemporalUserInformation implements Serializable {

    private Map<Long, Project> projects = new HashMap<>();
    private Map<Long, Template> templates = new HashMap<>();
    private Map<Long, Network> network = new HashMap<>();
    private Map<Long, Service> services = new HashMap<>();
    private Map<Long, Volume> volumes = new HashMap<>();

    public Map<Long, Project> getProjects() {
        return projects;
    }

    public Map<Long, Template> getTemplates() {
        return templates;
    }

    public Map<Long, Service> getServices() {
        return services;
    }

    public Map<Long, Volume> getVolumes() {
        return volumes;
    }

    public Map<Long, Network> getNetwork() {
        return network;
    }
}
