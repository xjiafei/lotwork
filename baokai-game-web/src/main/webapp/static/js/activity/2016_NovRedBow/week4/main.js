$(function () {
	
	redbow4query();
});

Date.prototype.yyyymmddhhmmss = function() {
   var yyyy = this.getFullYear();
   var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
   var dd  = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
   var hh = this.getHours() < 10 ? "0" + this.getHours() : this.getHours();
   var min = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
   var ss = this.getSeconds() < 10 ? "0" + this.getSeconds() : this.getSeconds();
   return "".concat(yyyy).concat("/").concat(mm).concat("/").concat(dd).concat(" ").concat(hh).concat(":").concat(min).concat(":").concat(ss);
  };

function redbow4query(){
	$.ajax({
		type: 'get',
		dataType: 'json',
		url: '/activity/redbow4query',
		beforeSend: function (e) {

		},
		complete: function (e) {
			$('body').css('visibility', 'visible');
		}
	}).success(function (data) {
		if (!data['_signIn']) {       //没有报名
			if(data['_status'] == -1){
				$('#signIn ._range').text("無此用戶id");
			} else if (data['_status'] == -2){
				listBetOrder(data);
				$('#signIn ._range').text("尚未报名");
			} else if (data['_status'] == -3){
				$('#signIn ._range').text("非活動時間");
			} else if (data['_status'] == -4){
				$('#signIn ._range').text("非活動時間");
				$('.btn').css('background', "url("+$("#imgPath").val()+"/images/activity/2016_NovRedBow/activity4/images/btn-done.png)");
			} else {
				$('#signIn ._range').text("请求失败");
			}
			$('#signIn ._name').text(data._name);
			$('#signIn ._money').text("-");
		} else {
			listBetOrder(data);
		}
		var date = new Date();
		$('.bot span').html(date.yyyymmddhhmmss());
	}).fail(function (e) {
		alert('请求失败');
	});
}

function listBetOrder(data){
	if(data['_status'] == 0){
		$('.btn').css('background', "url("+$("#imgPath").val()+"/images/activity/2016_NovRedBow/activity4/images/btn-done.png)");
	}
	if(data._range == 0){
		$('#signIn ._range').text("-");
	} else {
		$('#signIn ._range').text(data._range);
	}
	$('#signIn ._name').text(data._name);
	$('#signIn ._money').text(data._money);
	$('#list').html('');
	/*
	var list = data._list.sort(function (a, b) {
		return b._umoney - a._umoney;
	});
	*/
	var list = data._list;
	var str = '';
	for (var i = 0; i < list.length; i++) {
		var _str = '<tr>' +
			'<td>' + (i + 1) + '</td>';
		if (i < 10) {
			_str += '<td class="yellow">' + list[i]._uname + '</td>' +
				'<td class="yellow">' + moneyForamt(list[i]._umoney,3) + '</td>';
		} else {
			_str += '<td class="white">' + list[i]._uname + '</td>' +
				'<td class="white">' + moneyForamt(list[i]._umoney,3) + '</td>';
		}
		_str += '</tr>';
		str += _str;
	}
	$('#list').html(str);
}


function moneyForamt(str, cutsize) {
	if (typeof str == "number") {
		str += "";
	}
	var str_array = str.split(".")[0].split("");
	var size = str_array.length;
	if (size <= 3) {
		return str;
	}
	str_array = str_array.reverse();
	var temp_array = [];
	for (var i = 0; i < size; i++) {
		var s = str_array[i];
		temp_array.push(s);
		if ((i + 1) % cutsize == 0) {
			temp_array.push(",");
		}
	}
	var res = temp_array.reverse().join("");
	res = res.replace(/^,/, "");
	if(str.split('.')[1]){
		return res+'.'+str.split('.')[1];
	}else{
		return res;
	}
}