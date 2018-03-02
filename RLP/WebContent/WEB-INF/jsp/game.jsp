<%--
    Document   : game
    Created on : Oct 23, 2013, 3:05:29 PM
    Author     : SasaV
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <title>Game</title>
    <jsp:include page="commonhead.jsp" />
    <script type="text/javascript" charset="UTF-8">
        //кількість правильних відповідей після яких змінюем статус
        var ABC = ["abcdefghijklmnopqrstuvwxyz"];
        var maxcnttrue = 3;
        //Кількість слів в циклі  start=0
        var cntWordTest=10;
        //Кількість слів в циклі які на навчанні, решта береться з вивчених
        var cntWordTestStudy=8;
        var arrayStudyingWord;
        var arrayKnownWord;
        var arrayTestWord ;

        var inputword = "";
        var picnumb = 0;
        var learnword = 0;
        var img="";



        function onClickForB() {
            var inputFWValue = document.getElementsByName("inputForWord").item(0).value;
            inputFWValue += this.value;
            document.getElementsByName("inputForWord").item(0).value = inputFWValue;
            inputword += this.value;

            if (inputword.length == arrayTestWord[learnword].word.length) {
                if (inputword == arrayTestWord[learnword].word) {
                tikColore(1);
                arrayTestWord[learnword].cnttrue = arrayTestWord[learnword].cnttrue+1;
                if (arrayTestWord[learnword].cnttrue>=maxcnttrue){
                    if(arrayTestWord[learnword].status=1){
                      tikColore(3);
                      $.post('/RLP/myw', {id_worddic:arrayTestWord[learnword].worddicid, statuss:2});
                    }
                    arrayKnownWord[arrayKnownWord.length]=arrayTestWord[learnword];
                    arrayTestWord[learnword]= getNewWordForTest();
                }

                cleanOldAndGetNew();
            } else {
                if(arrayTestWord[learnword].cnttrue>0){
                    arrayTestWord[learnword].cnttrue = arrayTestWord[learnword].cnttrue-1;
                }
                tikColore(2);
                cleanOldAndGetNew();
            }
            }
           this.style.visibility = "hidden";
        }

        function createButton(context, name) {
            var button = document.createElement("input");
            button.type = "button";
            button.className = "lbtt";
            button.value = name;
            button.onclick =onClickForB;
            context.appendChild(button);
        }

        function createInputForWord() {
        }

        function createWordTitle() {
           $("#tl").text(arrayTestWord[learnword].translate);
        }

        function cleanOldAndGetNew() {
            inputword = "";
            learnword =getRandomInt(0,arrayTestWord.length-1);
            picnumb = 0;
            document.getElementById("game").innerHTML = "";
            $("#tl").empty();
            $("#tf").val('');
            createWordTitle();
            createLetterButton(document.getElementById("game"));
            loadImgFromGoogle();
        }

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        function createLetterButton(context) {

            var lengthW = arrayTestWord[learnword].word.length;
            var randomLetter = new Array(lengthW);
            var word = arrayTestWord[learnword].word;
            var position;
            for (var i = 0; i<(8-(lengthW % 8));i++){
                  word = word + ABC[0].charAt(getRandomInt(0,25));
                }
            lengthW = lengthW + (8-(lengthW % 8));
            for (var i = 0; i < lengthW; i++) {
                position = getRandomInt(0, word.length - 1);

                randomLetter[i] = word.charAt(position);
                if (position == 0) {
                    word = word.substr(1);
                } else {
                    if (position == word.length - 1) {
                        word = word.substr(0, word.length - 1);
                    } else {
                        word = word.substr(0, position) + word.substr(position + 1);
                    }
                }
            }
            ;

            for (var i = 0; i < lengthW; i++) {
                createButton(context, randomLetter[i]);
            }
            ;
        }

        function tikColore(fc){
            if(fc==1){
                $("#showstatus").css("background","green");
                $("#showstatus").text("RIGHT");
               setTimeout( function(){$("#showstatus").css("background","white");$("#showstatus").text("&nbsp;");},800);

            }
            if(fc==2){
                $("#showstatus").css("background","red");
                $("#showstatus").text(arrayTestWord[learnword].word );
                setTimeout( function(){$("#showstatus").css("background","white");$("#showstatus").text("&nbsp;");},800);
            }
            if(fc==3){
                $("#showstatus").css("background","green");
                $("#showstatus").text("STUDIED");
                setTimeout( function(){$("#showstatus").css("background","white");$("#showstatus").text("&nbsp;");},800);
            }
        }

        //load img
        function GoogleCallback(func, data) {
            window[func](data);
        }
        function loadImgFromGoogle() {
            //      var flickerAPI = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=fuzzy%20monkey&callback=GoogleCallback&context=?";
           //ПОВІДОМИТЬ КОЛИ ГУГЛ  ЗАЖАВ КАРТИНКУ
            var flickerAPI = encodeURI("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+arrayTestWord[learnword].translate + " " +arrayTestWord[learnword].word+"&start="+picnumb+"&callback=?");
            $.getJSON(flickerAPI, function (data) {
                $("#googleimg1").attr("src",data.responseData.results[0].tbUrl) ;
                $("#googleimg2").attr("src",data.responseData.results[1].tbUrl) ;
                $("#googleimg3").attr("src",data.responseData.results[2].tbUrl) ;
                $("#googleimg4").attr("src",data.responseData.results[3].tbUrl) ;
                //alert(data.responseData.results[0].tbUrl);
            });

        }
        //end load img

          // Array Remove - By John Resig (MIT Licensed)
          Array.prototype.remove = function(from, to) {
              var rest = this.slice((to || from) + 1 || this.length);
              this.length = from < 0 ? this.length + from : from;
              return this.push.apply(this, rest);
          };

         function getWFromModel() {
            arrayStudyingWord = new Array();
            arrayKnownWord = new Array();
            var indxKA=0;
            var indxSA=0;
            <c:forEach items="${words}" var="mword" varStatus="status">
            if ("${mword.status}"==1){
                     arrayStudyingWord[indxSA] = {
                        worddicid: "${mword.wordP.id}",
                        word: "${mword.wordP.wordOrig}",
                        translate: "${mword.wordP.translate}",
                        status: "${mword.status}"
                        };
                     indxSA = indxSA+1;
                    };
            if ("${mword.status}"==2){
                     arrayKnownWord[indxKA] = {
                        worddicid: "${mword.wordP.id}",
                        word: "${mword.wordP.wordOrig}",
                        translate: "${mword.wordP.translate}",
                        status: "${mword.status}"
                        };
                     indxKA = indxKA+1;
                    };

             </c:forEach>
             
          }

         function getNewWordForTest(){
             var typearray;
             var randind;
             var resutV;
             typearray = (getRandomInt(0,cntWordTest)<=cntWordTestStudy);
             if((arrayStudyingWord.length>0)||(arrayKnownWord.length>0)) {
                 if(typearray){
                    if(arrayStudyingWord.length>0){
                        randind=getRandomInt(0,arrayStudyingWord.length-1);
                        resutV = arrayStudyingWord[randind];
                        arrayStudyingWord.remove(randind);
                        resutV.cnttrue=0;
                    }else{
                        if(arrayKnownWord.length>0){
                            randind=getRandomInt(0,arrayKnownWord.length-1);
                            resutV = arrayKnownWord[randind];
                            arrayKnownWord.remove(randind);
                            resutV.cnttrue=0;
                        }
                    }

                 }else{
                    if(arrayKnownWord.length>0){
                        randind=getRandomInt(0,arrayKnownWord.length-1);
                        resutV = arrayKnownWord[randind];
                        arrayKnownWord.remove(randind);
                        resutV.cnttrue=0;
                    }else{
                        if(arrayStudyingWord.length>0){
                            randind=getRandomInt(0,arrayStudyingWord.length-1);
                            resutV = arrayStudyingWord[randind];
                            arrayStudyingWord.remove(randind);
                            resutV.cnttrue=0;
                        }
                    }
                 }
              }
           return resutV;
           }

         function getTestWords(){
            var indxNewTA = 0;
            var cnt;
            arrayTestWord=new Array();

            if ((arrayStudyingWord.length+arrayKnownWord.length)< cntWordTestStudy){
               cnt = arrayStudyingWord.length+arrayKnownWord.length;
            }else{
              cnt= cntWordTestStudy;
            }
            for(var indxNewTA=0;indxNewTA<cnt;indxNewTA++) {
             arrayTestWord[indxNewTA] = getNewWordForTest();
            }
         }
         
         function getSettingM(){
           if (${cntWordTest}>0){ 
            cntWordTest= ${cntWordTest};}
           if (${cntWordTestStudy}>0){
            cntWordTestStudy=${cntWordTestStudy};}
           if (${cntTrueAnswer}>0){
            maxcnttrue=${cntTrueAnswer};}
         }

        window.onload = function () {
            getWFromModel();
            getSettingM();
            if ((arrayStudyingWord.length==0)&&(arrayKnownWord==0)) {
             $("#maingame").css("display","none");
             $("#errordiv").css("visibility","visible");
             $("#errordiv").text("You don't have the words in a personal word list");
            }else{
            getTestWords();
            createWordTitle();
            createInputForWord();
            createLetterButton(document.getElementById("game"));
            loadImgFromGoogle();
            }
        }

    </script>
</head>
<body>
            <jsp:include page="header.jsp" />
            <div id="maingame" class = "grid">
                <div id="texthg">Google showed about this word</div>
                <div id="img">
                    <img id="googleimg1"/><img id="googleimg2"/><img id="googleimg3"/> <img id="googleimg4"/>
                    <img id="nextbutton" src="/RLP/resources/img/next.png" onclick="picnumb += 4; loadImgFromGoogle();"/>

                </div>
                <br>
                <div id="divspoiler">    
                <a class="spoiler" onclick="$('#tl').slideToggle('fast');" href="#spoiler">Show/hide original word</a>
                <div id="tl" style="display: none;"></div>
                </div>
                 <br>
                <div id="showstatus">&nbsp;</div>
                <div id="inputblock">
                    <input id="tf" type="text" name="inputForWord" size ="40" maxlength="40" readonly="true"></input>
                    <img id="eraserbutton" src="/RLP/resources/img/eraser.png" onclick="$('#tf').val('');inputword = '';$('.lbtt').css('visibility','visible');"/>
                </div>
                <br>
                <div id="game"></div>
                <br>
           </div>
</body>
</html>
