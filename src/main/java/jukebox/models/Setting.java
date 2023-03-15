package main.java.jukebox.models;

import java.util.List;

public class Setting {
    private String id;
    private List<String> requires;

    public Setting(String id, List<String> requires) {
        this.id = id;
        this.requires = requires;
    }

    public String getId() {
        return id;
    }

    public List<String> getRequires() {
        return requires;
    }
}