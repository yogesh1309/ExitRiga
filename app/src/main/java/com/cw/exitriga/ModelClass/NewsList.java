package com.cw.exitriga.ModelClass;

import java.io.Serializable;

public class NewsList implements Serializable {
    public String name;
    public Integer image;

    public NewsList(String name, Integer image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

}
