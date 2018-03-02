<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>   
        <table class="menu_table">
            <tr class="menu_tr">
                <td class="menu_td">
                    <ul class="menu">
                        <li id="indent"> </li>
                        <li><a href="/RLP/mainpage">Home</a></li>
                            <sec:authorize ifAllGranted="ROLE_ANONYMOUS">   
                            <li><a href="/RLP/guest/guestuploadtext">Start</a></li>
                            </sec:authorize>
                            <sec:authorize ifAllGranted="ROLE_USER">   
                            <li><a href="/RLP/uploadfile">Start</a></li>
                            </sec:authorize>
                            <sec:authorize ifAllGranted="ROLE_ANONYMOUS">   
                            <li><a href="/RLP/fileslist">Texts</a></li>
                            </sec:authorize>
                            <sec:authorize ifAllGranted="ROLE_USER">
                            <li><a href="/RLP/fileslist"><span class="icon" data-icon="R"></span>Texts</a>
                                <ul>
                                    <li><a href="/RLP/myfileslist"><span class="icon" data-icon="G"></span>My Texts</a></li>
                                </ul>
                            <li><a href="/RLP/wl/listMyWords">Words</a></li>   
                            <li><a href="/RLP/wl/game">Game</a></li> 
                            </sec:authorize>
                    </ul>
                </td>
                <td class="menu_td">
                    <ul class="menu">
                        <sec:authorize ifAllGranted="ROLE_USER">
                            <li><a href=""><span class="icon" data-icon="R"></span>
                                    <sec:authentication property="principal.profile.firstName" /> <sec:authentication property="principal.profile.lastName" /> 
                                </a>
                                <ul>
                                    <li><a href="/RLP/usersettings"><span class="icon" data-icon="G"></span>Settings</a></li>
                                    <li><a href="/RLP/j_spring_security_logout"><span class="icon" data-icon="A"></span>Log out</a>
                                </ul>
                            </sec:authorize>
                            <sec:authorize ifAllGranted="ROLE_ANONYMOUS">         
                            <li><a href="/RLP/login.jsp">Login</a></li>
                            </sec:authorize>
                    </ul>
                </td>
            </tr>
        </table>

        <div class="grid" id="errordiv" style="visibility: hidden;">&nbsp;</div>
