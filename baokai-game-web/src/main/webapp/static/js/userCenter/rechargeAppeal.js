(function () {
    var message = new phoenix.Message;
    var tipCase = new phoenix.Tip({cls: 'j-ui-tip-l j-ui-tip-yellow'});
    tipCase.showErrorTips = function (item, msg) {
        if(msg){
            tipCase.setText('<i class="error"></i>'+msg);
            tipCase.getDom().css('border','1px solid #AAA');
            tipCase.getTextContainer().css('color','red');
            tipCase.getTextContainer().css('font-size','15px');
            tipCase.show(item.width() + 20, 0, item);
        }
    };
    tipCase.hideErrorTips = function () {
        tipCase.hide();
    };

    var alertCheck = function () {
        var status = $('#status').val();
        if (status == 'success') {
            $('#pop-success').click();
        } else if (status == 'fail') {
            $('#pop-error').click();
        }
    };
    var cdSec = $('#cdSec').val();
    var createFileUpload = function (url, blockId, boxId) {
        var fileBlock = $('[id="' + blockId + '"]');
        var photoBox = $('[id="' + boxId + '"]');
        var def = {
            url: url,
            fileBlock: fileBlock,
            photoBox: photoBox,
            maxUploads: 3,
            submitBtn: $('<input type="button" class="btn btn-mini" value="上传照片"/>')
        }
        return new UploadFile(def);
    }
    var fileUploads1 = createFileUpload('/fundappeal/upload', 'uploadFile1', 'photoBox1');
    var fileUploads1_1 = createFileUpload('/fundappeal/upload', 'uploadFile1_1', 'photoBox1_1');
    var fileUploads2 = createFileUpload('/fundappeal/upload', 'uploadFile2', 'photoBox2');
    var fileUploads3 = createFileUpload('/fundappeal/upload', 'uploadFile3', 'photoBox3');
    var fileUploads5 = createFileUpload('/fundappeal/upload', 'uploadFile5', 'photoBox5');
    var fileUploads6 = createFileUpload('/fundappeal/upload', 'uploadFile6', 'photoBox6');
    var clearFiles = function () {
        fileUploads1.clearFiles();
        fileUploads1_1.clearFiles();
        fileUploads2.clearFiles();
        fileUploads3.clearFiles();
        fileUploads5.clearFiles();
        fileUploads6.clearFiles();
    };

    var initCdTime = function () {
        var doChangeCdTime = function () {
            if (cdSec > 0) {
                cdSec--;
                var min = Math.floor(cdSec / 60);
                var sec = cdSec % 60;
                if (sec < 10) {
                    sec = '0' + sec;
                }
                $('cdTime').text(min + ':' + sec);
                setTimeout(doChangeCdTime, 1000);
            } else {
                $('#btnSubmit').bind('click', submit);
                $('#btnSubmit').attr('disabled', false).removeClass('btn-disable');
                $('cdTime').parent().remove();
            }
        };
        if (cdSec != null && cdSec > 0) {
            $('#btnSubmit').unbind('click');
            $('#btnSubmit').attr('disabled', true).addClass('btn-disable');
            setTimeout(doChangeCdTime, 1000);
        }
    };

    var vaildateInput = function (form) {
        var type = form.attr('selectType');
        var account = form.find('[name="account"]');
        var amount = form.find('[name="amount"]');
        if (account.length > 0) {
            var value = account.val();
            if (value.length == 0) {
                tipCase.showErrorTips(account, '平台帐号不得为空。');
                account.focus();
                return false;
            } else if (value.length < 3 || value.length > 16) {
                tipCase.showErrorTips(account, '长度有误，请输入3-16位字符');
                account.focus();
                return false;
            } else if ($.isNumeric(value.substring(0, 1))) {
                tipCase.showErrorTips(account, '用户名不能数字开头');
                account.focus();
                return false;
            }
        }
        if (amount != null) {
            var value = amount.val();
            if (!$.isNumeric(value)) {
                tipCase.showErrorTips(amount, '充值金额只能输入数字');
                amount.focus();
                return false;
            }
        }
        switch (type) {
            case '1':
                if (!vaildateBankInput(form)) {
                    return false;
                }
                break;
            case '2':
                if (!vaildateFastInput(form)) {
                    return false;
                }
                break;
            case '3':
                if (!vaildateTenpayInput(form) || !vaildateRadioInput(form)) {
                    return false;
                }
                break;
            case '5':
                if (!vaildateBankOther(form)) {
                    return false;
                }
                break;
            case '6':
                if (!vaildateRadioInput(form)) {
                    return false;
                }
                break;
        }
        return true;
    };

    var vaildateBankInput = function (form) {
        var bank = form.find('[name="bank"]');
        var bankId = bank.val();
        if (bank.length > 0) {
            var value = bank.val();
            if (value.length == 0) {
                var bankLabel = form.find('.bank-label');
                tipCase.showErrorTips(bankLabel, '请选择银行');
                bankLabel.focus();
                return false;
            }
        }
        if (bankId == 1) {
            return vaildateBank1(form);
        } else {
            return vaildateBankOther(form);
        }
    };

    var vaildateBank1 = function (form) {
        var fieldType = form.find('[name="fieldType"]:checked').val();
        var electronicNumber = form.find('[name="electronicNumber"]');
        var transactionNum = form.find('[name="transactionNum"]').val();
        var hadImage = fileUploads1.submited();
        if (fieldType == 'electronic') {
            if (electronicNumber.length > 0) {
                var value = electronicNumber.val();
                if (value.length != 16) {
                    tipCase.showErrorTips($('[name="etcNumber"]').last(), '请输入16位电子回单号码');
                    $('[name="etcNumber"]').first().focus();
                    return false;
                }
            }
        } else if (fieldType=='transactionNum') {
        	if(transactionNum==''){
        		tipCase.showErrorTips($('[id="transactionNum"]'), '请输入指令序号');
        		return false;
        	}
        	
        }else {
        
            if (hadImage != null) {
                if (!hadImage) {
                    tipCase.showErrorTips($('[id="uploadFile1"]'), '请上传回执单截图');
                    return false;
                }
            }
        }
        return true;
    };

    var vaildateBankOther = function (form) {
        var rechargeName = form.find('[name="rechargeName"]');
        var rechargeCard = form.find('[name="rechargeCard"]');
        var type = form.attr('selectType');
        if(type == 5){
        	var hadImage = fileUploads5.submited();
        }else{
        	var hadImage = fileUploads1_1.submited();
        }
        
        if (rechargeName.length > 0) {
            var value = rechargeName.val();
            var regCn = new RegExp(/[\u4e00-\u9fa5\u00b7]/g);
            var firstVal = value.substr(0, 1);
            var isCnName = regCn.test(firstVal);
            if (value.length == 0) {
                tipCase.showErrorTips(rechargeName, '付款人不得为空');
                rechargeName.focus();
                return false;
            } else if (isCnName && (value.length < 2 || value.length > 15)) {
                tipCase.showErrorTips(rechargeName, '付款人长度有误，请输入4-30位字符的中文名称或英文名称');
                rechargeName.focus();
                return false;
            } else if (!isCnName && (value.length < 4 || value.length > 30)) {
                tipCase.showErrorTips(rechargeName, '付款人长度有误，请输入4-30位字符的中文名称或英文名称');
                rechargeName.focus();
                return false;
            }
        }
        if (rechargeCard.length > 0) {
            var value = rechargeCard.val().replace(/[^\d]/g, '');
            ;
            var length = value.length;
            if (length != 16 && length != 18 && length != 19) {
                tipCase.showErrorTips(rechargeCard, '付款人卡号由16或18或19位数字组成');
                rechargeCard.focus();
                return false;
            }
        }
        if (hadImage != null) {
            if (!hadImage) {
            	if(type == 5){
            		tipCase.showErrorTips($('[id="uploadFile5"]'), '请上传回执单截图');
            	}else{
            		tipCase.showErrorTips($('[id="uploadFile1_1"]'), '请上传回执单截图');
            	}
                return false;
            }
        }
        return true;
    };
    
    var vaildateRadioInput = function (form) {
    	var type = form.attr('selectType');
    	var transactionTips = form.find('[name="transactionNum"]');
    	var transactionNum =  form.find('[name="transactionNum"]').val();
    	if(type==3){
    		var hadImage = fileUploads3.submited();
    		if($('input[name=uploadType]:checked').val()=='ut1' && transactionNum==''){
        		tipCase.showErrorTips(transactionTips, '请输入交易流水号');
        		return false;
        	}else{
        		if (hadImage != null) {
                    if ($('input[name=uploadType]:checked').val()=='ut2' && !hadImage) {
                        tipCase.showErrorTips($('[id="uploadFile3"]'), '请上传回执单截图');
                        return false;
                    }
                }
        		
        	}
    	}else if (type==6){
    		var hadImage = fileUploads6.submited();
    		if($('input[name=uploadType2]:checked').val()=='ut1' && transactionNum==''){
        		tipCase.showErrorTips(transactionTips, '请输入交易流水号');
        		return false;
        	}else{
        		if (hadImage != null) {
                    if ($('input[name=uploadType2]:checked').val()=='ut2' && !hadImage) {
                        tipCase.showErrorTips($('[id="uploadFile6"]'), '请上传回执单截图');
                        return false;
                    }
                }
        		
        	}
    	}	
    	
    	
    	
    	 return true;
    };


    var vaildateFastInput = function (form) {
        var hadImage = fileUploads2.submited();
        var bank = form.find('[name="bank"]');
        if (bank.length > 0) {
            var value = bank.val();
            if (value.length == 0) {
                var bankLabel = form.find('.bank-label');
                tipCase.showErrorTips(bankLabel, '请选择银行');
                bankLabel.focus();
                return false;
            }
        }
        if (hadImage != null) {
            if (!hadImage) {
                tipCase.showErrorTips($('[id="uploadFile2"]'), '请上传回执单截图');
                return false;
            }
        }
        return true;
    };

    var vaildateTenpayInput = function (form) {
        var tenpayAccount = form.find('[name="tenpayAccount"]');
        var tenpayName = form.find('[name="tenpayName"]');
      
        if (tenpayAccount.length > 0) {
            var value = tenpayAccount.val();
            if (value.length < 4 || value.length > 35) {
                tipCase.showErrorTips(tenpayAccount, '财付通帐号长度有误，请输入4-35位字符');
                tenpayAccount.focus();
                return false;
            }
        }
        if (tenpayName.length > 0) {
            var value = tenpayName.val();
            if(value.length==0){
                tipCase.showErrorTips(tenpayName, '请输入财付通姓名');
                tenpayName.focus();
                return false;
            }
        }
        
        return true;
    };
    
  

    var submit = function () {
        var form = $('form:visible');
        tipCase.hideErrorTips();
        if (vaildateInput(form)) {
            form.submit();
        }
    };

    //日期元件
    DatePickerUtil($('[name="rechargeTime"]'), true);

    $('[name="amount"]').bind('blur', function () {
        var value = $(this).val();
        var length = value.length;
        var dotLocation = value.indexOf('.');
        if (length > 0) {
            if (dotLocation == -1) {
                $(this).val(value + '.00');
            } else if (dotLocation == length - 1) {
                $(this).val(value + '00');
            }
        }
    });

    //充值方式切換
    $('[name="type"]').bind('change', function () {
        var ul = $(this).parent().parent();
        var selectType = ul.find('[name="type"]').val();
        var account = ul.find('[name="account"]').val();
        var amount = ul.find('[name="amount"]').val();
        $('[name="type"]').val(selectType);
        $('[name="account"]').val(account);
        $('[name="amount"]').val(amount);
        clearFiles();
        $('form').each(function () {
            var formType = $(this).attr('selectType');
            formType == selectType ? $(this).show() : $(this).hide();
        });
        tipCase.hideErrorTips();
    });

    //银行展示与隐藏
    $('.bank-label').bind('click', function () {
        var bankSelect = $(this).parent();
        if (bankSelect.find('#icoName').attr("class") == 'ico-down') {
            bankSelect.find('#icoName').removeClass('ico-down').addClass('ico-up');
            bankSelect.find('.bank-more-content').show();
        } else {
            bankSelect.find('#icoName').removeClass('ico-up').addClass('ico-down');
            bankSelect.find('.bank-more-content').css("display", "none");
        }
    });

    //银行点击
    $(".bank-list span").bind('click', function () {
        var bankSelect = $(this).parent().parent().parent();
        var name = $.trim($(this).attr("name"));
        var bankId = $.trim($(this).attr("value"));
        var helpId = $.trim($(this).attr("helpId"));
        var helpId2 = $.trim($(this).attr("helpId2"));
        var text = $.trim($(this).attr("text"));
        bankSelect.find('span').removeClass("ico-bank-current");
        $(this).addClass("ico-bank-current " + name);
        bankSelect.find('#selectBank').removeAttr("style").removeAttr("class").addClass("ico-bank " + name).attr("title", name).html('');
        bankSelect.find('.bank-more-content').css("display", "none");
        bankSelect.find('#icoName').removeClass('ico-up').addClass('ico-down');
        bankSelect.find('[name="bank"]').val(bankId).attr('helpId', helpId).attr('text', text).attr('helpId2', helpId2).trigger('change');
      });

    //工商银行栏位不同
    $('#rechargeBank').bind('change', function () {
        var bankId = $(this).val();
        switch (bankId) {
            case '':
                $('.icbcBankInput').hide();
                $('.otherBankInput').hide();
                break;
            case '1':
                $('.icbcBankInput').show();
                $('.otherBankInput').hide();
                break;
            default:
                $('.icbcBankInput').hide();
                $('.otherBankInput').show();
                break;
        }
        clearFiles();
        tipCase.hideErrorTips();
    });

    //切换查询演练
    $('[name="bank"]').bind('change', function () {
        var ul = $(this).parent().parent().parent().parent();
        var selectType = ul.find('[name="type"]').val();
        var helpId = $(this).attr('helpId');
        var helpId2 = $(this).attr('helpId2');
        var bankName = $(this).attr('text');
        if (bankName != null) {
            $('[id="helpLink' + selectType + '"]').find('bankName').text(bankName + '回单查询演练');
            $('[id="helpLink' + selectType + '"]').attr('helpId', helpId);
            $('[id="helpLink' + selectType + '-1"]').attr('helpId', helpId2); //網銀工行指令演練
        }
    });

    //开启查询演练视窗
    $('.helpLink').bind('click', function () {
        var helpId = $(this).attr('helpId');
        var title = $(this).find('bankName').text();
        var url = '/help/queryGeneralDetail?helpId=' + helpId;
        if (helpId != null && helpId.length > 0) {
            window.open(url, title);
        }
    });

    //工商银行切换提交方式
    $('[name="fieldType"]').bind('change', function () {
        var type = $(this).val();
        switch (type) {
            case 'electronic':
                $('.electronicInput').show();
                $('.remittanceInput').hide();
                break;
            case 'remittance':
                $('[name="etcNumber"]').val('');
                $('[name="electronicNumber"]').val('');
                $('.electronicInput').hide();
                $('.remittanceInput').show();
                $('.transactionNumInput').hide();
                break;
                
            case 'transactionNum':
            	 $('.transactionNumInput').show();
            	 $('.electronicInput').hide();
                 $('.remittanceInput').hide();
            	break;
        }
        tipCase.hideErrorTips();
    });
    //电子回单号码
    $('[name="etcNumber"]').bind('keyup', function () {
        if ($(this).val().length == $(this).attr('maxlength')) {
            $(this).next(':input').val('');
            $(this).next(':input').focus();
        }
        var electronicNumber = '';
        $('[name="etcNumber"]').each(function () {
            electronicNumber += $(this).val();
        });
        $('[name="electronicNumber"]').val(electronicNumber);
    });
    
    
    $('[name="transactionNum"]').bind('keyup', function () {
	       
		var v = $.trim(this.value);
		
		if(v.length>62){
			console.log(v.length)
			$(this).val(v.substr(0,62))//包含四位一空格應該只有62
		}

    });

    //提交
    $('#btnSubmit').bind('click', submit);
    //提示popup
    $('#pop-success').bind('click', function () {
        message.show({
            mask: true,
            title: '温馨提示',
            content: $('#msgSuccessContent').text(),
            confirmText: '查看申诉进度',
            confirmIsShow: true,
            topCloseFun: function () {
                location.href = '/fundappeal/appealrechargelist';
            },
            confirmFun: function () {
                location.href = '/fundappeal/appealstatuslist';
            }
        });
    });
    $('#pop-error').bind('click', function () {
        message.show({
            mask: true,
            title: '温馨提示',
            content: $('#msgErrorContent').text(),
            hideClose: true,
            confirmIsShow: true,
            cancelIsShow: true,
            cancelText: '查看充值记录',
            confirmFun: function () {
                location.href = '/fundappeal/appealrechargelist';
            },
            cancelFun: function () {
                location.href = '/fund/history';
            }
        });
    });
    
    $('input[name=uploadType]').click(function(){
    	console.log($('input[name=uploadType]:checked').val())
    	if($('input[name=uploadType]:checked').val()=='ut1'){
    		$('#transNumDiv').css( "display", "block");
    		$('#uploadImgDiv').css( "display", "none");
    	}else{
    		$('#transNumDiv').css( "display", "none");
    		$('#uploadImgDiv').css( "display", "block");
    	}
    	tipCase.hideErrorTips();

    });
    
    $('input[name=uploadType2]').click(function(){
    	if($('input[name=uploadType2]:checked').val()=='ut1'){
    		$('#transNumDiv2').css( "display", "block");
    		$('#uploadImgDiv2').css( "display", "none");
    	}else{
    		$('#transNumDiv2').css( "display", "none");
    		$('#uploadImgDiv2').css( "display", "block");
    	}
    	tipCase.hideErrorTips();

    });

    $(document).ready(function () {
        $('.icbcBankInput').hide();
        $('.otherBankInput').hide();
        $('.electronicInput').hide();
        $('.remittanceInput').hide();
        $('.bank-list span[value="' + $('[name="defaultBank"]').val() + '"]').trigger('click');
//        $('[name="fieldType"][value="electronic"]').trigger('click');
        $('[name="fieldType"][value="transactionNum"]').trigger('click');
        alertCheck();
        initCdTime();
        tipCase.hideErrorTips();
    });
})();