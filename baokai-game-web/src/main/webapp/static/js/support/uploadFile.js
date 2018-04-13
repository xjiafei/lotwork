var UploadFile = function (def) {
    var allFiles = [];
    var submited = false;
    var resultMsg = '';
    var config = {
        url: '',
        maxUploads: 3,
        size: 2,
        multiple: false,
        fileBlock: $('<span/>'),
        selectBtn: $('<input type="button" class="btn" style="vertical-align:bottom;border-radius:0px;box-shadow:0 0px 0px" value="选择文件"/>'),
        fileNameInp: $('<input type="text" class="input w-3" style="height:16px;vertical-align:bottom;margin-left:-2px" disabled="true" placeholder="未选择任何文件"/>'),
        submitBtn: $('<input type="button" class="btn" style="vertical-align:bottom;" value="上传"/>'),
        fileBtn: $('<input type="file" name="file" style="display:none">'),
        photoBox: $('<div/>'),
        getImgContent: function (src, realName, fileName) {
            var div = $('<div class="uploadImgContent" style="height:25px;margin-top:10px;">');
            var name = $('<div class="imgText" style="float:left;height:25px;width:300px;border:1px solid #ddd;padding-left:5px;background:#666;color:white">');
            var deduce = $('<div style="float:left;height:25px;width:50px;padding-left:5px;"></div>');
            var a = $('<a class="imgDelete" href="javascript:void(0)" style="color:red" filename="' + fileName + '">删除</a>');
            a.bind('click', function () {
                var fileName = $(this).attr('filename');
                config.removeFile(fileName);
                config.checkFileCount();
            });
            name.text(realName);
            div.append(name);
            deduce.append(a);
            div.append(deduce);
            return div;
        },
        prepareUpload: function (file) {
            if (!file.name.match(/\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF|rar|RAR)$/)) {
                alert('檔案只支持JPG、GIF、PNG、RAR格式');
                return false;
            }else if (file.name.match(/[@#\$%\^&\*()<>\'\！@#￥%……&*（）——+|{}【】‘；：”“’。，、？"]/)){
				alert('上传文档名不能包含特殊字元，请重命名后再尝试');
                return false;
			}
            else if (file.size > (1024 * 1024 * config.size)) {
                alert('文件大小请控制在' + config.size + 'MB内');
                return false;
            }
            return true;
        },
        uploadProgress: function (event) {
            if (event.lengthComputable) {
                var percentComplete = Math.round(event.loaded * 100 / event.total);
                config.submitBtn.val('上传中...' + percentComplete.toString() + '%');
            } else {
                config.submitBtn.val('上传中...');
            }
        },
        uploadComplete: function (event) {
			var respText = event.target.responseText;
            var msg = JSON.parse(respText);
            if (typeof msg.error === 'undefined') {
                submited = true;
                config.submitBtn.val('上传成功');
                alert('上传成功');
				//因為瀏覽器支援responseText的結果不同, 因此在這做區分
				if(respText.substring(0, 1) == '"'){
					resultMsg = msg;
				} else {
					resultMsg = event.target.responseText;	
				}
                
                config.disableButton(true);
                config.checkFileCount();
                for (var i = 0; i < allFiles.length; i++) {
                    allFiles[i].content.find('.imgText').css('background', 'green');
                    allFiles[i].content.find('.imgDelete').remove();
                }
				if($("#fileErrorTxt").length >0){
					$("#fileErrorTxt").hide();
				}
            } else {
                alert(msg.data);
                config.disableButton(false);
                config.showDeleteBtn(true);
                config.checkFileCount();
            }
        },
        uploadFail: function () {
            config.submitBtn.val('上传');
            alert("檔案无法上传，请稍后再试。");
            config.disableButton(false);
            config.showDeleteBtn(true);
            config.checkFileCount();
        },
        uploadCanceled: function () {
            config.submitBtn.val('上传');
            alert("檔案上传已取消");
            config.disableButton(false);
            config.showDeleteBtn(true);
            config.checkFileCount();
        },
        disableButton: function (flag) {
            config.submitBtn.attr('disabled', flag);
            config.selectBtn.attr('disabled', flag);
            if (flag) {
                config.submitBtn.addClass('btn-disable');
                config.selectBtn.addClass('btn-disable');
            } else {
                config.submitBtn.removeClass('btn-disable');
                config.selectBtn.removeClass('btn-disable');
            }
        },
        checkFileCount: function () {
            var result = true;
            if (allFiles.length == config.maxUploads) {
                config.selectBtn.attr('disabled', true).addClass('btn-disable');
                result = false;
            } else if (allFiles.length > config.maxUploads) {
                config.selectBtn.attr('disabled', true).addClass('btn-disable');
                alert('最多上传' + config.maxUploads + '份文件');
                result = false;
            } else {
                config.selectBtn.attr('disabled', false).removeClass('btn-disable');
            }
            return result;
        },
        clearFiles: function () {
            config.disableButton(false);
            config.fileNameInp.val('');
            config.photoBox.find('.uploadImgContent').remove();
            config.photoBox.hide();
            submited = false;
            allFiles = [];
        },
        removeFile: function (fileName) {
            for (var i = 0; i < allFiles.length; i++) {
                if (allFiles[i].file.fileName == fileName) {
                    allFiles[i].content.remove();
                    allFiles.splice(i, 1);
                    break;
                }
            }
        },
        showDeleteBtn: function (flag) {
            for (var i = 0; i < allFiles.length; i++) {
                if (flag) {
                    allFiles[i].content.find('.imgDelete').show();
                } else {
                    allFiles[i].content.find('.imgDelete').hide();
                }
            }
        }
    };
    if (def != null) {
        if (def['url'] != null) {
            config.url = def['url'];
        }
        if (def['maxUploads'] != null) {
            config.maxUploads = def['maxUploads'];
        }
        if (def['size'] != null) {
            config.size = def['size'];
        }
        if (def['fileBlock'] != null) {
            config.fileBlock = def['fileBlock'];
        }
        if (def['selectBtn'] != null) {
            config.selectBtn = def['selectBtn'];
        }
        if (def['fileNameInp'] != null) {
            config.fileNameInp = def['fileNameInp'];
        }
        if (def['submitBtn'] != null) {
            config.submitBtn = def['submitBtn'];
        }
        if (def['photoBox'] != null) {
            config.photoBox = def['photoBox'];
        }
        if (def['getImgContent'] != null) {
            config.getImgContent = def['getImgContent'];
        }
        if (def['prepareUpload'] != null) {
            config.prepareUpload = def['prepareUpload'];
        }
        if (def['uploadProgress'] != null) {
            config.uploadProgress = def['uploadProgress'];
        }
        if (def['uploadComplete'] != null) {
            config.uploadComplete = def['uploadComplete'];
        }
        if (def['uploadFail'] != null) {
            config.uploadFail = def['uploadFail'];
        }
        if (def['uploadCanceled'] != null) {
            config.uploadCanceled = def['uploadCanceled'];
        }
        if (def['multiple'] != null) {
            config.multiple = def['multiple'];
            if (config.multiple) {
                config.fileBtn.attr('multiple', '');
            }
        }
    }
    config.selectBtn.bind('click', function () {
        if (!$(this).hasClass('btn-disable')) {
            config.fileBtn.click();
        }
    });
    config.fileBtn.bind('change', function (evt) {
        var currentFiles = evt.target.files;
		//IE 這方法會繞兩次, 第二次是0, 需額外做排除
		if(currentFiles.length == 0){
			return;
		}
        config.disableButton(false);
        config.submitBtn.val('上传');
        for (var i = 0; i < currentFiles.length; i++) {
            var currentFile = currentFiles[i];
            var fileName = currentFile.name;
            currentFile.fileName = fileName + new Date().getTime();
            if (config.prepareUpload(currentFile)) {
                var reader = new FileReader();
                reader.realName = fileName;
                reader.fileContent = {content: null, file: currentFile};
                reader.onload = function (e) {
                    var src = e.target.result;
                    var i = config.getImgContent(src, this.realName, this.fileContent.file.fileName);
                    this.fileContent.content = i;
                    config.photoBox.append(i);
                };
                reader.readAsDataURL(currentFile);
                allFiles.push(reader.fileContent);
                config.photoBox.show();
            }
            if (!config.checkFileCount()) {
                break;
            }
        }
        config.fileBtn.val(null);
    });
    config.submitBtn.bind('click', function (event) {
        if (!$(this).hasClass('btn-disable')) {
            event.stopPropagation();
            event.preventDefault();
            if (allFiles.length > 0) {
                config.disableButton(true);
                config.showDeleteBtn(false);
                var xhr = new XMLHttpRequest();
                var fd = new FormData();
                for (var i = 0; i < allFiles.length; i++) {
                    fd.append('idCard' + i, allFiles[i].file);
                }
                xhr.upload.addEventListener("progress", config.uploadProgress, false);
                xhr.addEventListener("load", config.uploadComplete, false);
                xhr.addEventListener("error", config.uploadFailed, false);
                xhr.addEventListener("abort", config.uploadCanceled, false);
                xhr.open("POST", config.url);
                xhr.send(fd);
            }
        }
    });
    config.fileBlock.append(config.selectBtn);
    config.fileBlock.append(config.fileBtn);
    config.fileBlock.append(config.fileNameInp);
    config.fileBlock.append(config.submitBtn);

    return {
        files: function () {
            return allFiles;
        },
        clearFiles: config.clearFiles,
        removeFile: config.removeFile,
        checkFileCount: config.checkFileCount,
        submited: function () {
            return submited;
        },
        getUploadResult: function () {
            return resultMsg;
        }
    };
};