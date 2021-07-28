package com.cw.exitriga.ModelClass;

import java.io.Serializable;
import java.util.List;

public class AllRoutesMain implements Serializable {

    private String name;
    private List<AllRoutesListData> data = null;

    public AllRoutesMain(String name, List<AllRoutesListData> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AllRoutesListData> getData() {
        return data;
    }

    public void setData(List<AllRoutesListData> data) {
        this.data = data;
    }
}
