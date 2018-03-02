<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>RLP - Upload text</title>
        <jsp:include page="commonhead.jsp" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class = "grid" id ="formtext">
            <p>Title (It is required):  
            <input id ="tt" name ="title" style="width: 50%;" onchange="document.getElementById('tarea').value=this.value; document.getElementById('tfile').value=this.value;"></input></p> 
            <form:form modelAttribute="fromTextArea" method="post"> 
                <table> 
                    <form:input id="tarea" path="name" style="width: 50%; display: none"/>
                    <tr> 
                    <p>Text:</p>
                    <p><form:textarea id="textfild"  path="body" maxlength="3000" style="width: 100%; height: 200px;"/></p>
                    </tr> 
                    <tr> 
                        <td colspan="2">
                            <button type="submit"  formaction="/RLP/uploadtextfromarea" onclick=" if ($('#tt').val().length<=0){document.getElementById('errordiv').style.visibility = 'visible';document.getElementById('errordiv').innerHTML = 'Title is required'; return false;}"> Add text </button>
                            <button type="submit"  formaction="/RLP/analyzetextfromarea" onclick=" document.getElementById('formtext').style.display = 'none';document.getElementById('errordiv').style.visibility = 'hidden'; document.getElementById('loader').style.display = 'block';">Analyze</button>
                        </td> 
                    </tr> 
                </table> 
            </form:form>   
            
            <hr align="center"size="2" color="#ccc" />
            
            <div>OR Upload file</div>
            <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data">
                <table> 
                        <form:input id="tfile" path="name" style="width: 50%; display: none" />
                    <tr>
                    <p>File: <form:input path="fileData" type="file" style="width: 50%;"/></p>
                    </tr>

                    <tr>  
                        <td  colspan="2">
                            <input type="submit" value="Upload text" formaction="/RLP/uploadfile"  onclick=" if ($('#tt').val().length<=0){document.getElementById('errordiv').style.visibility = 'visible';document.getElementById('errordiv').innerHTML = 'Title is required'; return false;}" />
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