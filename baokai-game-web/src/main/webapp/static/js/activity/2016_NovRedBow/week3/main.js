var mis_step;
var load_flag=false;
var isVip = false;

$(function(){
  
  redbow3chekbets();

  function redbow3chekbets () {
	  //讀取數值
	  $.ajax({
		url: '/activity/redbow3chekbets',
		dataType: 'json',
		cache: false,
		contentType: "application/json; charset=utf-8",
		success:function(data) {
		  //alert("data : " + data);	
		  var puzzle_data = data;
		  isVip = data['userLv'] > 0 ? true : false;
		  if(data['status'] == 0){
			init_data(puzzle_data);
			//load_flag=true;
		  } else if (data['status'] == -1) {
			  alert('無此用戶id');
		  } else if (data['status'] == -2) {
			  alert('尚未報名11月第三周活動');
		  } else if (data['status'] == -3) {
			  alert('目前非11月第三周活動時間');
		  } else {
			  alert('讀取資料發生錯誤');
		  }
		},error: function(msg) {
		  alert(msg);
		}
	  });
  }

  function init_data(data)
  {
    mis_step=data['mission'];
    //alert(mis_step);
    show_puzzle(mis_step);

	showBetMessage(mis_step, true);
    
  }

  function show_puzzle(step)
  {
    /*設定順序*/
    var ini_ary=new Array('p1','p5','p2','p3','p4');
    
    /* 0 離開*/
    if (step==0) return 
    
    
    for(var i=1;i<=step;i++)
    {
      var p_id=ini_ary[i-1];
      $(".pf."+p_id+",.pb."+p_id).show();
      $(".dot."+p_id).hide();
    }
    
    if(step==5)
    {
      $(".pf,.pb").hide();
      $(".phone_body").css("background","url("+$("#imgPath").val()+"/images/activity/2016_NovRedBow/activity3/images/full.png)no-repeat left top");
    }
  }
  
  function showBetMessage(mis_step, load_flag) {
	 if (mis_step==0) return ;
	 var cookie_step=getCookie('step')
     console.log(cookie_step);
	 if(load_flag) {
        /*提醒*/
        var mis_money=new Array('','28','58','288','888','5888');
		var vip_mis_money=new Array('','38','68','328','988','6188');
        var mis_step_string=new Array('','一','二','三','四','五');

		if(mis_step!=cookie_step)
        {
			if(mis_step>=5)
			{
				if(isVip){
					alert('恭喜您完成所有任务，获得奖金'+vip_mis_money[mis_step]+'元，累积奖金'+countVip(mis_step, vip_mis_money)+'元！明日平台公告将公布前三位获得iPhone7用户名单！');
				} else {
					alert('恭喜您完成所有任务，获得奖金'+mis_money[mis_step]+'元，累积奖金'+count(mis_step, mis_money)+'元！明日平台公告将公布前三位获得iPhone7用户名单！');
				}
			  
			}else
			{
				if(isVip){
					alert('恭喜您完成任务'+mis_step_string[mis_step]+'，可获得'+vip_mis_money[mis_step]+'元奖金。累积奖金'+countVip(mis_step, vip_mis_money)+'元！');
				} else {
					alert('恭喜您完成任务'+mis_step_string[mis_step]+'，可获得'+mis_money[mis_step]+'元奖金。累积奖金'+count(mis_step, mis_money)+'元！');
				}
			  
			}
			setCookie('step',mis_step,'365');
		}
        //window.clearInterval(chk_alert)
     }
  }

  function count(step, mis_money)
  {
	var count_n=0;
	for(var i=1;i<=step;i++)
	{
	  count_n=count_n+parseInt(mis_money[i])
	}
	
	return count_n
  }
  
  function countVip(step, vip_mis_money)
  {
	var count_n=0;
	for(var i=1;i<=step;i++)
	{
	  count_n=count_n+parseInt(vip_mis_money[i])
	}
	
	return count_n
  }
  
    //設定 cookie
    function setCookie(cookieName, cookieValue, exdays) {
      if (document.cookie.indexOf(cookieName) >= 0) {
        var expD = new Date();
        expD.setTime(expD.getTime() + (-1*24*60*60*1000));
        var uexpires = "expires="+expD.toUTCString();
        document.cookie = cookieName + "=" + cookieValue + "; " + uexpires; 
      } 
      var d = new Date();
      d.setTime(d.getTime() + (exdays*24*60*60*1000));
      var expires = "expires="+d.toUTCString();
      document.cookie = cookieName + "=" + cookieValue + "; " + expires;  
    }
  
    //讀取 cookie
    function getCookie(cookieName) {
      var name = cookieName + "=";
      var ca = document.cookie.split(';');
      for(var i=0; i<ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0)==' ') c = c.substring(1);
          if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
      }
      return "";
    }
});

/*取得資料 且頁面載入成功*/
/*
$(window).load(function() {

    if (mis_step==0) return 
    
    
    var chk_alert=setInterval(function(){
      
      if(load_flag)
      {
        //提醒
        var mis_money=new Array('','28','58','288','888','5888');
		var vip_mis_money=new Array('','38','68','328','988','6188');
        var mis_step_string=new Array('','一','二','三','四','五');


         if(mis_step>=5)
        {
			if(isVip){
				alert('恭喜您完成所有任务，获得奖金'+vip_mis_money[mis_step]+'元，累积奖金'+countVip(mis_step)+'元！明日平台公告将公布前三位获得iPhone7用户名单！');
			} else {
				alert('恭喜您完成所有任务，获得奖金'+mis_money[mis_step]+'元，累积奖金'+count(mis_step)+'元！明日平台公告将公布前三位获得iPhone7用户名单！');
			}
          
        }else
        {
			if(isVip){
				alert('恭喜您完成任务'+mis_step_string[mis_step]+'，可获得'+vip_mis_money[mis_step]+'元奖金。累积奖金'+countVip(mis_step)+'元！');
			} else {
				alert('恭喜您完成任务'+mis_step_string[mis_step]+'，可获得'+mis_money[mis_step]+'元奖金。累积奖金'+count(mis_step)+'元！');
			}
          
        }
        window.clearInterval(chk_alert)
      }
      
      function count(step)
      {
        var count_n=0;
        for(var i=1;i<=step;i++)
        {
          count_n=count_n+parseInt(mis_money[i])
        }
        
        return count_n
      }
	  
	  function countVip(step)
      {
        var count_n=0;
        for(var i=1;i<=step;i++)
        {
          count_n=count_n+parseInt(vip_mis_money[i])
        }
        
        return count_n
      }
      
    },1000)
    
      

})
*/