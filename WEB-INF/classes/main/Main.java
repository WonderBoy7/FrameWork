package main;

import java.io.File;
import java.util.HashMap;
import etu1897.framework.*;
import helper_classes.*;

public class Main {
    public static void main(String[] args) {
        File workspace = new File(".");
        String[] test=workspace.list();
        HashMap<String, Mapping> map = new HashMap<>();
        for (String string : test) {
            System.out.println("file :"+string);
        }
        File te = new File("./annotation");
        //PackageClasse.checkMethod("C:/Program Files/Apache Software Foundation/Tomcat 10.0/webapps/Sprint3/annotation", map);
        String[] tes = te.list();
        for (String string : tes) {
            System.out.println("file test : "+string);
        }
        //System.out.println("test = "+map.get("test2").getMethod());
    }
}
