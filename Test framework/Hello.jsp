<%@ page import="java.util.*,model.*" contentType="text/html; charset=UTF-8"%>
<%
    List<Emp> liste = (List<Emp>) request.getAttribute("list");
    for (Emp emp : liste) {
        out.print(emp.getname()+"<br>");  
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
</body>
</html>