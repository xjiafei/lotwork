
//遮罩加载图片开始....
//timeout时间缓慢
function ShowTipDiv(timeout) {
    if (timeout == "") {
        timeout = 0;
    }
    if ($("#datagrid-mask").length == 0) {
        $("<div id=\"datagrid-mask\" style=\"background:#666666;position:absolute;left:0;top:0;\"></div>").css({ display: "none", width: $("body")[0].offsetWidth , height: ($("body")[0].scrollHeight+200) }).appendTo("body");
        $("<div id=\"datagrid-mask-msg\" style=\"position:absolute;\"></div>").html("<img src="+global_path_url+"/css/images/loading.gif>").appendTo("body").css({ display: "none", left: ($(window).width() - 16) / 2 + 'px', top: ($(window).height() - 16) / 2 + 'px' });
    }
    $("#datagrid-mask,#datagrid-mask-msg").delay(timeout).show(0, function () {
        $("#datagrid-mask").css("-moz-opacity", "0.4");
        $("#datagrid-mask").css("opacity", ".40");
        $("#datagrid-mask").css("filter", "alpha(opacity = 40)");
    });

}
//隐藏
function HideTipDiv() {
    if ($("#datagrid-mask").length > 0) {
        $("#datagrid-mask,#datagrid-mask-msg").stop();
        $("#datagrid-mask,#datagrid-mask-msg").stop();
        $("#datagrid-mask,#datagrid-mask-msg").hide();
    }
}


/*
 *验证对象的值是否为空
 *obj:要验证的对象
 *errarlbl:错误信息显示的对象ID
 *errarInfo:要显示的错误信息
 */
function IsNullByInfo(obj, errarlbl, errarInfo) {
    if ($(obj).val() == "") {
        $(obj).parent().parent().find("#" + errarlbl).html(errarInfo + "&nbsp;&nbsp;&nbsp;");
        return false;
    }
    $(obj).parent().parent().find("#" + errarlbl).html("");
    return true;
}


/*
*验证对象的值是否为空
*obj:要验证的对象
*errarlbl:错误信息显示的对象ID
*eltype:要验证的类型
*isNullInfo:不能为空的错误提示不填则显示不能为空
*errarInfo:要显示的错误信息
*length:该值的最大长度
*/
function IsElJudge(obj, errarlbl, eltype, isNullInfo, errarInfo, length) {
    var Authentication;
    //debugger;
    switch (eltype) {
        case "number":
            Authentication = /^[0-9]+(.[0-9]{2})?$/;
            break;
        case "decimal":
            Authentication =/\.\d{1,2}$/;
            break;
        case "DomainName":
            Authentication = /^(?![^\.]*?[\u4e00-\u9fa5][^\.]*?\.)([a-zA-Z]+\.)?[\w-\u4e00-\u9fa5]+\.[a-zA-Z]+(\.[a-zA-Z]+)?$/;
            break;
        case "hour":
            Authentication = /^([0-1][0-9]{1}|[2][0-3]{1})$/;
            break;
        case "minute":
            Authentication = /^([0-5][0-9])$/;
            break;
        case "ip":
            Authentication = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
    }
   //alert($(obj).val());
    //alert(Authentication.toString());
    if ($(obj).val() == "") {
        $(obj).parent().parent().find("#" + errarlbl).html(isNullInfo == "" ? "不能为空" : isNullInfo + "&nbsp;&nbsp;&nbsp;");
        return false;
    }
    if (!Authentication.test($(obj).val())) {
        $(obj).parent().parent().find("#" + errarlbl).html(errarInfo + "&nbsp;&nbsp;&nbsp;");
        return false;
    }
    if (length!=0 && $(obj).val().toString().length > length) {
        $(obj).parent().parent().find("#" + errarlbl).html("长度必须小于"+length+"&nbsp;&nbsp;&nbsp;");
        return false;
    }
    $(obj).parent().parent().find("#" + errarlbl).html("");
    return true;
}
//---------------------------------验证所填是否符合，定位提示错误信息结束--------------------------------------


//-----------------------------------验证开始-----------------------------------------------
////把輸入的日期格式化“YY-MM-DD”
//obj:日期輸入框的ID
//調用實例：DateYMD10('20071220');   return:2007-12-20
function DateYMD10(obj)  
{  
    var Tvalue=document.getElementById(obj);
    if("468".indexOf(Tvalue.value.length)>=0)  
    {      
        if(Tvalue.value.length ==6)  
         Tvalue.value = "20" + Tvalue.value;  
        if(Tvalue.value.length ==4)  
        Tvalue.value = new Date().getYear() + Tvalue.value;     
        if(Tvalue.value.length ==8)  
        {
            var val = Tvalue.value;            
            Tvalue.value=val.substr(0,4) + "-" + val.substr(4,2) + "-" +val.substr(6,2);
        } 
    }  
} 


//值驗証,判斷日期YYYYMM是否合法,反回bool 值
//strDate:輸入的日期
////調用實例:alert(CheckDateYYYYMM('200712'));  return: true
function CheckDateYYYYMM(strDate)  
{     
//    var strDate=trimSpace(strDate);  
    var strY="";  
    var strM="";  
    var tempLen;  
    tempLen=strDate.length;  
    if(tempLen!=6)return false;  
    if(isNaN(strDate)==true)return false;  
    if(strDate.indexOf('.')!=-1)return false;  
    strY=strDate.substring(0,1);  
    strM=strDate.substring(tempLen-2);  
    if (strY == "0" )return false;  
    if (strM>= "01" && strM<="12")  
    return true;  
    else  
    return false;  
} 
//值驗証,判斷日期mmdd是否合法,反回bool 值
//strDate：輸入的日期
////調用實例: CheckDateMMDD('1220');  return: true
function CheckDateMMDD(strDate)  
{     
//    var strDate=trimSpace(strDate);  
    var strM="";  
    var strD="";  
    var tempLen;  								
    tempLen=strDate.length;  								
    if(tempLen!=4)return false;  								
    if(isNaN(strDate)==true)return false;  								
    strM=strDate.substring(0,2);  								
    strD=strDate.substring(tempLen-2);  								
    var intM=parseInt(strM,10);  								
    var intD=parseInt(strD,10);  								
    if (intM>12||intM==0 )return false;  								
    if (intD>31||intD==0 )return false;  								
    if (intM==2&&intD>29)return false;  								
    if(intM==4||intM==6||intM==9||intM==11)//小月  								
    {  								
        if(intD==31)return false;  								
    }  								
    return true;  								
} 
////把時間格式化爲'HH:MI:SS'形式
//objtime：要判斷的物體
//strH: 0='HH:MI' ,1='HH:MI:SS'
//調用實例: Check_Time_Field(document.all.TextBox1,'1');  return: HH:MI:SS
function Check_Time_Field(objtime,strH)    
{   
        var strTime;   
        var resStr="";     
        var vH;    
        var vM;      
        var vS;    
        strTime=objtime.value;//.trim();  
        if(strTime=="") return; //若時間為空值, 則不做     //設定時間為準模式 "12：00：00"  
        //XX.XX.XX  
        resStr="";   
        for(var i=0;i<strTime.length;i++)  
        resStr=resStr +((strTime.substring(i,i+1)).replace('.',':'));  
        strTime=resStr;  
        //XX XX XX  
        resStr="";  
        for(var i=0;i<strTime.length;i++)    
        resStr=resStr +((strTime.substring(i,i+1)).replace(' ',':'));  
        strTime=resStr;  
        //XX-XX-XX   
        resStr="";  
        for(var i=0;i<strTime.length;i++)  resStr=resStr+((strTime.substring(i,i+1)).replace('-',':')); 
        strTime=resStr;  
        //XXXXXXXX  
        if(strTime.indexOf(":")<=0)  
        {   
            if(strTime.length==1)   
            strTime="0"+""+strTime+":00:00";  
            else
            {   
                strTime=(strTime+'000000').substring(0,6);  
                strTime=strTime.substring(0,2)+":"+strTime.substring(2,4)+ ":"+strTime.substring(4,6);  
            }   
        }  
        //XX:XX  
        if(strTime.lastIndexOf(":")>0 && strTime.lastIndexOf(":")<=2)  
        strTime=strTime+':00';      
        var strTimeArry2=strTime.split(":");  
        vH=strTimeArry2[0];         
        vM=strTimeArry2[1];                          
        vS=strTimeArry2[2];  
        if(strTimeArry2.length==2)   
        vS="00";  
        if(!(isNaN(vH)||isNaN(vM)||isNaN(vS)))  
        {  
            if(vH>=24) vH=vH-24;       
            if(vM>=60) vM=vM-60;                     
            if(vS>=60) vS=vS-60;  
            vH="00"+(vH*1).toString();          
            vH=vH.substring(vH.length-2,vH.length);
            vM="00"+(vM*1).toString();         
            vM=vM.substring(vM.length-2,vM.length);
            vS="00"+(vS*1).toString();   
            vS=vS.substring(vS.length-2,vS.length);  
        }  
        strTime=vH+":"+vM+":"+vS;  
        if(strH==0)       
        strTime=strTime.substring(0,5);        
        objtime.value=strTime;  
 } 
//判斷Start日期是否大於End日期,參數必須是合法日期
//StartDate：起始日期
//EndDate:結束日期
//調用實例: checkSEDate('2007/12/20','2008/12/20');  return: false
 function checkSEDate(StartDate,EndDate)  
 {  
     var r = StartDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
     var dStart= new Date(r[1], r[3]-1, r[4]);    
     var r = EndDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
     var dEnd= new Date(r[1], r[3]-1, r[4]);   
     return(dStart>dEnd);  
 } 
//判斷一個日期是否在兩個日期之間
//checkDate:要判斷的日期
//StartDate：起始日期
//EndDate:結束日期
//調用實例: checkDateIn('2008/01/01','2007/12/20','2008/12/20');  return: true
 function checkDateIn(checkDate,StartDate,EndDate)  
 {  
        var r = checkDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
        var dCheck= new Date(r[1], r[3]-1, r[4]);    
        var r = StartDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
        var dStart= new Date(r[1], r[3]-1, r[4]);    
        var r = EndDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
        var dEnd= new Date(r[1], r[3]-1, r[4]);  
        if(StartDate>EndDate) return false;  
        if(dCheck>=dStart && dCheck<=dEnd) return true;  
        return false;  
 }  
//判斷輸入的時間（HH:MM:SS）是否合法
 //strTime:輸入的時間
//調用實例: IsTime('12:20:20');  return: true
 function IsTime(strTime)  
 {  
     var strHours;  
     var strMinutes;  
     var strSeconds;  
     var strTimeArry3=strTime.split(":");　//以":"為界分裝到數組中  
     if(strTimeArry3.length!=3)//如果不是時分秒標准形式不判斷　　  
     return false;  
     strHours=strTimeArry3[0];  
     strMinutes=strTimeArry3[1];  
     strSeconds=strTimeArry3[2]  ;
     if(isNaN(strHours)||isNaN(strMinutes)||isNaN(strSeconds))  
     return false;  
     if(strTimeArry3[0].length>2||strTimeArry3[1].length>2||strTimeArry3[2].length>2)  
     return false;  
     if(strHours>23||strHours<0||strMinutes>59||strMinutes<0||strSeconds>59||strSeconds<0)  
     return false;  
     return true;  
 } 
//實現日期的相加
 //currentDate:要增加的日期
 //format:增加年或者月：YYYY: 年;YYYYMM:月
 //num:要增加的年數或月數 
