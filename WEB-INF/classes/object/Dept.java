package object;

import annotation.*;

@Model(table = "test")
public class Dept {
    @Field(name = "DeptId")
    int id;

    String test;

    public Dept () {

    }

    @Url(value = "url")
    public void sayHello() {
        System.out.println("hello world !!");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
