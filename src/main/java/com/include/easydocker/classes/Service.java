package com.include.easydocker.classes;

import com.include.easydocker.utils.Utils;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String image;
    private String properties;

    @ManyToOne
    private Template template;

    @ManyToMany(mappedBy = "services")
    private List<Network> networks;

    @ManyToMany(mappedBy = "services")
    private List<Volume> volumes;

    public Service() {
    }

    public Service(String name){
        this.name = name;
    }

    public String toCompose(){
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(this.getName()).append(":\n");
        sb.append("    image: ").append(this.getImage()).append("\n");
        if (this.getProperties() != null){
            String[] lines = properties.split("\\r?\\n");
            for(String line : lines)
                sb.append("    ").append(line).append("\n");
            sb.append("\n");
        }
        if (!networks.isEmpty()) {
            sb.append("    networks:\n");
            for (Network network : networks)
                sb.append("    - ").append(network.getName()).append("\n");
        }
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}