//調用實例: getNextCircle('2007','YYYY','2');  return: 2009
 function getNextCircle(currentDate,format,num)  
 {  //
    var  nextDate=currentDate;  
    var  year;  
    var  month;  
    if(format=="YYYY")//格式為YYYY的處理  
    {  
        year=currentDate.substring(0,4);   

        nextDate=parseInt(year)+parseInt(num);  
    }
    else if(format=="YYYYMM")//格式為YYYYMM的處理  
    {  
        year=currentDate.substring(0,4);  
        month=currentDate.substring(4,6);  
        var iMonth=parseInt(month);   
        var iYear=parseInt(year);  

        for (var i=0;i<num;i++)  
        {   
            iMonth=iMonth+1;  
            if(iMonth>12)  
            {   
                iMonth=1;   
                iYear=iYear+1;   
            }  
        }  
        month=iMonth.toString();  
        year=iYear.toString();  
        if(month.length==1)  
        { 
            month="0"+month;  
        }  
        nextDate=iYear+month;  
    }
    else if(format=="YYYY/MM")//格式為YYYY/MM的處理  
    {                                                                                                     
 year=currentDate.substring(0,4); 
 month=currentDate.substring(5,7); 
 var iMonth=parseInt(month);                                     
var iYear=parseInt(year);  
        for (var i=0;i<num;i++)  
        {   
            iMonth=iMonth+1;  
            if(iMonth>12)  
            {   
                iMonth=1;  
                iYear=iYear+1;  
            } 
        }  
        month=iMonth.toString(); 
        year=iYear.toString();  
        if(month.length==1)  
        {   
            month="0"+month;  
        }  
        nextDate=year+"/"+month; 
    }  
    return nextDate; 
} 
//西元年轉民國年  
//str:要轉換的日期
//調用實例: 
//    toCdate('2007/12/20')   return: 96/12/20
function toCdate(str)
{  
    var tempArray=str.split("/");  
    tempArray[0]=parseInt(tempArray[0])-1911;  
    return tempArray.join("/");  
}  
//民國年轉西元年  
//str:要轉換的日期
//調用實例: 
//    toCdate('96/12/20')   return: 2007/12/20
 function toDate(str)
{  //
    var tempArray=str.split("/");  
    tempArray[0]=parseInt(tempArray[0])+1911;  
    return tempArray.join("/");  
}  
//判斷西元日期是否合法
//str:要轉換的日期
//調用實例: 
//    IsDate('2007/12/20')   return:true
function IsDate(str)   
{   
    if(str.length==6)return CheckDateMMDD(str.substring(2,6));  
    if(str.length==4)return CheckDateMMDD(str);  
    if(str.length==8 && str.indexOf('/')==-1)  
    {  
        str=str.substring(0,4)+"/"+str.substring(4,6)+"/"+str.substring(6,8);  
    }         
    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
    if(r==null)return false;    
    var d= new Date(r[1], r[3]-1, r[4]);    
    return (d.getFullYear()==r[1] && (d.getMonth()+1)==r[3] && d.getDate()==r[4]);   
}  
//獲取第一個可獲取焦點的控件
//調用實例:  setFirstEnableObjectFocus(); 
 function setFirstEnableObjectFocus()  
 {  //
    var formElements = document.forms[0].elements;   
    var needFocus = true;  
    for(var i=0;i<formElements.length;i++)  
    {  
        var tmpElement = formElements[i];  
        if(tmpElement.type != 'hidden' && tmpElement.readOnly != true && tmpElement.disabled != true && tmpElement.style.display != 'none')  
        {  
            if(tmpElement.isFirst != null)  
            {  
                if(arguments.length == 0) tmpElement.focus();  
                if(tmpElement.type == 'text' || tmpElement.tagName == 'TEXTAREA') tmpElement.select();  
                    return;  
            }
            if(needFocus)  
            {  
                if(tmpElement.type != 'hidden'  && tmpElement.readOnly != true  && tmpElement.disabled != true   
                && tmpElement.style.display != 'none'  && tmpElement.style.width != '0px') 
                {  
                    try  
                    {  
                        if(arguments.length == 0)  
                        {   
                            tmpElement.focus();  
                            if(tmpElement.type == 'text' || tmpElement.tagName == 'TEXTAREA') tmpElement.select();  
                        }    
                        tmpElement.isFirst = true;   needFocus = false;  
                    }  
                    catch(e)  
                    {   
                        needFocus=true;   
                    }  
                } 
                else   
                {  
                    return;  
                }
            }  
            tmpElement.focusedIndex = i;  
        } 
    } 
  } 
//把焦點設置到最後一個可獲取焦點的控件上
//調用實例:  setLastEnableObjectFocus(); 
function setLastEnableObjectFocus() 
{  
    var formElements = document.forms[0].elements;  
    for(var i=formElements.length; i>0; i--)  
    {  
        var tmpElement = formElements[i];  
        try  
        {  
            if(tmpElement.type != 'hidden' && tmpElement.readOnly != true && tmpElement.disabled != true && tmpElement.style.display != 'none')  
            {  
                if(tmpElement.readOnly != 'true')  
                {  
                    tmpElement.focus();  
                    return;  
                }  
                else 
                {   
                    continue; 
                }  
            }  
        }  
        catch(e)   
        { 
            continue;  
        }   
    }  
} 
//欄位長度格式化，不足位時用自定議字元補足
//obj:要格式化的欄位ID
//len:設定字符串長度
//Char:要添加的字元 
//way：L：在左邊添加；R: 在右邊添加
//調用實例:  stringPad(document.all.TextBox1,10,'0','R'); return: 2200000000
function stringPad(obj,len,Char,way)  
{  
    if(way!="L"&&way!="R"&&way!="l"&&way!="r")return;  
    var val=obj.value; 
    var str="";  
    var i=0;  
    if(val!="")   
    {  
        for(i=0;i<len-val.length;i++)  
        str=str+Char;  
        if(way=="R"||way=="r")  
        val+=str;  
        else  
        val=str+val;  
        obj.value=val;  
    }  
}
 //取得當前瀏覽器支持的XMLHTTP對象   
