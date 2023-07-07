package etu1897.framework;

import java.util.*;

public class Modelview {
    String view;
    HashMap<String, Object> datas;
    HashMap<String, Object> session;

    public Modelview (String view){
        this.setView(view);
        datas = new HashMap<>();
        session = new HashMap<>();
    }

    public void addItem(String key, Object item) {
        datas.put(key, item);
    }
    
    // GETTERS AND SETTERS
    public String getView() {
        return view;
    }
    public void setView(String view) {
        if (!view.contains(".jsp")) {
            view += ".jsp";
        }
        this.view = view;
    }

    public HashMap<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<String, Object> datas) {
        this.datas = datas;
    }

    public void setAttributeSession(String key, Object obj) {
        this.session.put(key, obj);
    }

    public HashMap<String, Object> getSession() {
        return this.session;
    }

    
}
