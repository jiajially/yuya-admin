
package com.liug.model.entity;
import java.util.Date;
public class MantisProjectSummary {
Integer id;
String name;
Integer together;
Integer notsovled;
Integer sovled;
Integer closed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTogether() {
        return together;
    }

    public void setTogether(Integer together) {
        this.together = together;
    }

    public Integer getNotsovled() {
        return notsovled;
    }

    public void setNotsovled(Integer notsovled) {
        this.notsovled = notsovled;
    }

    public Integer getSovled() {
        return sovled;
    }

    public void setSovled(Integer sovled) {
        this.sovled = sovled;
    }

    public Integer getClosed() {
        return closed;
    }

    public void setClosed(Integer closed) {
        this.closed = closed;
    }
}