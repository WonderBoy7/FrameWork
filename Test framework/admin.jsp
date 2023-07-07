<%@ page import="java.util.*,model.*" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p>Welcom admin</p>
    <p><%out.println(request.getAttribute("message"));%></p>
    <p>Test object sending : 
        <%
        if(request.getAttribute("obj") != null){
            Test ob = (Test) request.getAttribute("obj");
            out.print(ob.getTest());
            out.print(ob.getNom());
        }
        %>
    </p>
</body> 
</html>