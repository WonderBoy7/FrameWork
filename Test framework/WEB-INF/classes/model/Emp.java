package model;

import annotation.*;
import helper_classes.Modelview;

@Model(table = "test")
public class Emp {
    @Field(name = "empId")
    int id;

    String name;

    public Emp () {

    }

    @Url(value = "getEmp")
    public static Modelview sayHello() {
        System.out.println("hello world !!");
        Modelview test = new Modelview("Hello.jsp");
        return test;
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
