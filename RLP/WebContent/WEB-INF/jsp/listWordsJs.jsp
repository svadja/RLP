<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

    <head>
        <title>RLP - Word list</title>
        <jsp:include page="commonhead.jsp" />
         <link rel="stylesheet" type="text/css" href="/RLP/resources/css/mycsstable.css">
        <script type="text/javascript" src="/RLP/resources/js/showwordfromtext.js"></script>
        <script type="text/javascript" charset="UTF-8">
            function getWFTFromModel() {
                var arr = new Array();
                       <c:forEach items="${words}" var="word" varStatus="status">
                            var arrworddic = new Array();
                               <c:forEach items="${word.worddicList}" var="worddicList" varStatus="statust">
                                  arrworddic[${statust.index}] = {
                                  idtranslate: "${worddicList.id}",
                                  translate: "${worddicList.translate}"
                                };
                                </c:forEach>
                                        arr[${status.index}] = {
                                            amt: "${word.amt}",
                                            wordorg: "${word.word}",
                                            tranlatelist: arrworddic
                                        };
                      </c:forEach>
                      return arr;
                      }

              function getMWDFromModel() {
                         var arr = new Array();
                         <c:forEach items="${myworddic}" var="worddic" varStatus="status">
                         arr[${status.index}] = {
                         idwordmydic: "${worddic.wordP.id}",
                         status: "${worddic.status}"
                         };
                         </c:forEach>
                         return arr;
                         }

        </script>

        <script charset="UTF-8">
            $(document).ready(InitPage);
            function InitPage() {
                arraywordfromtext = getWFTFromModel();
                arraymyworddic = getMWDFromModel();
                showDicElements(arraywordfromtext, arraymyworddic, statusvisible);
            }
        </script>




    </head>

    <body>
        <jsp:include page="header.jsp" />
         <div class = "grid">
            <div class="tableheader">
                <sec:authorize ifAllGranted="ROLE_USER">
                Show ...
                <form method="post" action="">
                    <input id = "ch0" type="checkbox" name="visiblecb" value="0" checked="checked" onclick="changeVisibility();"/>
                    New
                    <input id = "ch1" type="checkbox" name="visiblecb" value="1" checked="checked" onclick="changeVisibility();"/>
                    Studying
                    <input id = "ch2" type="checkbox" name="visiblecb" value="2" checked="checked" onclick="changeVisibility();"/>
                    Known
                </form>
                </sec:authorize>
            </div>
          <hr class="hline" align="center" size="1" color="#ccc" />
          <div class="tableword">
            <div class="tablew" id="tw">
                <div class="roww" id="rid">
                    <div class="cellatm">
                    </div>
                    <div class="cellword">
                    </div>
                    <div class="celltranslate">
                        <div class="rowtranslate" id="tid" >
                            <div class="translatevalue" >
                            </div>
                            <div class="translateaction" >
                                <sec:authorize ifAllGranted="ROLE_USER">
                                <form class="actionform" action="" method="post" id="form1"   style="display :none;">
                                    <input class="anew" type="radio" name="ract" value="0" onclick="sendStatus(this.value);"/>
                                    New
                                    <input class="astady" type="radio" name="ract" value="1" onclick="sendStatus(this.value);"/>
                                    Studying
                                    <input class="aknown" type="radio" name="ract" value="2" onclick="sendStatus(this.value);"/>
                                    Known
                                </form>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>

    </body>
</html>
