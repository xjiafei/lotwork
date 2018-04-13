$(document).ready(function(){
	
	
	var data = document.getElementById('gold').value;      //number类型或string类型
	

    $('.ten').css('backgroundImage','url('+Math.floor(parseInt(data)/10)+'.png');
    $('.bit').css('backgroundImage','url('+parseInt(data)%10+'.png');
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
