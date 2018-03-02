var statusvisible = 111;
var arraywordfromtext;
var arraymyworddic;

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

function sendStatus(stvalue) {
    $.post('/RLP/myw', {id_worddic:Math.round(stvalue/10), statuss: stvalue%10 });
    var indarr = findInMyDic(Math.round(stvalue / 10), arraymyworddic);
    if (indarr>=0){
    arraymyworddic[indarr].status = stvalue % 10;
    } else {
       arraymyworddic[arraymyworddic.length]= {
                idwordmydic: Math.round(stvalue/10),
                status: stvalue%10
                } 
    }
  refreshListWord();
}

function findInMyDic(idword, arr) {
    for (var f = 0; f < arr.length; f++) {
        if (arr[f].idwordmydic == idword) {
           return f;
        }
    }
    return -1;
}

function refreshListWord(){
   var newElement = $('#rid').clone();
   $('#tw').empty();
   $('#tw').append(newElement);
   showDicElements(arraywordfromtext, arraymyworddic, statusvisible);
}


function showDicElements(arrWFT, arrMWD, shstatus) {
    for (var i = 0; i < arrWFT.length; i++) {
        var colorbackground;
        var visibleWFT = false;
        var newElementWFT = $('#rid').clone();
        for (var j = 0; j < arrWFT[i].tranlatelist.length; j++) {

            var newElementWD = $('#tid').clone();
            var indexOfDic = findInMyDic(arrWFT[i].tranlatelist[j].idtranslate, arrMWD);
            if (indexOfDic >= 0) {
                var statusW = arrMWD[indexOfDic].status;
            } else {
                var statusW = 0;
            };
            if (Math.round(shstatus / (Math.pow(10, statusW))) % 10 == 1) {
                visibleWFT = true;
                newElementWD.children('.translatevalue').text(arrWFT[i].tranlatelist[j].translate);
                //radio
                newElementWD.children('.translateaction').children('.actionform').children('.anew').attr("value", arrWFT[i].tranlatelist[j].idtranslate * 10 + 0);
                newElementWD.children('.translateaction').children('.actionform').children('.astady').attr("value", arrWFT[i].tranlatelist[j].idtranslate * 10 + 1);
                newElementWD.children('.translateaction').children('.actionform').children('.aknown').attr("value", arrWFT[i].tranlatelist[j].idtranslate * 10 + 2);
                if (statusW == 0) {
                    newElementWD.children('.translateaction').children('.actionform').children('.anew').attr("checked", "checked");
                }
                if (statusW == 1) {
                    newElementWD.children('.translateaction').children('.actionform').children('.astady').attr("checked", "checked");
                }
                if (statusW == 2) {
                    newElementWD.children('.translateaction').children('.actionform').children('.aknown').attr("checked", "checked");
                }
                //end radio
                newElementWD.children('.translateaction').children('.actionform').css("display", "inline");
                newElementWFT.children('.celltranslate').append(newElementWD);
            };
        };
        if (visibleWFT) {
            newElementWFT.children('.cellatm').text(arrWFT[i].amt);
            newElementWFT.children('.cellword').text(arrWFT[i].wordorg);
            if (colorbackground==0){
                newElementWFT.css("background", "#F0F0F0");
                colorbackground = 1;
            }else{
                newElementWFT.css("background", "transparent");
                colorbackground = 0;
            };
            $('#tw').append(newElementWFT);
        }
        ;
    }
    ;
}

