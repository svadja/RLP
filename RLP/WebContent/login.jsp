<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html> 
    <head> 
        <title>Login</title> 
        <jsp:include page="/WEB-INF/jsp/commonhead.jsp" />
    </head> 
    <body> 
        <jsp:include page="/WEB-INF/jsp/header.jsp" />
        <div class = "grid">
            Login with ...
            <a href="/RLP/socialsignin?socialtype=1"><img src="/RLP/resources/img/facebookg.png"></a>
            <a href="/RLP/socialsignin?socialtype=2"><img src="/RLP/resources/img/vkg.png"></a>
            <a href="<c:url  value ="/j_spring_openid_security_check?openid_identifier=https://www.google.com/accounts/o8/id"/>"><img src="/RLP/resources/img/googleg.png"></a>
        </div>
    </body> 
</html>
