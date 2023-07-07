<%@ page import="java.util.*,model.*" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test JSP</title>
</head>
<body>
    <%  String t = (String) request.getAttribute("nom"); 
        double poid = (double) request.getAttribute("poids");
    %>
    Nom = <%= t  %><br>    
    nom= <%= poid  %>    
</body>
</html>