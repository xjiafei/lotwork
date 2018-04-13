$(document).ready(function(){
	
	
	var data = document.getElementById('gold').value;      //number类型或string类型
	

    $('.ten').css('backgroundImage','url(../../static/images/august/aoyun/'+Math.floor(parseInt(data)/10)+'.png');
    $('.bit').css('backgroundImage','url(../../static/images/august/aoyun/'+parseInt(data)%10+'.png');
    $('.rule').bind('click',function(evt){
        $('.preload').css('visibility','visible');
        $('#main').hide();
        $('.curtion').show();
    });
    $('.back').bind('click',function(evt){
        $('.preload').css('visibility','hidden');
        $('.curtion').hide();
        $('#main').show();
    });
})
