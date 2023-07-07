package model;

import java.util.ArrayList;
import java.util.List;
import annotation.*;
import etu1897.framework.*;

@Model(table = "test")
public class Emp {
    @Field(name = "empId")
    int id;

    String name;

    public Emp () {

    }
    

    public Emp(String name) {
        this.name = name;
    }


    @Url(value = "getEmp")
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

    @Url(value = "getid")
    public int getId() {
        return id;
    }

    @Url(value = "setid")
    public void setId(int id) {
        this.id = id;
    }

    @Url(value = "getEmpName")
    public String getname() {
        return name;
    }

    @Url(value = "setEmpName")
    public void setname(String name) {
        this.name = name;
    }
    
}
