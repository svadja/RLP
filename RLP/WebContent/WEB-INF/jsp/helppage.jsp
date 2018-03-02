<%-- 
    Document   : helppage
    Created on : Oct 7, 2013, 1:26:36 PM
    Author     : Vadya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <title>RLP - Help page</title>
        <jsp:include page="commonhead.jsp" />
        <style type="text/css">
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class = "grid">
            What are the most used words in the text? It's easy(But it may take a few minutes).<p>
            <img src="/RLP/resources/img/guestimg.png"/><p>
                Big bonus for logged-in(without registration):<p>
            <ul>
                <li>You can save your text</li>
                <li>You can create your own word list and filter the words of the text </li>
                <li>You can learn words by playing the game</li>
            </ul>
            <img src="/RLP/resources/img/authimg.png"/><p>
        </div>
    </body>
</html>
