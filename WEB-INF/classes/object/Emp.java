package object;

import annotation.*;

@Model(table = "test")
public class Emp {
    @Field(name = "empId")
    int id;

    String test;

    public Emp () {

    }

    @Url(value = "url_associate")
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
