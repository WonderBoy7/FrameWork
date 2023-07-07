package etu1897.framework.servlet;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;
import helper_classes.*;
import etu1897.framework.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import annotation.Auth;
import annotation.SessionConfig;

@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrls; 
    HashMap<String, Object> sigleton;
    String sessionName;
    String sessionProfil;

    @Override
    public void init() throws ServletException{
        this.sessionName = getInitParameter("sessionName");
        this.sessionProfil = getInitParameter("sessionProfil");
        System.out.println("SessionName : "+this.sessionName+ " SessionProfil : "+this.sessionProfil);
        this.MappingUrls = new HashMap<String, Mapping>();
        this.sigleton = new HashMap<String, Object>();
        ServletContext context = getServletContext();
        String absolutePath = context.getRealPath("/WEB-INF/classes");
        File workspace = new File(absolutePath);
        String[] test=workspace.list();
        for (String packageName : test) {
            String packagePath = absolutePath+'\\'+packageName;
            PackageClasse.initHashmap(packagePath,packageName, this.MappingUrls, sigleton);
        }
    }
    

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        if (request.getMethod().equals("GET")) {
            out.println("Method GET");
        } else if (request.getMethod().equals("POST")) {
            out.println("Method POST");
        } else {
            // Si la méthode HTTP n'est ni GET ni POST, on renvoie une erreur "Méthode non autorisée"
            out.println("Methode non reconnu");
        }
        processRequest(request, response);
    }
    protected void processRequest (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        String url = String.valueOf(req.getRequestURL());
        String parameter_url = Util.getParamURL(url);
        //String method = req.getMethod();

        out.println("URL : "+url);
        out.println("MAPPING :"+this.MappingUrls.toString());
        out.println("Parameter url : "+parameter_url);
        Mapping mapping = this.MappingUrls.get(parameter_url);
        if (mapping != null) {
            out.println("Value of "+parameter_url+": ");
            out.println("\t ClassName : "+mapping.getClassName());
            out.println("\t Method : "+mapping.getMethod());

            try {
                Class<?> cls = Class.forName(mapping.getClassName());
                Object obj = this.iniObject(cls, mapping, req);
                Object value = Util.invokeMethod(req, mapping, obj);
                
                Modelview view = (Modelview) value;
                this.setDatas(req, view);
                HashMap<String, Object> session = null;
                if (sessionPresent(obj)!= "") {
                    session = (HashMap<String, Object>) obj.getClass().getMethod("get"+Util.capitalize(sessionPresent(obj))).invoke(obj);
                    
                } else {
                    session = view.getSession();
                }
                this.setSession(req, session);
                displaySession(req);
                if (checkAuth(mapping, req)) {
                    if (checkProfil(req, stringMatching(cls.getDeclaredMethods(), mapping.getMethod()))) {
                        System.out.println("profil : true");
                        req.getRequestDispatcher(view.getView()).forward(req, res);
                    } else {
                        System.out.println("profil : false");
                        returnToPage(req, res);
                    }
                } else {
                    req.getRequestDispatcher(view.getView()).forward(req, res);
                }
                
                /*if (value instanceof Modelview) {
                    Modelview myview = (Modelview) value;
                    req.getRequestDispatcher(myview.getView()).forward(req,res);
                }*/
            } catch (Exception e) {
                out.println(e);
            }
        } else {
            out.println("The url `"+parameter_url+"` is not defined");
        }
    }

    private String sessionPresent(Object obj) {
        String toReturn = "";
        
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SessionConfig.class)) {
                toReturn = field.getName();
            }
        }
        return toReturn;
    }


    public void displaySession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ArrayList<String> attribute = Collections.list(session.getAttributeNames());
        for (String attr : attribute) {
            System.out.println("================== Attribute : "+attr + " / Value : "+session.getAttribute(attr));
        }
        System.out.println("=========");
        System.out.println();
    }

    public boolean checkAuth(Mapping map,HttpServletRequest req) throws Exception{
        boolean toReturn = false;

        Class<?> cls = Class.forName(map.getClassName());
        Method meth = stringMatching(cls.getDeclaredMethods(), map.getMethod());
        if (meth.isAnnotationPresent(Auth.class)) {
            toReturn = true;
        }
        return toReturn;
    }

    public boolean checkProfil(HttpServletRequest req, Method meth) {
        boolean toReturn = false;
        HttpSession session = req.getSession();
        if (session.getAttribute(this.sessionName) != null) {
            String profil = meth.getAnnotation(Auth.class).profil();
            System.out.println("PROFIL : "+profil);
            if (profil.equals("simple_user")) {
                toReturn = true;
            } else {
                String sessionprofil = session.getAttribute(this.sessionProfil).toString();
                System.out.println("PROFIL IN SESSION : "+sessionprofil);
                if (sessionprofil.equals(profil)) {
                    toReturn = true;
                } else {
                    toReturn = false;
                }
            }
        }
        return toReturn;
    }

    public Method stringMatching(Method[] methods, String methodName) {
        Method matching = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                matching = method;
            }
        }
        return matching;
    }

    public void setDatas(HttpServletRequest req, Modelview view) {
        HashMap<String, Object> datas = view.getDatas();
        for (String key : datas.keySet()) {
            req.setAttribute(key, datas.get(key));
        }
    }

    public void setSession(HttpServletRequest req, HashMap<String, Object> session) throws Exception{
        HttpSession httpSession = req.getSession();
        for (String key : session.keySet()) {
            System.out.println("////////////// SETTING SESSION");
            httpSession.setAttribute(key, session.get(key));
        }
    }

    public Object iniObject(Class<?> cls, Mapping map, HttpServletRequest req) throws ServletException, Exception, NoSuchMethodException, IOException{
        Object toReturn = null;
        String key = cls.getName();
        if (this.sigleton.containsKey(key)) {
            System.out.println("the object is a sigleton");
            if (this.sigleton.get(key) == null) {
                toReturn = cls.getDeclaredConstructor().newInstance();
                Util.setObjectField(toReturn, req);
                this.sigleton.put(key, toReturn);
                //System.out.println("the object is a sigleton(init)");
            } else {
                Util.resetObject(this.sigleton.get(cls.getName()));
                toReturn = this.sigleton.get(key);
                Util.setObjectField(toReturn, req);
            }
        } else {
            toReturn = Util.initObjectForm(req, map);
        }
        return toReturn;
    }

    public void returnToPage(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String referer = req.getHeader("Referer");
        res.sendRedirect(referer);
    }

    
}
