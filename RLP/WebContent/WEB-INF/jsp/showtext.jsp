<%-- 
    Document   : helppage
    Created on : Oct 7, 2013, 1:26:36 PM
    Author     : Vadya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>RLP - Text</title>
        <jsp:include page="commonhead.jsp" />
        <style type="text/css">
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="grid">
        <h5>${titlefile}</h5>
            <div>
                <pre>${textfile}</pre>
            </div>
        </div>
    </body>
</html>
