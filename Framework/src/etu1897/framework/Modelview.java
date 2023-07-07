package etu1897.framework;

import java.util.*;

public class Modelview {
    String view;
    HashMap<String, Object> datas;

    public Modelview (String view){
        this.view = view;
        datas = new HashMap<>();
    }

    public void addItem(String key, Object item) {
        datas.put(key, item);
    }
    
    // GETTERS AND SETTERS
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<String, Object> datas) {
        this.datas = datas;
    }
}
