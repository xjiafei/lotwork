//JS

//問題小項資料
function QuestionOption (pid, value, tempDesc, typename) {
	this.pid = pid; //大項代碼
	this.value = value; //小項代碼
	this.tempDesc = tempDesc; //小項template
	this.typename = typename; //小項中文
}

//問題小項選項
var questionOptionArray = new Array();
//問題小項初始化
function initQuestionOptions(){
	$('#question').find('option').each(function () {
		if($(this).attr("value") == -1){
			//alert($(this).html());
			questionOptionArray.push(
			new QuestionOption("",
								$(this).attr("value"),
								"",
								$(this).html()));
		} else {
			questionOptionArray.push(
			new QuestionOption($(this).attr("pid"),
								$(this).attr("value"),
								$(this).attr("tempDesc"),
								$(this).html()));
		}
	});
	$('#question').empty();
	$('#question').append('<option value="-1">请选择问题小类</option>');
}

	
$(function () {
    var createFileUpload = function (url, blockId, boxId) {
        var fileBlock = $('[id="' + blockId + '"]');
        var photoBox = $('[id="' + boxId + '"]');
        photoBox.css('margin-top','20px');
        var def = {
            url: url,
            fileBlock: fileBlock,
            photoBox: photoBox,
            maxUploads: 5,
            size: 1,
            multiple:true,
            fileNameInp:$(''),
            selectBtn: $('<a class="btn"><span style="font-size:large;margin-right:5px">+</span>新增文件</a>'),
            submitBtn: $('<input type="button" class="btn btn-small" style="margin-left:20px;" value="开始上传"/>')
        }
        return new UploadFile(def);
    };
    var fileUploads = createFileUpload('/support/fileUpload/uploadFiles', 'uploadFile', 'fileBox');
	
	//問題小項初始化
	initQuestionOptions();
	
    /*问题联动*/
    $('#questionParent').bind('change', function () {
        var qid = $(this).val();
		//因為IE不支援display:none.. 換個方式做
		$('#question').empty();
		$.each(questionOptionArray, function(idx, questionOption){
			if(qid == questionOption.pid){
				$('#question').append('<option pid="' + questionOption.pid + 
				'" value="' + questionOption.value + '" tempDesc="' + questionOption.tempDesc +
				'">' + questionOption.typename + '</option>');
			} else if (questionOption.value == -1) {
				$('#question').append('<option value="' + questionOption.value + '">' + questionOption.typename + '</option>');
			}
		});
        $('#question').val(-1);
        $('#question').focus();
    });

    var refreshCode = function () {
        var img = $('#verification-img'), src = img.attr('data-src-path');
        var random = Math.random();
        img.attr('src', src + '?r=' + random);
        img.attr('r', random);
    };
    $('#verification-img').click(function () {
        refreshCode();
    });
    refreshCode();


    var popError = function (item, msg) {
        var check = item.parent().find('.ui-check');
        check.html(msg);
        check.css({display: 'inline-block'});
    };
    var hideError = function (item) {
        var check = item.parent().find('.ui-check');
        check.hide();
    };

    $('#question').bind('change', function () {
        var description = $('#description');
        var tempDesc = $(this).find(':selected').attr('tempDesc');
        var options = $(this).find('option');
        var isTemplate = false;
        for (var i = 0; i < options.length; i++) {
            var opTempDesc = $(options[i]).attr('tempDesc');
            if (opTempDesc == description.val()) {
                isTemplate = true;
                break;
            }
        }
        if (description.val().length == 0 || isTemplate) {
            description.val(tempDesc);
        }
    });
    
    var form = $('#f1');
    var validate = {
        mail: function () {
            var mail = form.find('[name="mail"]');
            hideError(mail);
            if (mail.val() == null || mail.val().length == 0) {
                popError(mail, '请输入邮箱地址');
                return false;
            } else if (!/^([a-zA-Z0-9_\.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(mail.val())) {
                popError(mail, '请输入正确的邮箱地址');
                return false;
            }
            return true;
        },
        question: function () {
            var question = form.find('[name="question"]');
            hideError(question);
            if (question.val() == null || question.val().length == 0 || question.val() < 0) {
                popError(question, '请选择问题类型');
                return false;
            }
            return true;
        },
        title: function () {
            var title = form.find('[name="title"]');
            hideError(title);
            if (title.val() == null || title.val().length == 0) {
                popError(title, '请输入标题');
                return false;
            } else if (title.val().length > 50) {
                popError(title, '标题超过50个字元');
                return false;
            }
            return true;
        },
        description: function () {
            var description = form.find('[name="description"]');
            hideError(description.parent());
            if (description.val() == null || description.val().length == 0) {
                popError(description.parent(), '请输入问题描述');
                return false;
            } else if (description.val().length > 1000) {
                popError(description.parent(), '问题描述超过1000个字元');
                return false;
            }
            return true;
        },
        vCode: function () {
            var vCode = form.find('[name="vCode"]');
            hideError(vCode);
            if (vCode.val() == null || vCode.val().length == 0) {
                popError(vCode, '请输入验证码');
                return false;
            }
            return true;
        }
    };
    
    for(var key in validate){
        $('[name="'+key+'"]').bind('blur',validate[key]);
    }

    $('#submitBtn').bind('click', function () {
        $('.ui-check').hide();
        var vCode = form.find('[name="vCode"]');
        var validateResult = true;
        for(var key in validate){
            var result = validate[key]();
            validateResult = validateResult&&result;
        }
        if(!validateResult){
            return false;
        }
        if (fileUploads.getUploadResult() != null) {
            form.find('[name="files"]').val(fileUploads.getUploadResult());
        }
        $.ajax({
            url: '/support/imgVaildate/checkImg',
            data: {vcode: vCode.val(), r: $('#verification-img').attr('r')},
            method: "post",
			dataType: "text",
            success: function (data) {
				var result = JSON.parse(data);
                if (result.success) {
                    form.submit();
                } else {
                    alert('验证码错误，请重新输入');
                    refreshCode();
					vCode.val("");
                }
            }
        });
    });
});
