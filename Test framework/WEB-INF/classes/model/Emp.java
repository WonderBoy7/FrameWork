package model;

import java.util.ArrayList;
import java.util.List;
import etu1897.framework.*;
import annotation.Scope;
import annotation.Url;

@Scope(types = "singleton")
public class Emp {
    int id;

    String name;

    FileUpload badge;

    public Emp () {

    }
    

    public Emp(String name) {
        this.name = name;
    }


    @Url(value = "getEmp.do")
    public static Modelview findAll() {
        System.out.println("hello world !!");
        Modelview mv = new Modelview("Hello.jsp");
        List<Emp> liste = new ArrayList<>();
        liste.add(new Emp("Jean"));
        liste.add(new Emp("Jeanne"));
        liste.add(new Emp("Rakoto"));
        mv.addItem("list", liste);
        return mv;
    }
    @Url(value = "save.do")
    public Modelview save(){
        Modelview mv = new Modelview("result.jsp");
        mv.addItem("my_object", this);
        
        return mv;
    }

    @Url(value = "test.do", param_name = "nom,poids")
    public Modelview test(String nom, double poids) {
        Modelview mv = new Modelview("result_arg.jsp");
        mv.addItem("nom", nom);
        mv.addItem("poids", poids);
        return mv;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileUpload getBadge() {
        return badge;
    }

    public void setBadge(FileUpload badge) {
        this.badge = badge;
    }
    
    
}
