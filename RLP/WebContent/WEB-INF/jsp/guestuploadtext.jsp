<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>RLP - Input text</title>
        <jsp:include page="commonhead.jsp" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class = "grid" id ="formtext">
            <form:form  modelAttribute="fromTextArea" method="post"> 
                <table> 
                    <tr> 
                    <p>Text:</p>
                    <p><form:textarea  id="textfild" path="body" maxlength="3000" style="width: 100%; height: 200px;"/></p>
                    </tr> 
                    <tr>
                        <td colspan="2">
                            <button type="submit" formaction="/RLP/guest/guestuploadtext" onclick="document.getElementById('formtext').style.display = 'none';document.getElementById('errordiv').style.display = 'none';
                                document.getElementById('loader').style.display = 'block';">Analyze</button>
                        </td> 
                    </tr> 
                </table> 
            </form:form>   
        </div>
        <div class = "grid">
            <img id="loader" src="/RLP/resources/img/loader.gif" style="display: none; margin:0 auto;"/>
        </div>    
    </body>
</html>



