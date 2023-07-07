package helper_classes;


public class Util {
    // exemple URL : http://localhost:8080/Spring1/doHttpProcess
    public static String getParamURL(String url) {
        String paramUrl = "";
        String domain = url.substring(url.indexOf("//")+2);
        domain = domain.substring(domain.indexOf("/")+1);
        String temp = domain.substring(domain.indexOf("/")+1);
        if (temp.contains("?")) {
            temp = temp.replace("?", "//Split//");
            paramUrl = temp.split("//Split//")[0];
        } else {
            paramUrl = temp;
        }
        return paramUrl;
    }
}
