package model;

import annotation.*;

@Model(table = "test")
public class Emp {
    @Field(name = "empId")
    int id;

    String name;

    public Emp () {

    }

    @Url(value = "getEmp")
    public void sayHello() {
        System.out.println("hello world !!");
    }

    @Url(value = "getEmp/id")
    public int getId() {
        return id;
    }

    @Url(value = "setEmp/id")
    public void setId(int id) {
        this.id = id;
    }

    @Url(value = "getEmp/name")
    public String getname() {
        return name;
    }

    @Url(value = "setEmp/name")
    public void setname(String name) {
        this.name = name;
    }
    
}
