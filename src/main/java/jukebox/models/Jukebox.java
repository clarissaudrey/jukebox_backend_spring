package main.java.jukebox.models;

import java.util.List;
import java.util.Properties;

public class Jukebox {
    private String id;
    private String model;
    private List<Properties> components;

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public List<Properties> getComponents() {
        return components;
    }
}