//調用實例:  GetXmlHttpRequest();
function GetXmlHttpRequest()  
{  
     var xhr=null;    
     try   
     {   
        xhr=new ActiveXObject("Msxml2.XMLHTTP");//initialize a xmlhttp object   
     }   
     catch(e)   
     {   
         try    
         {   
         xhr=new ActiveXObject("Microsoft.XMLHTTP");   
         }  
         catch(oc)   
         {   
         xhr=null;   
         }   
     }   
     if (!xhr && typeof XMLHttpRequest != "undefined" )   
     {   
         xhr=new XMLHttpRequest();   
     }   
     return xhr;   
}  
// 將<,>,",&轉換為HTML描述方式
//調用實例:  ToHtmlEncode(&); return:&amp;
function ToHtmlEncode(strVal)  
{  
    strVal = strVal.replace(/&/g,"\&amp;");  
    strVal = strVal.replace(/</g,"\&lt;");  
    strVal = strVal.replace(/>/g,"\&gt;");  
    strVal = strVal.replace(/"/g,"\&quot;");  
    return strVal;  
} 

// 清空指定容器控件下的所有子控件的值
//Contains:容器ID
//調用實例:  fnClear('Panel1'); 
function fnClear(Contains)   
{  //
    var arrCtl;  
    arrCtl=document.all(Contains).getElementsByTagName("input");  
    for(i=0;i<arrCtl.length;i++)  
    {  
        switch(arrCtl[i].type)  
        {  
            case "text":  
                arrCtl[i].value="";             
                break;  
            case "checkbox":  
            case "radio":  
                arrCtl[i].checked=false;               
                break;  
         }       
    }  
    arrCtl=document.all(Contains).getElementsByTagName("select");  
    for(i=0;i<arrCtl.length;i++)   
    {  
        arrCtl[i].value="";  
    }  
    arrCtl=document.all(Contains).getElementsByTagName("textarea");  
    for(i=0;i<arrCtl.length;i++)  
    {  
       arrCtl[i].value="";              
    }  
    return false;   
} 

//去前後空格
//str:要去空格的字符串					
//調用實例:  trimSpace(' Pa nel1 ');  return:Pa nel1					
 function trimSpace(str) 					
 {  					
     var instring=str.toString();  					
     var value=new String();   //去頭  					
     for(var i=0;i<instring.length;i++)					
     {  					
         if(instring.charAt(i)!=" ")					
         {  					
             value=instring.substring(i);  					
             break;  					
         }  					
     }  //去尾  					
     while(value.charAt(value.length-1)==" ")  					
     {  					
        value=value.substring(0,value.length-1);  
     }  
     return value;  
 } 

//---------------------------------------------------------------------[去前後空格] (附加為基類方法)
//eg:var str=document.all.txtName.value.trim();	
String.prototype.trim=function()	
{	
	return this.replace(/(^\s*)|(\s*$)/g,'');
}	

//輸入控制,僅限輸入 0~9 十個字元(不禁中文)
 function  inputNubmer()   
 {  
    var key=window.event.keyCode;  
    if(key>=48 && key<=57)   
    {  
        return true;   
    }  
    else  
    {  
        window.event.keyCode=0;  
        return true;  
    }  
  } 
  
//輸入控制,僅限輸入 0~9 十個字元和小數點(不禁中文)
function  inputNubmerFloat()  
{  
    var key=window.event.keyCode;  
    if((key>=48 && key<=57)||key==46)  
    {  
        return true;  
    }  
    else 
    {  
        window.event.keyCode=0;  
        return true;  
    }  
} 
//輸入控制,過濾中文輸入
//調用實例:  this.TextBox1.Attributes.Add("onkeydown", "LeachInputChinese()");
function LeachInputChinese()  
{  
    var obj=event.srcElement;  
    if(obj.value.search(/[\u4E00-\u9FA5]/g) != -1)  
    {  
        obj.value=obj.value.replace(/[\u4E00-\u9FA5]/g,'');     
        obj.value=obj.value.replace(/[\uFE30-\uFFA0]/g,'');  
    }  
    if(obj.onkeydown==null)   
    {  
        obj.onkeydown=LeachInputChinese;  
    }     
}  
//輸入控制,僅限日期yyyy/dd/mm
//調用實例:  this.TextBox1.Attributes.Add("onkeypress", "inputDate()");
function  inputDate()   
{ 
    var key=window.event.keyCode;  
    if((key>=48 && key<=57)||key==47)  
    {  
        return true;  
    }  
    else  
    {  
        window.event.keyCode=0;  
        return true;  
    }  
} 
//判斷輸入的文字是否超出欄位的限制
//objField： 要判斷的物件
//MaxLength： 字符長度
//調用實例:  CheckLength(document.all.TextBox1,10);
function CheckLength(objField,MaxLength)  
{  
    if (objField.value!="")
    {  
        if(objField.value.length>MaxLength)
        {  
           // alert("輸入的文字長度超過欄位限制，只可輸入"+MaxLength+"個字");  
            objField.value=objField.value.substring(0,MaxLength);  
            objField.focus();  
            return;  
        }   
    }  
}   

//值驗証,判斷IP是否合法,反回bool 值
//strIP： IP字符串
//調用實例:  CheckIP('192.168.1.113');  return:true
 function CheckIP(strIP)  
 {      
    var re=/(\d+)\.(\d+)\.(\d+)\.(\d+)/g;   
    if(re.test(strIP)==false)  
    {  
        return false;  
    }  
    var ip=strIP.split('.');  
    var ip1=parseInt(ip[0],10);  
    var ip2=parseInt(ip[1],10);  
    var ip3=parseInt(ip[2],10);  
    var ip4=parseInt(ip[3],10);  
    if(ip1>255||ip2>255||ip3>255||ip4>255) return false;  
    if(ip1==0||ip4==0) return false;  
    return true;  
} 

//判斷是否為數字
//strNum： 要判斷的字符串
//調用實例:  isNumeric('192');  return:true
function isNumeric(strNum)  
{  
 var strCheckNum = strNum + "";  
 if(strCheckNum.length < 1) //空字符串  
 return false;  
 if(isNaN(strCheckNum)) //不是數值   
 {   
    return false;  
 }  
 return true;  
} 

//判斷是否是整數
//strNum： 要判斷的字符串
//調用實例:  isInteger('192.00');  return:false
function isInteger(strNum)  
{  
 var strCheckNum = strNum*1 + "";  
 if(strCheckNum.length < 1) //空字符串  
 return false;  
 if(isNaN(strCheckNum)) //不是數值   
 {  
    return false;  
 }  
if(strNum.indexOf(".")!=-1)  return false;   
 return true;  
} 							

//將金額格式（三位一撇）變為普通數字（去掉三位一撇）				
//srcStr： 要處理的金額格式字符串				
//調用實例:  unFormat('2,192.00');  return:2192.00				
function unFormat(srcStr)  				
{  				
     srcStr=srcStr.replace(/,/g,'');				
     return srcStr; 				
}				
//將格式化數字變為普通數字並返回原對像函數unFormatNum	
//入參：1. sText->對像	
function unFormatNum(sText)	
{	
	sText.value=unFormat(sText.value);
	sText.select();
}	
	
//檢查字符串是否包含除字母、數值、下橫綫以外的字符							
//strCheck： 要判斷的字符串							
//調用實例:  CheckStrCN('2,192.00');  return:true							
function CheckStrCN(strCheck) 							
{  							
     for(var i=0;i<strCheck.length;i++)  							
     {  							
         var strTemp=strCheck.charAt(i);  
         if((strTemp<"A" || strTemp>"Z") && (strTemp<"a" || strTemp>"z") && (strTemp<"0" || strTemp>"9") && (strTemp!="_"))  
         return true;  
     }  
     return false;  
} 
//得到字符串的bit長度
//strText： 要判斷的字符串
//調用實例:  GetBitLenght('地方11');  return:6
function GetBitLenght(strText)  
{  
 var intlen=0;  
 for(var i=0; i<strText.length; i++)  
 {  
    if(strText.charCodeAt(i)>255)
    {
        intlen=intlen+2;
    }  
    else
    {
        intlen++;
    }  
 }  
 return intlen;  
} 
//判斷中文字符串是否超過指定長度
//strText： 要判斷的字符串
//len： 想要的字符串長度
//調用實例:  CheckOver_CN('地方11',10);  return:false
function CheckOver_CN(strText,len)  
{  
 var intlen=strText.length;  
 if (intlen<=len && intlen>len/2) intlen=bitLenght(strText);  
 return (intlen>len);  
} 
//檢查控件是否包含除字母、數值以外的字符
//objID： 要判斷的控件的ID
//調用實例:  checkObjCN('TextBox1');  return: alert('該欄位的值必須是字母、數值'); 
function checkObjCN(objID)  
{  
     var obj=document.getElementById(objID);  
     if(obj==null)   
     {  
     return false;  
     }  
     var strCheck=obj.value;  
     if(strCheck=="") return true;  
     for(var i=0;i<strCheck.length;i++)   
     {  
         var strTemp=strCheck.charAt(i);  
         if((strTemp<"A" || strTemp>"Z") && (strTemp<"a" || strTemp>"z") && (strTemp<"0" || strTemp>"9"))  
         {  
             alert('該欄位的值必須是字母、數值');  
             obj.focus();  
             obj.select();  
             return false;  
         }  
     }  
     return true;  
} 
//限制只能輸入0-9,a-z,A-Z的字符
//調用實例: this.TextBox1.Attributes.Add("onkeydown", "CheckInputChar()");  
function CheckInputChar()
{ 
    var tmpCode = window.event.keyCode;   
    //A-Z keyCode 65-95  
    //a-z keyCode 97-122  
    //0-9 keyCode 48-57  
    try      
    {  
        if((tmpCode >= 48 && tmpCode <= 57) || (tmpCode >= 65 && tmpCode <= 90) || (tmpCode >= 97 && tmpCode <= 122))  
        {         
            return true;  
        }  
        else          
        {   
            event.returnValue=false;   
        }  
    }catch(e){}  
} 
//限制只能輸入0-9,a-z,A-Z的字符
//調用實例: this.TextBox1.Attributes.Add("onkeydown", "CheckInputChar()");  
function CheckSpecialChar()  {  //限制只能輸入0-9,a-z,A-Z和特殊字符‘ - . / , ( ) + :九種字符
    var tmpCode = window.event.keyCode;   
    //'             keyCode           39    //-             keyCode           45  
    //.             keyCode           46  ///             keyCode           47  
    //,             keyCode           44   //(             keyCode           40  
    //)             keyCode           41    //+             keyCode           43  
    //:             keyCode           58  
    try      {  
      　 if((tmpCode >= 39 && tmpCode <= 58&&tmpCode!=42) || (tmpCode >= 65 && tmpCode <= 90) || (tmpCode >= 97 && tmpCode <= 122))          {  
            return true;  
        }  
        else          {   
            event.returnValue=false;   
        }  
    }catch(e){}  
} 
//檢查是否為英文字母函數
//str:輸入字符串
//調用實例: isAlpha('ddd'); return : true    
function isAlpha(str){   
  var instring=str.toString();  
 for(i=0;i<instring.length;i++){  
 var value=instring.charAt(i);  
     if(value < "A" || value >"Z"&&value < "a"||value > "z") return false;  
 }  
 return true;  
} 

//檢查是否為英文字母函數
//objText:輸入字符串
//調用實例: checkEmail('TextBox1'); return : true    
function checkEmail(objText)   
{  
 var strText=objText.value;  
 var objRe =/^[\w]+@([\w]+\.)+[\w]{2,3}$/;   
 if(strText=="") return true;  
 if(objRe.test(strText))   
 {  
    return true;  
 }
 else
 {  
     alert('Email格式輸入不正確，請重新輸入！');  
     objText.focus();  
     objText.select();  
     return false;  
 }  
} 	
//限制只能輸入字母和數字	
function  _onluyNum_En(obj)	
{   	
    obj.value = obj.value.replace(/[^a-z A-Z 0-9]/g,'');	
}  	
//*****************************************************	
//* 函數名稱: isOver_CN	
//* 目    的: 判定輸入的中文是否超過指定的長度	
//* 參    數:objID(控件ID);len(指定的長度)	
//*****************************************************	
function isOver_CN(objID,len)	
{	
	var obj=document.getElementById(objID);
	var intlen=obj.value.length;
	if (intlen<=len && intlen>len/2)
   		intlen=bitLenght(obj);
	if (intlen>len)	
	{	obj.focus(); obj.select(); return false;}
	return true;	
}		
//全局替换		
 //as_expression:要處理的字符串		
 //as_find:要替換的字符		
 //as_replacement:替換后的字符		
 //調用實例: FIICgf_replace('DropDownList1','D','')   ;     return :ropDownList		
 function FIICgf_replace(as_expression,as_find,as_replacement)  		
 {  //全局替换		
   var fs_expression = as_expression;     		
   var fs_find = as_find;     		
   var fs_replacement = as_replacement;  		
   if (fs_expression == "") return ""; 		
   if (fs_find == "") return ""; //构造正则表达式\,$,(,),*,+,.,[,?,{,^,|为特殊字符,须替换  
   fs_regx1 = /\\/gi;    
   fs_find = fs_find.replace(fs_regx1,"\\\\");   
   fs_replacement = fs_replacement.replace(fs_regx1,"\\");       
   fs_regx1 = /\$/gi;    
   fs_find = fs_find.replace(fs_regx1,"\\\$");   
   fs_replacement = fs_replacement.replace(fs_regx1,"\$");           
   fs_regx1 = /\(/gi;     
   fs_find = fs_find.replace(fs_regx1,"\\\(");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\(");  
   fs_regx1 = /\)/gi;  fs_find = fs_find.replace(fs_regx1,"\\\)");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\)");         
   fs_regx1 = /\*/gi;  fs_find = fs_find.replace(fs_regx1,"\\\*");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\*");           
   fs_regx1 = /\+/gi;   fs_find = fs_find.replace(fs_regx1,"\\\+");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\+");  
   fs_regx1 = /\./gi;  fs_find = fs_find.replace(fs_regx1,"\\\.");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\.");  
   fs_regx1 = /\[/gi;   fs_find = fs_find.replace(fs_regx1,"\\\[");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\[");  
   fs_regx1 = /\?/gi;  fs_find = fs_find.replace(fs_regx1,"\\\?");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\?");  
   fs_regx1 = /\^/gi;   fs_find = fs_find.replace(fs_regx1,"\\\^");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\^");  
   fs_regx1 = /\,/gi;   fs_find = fs_find.replace(fs_regx1,"\\\,");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\,");  
   fs_regx1 = /\{/gi;  fs_find = fs_find.replace(fs_regx1,"\\\{");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\{");  
   fs_regx1 = /\|/gi;   fs_find = fs_find.replace(fs_regx1,"\\\|");  
   fs_replacement = fs_replacement.replace(fs_regx1,"\|");     
   fs_find = "/" + fs_find + "/gi";   //返回替换后的值  
   return fs_expression.replace(eval(fs_find),fs_replacement);  
}  
//格式化數字的函數FormatCurrency
//入參：1. srcStr->被格式化的數字
//      2. nAfterDot->小數位數
//      3. intClause->分隔位數
//調用實例:FormatCurrency('123456789','2','3') ;   return:123,456,789.00
function FormatCurrency(srcStr,nAfterDot,intClause)
{
    var flag = false;
    srcStr = srcStr + '';
    if(srcStr.indexOf('-') != -1)
    {
        srcStr = srcStr.substring(1);
        flag = true;
    }
    if(!isNaN(srcStr))
    {           
        if(intClause>0 || intClause<=srcStr.length)
        {   
            var IvarA;
            var IvarB;
            if(srcStr.indexOf('.')!=-1)
            {
                IvarA=srcStr.substr(0,srcStr.indexOf('.'));
                IvarB='0'+srcStr.substr(srcStr.indexOf('.'));
            }
            else
            {
                IvarA=srcStr;
                IvarB="";
            }
            var Icount=parseInt(IvarA.length/intClause);
            var Imod=IvarA.length%intClause;
            var newstr;
            if(Imod>0)
            {
                newstr=IvarA.substr(0,Imod)+',';
            }
            else
            {
                newstr='';
            }
             var format=',';
            for(var i=1;i<=Icount;i++)
            {               
                if(i==Icount)
                {
                    format='';
                }
                newstr+=IvarA.substr(Imod+(i-1)*intClause,intClause)+format;
            }
            var Point;
            if(IvarB=='')
            {   
                if(nAfterDot>0)
                {  
                    Point=Math.round(Math.pow(10,nAfterDot)); 
                    Point='0.'+Point.toString().substr(1);
                }
                else
                {
                    Point='';
                }
            }
            else
            {
                Point=Math.round(IvarB*(Math.pow(10,nAfterDot)))/Math.pow(10,nAfterDot)
            }
            newstr=newstr+Point.toString().substr(1);    
        }
    }
 if(flag)
 {
     newstr = '-' + newstr;
 }
 return newstr;
}

//取得兩個日期之間的所有年月
//o_DateTimeA:任一日期
//o_DateTimeB:任一日期
//eg:GetCircleDateHESAPPHL(new Date(2007, 10, 11),new Date(2008, 11, 11)) ;
//return: 200710;200711;200712;200801;200802;200803;200804;200805;200806;200807;200808;200809;200810;
function GetCircleDateHESAPPHL(o_DateTimeA,o_DateTimeB)
{
            var yearA = o_DateTimeA.getFullYear() ;
            var MonthA = o_DateTimeA.getMonth() ;
            var yearB = o_DateTimeB.getFullYear() ;
            var MonthB = o_DateTimeB.getMonth() ;
            var s_DateHESAPPHL="";
            if (o_DateTimeA>o_DateTimeB)
            {
               var i_YearNow = yearB;
               var i_MontNow = MonthB;
               for (var iCout = 0; iCout < (yearA - yearB) * 12 + (MonthA - MonthB); iCout++)
                {
                    if(i_MontNow<10)
                        s_DateHESAPPHL = s_DateHESAPPHL + i_YearNow +"0"+ i_MontNow + ";";
                    else
                        s_DateHESAPPHL = s_DateHESAPPHL + i_YearNow + i_MontNow + ";";
                    i_MontNow++;
                    if (i_MontNow > 12)
                    {
                        i_MontNow = 1;
                        i_YearNow++;
                    }
                }
            }
            else
            {
                var i_YearNow = yearA;
                var i_MontNow = MonthA;
                for (var iCout = 0; iCout < (yearB - yearA) * 12 + (MonthB - MonthA); iCout++)
                {
                     if(i_MontNow<10)
                        s_DateHESAPPHL = s_DateHESAPPHL + i_YearNow +"0"+ i_MontNow + ";";
                    else
                        s_DateHESAPPHL = s_DateHESAPPHL + i_YearNow + i_MontNow + ";";
                    i_MontNow++;
                    if (i_MontNow > 12)
                    {
                        i_MontNow = 1;
                        i_YearNow++;
                    }
                }
            }
            return s_DateHESAPPHL;       
}
//比較兩個日期，取得其年月差
//o_DateTimeA,o_DateTimeB 均為日期類型
//eg:GetYearMonth(new Date(2009, 11, 11),new Date(2007, 11, 11))  return: 2年0月
function GetYearMonth(o_DateTimeA,o_DateTimeB)
{
            var i_Month;
            var i_Year;
            var s_ReturnYearMonth="";
            if (o_DateTimeA>o_DateTimeB)
            {
                i_Month = o_DateTimeA.getMonth() - o_DateTimeB.getMonth();
                i_Year = o_DateTimeA.getFullYear() - o_DateTimeB.getFullYear();
            }
            else
            {
                i_Month = o_DateTimeB.getMonth() - o_DateTimeA.getMonth();
                i_Year = o_DateTimeB.getFullYear() - o_DateTimeA.getFullYear();
            }
            if (i_Month < 0)
            {
                i_Month = 12 + i_Month;
                i_Year = i_Year - 1;
            }

            if (i_Year > 0)
                s_ReturnYearMonth = i_Year + "年" + i_Month+"月";
            else
                s_ReturnYearMonth =i_Month + "月";

            return s_ReturnYearMonth;
}
//判斷Start日期是否大於End日期,參數必須是合法日期
//StartDate:Start日期
//EndDate:End日期
//eg:checkSEDate('1983/12/20','1981/10/18')  return: true
function checkSEDate(StartDate,EndDate)  
{  
    var r = StartDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
    var dStart= new Date(r[1], r[3]-1, r[4]);    
    var r = EndDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
    var dEnd= new Date(r[1], r[3]-1, r[4]);   
    return(dStart>dEnd);  
 } 
//計算兩個日期之間間隔的天數
 //o_DateTimeA:比較大日期
 //o_DateTimeB:比較小的日期
//eg:GetMinDays(new Date(2008, 10, 11),new Date(2007, 11, 11),'mm')  return :11
function GetMinDays(o_DateTimeA,o_DateTimeB,type)
{
    if(typeof(o_DateTimeA) != "object" || !(/Date/.test(o_DateTimeA.constructor)))  
        throw new Error(-1,"calDateDistance(o_DateTimeA,o_DateTimeB,type)的o_DateTimeA參數為Date類型.");
    if(typeof(o_DateTimeB) != "object" || !(/Date/.test(o_DateTimeB.constructor)))  
        throw new Error(-1,"calDateDistance(o_DateTimeA,o_DateTimeB,type)的o_DateTimeB參數為Date類型.");
     
    type = (type==null?'dd':type);  
    if(!((new RegExp(type+",","g")).test("yy,mm,ww,dd,hh,mi,ss,ms,")))     
        throw new Error(-1,"calDateDistance(o_DateTimeA,o_DateTimeB,type)的type參數為非法."); 
    var num=0;
    var o =   {  ww : 7*86400000,  dd : 86400000,  hh : 3600000,  mi : 60000,  ss : 1000,  ms : 1  };
    switch(type)  
    {  
    case "yy": num = o_DateTimeA.getFullYear() - o_DateTimeB.getFullYear(); break;  
    case "mm": num = (o_DateTimeA.getFullYear() - o_DateTimeB.getFullYear())*12+o_DateTimeA.getMonth()-o_DateTimeB.getMonth(); break;  
    default:   
     var sub = o_DateTimeA.valueOf() - o_DateTimeB.valueOf();  
     if (o[type])  num = (sub/o[type]);  break;
    }
    return  num;
}
//把0-9 的數字轉成大寫
//Digit:要轉換的數字
//調用實例:CONVERTDIGIT('1') return:ONE
function  CONVERTDIGIT(Digit)
{
    var returnvalue="";
    Digit=Digit+"";
    switch (Digit)
    {
          case "0":
             returnvalue = "ZERO";
             break;
          case "1":
             returnvalue = "ONE";
             break;
          case "2":
             returnvalue = "TWO";
             break;
          case "3":
             returnvalue = "THREE";
             break;
          case "4":
             returnvalue = "FOUR";
             break;
          case "5":
             returnvalue = "FIVE";
             break;
          case "6":
             returnvalue = "SIX";
             break;
          case "7":
             s_ReturnDigit = "SEVEN";
             break;
          case "8":
             s_ReturnDigit = "EIGHT";
             break;
          case "9":
             returnvalue = "NINE";
             break;
    }
    return returnvalue;
}
//檢查頁面上有幾個CheckBox已有勾選
//調用實例:SumCheckBox() return: 已勾選的CheckBox的個數
 function SumCheckBox() {
 var i_Count =0;
 for(i = 0;i < document.forms[0].elements.length;i++)
    {
        if(document.forms[0].elements[i].type!="checkbox") {
         continue;        
        }
        if(document.forms[0].elements[i].checked == true)
         i_Count++;
    }
return i_Count;
}
//全選，當某ID的CheckBox被選中時，讓全頁面的CheckBox全選
//thisCK：CheckBox物件
//調用實例:IsSelectAll(document.all.CheckBox1)   
//return: 讓全頁面的CheckBox.checked=document.all.CheckBox1.checked
function IsSelectAll(thisCK)
{
    var i_Count =0;
    for(i = 0;i < document.forms[0].elements.length;i++)
    {
        if(document.forms[0].elements[i].type!="checkbox") {
         continue;        
        }
        //其它CheckBox的狀態  
        if(thisCK.id != document.forms[0].elements[i].id)
        {
            document.forms[0].elements[i].checked = thisCK.checked;
        }
    }
}
//左右兩個TEXT物件範圍帶值
//obj1=起始物件;
//obj2=終止物件;
//cho=D:日期的物件;U:需轉成大寫的物件;L:需轉成小寫的物件
//pos=1:值來自起始物件;pos=2:值來自終止物件
//調用實例:text_range(document.all.TextBox1,document.all.TextBox2,'U','2')   
function text_range(obj1,obj2,cho,pos){
   flg = true 
   if (pos==1){
      text1=obj1;
      text2=obj2;
   }else{
      text1=obj2;
      text2=obj1;
   }

   if (text1.value==''){
       return;
   }
   
   if (cho=='D'){
      Check_Date_Field(text1,0);
      if (flg==true){
         if (text1.value !='' && text2.value !='')
         {            
            if (obj2.value !=''&& obj1.value > obj2.value ){
             //  alert('起日大於迄日');
               alert(ErrorCode("","1061", ""));  // 起日大於迄日
               obj2.value=obj1.value;                
               obj2.select();
               obj2.focus();
                return;
            }
         }
      }
   }else if(cho=='U'){
      text1.value=text1.value.toUpperCase();
   }else if(cho=='L'){
      text1.value=text1.value.toLowerCase();
   }
   
   if (flg==true && text2.value ==''){
      text2.value=text1.value;
      text2.select();
      text2.focus();
       return;
   }
}
//目的：欄位格式化，不足位時用0補足
//參數：obj對象,intBit欲補足的位數
//調用實例:funFormat(document.all.TextBox1,10)  return:00000012 (PS:document.all.TextBox1.value=12)    
function funFormat(obj,intBit)
{
 var val=obj.value;
 var str="";
 var i=0;
 if(val!="")
 {
  for(i=0;i<intBit;i++)
   str=str+"0";
  val=str + val;
  val=val.substring(val.length-intBit,val.length);
  obj.value=val;
  return true;
 }
 return false;
}
//格式化數字的函數FormatNumber
//入參：1. srcStr->被格式化的數字
//     2. nAfterDot->小數位數
//調用實例:FormatNumber('123456789','2')  return:123456789.00
function FormatNumber(srcStr,nAfterDot){
  var srcStr,nAfterDot;
  var resultStr,nTen;
  srcStr = ""+srcStr+"";
  strLen = srcStr.length;
  dotPos = srcStr.indexOf(".",0);
  if (dotPos == -1){
    resultStr = srcStr+".";
    for (i=0;i<nAfterDot;i++)
      resultStr = resultStr+"0";
    return resultStr;
  }
  else{
    if((strLen - dotPos - 1) >= nAfterDot){
      nAfter = dotPos + nAfterDot + 1;
      nTen =1;
      for(j=0;j<nAfterDot;j++)
        nTen = nTen*10;
      resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
      return resultStr;
    }
    else{
      resultStr = srcStr;
      for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++)
        resultStr = resultStr+"0";
      return resultStr;
    }
  }
} 
//检测在VS2005,视线TreeView中的选择框中 全选或者全不选
//调用实例：TreeView1.Attributes.Add("onclick", "OnTreeNodeChecked();");
function OnTreeNodeChecked() 
{
    var ele = event.srcElement; 
    if(ele.type=='checkbox') 
    { 
        var childrenDivID = ele.id.replace('CheckBox','Nodes'); 
        var div = document.getElementById(childrenDivID); 
        if(div==null)
        {
            TreeNodeCheckedFalse(ele);
            return; 
        }
        var checkBoxs = div.getElementsByTagName('INPUT'); 
        for(var i=0;i<checkBoxs.length;i++) 
        { 
            if(checkBoxs[i].type=='checkbox') 
            checkBoxs[i].checked=ele.checked; 
        }
        if(ele.checked==false)
        {
            TreeNodeCheckedFalse(ele);
        }
    } 
} 
//全不选
function TreeNodeCheckedFalse(ele)
{
    var obj = document.getElementById(ele.parentNode.parentNode.parentNode.parentNode.parentNode.id.replace('Nodes','CheckBox'));
    if(obj!=undefined)
        obj.checked=false; 
    var obj2 = document.getElementById(ele.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id.replace('Nodes','CheckBox'));
     if(obj2!=undefined)
        obj2.checked=false;             
    var obj3 = document.getElementById(ele.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id.replace('Nodes','CheckBox'));
     if(obj3!=undefined)
        obj3.checked=false;
    var obj4 = document.getElementById(ele.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id.replace('Nodes','CheckBox'));
     if(obj4!=undefined)
        obj4.checked=false;
}
//使當前日期加一個數或減一個數
// objInt為+n或-n
// objDate為要加減的日期對象
// objStr設置置描述 yyyy 年 q 季度 m 月 y 一年的日數 d 日 w 一周的日數 ww 周 h 小時 n 分鐘 s 秒
// 调用实例：  function  test()
//{
//        var getdate=document.all.TextBox1;
//        SetDate('2',getdate,'yyyy');		
//}		
		
function SetDate(objInt,objDate,objStr)		
{		
	execScript('dim n : n = DateAdd("'+objStr+'", "'+objInt+'", "'+ objDate.value +'")','vbscript');//好象只能用在IE瀏覽器上	
	var d = new Date(n);	
	var strMonth=d.getMonth()+1;	
	var strDate=d.getDate();	
	if(d.getMonth()<10)	
		strMonth = "0"+(d.getMonth()+1);
	if(d.getDate()<10)	
		strDate = "0"+d.getDate();
	var nextDate = d.getFullYear() + "-" + strMonth + "-" + strDate;	
	objDate.value =nextDate;	
}		

	
//-----[欄位長度格式化，不足位時用自定議字元補足]	
//      參數：	
//      obj :對象	
//      len :長度	
//      Char:補位的字元	
//      way :補位的方向，L為左補 R為右補	
function stringPad(obj,len,Char,way)	
{			
    if(way!="L"&&way!="R"&&way!="l"&&way!="r")return;			
	var val=obj.value.trim();		
	var str="";		
	var i=0;		
	if(val!="")		
	{		
		for(i=0;i<len-val.length;i++)	
			str=str+Char;

		if(way=="R"||way=="r")	
		  val+=str;	
		else	
		  val=str+val;	
		  	
		obj.value=val;	
    }			

}			


//--------------[值驗証,判斷日期YYYY/MM/DD是否合法,反回bool 值
//功能			：驗證年月,返回bool值

//eg:var b=IsYYYYMMDD("2007/02/29","YM");//反回false			
// strFLG="M"  驗證月			
// strFLG="Y"  驗證年			
// strFLG="D"  驗證日			
// strFLG="MD" 驗證月日			
// strFLG="YMD"驗證年月日			
function IsYYYYMMDD(strDate,strFLG) 			
{ 		
	var r = strDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);  	
	var bYMD=false;	
	if(r==null) return false;  	

	var d= new Date(r[1], r[3]-1, r[4]);  	
	strFLG=strFLG.toUpperCase();	

	switch(strFLG)	
	{	
		case "Y": {bYMD=(d.getFullYear()==r[1]);break;}
		case "M": {bYMD=((d.getMonth()+1)==r[3]);break;}
		case "D": {bYMD=(d.getDate()==r[4]);break;}
		case "YM": {bYMD=(d.getFullYear()==r[1] && (d.getMonth()+1)==r[3]);break;}
		case "MD": {bYMD=((d.getMonth()+1)==r[3] && d.getDate()==r[4]);break;}
		case "YMD": {bYMD=(d.getFullYear()==r[1] && (d.getMonth()+1)==r[3] && d.getDate()==r[4]);break;}
	}	
	return bYMD; 	
} 		

//---------------[值驗証,判斷欄位是否輸入為空,反回bool 值]
//參數          ：傳入要警訊的消息內容的和頁面物件ID		
//eg:return IsEmpty('特店代碼','_ctl0_txtMerNo');		
function IsEmpty(objTitle,objID)		
{		
    var obj=document.getElementById(objID);		
	if(obj==null)	
	{	
		return false;
	}	
	var objValue=obj.value;	
	if(trimSpace(objValue)=="")	
	{	
		alert("請輸入"+objTitle);
		obj.select();
		obj.focus();
		return false;
	}	
	return true;	
}		

//判斷欄位值是否為數字（驗證控件）		
function Check_Num_Field(ObjName)		
{		
	var LV_Value = ObjName.value.trim();	

    if ((LV_Value != '') && (!isNumeric(LV_Value)))		
    {		       
        ObjName.value="";	      
        return false;	
    }	
    return true;	
}	

//判斷欄位值是否是整數（驗證控件）	
function Check_Int_Field(ObjName)	
{	
	var LV_Value =ObjName.value.trim();
    if ((LV_Value != '') && (!isInteger(LV_Value)))	
    {	
       // alert('請輸入整數！');	
        ObjName.focus();	
        //ObjName.select();	
          ObjName.value="";	
       return false;			
    }			
    return true;			
}			

//判斷日期是否合法（驗證控件）			
//輸入參數：vobjName　日期對象			
//　　　　　bolChDate 是否為民國日期，0－公元日期，1－民國日期 			
function Check_Date_Field(vobjName,bolChDate)			
{ 			
	var strDay,strMonth,strYear,strCYear,strTimeAry;		
	var strDate,StandDate,strWeekName,datDate,intNum,strChangeDate;		
	var resStr="";		
	strDate=vobjName.value.trim();		
	if(strDate=="")	return; //若日期為空值, 則不做 	//如果是民國日期先轉換為公元日期 
	if(bolChDate==1)		
	{ 				
		if(strDate.indexOf("-")>0)			
			strDate=(strDate.substring(0,strDate.indexOf("-"))*1+1911).toString()+(strDate.substring(strDate.indexOf("-"),strDate.length)).toString();		
		else			
			if(strDate.indexOf(".")>0)		
				strDate=(strDate.substring(0,strDate.indexOf("."))*1+1911).toString()+(strDate.substring(strDate.indexOf("."),strDate.length)).toString();	
			else		
				if(strDate.indexOf("/")>0)	
					strDate=(strDate.substring(0,strDate.indexOf("/"))*1+1911).toString()+(strDate.substring(strDate.indexOf("/"),strDate.length)).toString();
				else	
					strDate=(strDate.substring(0,strDate.length-4)*1+1911).toString()+(strDate.substring(strDate.length-4,strDate.length)).toString();
	}				
	//設定日期為準模式 "1997/5/6"				
	//XXXX-XX-XX				
	if(strDate.indexOf("-")!=0)				
	{	 resStr="";			
		for(var i=0;i<strDate.length;i++)	
			resStr=resStr +((strDate.substring(i,i+1)).replace('-','/'));
		strDate=resStr;	
	}		
	//XXXX.XX.XX		
	if(strDate.indexOf(".")!=0)		
	{	resStr="";	
		for(var i=0;i<strDate.length;i++)	
			resStr=resStr +((strDate.substring(i,i+1)).replace('.','/'));
		strDate=resStr;	
	}		
	//XXXXXXXX		
	if(strDate.indexOf("/")<=0)		
	{	strDate=(strDate+'000000000a').substring(0,8);	
	 	strDate=strDate.substring(0,4)+"/"+strDate.substring(4,6)+ "/"+strDate.substring(6,8);	
	}		
	//判斷日期是否合法	
	if(!IsDate(strDate))	
	{	
		alert('請輸入正確日期, 格式為YYYY/MM/DD！');
		vobjName.focus();
		vobjName.select();
		return;
	}	

	if(bolChDate==1)	
	{ 	
		strYear='000'+(strDate.substring(0,4)*1-1911);
		strYear=strYear.substring(strYear.length-3,strYear.length); 
		strDate=strYear.toString()+(strDate.substring(4,strDate.length));
	} 	
 	vobjName.value=strDate;	
}			

//目的：比較大小 			
//參數：obj1,obj2比較對象,sNO序位：1-由obj1引發事件，2－由obj2引發事件			
//  xx. YYYY/MM/DD   VER     AUTHOR      COMMENTS			
//   1. 2005/06/09   1.00    XIONG       Create			
function funRange(obj1,obj2,sNO)			
{			
	var val_1=obj1.value;		
	var val_2=obj2.value;		
	if(val_1!="" && val_2!="" && val_1>val_2)		
	{		
		alert('起值不能大於迄值！');	
		if(sNO==1)	
		{	
			obj1.value=obj2.value;
			obj1.select();
		}	
		else	
		{	
			obj2.value=obj1.value;
			obj2.select();
		}	
		return false;	

	}		

	if(val_1!="" && val_2=="" && sNO==1)		
	{		
		obj2.value=obj1.value;	
		obj2.select();	
	}		

	if(val_2!="" && val_1=="" && sNO==2)	
	{	
		obj1.value=obj2.value;
		obj1.select();
	}	
	return true;	
}		


//目的：比較月份大小 		
//參數：objY1,objM1,objY2,objM2比較對象,sNO序位：1-由objM1引發事件，2－由objM2引發事件		
//  xx. YYYY/MM/DD   VER     AUTHOR      COMMENTS		
//   1. 2005/05/26   1.00    XIONG       New Create		
function funRangeMonth(objY1,objM1,objY2,objM2,sNO)		
{		
	var valY_1=objY1.value.trim();		
	var valY_2=objY2.value.trim();		
	var valM_1=objM1.value.trim();		
	var valM_2=objM2.value.trim();		
	if(valY_1!="" && valY_2!="" && valY_1>valY_2)		
	{		
		alert("年度起值不能大於迄值！");
		if(sNO==1)	
		{	
			objY1.value=objY2.value;
			objM1.value=objM2.value;
			objM1.select();
		}	
		else	
		{	
			objY2.value=objY1.value;
			objM2.value=objM1.value;
			objM2.select();
		}	
		return false;	

	}		
	if(valY_1!="" && valY_2!="" && valY_1==valY_2 && valM_1!="" && valM_2!="" && valM_1 > valM_2 )		
	{		
		alert("月份起值不能大於迄值！");	
		if(sNO==1)	
		{	
			objM1.value=objM2.value;
		}	
		else	
		{	
			objM2.value=objM1.value;
		}
		return false;

	}	
	return true;	
}		
/******************************		
*作用：判斷一個物件中輸入的值是否超過設定大小,不區分中英文的，中文占１個長度		
*名稱：isOver_EN（）		
*參數：sText：對像名，len：允許長度		
*用法示例：isOver_EN('_ctl0_txtMerNo',20)		
*/		
function isOver_EN(objID,len)		
{		
	var obj=document.getElementById(objID);	
	var intlen=obj.value.length;	
	if (intlen>len)	
	{	//alert("資料要求長度不得超過"+len+"個字符，目前資料長度是"+intlen+"個字符");
		obj.focus();
		obj.select();
		return false;
	}	
	return true;	
}		
//全選checkBox		
//參數：CheckBoxHead,CheckBoxItem,objFormName		
function checkBoxSelect(CheckBoxHead,CheckBoxItem,objFormName)		
{		
	if(CheckBoxHead=="" || CheckBoxItem=="") return;	
	var boolSelect=false;	
	var boolCheck=false;	
	for (var i=0;i<objFormName.length;i++) 			
	{			
		if(boolCheck==false)//避免查找重名的元素 		
		{		
			if (objFormName.elements[i].id.indexOf(CheckBoxHead) != -1) 	
			{	
				boolSelect=objFormName.elements[i].checked;
				boolCheck=true;
			}	
		}		
		if (objFormName.elements[i].name.indexOf(CheckBoxItem) != -1) 		
		{ 		
			objFormName.elements[i].checked=boolSelect;	
		}		
	}			
}				
//目的：檢查控件是否包含除字母、數值、下橫?以外的字符 			
function checkTextCN(objID)			
{			
	var TextName=document.getElementById(objID);		
	if(TextName==null)		
	{		
		return false;	
	}		
	var strCheck=TextName.value.trim();		
	if(strCheck=="") return true;		
	for(var i=0;i<strCheck.length;i++)		
	{		
		var strTemp=strCheck.charAt(i);	
		if((strTemp<"A" || strTemp>"Z") && (strTemp<"a" || strTemp>"z") && (strTemp<"0" || strTemp>"9") && (strTemp!="_"))	
		{	
			alert('該欄位的值必須是字母、數值以及下橫');
			TextName.focus();
			TextName.select();
			return false;
		}	
	}		
	return true;		
}			
//判斷日期是否合法（驗證控件）			
function checkDate(objText) 			
{ 			
	var strTemp=objText.value.trim();		
	if(strTemp=="") return true;		
	var r = strTemp.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);  		
	if(r==null)		
	{		
		alert('日期不符合格式！');	
		objText.focus();
		objText.select();
		return false;  
	} 	
	var d= new Date(r[1], r[3]-1, r[4]);  	
	if(!((d.getFullYear()==r[1] && (d.getMonth()+1)==r[3] && d.getDate()==r[4])))	
	{	
		alert('日期不符合格式！');
		objText.focus();
		objText.select();
		return false; 
	}	
	return true;	
} 		
//判斷是否是整數（驗證控件） 		
function checkInteger(objText)		
{		
	var strCheckNum=objText.value.trim();	
	if(strCheckNum=="") return true;	
	if(isNaN(strCheckNum))//不是數值 	
	{	
		alert('該欄位的值必須是整數值');
		objText.focus();
		objText.select();
		return false;
	}	
	if(parseFloat(strCheckNum) > parseInt(strCheckNum)) //不是整數	
	{	
		alert('該欄位的值必須是整數值');
		objText.focus();
		return false;
	}	
	return true;						
}							
										
//判斷Start日期是否大於End日期,參數必須是合法日期(驗證控件)											
function checkTowDateText(objStartDate,objEndDate)											
{											
	var StartDate=objStartDate.value.trim();										
	var EndDate=objEndDate.value.trim();										
	if(StartDate=="")										
	{										
		alert('請輸入日期, 格式為YYYY/MM/DD！');									
		objStartDate.focus();									
		objStartDate.select();									
		return false;									
	}										
	if(EndDate=="")	
	{	
		alert('請輸入日期, 格式為YYYY/MM/DD！');
		objEndDate.focus();
		objEndDate.select();
		return false;
	}	

	var r = StartDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 	
	var dStart= new Date(r[1], r[3]-1, r[4]);  	
	var r = EndDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);  	
	var dEnd= new Date(r[1], r[3]-1, r[4]); 	
	if(dStart>dEnd)	
	{	
		alert('起值不能大於迄值！');
		objEndDate.value=StartDate;
		objStartDate.focus();
		objStartDate.select();
		return false;
	}	
	return true;	
}		
	
//實現List的單一Item添加
//參數    ：頁面物件ID,待選,已選的物件ID		
function addItem(objSelectID,objSelectedID)		
{		
  	var objSelect=document.getElementById(objSelectID);//待選物件	
  	var objSelected=document.getElementById(objSelectedID);//已選物件	
  	if(objSelect==null || objSelected==null)//只要找不到一個物件,退出	
  	{	
  		return false;	
  	}		
	for(var i=0;i<objSelect.options.length;i++)//循環將待選物件中的選中項目加入已選		
	{		
		if(objSelect.options[i].selected)	
		{	
			var iKey =objSelect.options[i].value;
			var iText=objSelect.options[i].text;
			var options=new Option(iText,iKey);
			var len=objSelected.options.length;
			objSelected.options[len]=options;
			options=null;
			objSelect.options[i]=null;
			i=i-1;
		}	
	}		
	return true;	
 }		
//實現List的所有Item添加		
//Author        ：Ales		
//Last Modifiy  ：2006/03/13		
//參數          ：頁面物件ID,待選,已選的物件ID		
function addAllItem(objSelectID,objSelectedID)		
{		
  	var objSelect=document.getElementById(objSelectID);//待選物件	
  	var objSelected=document.getElementById(objSelectedID);//已選物件	
  	if(objSelect==null || objSelected==null)//只要找不到一個物件,退出	
  	{	
  		return false;
  	}	
    	for(var i=0;i<objSelect.options.length;i++)	
	{	
		var iKey =objSelect.options[i].value;
		var iText=objSelect.options[i].text;
		var options=new Option(iText,iKey);
		var len=objSelected.options.length;
		objSelected.options[len]=options;
		options=null;
	}	
	while(objSelect.options.length=0)	
	{	
		objSelect.options[objSelect.options.length]=null;
	}	
	return true;	
}		
//實現List的所有單一刪除
//參數     ：頁面物件ID,待選,已選的物件ID			
function removeItem(objSelectID,objSelectedID)			
{			
  	var objSelect=document.getElementById(objSelectID);//待選物件		
  	var objSelected=document.getElementById(objSelectedID);//已選物件		
  	if(objSelect==null || objSelected==null)//只要找不到一個物件,退出		
  	{		
  		return false;	
  	}		
	for(var i=0;i<objSelected.length;i++)		
	{		
		if(objSelected.options[i].selected)	
		{	
			var iKey =objSelected.options[i].value;
			var iText=objSelected.options[i].text;
			var options=new Option(iText,iKey);
			var len=objSelect.options.length;
			objSelect.options[len]=options;
			options=null;
			objSelected.options[i]=null;
			i=i-1;
		}	
	}		
	return true;		
 }			
//實現List的所有刪除		
//參數          ：頁面物件ID,待選,已選的物件ID			
function removeAllItem(objSelectID,objSelectedID)			
{			
  	var objSelect=document.getElementById(objSelectID);//待選物件		
  	var objSelected=document.getElementById(objSelectedID);//已選物件	
  	if(objSelect==null || objSelected==null)//只要找不到一個物件,退出	
  	{	
  		return false;
  	}	
	for(var i=0;i<objSelected.options.length;i++)	
	{	
		var iKey =objSelected.options[i].value;
		var iText=objSelected.options[i].text;
		var options=new Option(iText,iKey);
		var len=objSelect.options.length;
		objSelect.options[len]=options;
		options=null;
	}	
	while(objSelected.options.length=0)	
	{	
		objSelected.options[objSelected.options.length]=null;
	}	
	return true;	
}		
//實現List的所有選中	
//參數          ：頁面物件ID,待選,已選的物件ID		
function selectAllItem(objSelectedID)		
{		
  	var objSelected=document.getElementById(objSelectedID);//已選物件	
  	if(objSelected==null)//只要找不到物件,退出	
  	{	
  		return false;
  	}	
	if(objSelected.options.length > 0)	
	{	
	for(var i=0;i<objSelected.options.length;i++)	
	{	
		objSelected.options[i].selected=true;
	}	
	}	
	return true;	
}		
//實現單一List的所有刪除			
//參數          ：頁面物件ID,待選,已選的物件ID		
function deleteLstItem(objID)		
{		
  	var obj=document.getElementById(objID);//已選物件	
  	if(obj==null )//只要找不到物件,退出	
  	{		
  		return false;	
  	}		
	for(var i=0;i<obj.length;i++)		
	{		
		if(obj.options[i].selected)	
		{	
			obj.options[i]=null;
			i=i-1;
		}	
	}		
	return true;		
 }			
/*功能:返回兩日期之差			
 *    type: 返回類別標識.yy:年,mm:月,ww:周,dd:日,hh:小時,mi:分,ss:秒,ms:毫秒			
 *    intOrFloat :返回整型還是浮點型值 0:整型,不等於0:浮點型			
 *    output : 輸出提示,如:時間差為#
 *    Example:		
 *     var date1=new Date("2006","03","01");		
 *     var date2=new Date("2006","04","01");		
 *     date2.DateDiff(date1,"dd","0",null)返回值為30		
 */   		
Date.prototype.DateDiff = function (date,type,intOrFloat,output)		
{		
	if(typeof(date) != "object" || !(/Date/.test(date.constructor)))	
		throw new Error(-1,"calDateDistance(date,type,intOrFloat)的date參數為Date類型.");
		type = (type==null?'dd':type);
	if(!((new RegExp(type+",","g")).test("yy,mm,ww,dd,hh,mi,ss,ms,")))	
		throw new Error(-1,"calDateDistance(pd,type,intOrFloat,output)的type參數為非法.");
	var iof = (intOrFloat==null?0:intOrFloat);	
	var num=0;	
	var o = 	
	{	
		"ww" : 7*86400000,
		"dd" : 86400000,
		"hh" : 3600000,
		"mi" : 60000,
		"ss" : 1000,
		"ms" : 1
	}	
	switch(type)	
	{	
		case "yy": num = this.getFullYear() - date.getFullYear(); break;
		case "mm": num = (this.getFullYear() - date.getFullYear())*12+this.getMonth()-date.getMonth(); break;
		default: 
		var sub = this.valueOf() - date.valueOf();
		if (o[type])
		num = (sub/o[type]).fmtRtnVal(iof);
		break;
	}	
	return (output ? output.replace(/#/g," "+num+" ") : num);	
}		
//返回整數或者兩位小數的浮點數		
Number.prototype.fmtRtnVal = function (intOrFloat)		
{		
	return (intOrFloat == 0 ? Math.floor(this) : parseInt(this*100)/100);	
}		
//根據當前日期所在年和周數返回週日的日期		
//Author:Seven		
Date.prototype.RtnByWeekNum = function (weekNum)		
{		
	if(typeof(weekNum) != "number")	
	throw new Error(-1,"RtnByWeekNum(weekNum)的參數是數字類型.");
	var date = new Date(this.getFullYear(),0,1);
	var week = date.getDay();
	week = (week==0?7:week);
	return date.dateAfter(weekNum*7-week,1);
}	
//根據日期返回該日期所在年的周數	
Date.prototype.getWeekNum = function (){	
	var dat = new Date(this.getFullYear(),0,1);
	var week = dat.getDay();
	week = (week==0?7:week);
	var days = this.calDateDistance(dat,"dd")+1;
	return ((days + 6 - this.getDay() - 7 + week)/7);
}	
/*功能:設置畫面上聯動的年月日,年以前后N年進行聯動	
* 傳入日期:drpYea:年的下拉ObjectID;drpMonth:月的下拉ObjectID;drpDay:天的下拉ObjectID;i_Year前後聯動的年數 	
*                       flag:若為1則後N年;若為-1則為前N;若為0,則前後N年;若為2則為年不需要推算	
* 範例:在onchange時調用setDrpDate('drpYear','drpMonth','drpDay',5,-1)	
*/	
function setDrpDate(drpYear,drpMonth,drpDay,n_Year,flag)	
{	
	var c_Year;
	var c_Month;
	var c_Day;
	var o_Year=document.getElementById(drpYear);
	var o_Month=document.getElementById(drpMonth);
	var o_Day=document.getElementById(drpDay);
	//設定年份
	if(o_Year!=null )
	{			
			c_Year=parseInt(o_Year.value);	
			if(flag!=2)	
			{	
			//清空年的當前下拉選項。	
			while(o_Year.options.length=0)	
			{	
				o_Year.options[o_Year.options.length]=null;
			}	
			//把前五年寫入年下拉框,加上當前年,寫入6個Item	
			for(var i=n_Year;flag<=0 && i>=0;i--)	
			{	
				var iKey =c_Year-i;
				var iText=c_Year-i;
				var options=new Option(iText,iKey);
				var len=o_Year.options.length;
				o_Year.options[len]=options;
				options=null;
			}	
			//把后五年寫入年下拉框,寫入5個ITEM	
			for(var i=1;flag>=0 && i<=n_Year;i++)	
			{	
				var iKey =c_Year+i;
				var iText=c_Year+i;
				var options=new Option(iText,iKey);
				var len=o_Year.options.length;
				o_Year.options[len]=options;
				options=null;
			}	
			//選中當前年,第六項	
			o_Year.options[n_Year].selected=true;	
		}//end of "if(flag!=2)"		
	}//end of "if(o_Year!=null )"	
	//月份的設定不需要進行聯動改變	
	//根據年與月份，獲取該月份的天數，並設置天數下拉選項	
	if(o_Month!=null)	
	{	
		c_Month=o_Month.value;
	}	
	var c_Date=new Date(c_Year,c_Month,'00');	
	var i_Day=parseInt(c_Date.getDate());	
	//設定天	
	if(o_Day!=null )	
	{	
		c_Day=o_Day.value;
		//清空天的當前下拉選項。

		while(o_Day.options.length=0)
		{
			o_Day.options[o_Day.options.length]=null;		
		}			
		if(c_Month!="")			
		{			
			//補一個空的下拉項給LIST		
			o_Day.options[0]=new Option("","");		
			//把天數寫入天的下拉框		
			for(var i=1;i<=i_Day;i++)		
			{		
				var iKey =i;	
				var iText=i;	
				if(i<10)	
				{	
					ikey="0"+iKey;
					iText="0"+iText;
				}	
				var options=new Option(iText,iKey);	
				var len=o_Day.options.length;	
				o_Day.options[len]=options;	
				if(iKey==c_Day)//選中前面已選中的日期	
				{	
					o_Day.options[i-1].selected=true;
				}	
				options=null;	
			}		
		}			
	}				
}					
/**********************************************************************************					
*作用：檢查控件是否為時間格式(05:30)					
*名稱：CheckFirmIDUpdate（）					
*參數：					
*用法示例：CheckFirmIDUpdate()		
*************************************************************************************/		
//目的： 		
function CheckTextTime(objID)		
{		
		
	var objTime=document.getElementById(objID);	
	var strCheck=objTime.value.trim();	
	if(strCheck=="") return true;	
	if(!IsTimeOne(strCheck))	
	{	
		alert('請輸入時間格式為[HH:MM]!');
		objTime.value="";
		objTime.focus();
		objTime.select();
	}	
}		
		
//檢查時間合法性（驗證變量） 		
function IsTimeOne(strTime)		
{		
	var strHours;	
	var strMinutes;	
	var strTimeArry3=strTime.split(":");　//以":"為界分裝到數組中	
	if(strTimeArry3.length!=2)//如果不是時分秒標准形式不判斷　　	
		return false;
	strHours=strTimeArry3[0];	
	strMinutes=strTimeArry3[1];	
	if(isNaN(strHours)||isNaN(strMinutes))	
		return false;
	if(strTimeArry3[0].length!=2||strTimeArry3[1].length!=2)	
		return false;
	if(strHours>23||strHours<0||strMinutes>59||strMinutes<0)	
		return false;
	return true;	
}		
/*功能:通過Data/messages.xml中的信息代號來show出提示信息		
* 傳入參數:s_MsgCode   信息代號		
* 範例:在onclick時調用showMsg('E000')		
*/		
function showMsg(s_MsgCode)		
{		
	var oDoc = new ActiveXObject("MSXML2.DOMDocument");	
	var result ="../Data/Message.xml";	
	oDoc.async=false;	
	oDoc.load(result);	
	var root=oDoc.documentElement;		
	if (root==null) return;		
	var i;		
	for(i=1;i<root.childNodes[0].childNodes[1].childNodes.length;i++)		
	{     		
		if(root.childNodes[0].childNodes[1].childNodes[i].childNodes[0].text==s_MsgCode)	
		{	
			alert(root.childNodes[0].childNodes[1].childNodes[i].childNodes[1].text);
		}	
	}		
 }			
/*功能:通過Data/messages.xml中的信息代號來查出提示信息					
* 傳入參數:s_MsgCode   信息代號			
* 範例:在onclick時調用getMsg('E000')			
*/			
function getMsg(s_MsgCode)			
{			
	var oDoc = new ActiveXObject("MSXML2.DOMDocument");		
	var result = "../Data/messages.xml";		
	oDoc.async=false;		
	oDoc.load(result);		
	var root=oDoc.documentElement;		
	if (root==null) return;		
	var i; 		
	for(i=1;i<root.childNodes[0].childNodes[1].childNodes.length;i++)		
	{     		
		if(root.childNodes[0].childNodes[1].childNodes[i].childNodes[0].text==s_MsgCode)	
		{	
			return (root.childNodes[0].childNodes[1].childNodes[i].childNodes[1].text);
		}	
		return '';
	}	
}		
/*功能:替換提示信息中的%，得到替換后的信息		 		
* 傳入參數:vstrMsg 錯誤信息, vstrPara 替換參數		
* 備註:參數之間通過$來區分開		
* 範例:在onclick時調用getReplaceMsgDesc(getMsg('E000'),'你好$嗎？')		
*/		
function getReplaceMsgDesc(vstrMsg,vstrPara)		
{		
	var vntArray=vstrPara.split('$');	
	for(var i=0;i<vntArray.length;i++)	
	{	
		var intPos = vstrMsg.indexOf('%');
		if(intPos!=-1)	
		{	
			vstrMsg = vstrMsg.replace('%', vntArray[i]);
		}	
	}		
	return vstrMsg		
}			
/*功能:替換提示信息中的%，show出替換后的信息			 			
* 傳入參數:vstrMsg 錯誤信息, vstrPara 替換參數			
* 備註:參數之間通過$來區分開			
* 範例:在onclick時調用showReplaceMsgDesc(getMsg('E000'),'你好$嗎？')			
*/			
function showReplaceMsgDesc(vstrMsg,vstrPara)			
{			
	var vntArray=vstrPara.split('$');		
	for(var i=0;i<vntArray.length;i++)		
	{		
		var intPos = vstrMsg.indexOf('%');	
		if(intPos!=-1)	
		{	
			vstrMsg = vstrMsg.replace('%', vntArray[i]);
		}	
	}		
	alert(vstrMsg);		
}			
/*功能:在Grid查無資料的時候,動態設置GridDetail所屬Div的寬度。						
* 傳入參數:grdDetailId DIV的ID,height 高度 ,width 寬度			
* 備註:參數之間通過$來區分開			
* 範例:setGridCSS()//表示採用Deafult的用法。若有特殊的可傳入setGridCSS('grdDetail','50','750')			
*/			
function setGridCSS(grdDetailId,height,width)			
{			
	if(grdDetailId==null)		
	{		
		var obj=document.getElementById("grdDetail");	
	}else		
	{		
		var obj=document.getElementById(grdDetailId);	
	}		
	if(obj!=null)		
	{		
		if(height==null)	
		{	
			obj.style.height="50";
		}else	
		{	
			obj.style.height=height;
		}	
		//	
		if(width==null)	
		{	
			obj.style.width="750";
		}else	
		{	
			obj.style.width=width;
		}	
	}		
}			
//*****************************************************			
//* 函數名稱: GetCulture			
//* 目    的: 獲取當前語言
//* 參    數: GetCulture()
//* 範    例: alert(GetCulture());
//*****************************************************
function GetCulture()
{
    var o_Culture = document.getElementById("hd_CULTURE");
    if(o_Culture==null)
    {
        return 1;
    }
    else
    {
        if(o_Culture.value=="")
        {
            return 1;
        }
        else
        {
            if(o_Culture.value.toUpperCase()=="ZH-TW")return 1;
            if(o_Culture.value.toUpperCase()=="ZH-CN")return 2;
            if(o_Culture.value.toUpperCase()=="EN-US")return 3;
        }
    }
}
//*****************************************************
//* 函數名稱: showMsg
//* 目    的: 將訊息alert顯示
//* 參    數: s_MsgCode(訊息ID)
//* 範    例: showMsg('000000')
//*****************************************************
function showMsg(s_MsgCode)
{
    var k = GetCulture();
    var oDoc = new ActiveXObject("MSXML2.DOMDocument");
    var result ="../Data/Message.xml";
    oDoc.async=false;
    oDoc.load(result);
    var root=oDoc.documentElement;
    if(root==null)
    {
        var result ="../../Data/Message.xml";
        oDoc.async=false;
        oDoc.load(result);
        root=oDoc.documentElement;
    }
    if (root==null)
    {
        alert("Error");
        return;
    } 
    var i; 
    for(i=0;i<root.childNodes.length;i++)
    {     
        if(root.childNodes[i].childNodes[0].text==s_MsgCode)
        {
            alert(root.childNodes[i].childNodes[k].text);
            return;
        }
    }
    alert("this Message not find");
    return;
}
//*****************************************************
//* 函數名稱: getMsg
//* 目    的: 將訊息return給頁面

//* 參    數: s_MsgCode(訊息ID)
//* 範    例: getMsg('000000')
//*****************************************************
function getMsg(s_MsgCode)
{
    var k = GetCulture();
    var oDoc = new ActiveXObject("MSXML2.DOMDocument");
    var result = "../Data/Message.xml";
    oDoc.async=false;
    oDoc.load(result);
    var root=oDoc.documentElement;
    if(root==null)
    {
        var result ="../../Data/Message.xml";
        oDoc.async=false;
        oDoc.load(result);
        var root=oDoc.documentElement;
    }
    if(root==null)
    {
        var result ="Data/Message.xml";
        oDoc.async=false;
        oDoc.load(result);
        var root=oDoc.documentElement;
    }
    if (root==null) return "Error";
    var i; 
    for(i=0;i<root.childNodes.length;i++)
    {     
        if(root.childNodes[i].childNodes[0].text==s_MsgCode)
        {
           if(root.childNodes[i].childNodes[k].text.trim().length>0)
           {
              return(root.childNodes[i].childNodes[k].text);
           }
           else
           {
            return "ID"+s_MsgCode+", Message  value not find";
           }
          
        }
    }
   return "ID"+s_MsgCode+",Message  value not find";
}
//*****************************************************
//* 函數名稱: inputNubmerFloatTwo
//* 目    的: 輸入控制,僅限輸入 0~9 十個字元和小數點			
//* 參    數: 			
//*****************************************************			
function  inputNubmerFloatTwo() 			
{			
	var key=window.event.keyCode;		
	var _obj = window.event.srcElement;		
	var _objValue = _obj.value;		
			
	var inputValue=_objValue.replace(document.selection.createRange().text,'');		
	if(key==46)		
	{		
		if(inputValue.length==0)	
		{	
			window.event.keyCode=0;
			return;
		}	
		if(inputValue.indexOf(".")>=0)	
		{	
			window.event.keyCode=0;
			return;
		}	
	}		
	if((key>=48 && key<=57)||key==46)		
	{		
		return true;	
	}		
	else		
	{  		
		window.event.keyCode=0;	
		return true;	
	}		
}		
//*****************************************************		
//* 函數名稱: Transfer		
//* 目    的: 保留小數點後N位		
//* 參    數: obj(控件,如this),num(保留小數點位數)		
//*****************************************************		
function Transfer(obj,num)		
{   		
	var _value= parseFloat(obj.value);	
	if(isNaN(_value))	
	{	
		return;
	}	
	obj.value = _value.toFixed(num);	
}		
//add by ice*****************************************************		
//控制gridview中的全選	
function fun_SelectRows(dgName,chkName,chkId)	
{	
    var intLen = document.getElementsByTagName("input").length;	
	var objA = document.getElementsByTagName("input");
	//在所有input控件中循環，當找到checkbox時就做操作

	var forClient=document.getElementById(dgName+"_ctl01_"+chkName);//GridView對其中的控件中的自動命名（ID）

	if( forClient == null )
	    return;
	var objId;
	for(var i=0;i<intLen;i++)
	{
	    objInput = objA[i]; 
	    objId=objInput.id;
	 
	    var Temp=objId.split("_");
	    	
	    var chkIdTemp=Temp[2];	
		var objS = objA[i].getAttribute("type");
		if(objS=="checkbox" && chkIdTemp==chkId)
		{
            objA[i].checked=forClient.checked;		
		}
	}	
	return false;	
}		
//*****************************************************		
//檢查輸入的是否數字跟小數點，如果不是的話會刪掉，實例onkeyup="checkNubmerFloat()"		
function checkNubmerFloat()		
{		
    var obj=event.srcElement;		
    		
    if(obj.value.length > 0)
    {
        if(/[\d|.]/.test(obj.value.substring(obj.value.length-1,obj.value.length)) == false)
        {
            obj.value = obj.value.substring(0,obj.value.length-1);
        }
    }
}
//檢查是否為英文字母函數或數字函數  
function isIA(){   
  var instring=this.toString();  
 for(i=0;i<instring.length;i++){  
 var value=instring.charAt(i);  
     if((value < "A" || value >"Z")&&(value < "a"||value > "z")&&(value < "0" || value >"9")) return false;  
 }  
 return true;  
}
//---[輸入控制,過濾非數字內容]
//eg:onblur="OnlyInputNumber()"
function OnlyInputNumber()
{
    var obj=event.srcElement;
    if(obj.value.search(/\W/g) != -1)
    {
        obj.value=obj.value.replace(/\W/g,'');   
        obj.value=obj.value.replace(/\W/g,'');
    }
    if(obj.onkeydown==null)
    {
        obj.onkeydown=OnlyInputNumber;
    }   
}
//將輸入的字符在onblur時候自動轉化為大寫字母
//example: onblur="return chartoUpper(this);"
function chartoUpper(objid)
{
 objid.value=objid.value.toUpperCase();
}
//驗證電話號碼
function checkTelNumber(obj)
{

   var objValue=document.getElementById(obj).value;
   if (objValue.trim()!= "")
   {
         var phone=objValue;
         var p1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
         var me = false;
         if (p1.test(phone))
         me=true;
         if (!me)
         {
             return false;
         }
         else
         {
         return true;
         }
    }
}
/********************************************
* 函數名稱：IsDelete()
* 目的：刪除前判斷l	
* 參數說明：		
* example: return IsDelete();		
*********************************************/		
//刪除前判斷		
function IsDelete()		
{		
ClearError();		
 	//判斷是否有勾選	
 	if(SumCheckBox() == 0){	
        //請勾選要刪除的資料！		
        AddMsg(getMsg('E00003'));		
		return false;
 	}	
 	else
 	{
 	    //確認刪除資料？

 	    return confirm(getMsg('E00004'));
 	}
}	
//檢測Q頁面是否有選取	
function SumCheckBox() {	
	var i_Count =0;
	for(i = 0;i < document.forms[0].elements.length;i++)
    {	
        if(document.forms[0].elements[i].type!="checkbox")	
        {	
        	continue;        
        }	
        //修改：加入document.forms[0].elements[i].id!="grvQuery_ctl01_chkHitAll"	
        //目的：全選的checkbox不統計	
        //modify by 小魚(2007/08/07)　	
        if(document.forms[0].elements[i].checked == true && document.forms[0].elements[i].id!="grvQuery_ctl01_chkHitAll")	
        	i_Count++;
    }	
return i_Count;	
}	
//-----[將項次格式化成1.1.1.1的格式 getItemCode(TestBox對象)]	
/********************************************	
* 函數名稱：getItemCode(s_aItemCode)	
* 目的：將項次格式化成1.1.1.1的格式
* 參數說明：未格式化的項次，例如：1111或1.1.1.1	
* example: return 格式化後的項次，例如：1.1.1.1;	
*********************************************/	
function getItemCode(s_aItemCode)	
{   	
      var obj=event.srcElement;	
    if(obj.value.search(/[\u4E00-\u9FA5]/g) != -1)	
    {	
        obj.value=obj.value.replace(/[\u4E00-\u9FA5]/g,'');   	
        obj.value=obj.value.replace(/[\uFE30-\uFFA0]/g,'');	
    }	
	
    if(obj.onkeydown==null)	
    {	
        obj.onkeydown=LeachInputChinese;	
    }  	
    	
 	var s_ItemCode=s_aItemCode.value; 
	var s_newItemCode="";
	s_ItemCode=trimSpace(s_ItemCode);
	if(s_ItemCode!=""||s_ItemCode.length > 1)
	if(s_ItemCode.indexOf('.') == -1)
	{
        //如果沒有點，則要補點             	
        for ( i = 0; i < s_ItemCode.length; i++)	
        {	
            s_newItemCode += s_ItemCode.substring(i,i+1);	
            s_newItemCode += ".";	
        }	
        //去掉最多一個點	
        s_newItemCode = s_newItemCode.substring(0, s_newItemCode.length - 1);	
        	
        s_aItemCode.value=s_newItemCode;          	
	}
}	
//替換字符串

function ReplaceAll(strOrg,strFind,strReplace)
{
    var index = 0;
    while(strOrg.indexOf(strFind,index) != -1)
    {
        strOrg = strOrg.replace(strFind,strReplace);
        index = strOrg.indexOf(strFind,index);
    }
    return strOrg;
}
//限定輸入數字
function numberKey(obj)
{
   
    if(event.keyCode != 46)
    {
        if((event.keyCode <48) || (event.keyCode >57))
        {
            event.returnValue =0;
        }
    }
    else
    {
        if(obj.value.indexOf('.') >=0)
        {
            var ints,inte;
            var caretPos = window.document.selection.createRange().duplicate();
            caretPos.collapse(true);
            caretPos.moveStart("textedit",-1);
            ints = caretPos.text.length;

            var caretPos2 = window.document.selection.createRange().duplicate();
            caretPos2.collapse(false);
            caretPos2.moveStart("textedit",-1);
            inte = caretPos2.text.length;
            if(obj.value.substring(ints,inte).indexOf('.')<0)
            {  
                event.returnValue =0;
            }          
        }
        else if(obj.value.trim()=="")
        {
           event.returnValue =0;
        }
    }
}

//----判斷Start日期是否大於End日期,參數必須是合法日期	
 function DateFormat(obj1)	
{	
  var obj=document.getElementById(obj1);	
    if("468".indexOf(obj.value.length)>=0)	
    {	
    	
        if(obj.value.length ==6)	
	        obj.value = "20" + obj.value;
	    if(obj.value.length ==4)
	        obj.value = new Date().getYear() + obj.value;
    	
	    var val = ReplaceAll(obj.value,'/','');
        	
        val = val.substr(0,4) + "/" + val.substr(4,2) + "/" +val.substr(6,2);	
        obj.value =val;	
    }
}
function checkDateSE(objDateS,objDateE,ISstart) 
 {
     ClearError();
     DateFormat(objDateS);
     DateFormat(objDateE);
     var objStartDate=document.getElementById(objDateS);
     var objEndDate=document.getElementById(objDateE);
     var StartDate=objStartDate.value.trim();
     var EndDate=objEndDate.value.trim();
     if(StartDate!=""&&IsDate(StartDate)==false)
     {
         AddMsg(getMsg('J00022'));//日期輸入錯誤
         objStartDate.select();
        // objStartDate.focus();
         return false;
         
     }
     if(EndDate!=""&&IsDate(EndDate)==false)
     {
      AddMsg(getMsg('J00022'));////日期輸入錯誤
      objEndDate.select();
     // objEndDate.focus();
      return false;
     }
     if(StartDate!=""&&EndDate!="")
     {
        if(IsDate(StartDate)&&IsDate(EndDate))  
        {
             var r = StartDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
             var dStart= new Date(r[1], r[3]-1, r[4]);    
             var r = EndDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);    
             var dEnd= new Date(r[1], r[3]-1, r[4]);  
             if(dStart>dEnd)
             {
              if(ISstart==true)
              {
                 AddMsg(getMsg('J00001'));//開始日期不能大於結束日期
 
              }
              else
              {
                 AddMsg(getMsg('J00020'));//結束日不能小於開始日
              }
              return false;
             } 
             else
             {
              return true;
             }
          //   return(dStart>dEnd); 
         }
      }
  }

//---------------------------------------------------------------------判斷Start日期是否大於End日期,參數必須是合法日期

//Last Modifiy  ：2006/02/05去點金額中的逗號
//eg:var str=document.all.txtName.value.trim();
String.prototype.trimAmt = function()
{
    return this.replace(/(,)/ig,'');  
}

//---------------------------------------------------------------------[輸入控制,僅限輸入 0~9 十個字元和字母]	
	
//eg:onkeypress="return inputCharNubmer()" 		
function  inputCharNubmer() 		
{		
		var key=window.event.keyCode;
		if((key>=65 && key<=90)||(key>=97 && key<=122)||(key>=48 && key<=57)||(key==45)||(key==95))
		{
		 return true;
		}
		else
		{
		 window.event.keyCode=0;
		 return true;
		}
}		

//---------------------------------------------------------------------[輸入控制,僅限輸入字母]		
//eg:onkeypress="return inputCharString()" 		
function  inputCharString() 		
{		
		var key=window.event.keyCode;
		if((key>=65 && key<=90)||(key>=97 && key<=122))
		{
		 return true;
		}
		else
		{
		 window.event.keyCode=0;
		 return true;
		}
}		

//Grid全選功能		
function SelectAllGrid(objSelectAll,objGrid,objIndexCell)		
{		
    var objGrid = document.getElementById(objGrid);		
    if(objGrid!=null)		
    {		
        for(var i=1;i<objGrid.rows.length;i++)		
        {		
            if(objGrid.rows[i].cells[objIndexCell]!=null)		
            {		
                if(objGrid.rows[i].cells[objIndexCell].childNodes[0]!=null)		
                {	
                   objGrid.rows[i].cells[objIndexCell].childNodes[0].checked = objSelectAll.checked;	
                }	
            }	
        }	
    }	
}	

//判斷是否至少選中一項	
function IsCheckBoxs(objGrids,objIndexCell)	
{	
    var objGrid= document.getElementById(objGrids);	
	if(objGrid==null || objGrid.rows.length<2) 
	{
	    ShowError('00041','');
	    return false;
	}		
	for(var intRow=1;intRow<objGrid.rows.length;intRow++)		
	{		
		var oApprovalButton=objGrid.rows[intRow].cells[objIndexCell].childNodes[0];	
			
		if(oApprovalButton.checked)	
		{    	
			return true;
		}	
	}		
	ShowError('00004','');		
	return false;		
}			

//限制只能輸入字母			
function  _onluyNum_Wo(obj)			
{   
    obj.value = obj.value.replace(/[^a-z A-Z]/g,'');
}  

//限制只能輸入數字和小數點
function _onlyNum(obj) 
{ 
  obj.value = obj.value.replace(/[^0-9 .]/g,'');
}
//限制只能輸入數字
function onlyInt(obj) 
{ 
  obj.value = obj.value.replace(/[^0-9]/g,'');
}

/*功能:Textarea長度限定
* 傳入參數:textarea:this,limit:長度
* 備註:
* 範例:
*/
function textLimiter(textarea,limit) 
{   
    if(textarea.value.length>limit) 
    {   
        textarea.value=textarea.value.substring(0,limit);   
    }  
}

/*
功能：驗證傳入值是否為正浮點數
*/
function ValidatePositiveFloat(strValue)
{
     var ReturnValue = true;
     
     //检查值是否有"."字符
     if(strValue.indexOf(".")>1){
         var patrn=/^[1-9]+[0-9]*(\.[0-9]+)?$/;
         if (!patrn.exec(strValue))
         {
            ReturnValue = false;
         }   
     }
     else if(strValue.indexOf(".")==1)
     {
         var patrn=/^[0-9]{1}(\.[0-9]+)?$/;
         if (!patrn.exec(strValue))
         {
            ReturnValue = false;
         }          
     }
     else{
         if(strValue.length>1)
         {
              var patrn=/^[1-9]+[0-9]*$/;
              if (!patrn.exec(strValue))
              {
                  ReturnValue = false;
              }
         }
         else{
              var patrn=/^[0-9]{1}$/;
              if (!patrn.exec(strValue))
              {
                 ReturnValue = false;
              }
         }
     }
          
     return ReturnValue;
     
  } 
  

/*
功能：驗證某個值是否為浮點數:0是指正負浮點數，1是指正浮點數,2是指負浮點數
*/  
function CheckIsFloat(strValue,intBit){
     //標識值

     var ReturnValue = true;
          
     switch(intBit)
     {
          case 0:
               var intPos = strValue.indexOf("-");
               if(intPos==0){
                   ReturnValue = ValidatePositiveFloat(strValue.substring(1,strValue.length)); 
               }
               else{
                   ReturnValue = ValidatePositiveFloat(strValue); 
               }
               
               break;
               
          case 1: 
               ReturnValue = ValidatePositiveFloat(strValue);             
               break;
               
          case 2:
               var intPos = strValue.indexOf("-");
               if(intPos==0){
                   ReturnValue = ValidatePositiveFloat(strValue.substring(1,strValue.length)); 
               } 
               else{
                   ReturnValue=false;
               }                                  
               break;   
     }
     
     //返回值

     return ReturnValue;

}

/*****************************************************		
* 函數名稱: Fun_idCheck()		
* 目    的: 身分証檢查副程式		
* 參    數: obj:物件本身		
*****************************************************/		
function Fun_idCheck(objid)		
{   		
   var id=objid.value.trim();		
   var fResult=true;		
   var value = 0;		
   var sId=id;		
   if(sId == '')		
   {  		
		return true;
   }		
   if(sId.length<10) fResult = false;
   else
   {
     if((sId.charAt(0)=='A') || (sId.charAt(0)=='a')) value=10;
     else if((sId.charAt(0)=='B') || (sId.charAt(0)=='b')) value=11;
     else if((sId.charAt(0)=='C') || (sId.charAt(0)=='c')) value=12;
     else if((sId.charAt(0)=='D') || (sId.charAt(0)=='d')) value=13;
     else if((sId.charAt(0)=='E') || (sId.charAt(0)=='e')) value=14;
     else if((sId.charAt(0)=='F') || (sId.charAt(0)=='f')) value=15;
     else if((sId.charAt(0)=='G') || (sId.charAt(0)=='g')) value=16;
     else if((sId.charAt(0)=='H') || (sId.charAt(0)=='h')) value=17;
     else if((sId.charAt(0)=='J') || (sId.charAt(0)=='j')) value=18;
     else if((sId.charAt(0)=='K') || (sId.charAt(0)=='k')) value=19;
     else if((sId.charAt(0)=='L') || (sId.charAt(0)=='l')) value=20;
     else if((sId.charAt(0)=='M') || (sId.charAt(0)=='m')) value=21;
     else if((sId.charAt(0)=='N') || (sId.charAt(0)=='n')) value=22;
     else if((sId.charAt(0)=='P') || (sId.charAt(0)=='p')) value=23;
     else if((sId.charAt(0)=='Q') || (sId.charAt(0)=='q')) value=24;
     else if((sId.charAt(0)=='R') || (sId.charAt(0)=='r')) value=25;
     else if((sId.charAt(0)=='S') || (sId.charAt(0)=='s')) value=26;
     else if((sId.charAt(0)=='T') || (sId.charAt(0)=='t')) value=27;
     else if((sId.charAt(0)=='U') || (sId.charAt(0)=='u')) value=28;
     else if((sId.charAt(0)=='V') || (sId.charAt(0)=='v')) value=29;
     else if((sId.charAt(0)=='X') || (sId.charAt(0)=='x')) value=30;
     else if((sId.charAt(0)=='Y') || (sId.charAt(0)=='y')) value=31;
     else if((sId.charAt(0)=='W') || (sId.charAt(0)=='w')) value=32;
     else if((sId.charAt(0)=='Z') || (sId.charAt(0)=='z')) value=33;
     else if((sId.charAt(0)=='I') || (sId.charAt(0)=='i')) value=34;
     else if((sId.charAt(0)=='O') || (sId.charAt(0)=='o')) value=35;
     else fResult = false ;
   }
   if(fResult==true)
   {
     value = Math.floor(value/10) + (value%10)*9 +
             parseInt(sId.charAt(1))*8+
             parseInt(sId.charAt(2))*7+
             parseInt(sId.charAt(3))*6+
             parseInt(sId.charAt(4))*5+
             parseInt(sId.charAt(5))*4+
             parseInt(sId.charAt(6))*3+
             parseInt(sId.charAt(7))*2+
             parseInt(sId.charAt(8))+
             parseInt(sId.charAt(9)) ;
     //alert('Value='+value) ;    
     value = value % 10 ;
     if(value!=0) fResult = false ;
    }
    return fResult ;
}

//限制只能輸入數字 Add by Kangco
function _onlyNum2(obj) 
{ 
  obj.value = obj.value.replace(/[^0-9]/g,'');
  if(obj.value.trim()!="")
  {
   obj.value=parseInt(obj.value,10);
  }
} 

/*
* 功能:限制只能輸入正浮點
* 傳入參數:obj      默認為 this
           leftNo   小數點左邊數字允許的最大長度（必須為正整數或者"0"，當為"0"時，默認為無最大長度限定）
           rightNo  小數點右邊數字允許的最大長度（必須為正整數或者"0"，當為"0"時，默認為無最大長度限定）
* 備註:文本框必須註冊兩种JS事件'onkeyup'和'onblur'
       'onkeyup'     對應     k_OnlyNum()
       'onblur'      對應     o_OnlyNum()
       小數點右邊數字長度不足時，自動補"0"
* 範例:k_OnlyNum(this,4,3)
       o_OnlyNum(this,4,3)
*/
function k_FloatNum(obj,leftNo,rightNo) 
{ 
    obj.value = obj.value.replace(/[^0-9 .]/g,'');
    if (obj.value != "")
    {
        var arryValue = obj.value.split(".");
        if (arryValue.length == 1)//-------------------------------無小數點
        {
            obj.value = arryValue[0] * 1;
            if (leftNo != 0 && obj.value.length > leftNo) { obj.value = obj.value.substring(0,leftNo); }
        }
        else if (arryValue.length == 2)//--------------------------一個小數點
        {
            var leftNum;    //小數點左邊數字

            var rightNum;   //小數點右邊數字

            leftNum = arryValue[0];
            rightNum = arryValue[1];
            obj.value = leftNum * 1 + ".";
            if (rightNo != 0 && rightNum.length > rightNo) { rightNum = rightNum.substring(0,rightNo); }
            obj.value = obj.value + rightNum;
        }
        else if(arryValue.length > 2)//----------------------------一個以上小數點
        {
            obj.value = arryValue[0] * 1 + "." + arryValue[1];
        }
    }
} 

function o_FloatNum(obj,leftNo,rightNo) 
{ 
    obj.value = obj.value.replace(/[^0-9 .]/g,'');
    
    if (obj.value != "")
    {
        var arryValue = obj.value.split(".");
        if (arryValue.length == 1)//-------------------------------無小數點
        {
            obj.value = arryValue[0] * 1;
            if (leftNo != 0 && obj.value.length > leftNo) { obj.value = ""; }
            else if (leftNo != 0) { obj.value = obj.value + "."; for (i=0;i<rightNo;i++) { obj.value = obj.value + "0"; } }
        }
        else if (arryValue.length == 2)//--------------------------一個小數點
        {
            var leftNo;    //小數點左邊數字

            var rightNo;   //小數點右邊數字

            leftNum = arryValue[0];
            rightNum = arryValue[1];
            if (leftNo != 0 && leftNum.length > leftNo) { obj.value = ""; }
            else if (rightNo != 0 && rightNum.length > rightNo) { obj.value = ""; }
            else if (rightNo == 0) { obj.value = leftNum * 1 + (rightNum == "" ? "" : "." + rightNum); }
            else { obj.value = leftNum * 1 + "." + rightNum; for (i=0;i<rightNo-rightNum.length;i++) { obj.value = obj.value + "0"; } }
        }
        else if(arryValue.length > 2)//----------------------------一個以上小數點
        {
            obj.value = "";	
        }	
    }	
}	

/******************************	
*作用：提示後頁面轉向	
*名稱：FN_Redirect（）	
        *參數：obj：要判斷的對象	
*用法示例：FN_Redirect('SC0100000','J00080')	
*/	
function FN_Redirect(url,s_amsg)	
{ 	
	 var msg=getMsg(s_amsg);
     if  (confirm(msg))	
     {	
	    self.location.href=url;
	 }
}	

//-----------------------------------验证结束-----------------------------------------------