package com.liug.black.common.ssh;

/**
 * Created by liugang on 2017/6/20.
 */
public class SelectContnet {
    long id;
    String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SelectContnet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
