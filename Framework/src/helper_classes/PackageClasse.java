package helper_classes;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Vector;
import annotation.*;

import etu1897.framework.Mapping;


public class PackageClasse {
    static String[] methodsName = {"equals", "toString", "hashCode", "annotationType", "isProxyClass", "newProxyInstance", "getInvocationHandler", "getProxyClass", "wait", "getClass", "notify", "notifyAll"};
/* 
 * equals
 * hashCode
 * toString
 * annotationType
 */
    public static File[] getFileInPackage(String packageName){
        File[] files = convert(getFiles(packageName));
        return files;
    }
    public static Vector<File> getFiles(String folder) {
        File file = new File(folder);
        Vector<File> reponse = new Vector<>();
        File[] files = file.listFiles();
        if(files == null) {
            return reponse;
        }
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()) {
                reponse.addAll(
                getFiles(files[i].getPath())
                );
            } else {
                if(files[i].getName().endsWith(".class")) {
                    reponse.add(files[i]);
                }        
            }
        }
        return reponse;
    }

    public static File[] convert(Vector<File> listFiles) {
        File[] toReturn = new File[listFiles.size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = listFiles.get(i);
        }
        return toReturn;
    }

    public static String getClassName(File file) {
        String className = file.getPath();
        className = className.replace("\\", ".");
        className = className.substring(className.indexOf("classes")+8);
        className = className.substring(0, className.length() - 6);
        System.out.println("CLASSNAME : "+className);
        return className;
    }

    public static void initHashmap(String packagePath,String packageName, HashMap<String, Mapping> map, HashMap<String, Object> sigleton){
        System.out.println("Package name : "+packagePath);
            File[] files = getFileInPackage(packagePath);

            // Obtenez toutes les classes de ce package
            for (File file : files) {
                try {
                    String className = getClassName(file);
                    Class<?> cls = Class.forName(className);
                    System.out.println("\tclasses = "+cls.getCanonicalName());
                    if (cls.isAnnotationPresent(Scope.class)) {
                        //Annotation scope = cls.getDeclaredAnnotation(Scope.class);
                        System.out.println("sigleton checking");
                        
                        //String type = (String) scope.getClass().getDeclaredMethod("types").invoke(scope);
                        String type = cls.getAnnotation(Scope.class).types().toString();
                        System.out.println("\t type: "+type);
                        if (type.equals("singleton")) {
                            System.out.println("Class "+cls.getSimpleName()+" is sigleton");
                            sigleton.put(cls.getName(),null);
                        }
                    }
                    if (!Modifier.isAbstract(cls.getModifiers())) {
                        // Obtenez toutes les méthodes de cette classe
                        Method[] methods = cls.getDeclaredMethods();
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(Url.class)) {
                                Annotation annotation = method.getDeclaredAnnotation(Url.class);
                                String key = (String) annotation.getClass().getDeclaredMethod("value").invoke(annotation);
                                System.out.println("c'est la mort !!! "+className+" , "+method.getName());
                                map.put(key, new Mapping(className, method.getName()));
                                
                            }
                        }
                    }
                } catch (Exception e) {
                    //out.println("Error: " + e.getMessage());
                    System.out.println("Error :" + e);
                }
                
            }
    }



    // public static void CheckClassesAnnotation(String packageName, Class<? extends Annotation>...specific){
    //     File[] files = getFileInPackage(packageName);
    //     for (File file : files) {
    //         if (specific.length > 0) {
    //             String fileName = file.getName();
    //                 String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
    //                 try {
    //                     Class<?> cls = Class.forName(className);
    //                     for (Class<? extends Annotation> annot : specific) {
    //                         if (cls.isAnnotationPresent(annot)) {
    //                             System.out.println(className);
    //                         }
    //                     }
    //                 } catch (ClassNotFoundException e) {
    //                     e.printStackTrace();
    //                 }
                
    //         } else {
    //             String fileName = file.getName();
    //                 String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
    //                 try {
    //                     Class<?> cls = Class.forName(className);
    //                     java.lang.annotation.Annotation[] annotations = cls.getAnnotations();
    //                     if (annotations.length > 0) {
    //                         System.out.println(cls.getName() + " est annote avec " + annotations[0].annotationType().getSimpleName());
    //                     } else {
    //                         System.out.println(cls.getName() + " n'est pas annote");
    //                     }
    //                 } catch (ClassNotFoundException e) {
    //                     e.printStackTrace();
    //                 }
    //         }
    //     }
    // }

    // public static void getValueAnnotation(String packageName, Class<? extends Annotation>...specific) {
    //     File[] files = getFileInPackage(packageName);
    //     for (File file : files) {
    //         if (specific.length > 0) {
    //             String fileName = file.getName();
    //                 String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
    //                 try {
    //                     Class<?> cls = Class.forName(className);
    //                     System.out.println(cls.getClassLoader());
    //                     Object ze  = cls.getClassLoader();
    //                     for (Class<? extends Annotation> annot : specific) {
    //                         if (cls.isAnnotationPresent(annot)) {
    //                             Method[] methods = cls.getAnnotation(annot).getClass().getMethods();
    //                             for (Method metho : methods) {
    //                                 String methodName = metho.getName();
    //                                 if (notIn(methodsName, methodName)) {
                                        
    //                                     System.out.println("Voici le truc "+metho.invoke(ze));
    //                                     System.out.println(metho.getName());
    //                                 }
    //                             }
    //                         }
    //                     }
    //                 } catch (Exception e) {
    //                     e.printStackTrace();
    //                 }
    //         } else {

    //         }
    //     }
    // }

    // public static void CheckField(Object ob){
    //     Field[] field = ob.getClass().getDeclaredFields();
    //     for(Field fiel : field){
    //         java.lang.annotation.Annotation[] annotations = fiel.getAnnotations();
    //         if(annotations.length>0){
    //             System.out.println(fiel.getName() + " est annoté avec "+ annotations[0].annotationType().getSimpleName());
    //         }
    //         else{
    //             System.out.println(fiel.getName()+" n'est pas annoté");
    //         }
    //     }        
    // }

    // public static void getMethodAnnotation(Object ob) {
    //     // Method[] all = ob.getClass().getDeclaredMethods();
    //     // for (Method method : all) {
    //     //     if (method.get) {
                
    //     //     }
    //     // }
    // }

    public static boolean notIn(String[] all,String target) {
        for (String item : all) {
            if (target.equals(item)) {
                return false;
            }
        }
        return true;
    }
}
