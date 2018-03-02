<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html> 

    <head> 
         <title>RLP - Personal word list</title>
        <jsp:include page="commonhead.jsp" />
        <script type="text/javascript" charset="UTF-8">
           var statusvisible=110;
           var arraymyword;
           function getWFromModel() {
            var arr = new Array();
            <c:forEach items="${words}" var="mword" varStatus="status">
            arr[${status.index}] = {
               worddicid: "${mword.wordP.id}",
               wordorig:"${mword.wordP.wordOrig}",
               translate: "${mword.wordP.translate}",
               status: "${mword.status}"
             };
             </c:forEach>
             return arr;
             }
            function showDicElements(){
                for (var i = 0; i < arraymyword.length; i++) {
                    statusw = arraymyword[i].status;
                    if (Math.round(statusvisible / (Math.pow(10, statusw))) % 10 == 1) {
                        var newElementWD = $('#rid').clone();
                        newElementWD.children(".cellword").text(arraymyword[i].wordorig);
                        newElementWD.children(".celltranslate").text(arraymyword[i].translate);
                        newElementWD.children('.celltranslateaction').children('.actionform').children('.astady').attr("value", arraymyword[i].worddicid * 10 + 1);
                        newElementWD.children('.celltranslateaction').children('.actionform').children('.aknown').attr("value", arraymyword[i].worddicid * 10 + 2);
                        newElementWD.children('.celltranslateaction').children('.actionform').children('.deletew').attr("value", arraymyword[i].worddicid * 10 + 0);
                       if (statusw == 1) {
                            newElementWD.children('.celltranslateaction').children('.actionform').children('.astady').attr("checked", "checked");
                              }
                       if (statusw == 2) {
                          newElementWD.children('.celltranslateaction').children('.actionform').children('.aknown').attr("checked", "checked");
                           }
                      newElementWD.children('.celltranslateaction').children('.actionform').css("display", "inline");
                      if (i%2==0){
                          newElementWD.css("background", "#F0F0F0");
                      }
                      $('#tw').append(newElementWD);
                    }    
                }  
            };
    
    
            function sendStatus(stvalue) {
              $.post('/RLP/myw', {id_worddic:Math.round(stvalue/10), statuss: stvalue%10 });
              var indarr = findInMyDic(Math.round(stvalue / 10), arraymyword);
              if (indarr>=0){
                 arraymyword[indarr].status = stvalue % 10;
                 }
              refreshListWord();
              }
            
            function findInMyDic(idword, arr) {
              for (var f = 0; f < arr.length; f++) {
                  if (arr[f].worddicid == idword) {
                     return f;
                   }
                     }
                 return -1;
              }
            
            function refreshListWord(){
              var newElement = $('#rid').clone();
              $('#tw').empty();
              $('#tw').append(newElement);
              showDicElements();
              }
            function changeVisibility(){
              var ch0=0;
              var ch1=0;
              var ch2=0;
              if($('#ch0').prop('checked')){
               ch0=1;
                }
              if($('#ch1').prop('checked')){
               ch1=1;
                }
              if($('#ch2').prop('checked')){
               ch2=1;
                }
              statusvisible = ch0+ch1*10+ch2*100;
              refreshListWord();
              }    
            
            $(document).ready(InitPage);
           
            function InitPage() {
                arraymyword = getWFromModel();
                showDicElements();
               
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
                        <input id = "ch1" type="checkbox" name="visiblecb" value="1" checked="checked" onclick="changeVisibility();"/>
                        Studying
                        <input id = "ch2" type="checkbox" name="visiblecb" value="2" checked="checked" onclick="changeVisibility();"/>
                        Known
                    </form>
                </sec:authorize>
            </div>
           <hr />
            <div class="tableword">
                <div class="tablew" id="tw">
                    <div class="roww" id="rid">
                        <div class="cellword">
                        </div>
                        <div class="celltranslate">
                        </div>    
                        
                        <div class="celltranslateaction" >
                                    <sec:authorize ifAllGranted="ROLE_USER">
                                        <form class="actionform" action="" method="post" id="form1"   style="display :none;">

                                            <input class="astady" type="radio" name="ract" value="1" onclick="sendStatus(this.value);"/>
                                            Studying
                                            <input class="aknown" type="radio" name="ract" value="2" onclick="sendStatus(this.value);"/>
                                            Known
                                            <button class="deletew" value="0"  onclick="sendStatus(this.value);">
                                                <img src="/RLP/resources/img/delete.png" width="18" height="18"/>
                                             </button>

                                       </form>
                                    </sec:authorize>
                       </div>
                      </div>
                </div>
             </div>


    </body> 
</html>
