<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html> 
    <head> 
        <title>RLP - My texts</title> 
        <jsp:include page="commonhead.jsp" />
    </head> 

    <body> 
        <jsp:include page="header.jsp" />
        <div class = "grid" id ="wordlist">
            <c:forEach items="${files}" var="file"> 
                <table> 
                    <tr>
                        <td width="80%">
                            <a href="f/${file.id}">${file.name}</a>
                        </td>

                        <td><a href="w/${file.id}" onclick="document.getElementById('wordlist').style.display = 'none';
                                document.getElementById('loader').style.display = 'block';" >
                                <c:choose>
                                    <c:when test="${file.analyzed}">
                                        List words
                                    </c:when>
                                    <c:otherwise>
                                        Analyze    
                                    </c:otherwise>
                                </c:choose>
                            </a>
                         &nbsp;
                         <a href="deletefile?fileid=${file.id}"><img src="/RLP/resources/img/delete.png" width="18" height="18" ></a>
                        </td>
                    </tr> 
                </table> 
                <hr /> 
            </c:forEach> 
        </div>
        <div class = "grid">
            <img id="loader" src="/RLP/resources/img/loader.gif" style="display: none; margin:0 auto;"/>
        </div>

    </body> 
</html>