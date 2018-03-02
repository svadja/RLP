<%-- 
    Document   : helppage
    Created on : Oct 7, 2013, 1:26:36 PM
    Author     : Vadya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title>RLP - Settings page</title>
        <jsp:include page="commonhead.jsp" />
        <style type="text/css">
        </style>
        <script type="text/javascript">
            function sendPost(){
                var jWT = $('#textfield').val();
                var jWTS = $('#textfield3').val();
                var jTA = $('#textfield2').val();
                if((Math.floor(jWT) == jWT && $.isNumeric(jWT))&&(Math.floor(jWTS) == jWTS && $.isNumeric(jWTS))&&(Math.floor(jTA) == jTA && $.isNumeric(jTA))){
                $.post('/RLP/usersettings', {
                    cntWordTest:jWT,
                    cntWordTestStudy:jWTS,
                    cntTrueAnswer:jTA
                });
                }else{
                    alert("Inputted data not number");
                }
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="grid">
            <form id="form1" name="form1" method="post" action="">
                <p>
                    0=default (20 words, 15 studied and 3 correct answers)
                </p>    
                <p>
                    <label for="textfield">Number of words in one cycle game</label>
                    <input type="text" name="textfield" id="textfield" value="${cntWordTest}" />
                </p>
                <p>
                    <label for="textfield3">Number of studied words in one cycle game</label>
                    <input type="text" name="textfield3" id="textfield3" value="${cntWordTestStudy}"/>
                </p>
                <p>
                    <label for="textfield2">Number of correct answers for changing the status</label>
                    <input type="text" name="textfield2" id="textfield2" value="${cntTrueAnswer}"/>
                </p>
                <p>
                    <input type="button" name="button" id="button" value="Save" onclick="sendPost();return false;" />
                </p>
            </form>
        </div>
    </body>
</html>
