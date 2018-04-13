(function () {

    var minWindow, mask, initFn;
    minWindow = new phoenix.MiniWindow();
    mask = phoenix.Mask.getInstance();
    var msg = phoenix.Message.getInstance();
    minWindow.addEvent('beforeShow', function () {
        mask.show();
    });
    minWindow.addEvent('afterHide', function () {
        mask.hide();
    });

    var editConfirm = new phoenix.EditConfirm();
    //菜单样式加载
    $('.menu-list li').removeAttr("class");
    $('.menu-list li:eq(3)').attr("class", "current");
    $('.col-side .nav dd:eq(0)').attr("class", "current");
	$('#issueRuleMenu').attr("class","current");
	
    var lotteryId = $('#lotteryId').val();
    var ruleId = $('#ruleId').val();
    //----------------------------------时间加载开始-----------------------------------------------------------
	
	
    $("[name='times']").click(function () {
        var Dt = new phoenix.DatePicker({ input:  this, isShowTime: true });
        Dt.show();
		Dt.addEvent('afterRender', function(e){			
			//e.data.defConfig.date
			//var time=$('#ruleStartTime').val();
			var jsonStr = '',
				data ='';
				
				jsonStr += '{"lotteryId":';
				jsonStr += $.trim($('#lotteryId').val());
				jsonStr += ',"ruleStartTime":';
				jsonStr += convertDate2UnixTimestamp(formatDate(e.data._date));
				jsonStr += '}';			
				data = "reqData=" + jsonStr;		
				$.ajax({
					url : '/gameoa/checkUpapteCommonOrSpecialGameIssueRuleTime',				
					type : "post",					
					data :data,
					success:function(data){
						if(Number(data['status']) == 0){//在追号期内
							if($('#J-date-start').val()< data['maxIssuesDay']){
								$('#J_Effective_Button').show();
								$('#J_Submit_Button').hide();
							}
							
						}else{//不在追号期内
							$('#J_Effective_Button').hide();
							$('#J_Submit_Button').show();
						}
						 
					}
				});
			
			//if(convertDate2UnixTimestamp(formatDate(e.data._date))< time)
			
		});
    });
	
    //------------------------时间加载结束----------------------------------------------


    //-----------------------------回显--------------------------

    var ruleType = $('#ruleType').val() == 'undefined' ? 1 : Number($('#ruleType').val());
    //Tab组件使用规则 请按示例做，便于整个项目维护 (index索引以0开始)
    new phoenix.Tab({ triggers: '.ui-tab-title2 li', panels: '.ui-tab-content', eventType: 'click', currPanelClass: 'ui-tab-content-current', index: ruleType - 1 });
	
	switch(ruleType){
		case 1:
			$('.ui-tab > ul').find('#li2').remove();
			$('.ui-tab > ul').find('#li3').remove();
			$('#tlRuleUl').remove();
			$('#xsRuleUl').remove();
			break;
		case 2:
			$('.ui-tab > ul').find('#li1').remove();
			$('.ui-tab > ul').find('#li3').remove();
			$('#DivRules').remove();
			$('#xsRuleUl').remove();
			break;
		case 3:
			$('.ui-tab > ul').find('#li1').remove();
			$('.ui-tab > ul').find('#li2').remove();
			$('#DivRules').remove();
			$('#tlRuleUl').remove();
			break;
		default:
			break;		
	
	}
	
    if ($('#ruleStartTime').val() != '') {
        $('#J-date-exception-start').val(formatDate(new Date(Number($('#ruleStartTime').val()))));
        $('#J-date-start').val(formatDate(new Date(Number($('#ruleStartTime').val()))));

        $('#maintenanceTimeStarts').val(formatDate(new Date(Number($('#ruleStartTime').val()))));
    }

    if ($('#ruleEndTime').val() != '') {
        $('#J-date-exception-end').val(formatDate(new Date(Number($('#ruleEndTime').val()))));
        $('#maintenanceTimeEnd').val(formatDate(new Date(Number($('#ruleEndTime').val()))));
    }

    if ($('#issueRulesName').val() != '') {

        $('#X_IssueName').attr('value', $('#issueRulesName').val());
    }

    if (ruleType == 1) {

        if ($('#openAwardPeriod').val() != '') {
            var period = $('#openAwardPeriod').val();
            var periods = period.split(",");


            $("input[name='checkbox1']").each(function (i, v) {

                for (var k = 0; k < periods.length; k++) {

                    if ($(this).val() == periods[k]) {
                        $("input[name='checkbox1'][value=" + periods[k] + "]").attr('checked', 'checked');
                    }
                }
            });

        }
    } else if (ruleType == 2) {
        if ($('#openAwardPeriod').val() != '') {
            var period = $('#openAwardPeriod').val();
            var periods = period.split(",");


            $("input[name='checkbox2']").each(function (i, v) {

                for (var k = 0; k < periods.length; k++) {

                    if ($(this).val() == periods[k]) {
                        $("input[name='checkbox2'][value=" + periods[k] + "]").attr('checked', 'checked');
                    }
                }
            });

        }
    }
    else if (ruleType == 3) {
        if ($('#openAwardPeriod').val() != '') {
            var period = $('#openAwardPeriod').val();
            var periods = period.split(",");


            $("input[name='checkbox3']").each(function (i, v) {

                for (var k = 0; k < periods.length; k++) {

                    if ($(this).val() == periods[k]) {
                        $("input[name='checkbox3'][value=" + periods[k] + "]").attr('checked', 'checked');
                    }
                }
            });

        }
    }

    if ($('#salesIssueStrucs').val() != '' && ruleType == 1) {

        var strucs = jQuery.parseJSON($('#salesIssueStrucs').val());

        var t = '';
        for (var i = 0; i < strucs.length; i++) {
            //var _saleStartTime = formatDate(Number(new Date(strucs[i].saleStartTime)));
            t += '<li class="break-time">';
            t += '<input type="radio" class="radio" checked="checked"  name="radios"/><ul class="ui-form">';
            t += '<input type="hidden" id="tempId" name="tempId" value="' + strucs[i].id + '"/>';
            t += '<li class="close delete-close"></li>';
            /*t += '<li><label for="" class="ui-label w-3">销售开始时间：</label><select class="ui-select w-1" id="" name="saleH">';

            var _sTime = _saleStartTime.split(" ")[1].split(":");
            for (var j = 0; j < 24; j++) {
                if (j < 10) {
                    if (_sTime[0] == j) {
                        t += '<option value="0' + j + '" selected= selected >0' + j + '</option>';
                    } else {
                        t += '<option value="0' + j + '" >0' + j + '</option>';
                    }
                } else {
                    if (_sTime[0] == j) {
                        t += '<option value="' + j + '"  selected=selected>' + j + '</option>';
                    } else {
                        t += '<option value="' + j + '">' + j + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="saleM">';
            for (var v = 0; v < 60; v++) {
                if (v < 10) {
                    if (_sTime[1] == v) {
                        t += '<option value="0' + v + '" selected=selected >0' + v + '</option>';
                    } else {
                        t += '<option value="0' + v + '">0' + v + '</option>';
                    }
                } else {
                    if (_sTime[1] == v) {
                        t += '<option value="' + v + '" selected=selected>' + v + '</option>';

                    } else {

                        t += '<option value="' + v + '">' + v + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="saleS">';
            for (var k = 0; k < 60; k++) {
                if (k < 10) {
                    if (_sTime[2] == k) {
                        t += '<option value="0' + k + '" selected=selected>0' + k + '</option>';
                    } else {

                        t += '<option value="0' + k + '">0' + k + '</option>';
                    }
                } else {

                    if (_sTime[2] == k) {
                        t += '<option value="' + k + '" selected=selecte>' + k + '</option>';
                    } else {

                        t += '<option value="' + k + '">' + k + '</option>';
                    }
                }
            }
            t += '</select></li>';*/
            if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
			t += '<li><label for="" class="ui-label w-3">销售周期（秒）：</label>	<input type="text" value="' + strucs[i].salePeriodTime + '" class="input sale-time w-1" name="saleWeek" /></li>';
            }
			if(i==0)
			{
            t += '<li><label for="" class="ui-label w-3">等待开奖时间（秒）：</label><input type="text" value="' + strucs[i].scheduleStopTime + '" class="input sale-wait-time w-1" name="saleWait" />&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="btn btn-important" id="J_Effective_Button" style="display:none;">立即生效<b class="btn-inner"></b></a></li>';
			}else
			{
				t += '<li><label for="" class="ui-label w-3">等待开奖时间（秒）：</label><input type="text" value="' + strucs[i].scheduleStopTime + '" class="input sale-wait-time w-1" name="saleWait" /></li>';
			}
            t += '<li><label for="" class="ui-label w-3">官方第一期开奖时间：</label><select class="ui-select w-1" id="" name="firstH">';

            //----官方第一期开奖时间
            var _firstAwardTime = formatDate(Number(new Date(strucs[i].firstAwardTime)));
            var _aTime = _firstAwardTime.split(" ")[1].split(":");
            for (var j2 = 0; j2 < 24; j2++) {
                if (j2 < 10) {
                    if (_aTime[0] == j2) {
                        t += '<option value="0' + j2 + '" selected=selected>0' + j2 + '</option>';
                    } else {

                        t += '<option value="0' + j2 + '">0' + j2 + '</option>';
                    }
                } else {
                    if (_aTime[0] == j2) {
                        t += '<option value="' + j2 + '" selected=selected >' + j2 + '</option>';
                    } else {
                        t += '<option value="' + j2 + '">' + j2 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="firstM">';
            for (var v2 = 0; v2 < 60; v2++) {
                if (v2 < 10) {
                    if (_aTime[1] == v2) {
                        t += '<option value="0' + v2 + '" selected=selected>0' + v2 + '</option>';
                    } else {
                        t += '<option value="0' + v2 + '">0' + v2 + '</option>';
                    }
                } else {

                    if (_aTime[1] == v2) {

                        t += '<option value="' + v2 + '" selected=selected>' + v2 + '</option>';
                    } else {
                        t += '<option value="' + v2 + '">' + v2 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="firstS">';

            for (var k2 = 0; k2 < 60; k2++) {
                if (k2 < 10) {
                    if (_aTime[2] == k2) {
                        t += '<option value="0' + k2 + '" selected=selected >0' + k2 + '</option>';
                    } else {

                        t += '<option value="0' + k2 + '">0' + k2 + '</option>';
                    }
                } else {
                    if (_aTime[2] == k2) {
                        t += '<option value="' + k2 + '" selected=selected>' + k2 + '</option>';

                    } else {

                        t += '<option value="' + k2 + '">' + k2 + '</option>';
                    }
                }
            }
            t += '</select></li>';
            t += '<li><label for="" class="ui-label w-3">官方最后一期开奖时间：</label><select class="ui-select w-1" id="" name="lastH">';

            var _lastAwardTime = formatDate(Number(new Date(strucs[i].lastAwardTime)));
            var _lTime = _lastAwardTime.split(" ")[1].split(":");
            for (var j3 = 0; j3 < 24; j3++) {
                if (j3 < 10) {
                    if (_lTime[0] == j3) {
                        t += '<option value="0' + j3 + '" selected=selected>0' + j3 + '</option>';
                    } else {

                        t += '<option value="0' + j3 + '">0' + j3 + '</option>';
                    }
                } else {
                    if (_lTime[0] == j3) {
                        t += '<option value="' + j3 + '" selected=selected>' + j3 + '</option>';
                    } else {

                        t += '<option value="' + j3 + '">' + j3 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="lastM">';
            for (var v3 = 0; v3 < 60; v3++) {
                if (v3 < 10) {
                    if (_lTime[1] == v3) {
                        t += '<option value="0' + v3 + '" selected=selected>0' + v3 + '</option>';
                    } else {

                        t += '<option value="0' + v3 + '">0' + v3 + '</option>';
                    }
                } else {
                    if (_lTime[1] == v3) {
                        t += '<option value="' + v3 + '" selected=selected>' + v3 + '</option>';

                    } else {

                        t += '<option value="' + v3 + '">' + v3 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="lastS">';
            for (var k3 = 0; k3 < 60; k3++) {
                if (k3 < 10) {
                    if (_lTime[2] == k3) {
                        t += '<option value="0' + k3 + '" selected=selected>0' + k3 + '</option>';
                    } else {

                        t += '<option value="0' + k3 + '">0' + k3 + '</option>';
                    }
                } else {

                    if (_lTime[2] == k3) {
                        t += '<option value="' + k3 + '" selected=selected>' + k3 + '</option>';

                    } else {

                        t += '<option value="' + k3 + '">' + k3 + '</option>';
                    }
                }
            }

            t += '</select>	</li></ul>';

            t += '<br/></br/>';
            t +='</li>';
        }
        $("#cgBreak").after(t);
    } else if ($('#salesIssueStrucs').val() != '' && ruleType == 2) {

        var strucs = jQuery.parseJSON($('#salesIssueStrucs').val());

        var t = '';
        for (var i = 0; i < strucs.length; i++) {
            //var _saleStartTime = formatDate(Number(new Date(strucs[i].saleStartTime)));
            t += '<li class="break-time">';
            t += '<input type="radio" class="radio" checked="checked"  name="radios"/><ul class="ui-form">';
            t += '<input type="hidden" id="tempId" name="tempId2" value="' + strucs[i].id + '"/>';
            t += '<li class="close delete-close"></li>';
           /* t += '<li><label for="" class="ui-label w-3">销售开始时间：</label><select class="ui-select w-1" id="" name="saleH">';

            var _sTime = _saleStartTime.split(" ")[1].split(":");
            for (var j = 0; j < 24; j++) {
                if (j < 10) {
                    if (_sTime[0] == j) {
                        t += '<option value="0' + j + '" selected= selected >0' + j + '</option>';
                    } else {
                        t += '<option value="0' + j + '" >0' + j + '</option>';
                    }
                } else {
                    if (_sTime[0] == j) {
                        t += '<option value="' + j + '"  selected=selected>' + j + '</option>';
                    } else {
                        t += '<option value="' + j + '">' + j + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="saleM">';
            for (var v = 0; v < 60; v++) {
                if (v < 10) {
                    if (_sTime[1] == v) {
                        t += '<option value="0' + v + '" selected=selected >0' + v + '</option>';
                    } else {
                        t += '<option value="0' + v + '">0' + v + '</option>';
                    }
                } else {
                    if (_sTime[1] == v) {
                        t += '<option value="' + v + '" selected=selected>' + v + '</option>';

                    } else {

                        t += '<option value="' + v + '">' + v + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="saleS">';
            for (var k = 0; k < 60; k++) {
                if (k < 10) {
                    if (_sTime[2] == k) {
                        t += '<option value="0' + k + '" selected=selected>0' + k + '</option>';
                    } else {

                        t += '<option value="0' + k + '">0' + k + '</option>';
                    }
                } else {

                    if (_sTime[2] == k) {
                        t += '<option value="' + k + '" selected=selecte>' + k + '</option>';
                    } else {

                        t += '<option value="' + k + '">' + k + '</option>';
                    }
                }
            }
            t += '</select></li>';*/
            if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
            	t += '<li><label for="" class="ui-label w-3">销售周期（秒）：</label>	<input type="text" value="' + strucs[i].salePeriodTime + '" class="input sale-time w-1" name="saleWeek" /></li>';
            }
            t += '<li><label for="" class="ui-label w-3">等待开奖时间（秒）：</label><input type="text" value="' + strucs[i].scheduleStopTime + '" class="input sale-wait-time w-1" name="saleWait" /></li>';
            t += '<li><label for="" class="ui-label w-3">官方第一期开奖时间：</label><select class="ui-select w-1" id="" name="firstH">';

            //----官方第一期开奖时间
            var _firstAwardTime = formatDate(Number(new Date(strucs[i].firstAwardTime)));
            var _aTime = _firstAwardTime.split(" ")[1].split(":");
            for (var j2 = 0; j2 < 24; j2++) {
                if (j2 < 10) {
                    if (_aTime[0] == j2) {
                        t += '<option value="0' + j2 + '" selected=selected>0' + j2 + '</option>';
                    } else {

                        t += '<option value="0' + j2 + '">0' + j2 + '</option>';
                    }
                } else {
                    if (_aTime[0] == j2) {
                        t += '<option value="' + j2 + '" selected=selected >' + j2 + '</option>';
                    } else {
                        t += '<option value="' + j2 + '">' + j2 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="firstM">';
            for (var v2 = 0; v2 < 60; v2++) {
                if (v2 < 10) {
                    if (_aTime[1] == v2) {
                        t += '<option value="0' + v2 + '" selected=selected>0' + v2 + '</option>';
                    } else {
                        t += '<option value="0' + v2 + '">0' + v2 + '</option>';
                    }
                } else {

                    if (_aTime[1] == v2) {

                        t += '<option value="' + v2 + '" selected=selected>' + v2 + '</option>';
                    } else {
                        t += '<option value="' + v2 + '">' + v2 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="firstS">';

            for (var k2 = 0; k2 < 60; k2++) {
                if (k2 < 10) {
                    if (_aTime[2] == k2) {
                        t += '<option value="0' + k2 + '" selected=selected >0' + k2 + '</option>';
                    } else {

                        t += '<option value="0' + k2 + '">0' + k2 + '</option>';
                    }
                } else {
                    if (_aTime[2] == k2) {
                        t += '<option value="' + k2 + '" selected=selected>' + k2 + '</option>';

                    } else {

                        t += '<option value="' + k2 + '">' + k2 + '</option>';
                    }
                }
            }
            t += '</select></li>';
            t += '<li><label for="" class="ui-label w-3">官方最后一期开奖时间：</label><select class="ui-select w-1" id="" name="lastH">';
            

            var _lastAwardTime = formatDate(Number(new Date(strucs[i].lastAwardTime)));
            var _lTime = _lastAwardTime.split(" ")[1].split(":");
            for (var j3 = 0; j3 < 24; j3++) {
                if (j3 < 10) {
                    if (_lTime[0] == j3) {
                        t += '<option value="0' + j3 + '" selected=selected>0' + j3 + '</option>';
                    } else {

                        t += '<option value="0' + j3 + '">0' + j3 + '</option>';
                    }
                } else {
                    if (_lTime[0] == j3) {
                        t += '<option value="' + j3 + '" selected=selected>' + j3 + '</option>';
                    } else {

                        t += '<option value="' + j3 + '">' + j3 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="lastM">';
            for (var v3 = 0; v3 < 60; v3++) {
                if (v3 < 10) {
                    if (_lTime[1] == v3) {
                        t += '<option value="0' + v3 + '" selected=selected>0' + v3 + '</option>';
                    } else {

                        t += '<option value="0' + v3 + '">0' + v3 + '</option>';
                    }
                } else {
                    if (_lTime[1] == v3) {
                        t += '<option value="' + v3 + '" selected=selected>' + v3 + '</option>';

                    } else {

                        t += '<option value="' + v3 + '">' + v3 + '</option>';
                    }
                }
            }
            t += '</select>&nbsp;&nbsp;：';
            t += '<select class="ui-select w-1" id="" name="lastS">';
            for (var k3 = 0; k3 < 60; k3++) {
                if (k3 < 10) {
                    if (_lTime[2] == k3) {
                        t += '<option value="0' + k3 + '" selected=selected>0' + k3 + '</option>';
                    } else {

                        t += '<option value="0' + k3 + '">0' + k3 + '</option>';
                    }
                } else {

                    if (_lTime[2] == k3) {
                        t += '<option value="' + k3 + '" selected=selected>' + k3 + '</option>';

                    } else {

                        t += '<option value="' + k3 + '">' + k3 + '</option>';
                    }
                }
            }

            t += '</select>	</li></ul>';

            t += '<br/></br/>';
			t +='</li>';
        }
        $("#tlBreak").after(t);
    }
    
    var stopStartTime = $('#stopStartTime').val();
    var stopEndTime = $('#stopEndTime').val();
    var seriesIssueCodeView = $('#seriesIssueCodeView').val();
    if(seriesIssueCodeView != null && seriesIssueCodeView!='' && ruleType == 3){
    	if(seriesIssueCodeView == 1){
    		 $("#seriesIssueCode").attr("checked","checked");
    	}
    }
    if(stopStartTime != null && stopStartTime!='' && ruleType == 3){
    	var _stopStartTime = formatDate(new Date(Number($('#stopStartTime').val())));
		var _aTime = _stopStartTime.split(" ")[1].split(":");
		 for (var j2 = 0; j2 < 24; j2++) {
             if (j2 < 10) {
                 if (_aTime[0] == j2) {
                     var value = '0'+j2;
                     $('#firstStopH2').val(value);
                     
                 } 
             } else {
                 if (_aTime[0] == j2) {
                	 $('#firstStopH2').val(j2);
                 }
             }
         }
		 
		 for (var v2 = 0; v2 < 24; v2++) {
             if (v2 < 10) {
                 if (_aTime[1] == v2) {
                     var value = '0'+v2;
                     $("#firstStopM2 option[value='"+value+"']").attr("selected","selected");
                 } 
             } else {
                 if (_aTime[1] == v2) {
                	 $("#firstStopM2 option[value='"+v2+"']").attr("selected","selected");
                 }
             }
         }
		 
		 for (var k2 = 0; k2 < 24; k2++) {
             if (k2 < 10) {
                 if (_aTime[2] == k2) {
                     var value = '0'+k2;
                     $("#firstStopS2 option[value='"+value+"']").attr("selected","selected");
                 } 
             } else {
                 if (_aTime[2] == k2) {
                	 $("#firstStopS2 option[value='"+k2+"']").attr("selected","selected");
                 }
             }
         }
    }
    
  if(stopEndTime != null && stopEndTime!='' && ruleType == 3){
    	var _stopEndTime = formatDate(new Date(Number($('#stopEndTime').val())));
		var _aTime = _stopEndTime.split(" ")[1].split(":");
		 for (var j2 = 0; j2 < 24; j2++) {
             if (j2 < 10) {
                 if (_aTime[0] == j2) {
                     var value = '0'+j2;
                     $("#lastStopH2 option[value='"+value+"']").attr("selected","selected");
                 } 
             } else {
                 if (_aTime[0] == j2) {
                	 $("#lastStopH2 option[value='"+j2+"']").attr("selected","selected");
                 }
             }
         }
		 
		 for (var v2 = 0; v2 < 24; v2++) {
             if (v2 < 10) {
                 if (_aTime[1] == v2) {
                     var value = '0'+v2;
                     $("#lastStopM2 option[value='"+value+"']").attr("selected","selected");
                 } 
             } else {
                 if (_aTime[1] == v2) {
                	 $("#lastStopM2 option[value='"+v2+"']").attr("selected","selected");
                 }
             }
         }
		 
		 for (var k2 = 0; k2 < 24; k2++) {
             if (k2 < 10) {
                 if (_aTime[2] == k2) {
                     var value = '0'+k2;
                     $("#lastStopS2 option[value='"+value+"']").attr("selected","selected");
                 } 
             } else {
                 if (_aTime[2] == k2) {
                	 $("#lastStopS2 option[value='"+k2+"']").attr("selected","selected");
                 }
             }
         }
    }

    //-----------------------------回显结束----------------------------

    function operationSuccess() {
        fnDiv('DivSuccessful');
        setTimeout(function () { $("#DivSuccessful").css("display", "none"); }, 1500);
        //		location.reload();
        location.href = baseUrl + '/gameoa/gameIssueIndex?lotteryId=' + lotteryId + '&ruleId=';
    }

    function operationFailure() {
        fnDiv('DivFailed');
        $(".close,.btn").click(function (e) {
            $("#DivFailed").css("display", "none");
        });
    }

    //删除分段奖期时间示例效果()
    $('body').on('click', '.delete-close', function (e) {
        e.preventDefault();
        var dom = $(this).parents('.break-time').eq(0);
        minWindow.setTitle('温馨提示');
        minWindow.setContent($('#DeleteSegmented').val());
        minWindow.show();
        initFn(dom);

    });
    initFn = function (dom) {
        //关闭弹窗
        $('#closeDs').click(function (e) {
            minWindow.hide();
        });
        //提交删除操作，
        $('#J-submit-safePassword').click(function (e) {
            minWindow.hide();
            dom.remove();
            //$('.hd').css("display","none");
            //			$('.j-ui-miniwindow').removeClass("j-ui-miniwindow").addClass("DivSuccessful");
            //			minWindow.setContent($('#DivSuccessful').html());
            //			minWindow.show();

            //成功展示,自动隐藏
            fnDiv('DivSuccessful');
            setTimeout(function () { $("#DivSuccessful").css("display", "none"); }, 1500);

        });
    };

    //修改分段奖期时间(系统检测到您已经设置过一条常规规则了)
    // $('#J_Submit_Button').click(function(e){

    // 	e.preventDefault();		
    // 	minWindow.setTitle('温馨提示');
    // 	minWindow.setContent($('#UpdateSegmented').val());
    // 	minWindow.show();	
    // 	initFns();	
    // });
    // initFns = function(){	
    // 	//关闭弹窗
    // 	$('#closeDd').click(function(e){
    // 		minWindow.hide();
    // 	});
    // };



    //操作后提示	 
    function fnDiv(obj) {
        var Idivdok = document.getElementById(obj);
        Idivdok.style.display = "block";
        Idivdok.style.left = (document.documentElement.clientWidth - Idivdok.clientWidth) / 2 + document.documentElement.scrollLeft + "px";
        Idivdok.style.top = (document.documentElement.clientHeight - Idivdok.clientHeight) / 2 + document.documentElement.scrollTop - 40 + "px";
    }

    //数字校验，自动矫正不符合数学规范的数学(除了特例奖期规则名称，休市/维护时间名称)
    var inputs = $('.break-time').find('input:not([name="names"])'), checkFn;
    checkFn = function () {
        var me = this, v = me.value, index;
        me.value = v = v.replace(/^\.$/g, '');
        index = v.indexOf('.');
        if (index >= 0) {
            me.value = v = '0';
        }
        me.value = v = v.replace(/^00/g, '0');


    };
    inputs.keyup(checkFn);
    inputs.blur(checkFn);


    //日期控件跟随最外层容器滚动条滚动位置
    $('.col-main').scroll(function () {
        var dom = $(this), datepicker = $('.j-ui-datepicker '), oldTop = Number(datepicker.attr('oldTop'));
        if (isNaN(oldTop)) {
            datepicker.attr('oldTop', oldTop = parseInt(datepicker.css('top')));
        }
        datepicker.css('top', oldTop - dom.scrollTop());
    });

    $('#J_clean_botton,#J_clean_botton_a,#J_clean_botton_b').click(function () {
        //${currentContextPath}/gameoa/gameIssueIndex?lotteryId=${lotteryId}&ruleId=
        location.href = baseUrl + '/gameoa/gameIssueIndex?lotteryId=' + $('#lotteryId').val() + '&ruleId='
    });

    $('#AddRule, #Addrule_a').click(function () {
        var html = $('#addbreaktimeHtml').val(),
			parent = $(this).parents('ul').eq(0);

        parent.find('.ui-btn').before(html)
    });

    convert2Sec = function (h, m, s) {

        //		return h*3600+m*60+s;
        var da = new Date();
        var str = da.getFullYear() + "/" + (da.getMonth() + 1) + "/" + da.getDate() + " " + h + ":" + m + ":" + s;
        var getDd = new Date(str);
        return Math.round(getDd.getTime());
    }

    convert2Arr = function (v) {
        var _v = '';
        var j = 2;
        var k = 0;
        for (var i = 0; i < v.toString().length; i++) {
            if (i % 2 == 0) {
                _v += v.toString().substring(k, j);
                _v += ',';
                j += 2;
                k += 2;
            }
        }
        _v = _v.toString().substring(0, _v.toString().length - 1);
        return _v.split(',');

    }
	//公用方法：获取时间时，分，秒
	function get_AllTime(_typeT,obj){	
		
		var  _time = $('.break-time').eq(obj).find($("select[name='"+_typeT+"']")).find("option:selected").text();
		if(_time==''){
			_time = '00';
		}
		_time = convert2Arr(_time);
		return _time;
	}		
	
	//公用方法：获取周期，开奖时间
	function get_AllData(_typeT,obj){				
		var _Cyc = $('.break-time').eq(obj).find($("input[name='"+_typeT+"']")).val();
		return _Cyc;
	}			
	
    $('#J_Submit_Button').click(function () {
		//获取
		
        var parent = $(this).parents('ul').eq(0);
        saleWaitTime = parent.find('.sale-wait-time'),
		saleTime = parent.find('.sale-time'),
		checkboxlist=parent.find('[name="checkbox1"]'),
		isChecked= true;
        saleWaitTime.each(function (i) {
            if ($(this).val() == '') {
                msg.show({
                    mask: true,
                    content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">等待开奖时间 未填写请检查！</h4></div>',
                    closeIsShow: true,
                    closeFun: function () {
                        this.hide();
                    }
                })
            }




        })

        if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
	        saleTime.each(function (i) {
	            if ($(this).val() == '') {
	                msg.show({
	                    mask: true,
	                    content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">销售周期时间 未填写请检查！</h4></div>',
	                    closeIsShow: true,
	                    closeFun: function () {
	                        this.hide();
	                    }
	                })
	            }
	        })
        }

        //拼装数据发送
        //发送数据
        var jsonStr = "";
        var url = '';
        var data = '';

        var ruleType = $('#currentRuleTypeId').val();

        //常规规则
        var period = '';
        var u = 1;
        $("input[name=checkbox1]").each(function (i, v) {
            if ($('input[name=checkbox1][value=' + u + ']').is(':checked') == true) {
                period += $('input[name=checkbox1][value=' + u + ']').val();
                period += ',';
            }
			 u = u + 1;
        });

        jsonStr += '{"lotteryId":"';
        jsonStr += lotteryId;
        jsonStr += '","ruleId":"';
        jsonStr += ruleId
        jsonStr += '","ruleStartTime":';
        jsonStr += convertDate2UnixTimestamp($('#J-date-start').val());
        jsonStr += ',"openAwardPeriod":"';
        jsonStr += period + '"';
        //操作类型
        jsonStr += ',"ruleType":1,"operationType":2'; //1新增，2 修改		
		jsonStr += ',"salesIssueStrucs":[';		
		
		for(var i = 0 ;i < $('.break-time').size(); i++){				
			jsonStr += '{"saleStartTime":';
			jsonStr += 0;				
			jsonStr += ',"salePeriodTime":';
			if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				jsonStr += get_AllData('saleWeek',i);
			}
			else{
				jsonStr += 0;
			}
			jsonStr += ',"scheduleStopTime":';
			jsonStr += get_AllData('saleWait',i);
			jsonStr += ',"firstAwardTime":';				
			jsonStr += convert2Sec(get_AllTime('firstH',i),get_AllTime('firstM',i),get_AllTime('firstS',i));					
			jsonStr += ',"lastAwardTime":';				
			jsonStr += convert2Sec(get_AllTime('lastH',i),get_AllTime('lastM',i),get_AllTime('lastS',i));	
			jsonStr += "}";
			
			if(i != ($('.break-time').size() - 1)){
				
				jsonStr += ','
			}
		}
		jsonStr += ']}';

       
        url = baseUrl + '/gameoa/addOrUdapteCommonOrSpecialGameIssueRule';
        data = "reqData=" + jsonStr;
        
            var msgError = '';
			if($.trim($('#J-date-start').val()) == ''){
				msgError = '开始执行时间';					
			}
			checkboxlist.each(function(i) {
                if(this.checked)
				{
					isChecked=false;
				}
            });
			if(isChecked)
			{
				msgError = '开奖周期不能为空';
			}
			if(msgError != ''){
				msg.show({
					mask:true,
					content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+msgError+'</h4></div>',
					closeIsShow: true,
					closeFun : function(){
						this.hide();
					} 
				});
			}else{
				sendUrl(url, data);
			}	
    });


    $('#J_Submit_Button_a').click(function () {
        var parent = $(this).parents('ul').eq(0);
        saleWaitTime = parent.find('.sale-wait-time'),
		checkboxlist=parent.find('[name="checkbox2"]'),
		isChecked= true,
		saleTime = parent.find('.sale-time');
        saleWaitTime.each(function (i) {
            if ($(this).val() == '') {
                msg.show({
                    mask: true,
                    content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">等待开奖时间 未填写请检查！</h4></div>',
                    closeIsShow: true,
                    closeFun: function () {
                        this.hide();
                    }
                })
            }




        })

        if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
	        saleTime.each(function (i) {
	            if ($(this).val() == '') {
	                msg.show({
	                    mask: true,
	                    content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">销售周期时间 未填写请检查！</h4></div>',
	                    closeIsShow: true,
	                    closeFun: function () {
	                        this.hide();
	                    }
	                })
	            }
	        })
        }

        //拼装数据发送
        var jsonStr = '';
        var url = '';
        var data = '';
        var lotteryId = $('#lotteryId').val();
        var ruleType = $('#currentRuleTypeId').val();

        //常规规则

        var period2 = '';
        var k = 1;
        $("input[name=checkbox2]").each(function (i, v) {

            if ($('input[name=checkbox2][value=' + k + ']').is(':checked') == true) {
                period2 += $('input[name=checkbox2][value=' + k + ']').val();
                period2 += ',';
            }
			k = k + 1;
        });

        //特例规则
        jsonStr += '{"lotteryId":"';
        jsonStr += lotteryId;
        jsonStr += '","ruleId":"';
        jsonStr += ruleId
        jsonStr += '","issueRulesName":"';
        jsonStr += $('#issueRulesNameId').val();
        jsonStr += '","ruleStartTime":';
        jsonStr += convertDate2UnixTimestamp($('#J-date-exception-start').val());
        jsonStr += ',"ruleEndTime":';
        jsonStr += convertDate2UnixTimestamp($('#J-date-exception-end').val());
        jsonStr += ',"openAwardPeriod":"';
        jsonStr += period2 + '"';

        //操作类型
        jsonStr += ',"ruleType":2,"operationType":2'; //1新增，2 修改       

        jsonStr += ',"salesIssueStrucs":[';

        //获取id
        //var ids = '';    

        //ids = $('input[name=tempId2]').val();

        for(var i = 0 ;i < $('.break-time').size(); i++){		

            jsonStr += '{"saleStartTime":';
			jsonStr += 0;            
            jsonStr += ',"salePeriodTime":';
            if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
            jsonStr += get_AllData('saleWeek',i);
            }else{
            	jsonStr += 0;
            }
            jsonStr += ',"scheduleStopTime":';
            jsonStr += get_AllData('saleWait',i);
            jsonStr += ',"firstAwardTime":';
            jsonStr += convert2Sec(get_AllTime('firstH',i),get_AllTime('firstM',i),get_AllTime('firstS',i)); 
            //jsonStr += ',"id":';
            //jsonStr += ids;
            jsonStr += ',"saleTimeSn":"0"';
            jsonStr += ',"lastAwardTime":';
            jsonStr += convert2Sec(get_AllTime('lastH',i),get_AllTime('lastM',i),get_AllTime('lastS',i)); 
            jsonStr += "}";
            if(i != ($('.break-time').size() - 1)){
				
				jsonStr += ','
			}
        }
        jsonStr += ']}';

        url = baseUrl + '/gameoa/addOrUdapteCommonOrSpecialGameIssueRule';
        data = "reqData=" + jsonStr;
        
	    var msgError = '';
			if($.trim($('#issueRulesNameId').val()) == ''){
				msgError = '特例奖期规则名称不能为空';					
			}else if($.trim($('#J-date-exception-start').val()) == ''){
				msgError = '特例时间段不能空';			
				
			}
			checkboxlist.each(function(i) {
                if(this.checked)
				{
					isChecked=false;
				}
            });
			if(isChecked)
			{
				msgError = '开奖周期不能为空';
			}
			if(msgError != ''){
				msg.show({
					mask:true,
					content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+msgError+'</h4></div>',
					closeIsShow: true,
					closeFun : function(){
						this.hide();
					} 
				});
			}else{
				sendUrl(url, data);
			}	
        //sendUrl(url, data);
    });

    $('#J_Submit_Button_b').click(function () {
        //休市/维护
        var issueName = $('#X_IssueName').val();

        if (issueName == '') {
            msg.show({
                mask: true,
                content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市/维护时间名称 未填写请检查！</h4></div>',
                closeIsShow: true,
                closeFun: function () {
                    this.hide();
                }
            })
            return false;
        }

        var _maintenanceTimeStarts = $('#maintenanceTimeStarts').val();
        var _maintenanceTimeEnd = $('#maintenanceTimeEnd').val();
        if (_maintenanceTimeStarts == '' || _maintenanceTimeEnd == '') {

            msg.show({
                mask: true,
                content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市/维护时间段 未填写请检查！</h4></div>',
                closeIsShow: true,
                closeFun: function () {
                    this.hide();
                }
            })

            return false;
        }
        
        

        var period3 = '';
		var k = 1;
		$("input[name=checkbox3]").each(function(i,v){
			if($('input[name=checkbox3][value='+k+']').is(':checked')==true){
				period3 += $('input[name=checkbox3][value='+k+']').val();
				period3 += ',';
			}
			k = k+1;
		});
		
		
		//官方第一期开奖时间
		//时
		var _firstH = $("select[name=firstStopH2]").find("option:selected").text();
		if(_firstH == ''){
			_firstH = '00';
		}
		_firstH = convert2Arr(_firstH);
		//分
		var _firstM = $("select[name=firstStopM2]").find("option:selected").text();
		if(_firstM == ''){
			_firstM = '00';
		}
		_firstM = convert2Arr(_firstM);
		//秒
		var _firstS = $("select[name=firstStopS2]").find("option:selected").text();
		if(_firstS == ''){
			_firstS = '00';
		}
		_firstS = convert2Arr(_firstS);
		//官方最后一期开奖时间
		var _lastH = $("select[name=lastStopH2]").find("option:selected").text();
		if(_lastH == ''){
			_lastH = '00';
		}
		_lastH = convert2Arr(_lastH);
		var _lastM = $("select[name=lastStopM2]").find("option:selected").text();
		if(_lastM == ''){
			_lastM = '00';
		}
		_lastM = convert2Arr(_lastM);
		var _lastS = $("select[name=lastStopS2]").find("option:selected").text();
		if(_lastS == ''){
			_lastS = '00';
		}
		_lastS = convert2Arr(_lastS);
		/*if((_firstH[0] == '00' && _firstM[0] == '00' && _firstS[0] == '00') || (_lastH[0] == '00' && _lastM[0] == '00' && _lastS[0] == '00')){
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市开始结束时间（时-分-秒）不能全为0！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}*/
		var stopStartTime_=convert2Sec(_firstH[0],_firstM[0],_firstS[0]);
		var stopEndTime_=convert2Sec(_lastH[0],_lastM[0],_lastS[0]);
		var seriesIssueCode=1;
		if($('#seriesIssueCode').is(':checked')==false){
			seriesIssueCode=0;
		}

		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += $('#lotteryId').val();
		jsonStr += ',"seriesIssueCode":';
		jsonStr += seriesIssueCode;
		jsonStr += ',"issueRulesName":"';
        jsonStr += issueName;
        jsonStr += '","ruleId":"';
        jsonStr += ruleId
		jsonStr += '","ruleStartTime":';
		jsonStr += convertDate2UnixTimestamp(_maintenanceTimeStarts);
		jsonStr += ',"ruleEndTime":';
		jsonStr += convertDate2UnixTimestamp(_maintenanceTimeEnd);
		jsonStr +=',"stopStartTime":';
		jsonStr += stopStartTime_;
		jsonStr +=',"stopEndTime":';
		jsonStr += stopEndTime_;
		jsonStr += ',"openAwardPeriod":"';
		jsonStr +=	period3;
		jsonStr += '","operationType":"2"}';
		
		url = baseUrl + '/gameoa/addOrUdapteStopGameIssueRule';
		data = "reqData=" + jsonStr;
		

        sendUrl(url, data);
    });
	
	

	//立即生效事件
	$(document).on('click',"#J_Effective_Button",function(){
		var par=$(this).parent(),
			jsonStr = '',
			data ='';
			
			jsonStr += '{"lotteryId":';
			jsonStr += $.trim($('#lotteryId').val());
			jsonStr += ',"scheduleStopTime":';
			jsonStr += $.trim(par.find('[name="saleWait"]').val());
			jsonStr += '}';			
			data = "reqData=" + jsonStr;			
			
		$.ajax({
			url:'/gameoa/updateCommonRuleScheduleStopTime',				
			type: "post",					
			data:data,
			success:function(data){
				if(Number(data['status']) == 1){
					 msg.show({
                        mask: true,
                        content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">奖期规则已立即生效</h4></div>',
                        closeIsShow: true,
                        closeFun: function () {
                            this.hide();
							window.location.reload();
                        }
                    })
				}else{
					msg.show({
                        mask: true,
                        content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+data['message']+'</h4></div>',
                        closeIsShow: true,
                        closeFun: function () {
                            this.hide();
                        }
                    })
				}
			}
		});
	});

    //ajax
    function sendUrl(url, data) {
		
		editConfirm.isFlag = false;
        jQuery.ajax({
            type: "post",
            url: url,
            data: data,
            success: function (data) {
                if (data.status == 1) {
                    operationSuccess();
                } else {
                    //					operationFailure();
                    msg.show({
                        mask: true,
                        content: '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">' + data.message + '</h4></div>',
                        closeIsShow: true,
                        closeFun: function () {
                            this.hide();
                        }
                    })
                }
            },
            error: function () {
                operationFailure();
            }
        });
    }


})();
