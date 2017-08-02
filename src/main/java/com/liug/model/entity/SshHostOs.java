package com.liug.model.entity;

/**
 * Created by liugang on 2017/7/31.
 */
public class SshHostOs {
    long id;
    String name;
    String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SshHostOs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
