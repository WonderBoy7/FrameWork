package helper_classes;

import javax.jws.WebParam.Mode;

public class Modelview {
    String view;
    public Modelview (String view){
        this.view = view;
    }
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    
}
