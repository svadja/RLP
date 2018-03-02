<%-- 
    Document   : mainpage
    Created on : Jul 29, 2013, 10:08:06 AM
    Author     : Vadya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RLP - MAIN PAGE</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <a href="<c:url value="/testcontroler"/>">Test</a>
        <br>
        <a href="<c:url value="/welcomeuser"/>">Welcome user</a>
        <br>
        <a href="<c:url value="/login.jsp"/>">Login</a>
        <br>
        <a href="<c:url value="/regUser"/>">Registration User</a>
        <br>
        <a href="<c:url value="/userAdd"/>">User add</a>
        <br>
        <a href="<c:url value="/personAdd"/>">T person add</a>
        <br>
        <a href="<c:url value="/uploadfile"/>">Upload File</a>
        <br>
        <a href="<c:url value="/fileslist"/>">File List</a>
        <br>
        <a href="<c:url value="/mylist"/>">My Word List</a>
        <br>
    </body>
</html>
