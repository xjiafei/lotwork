var UploadFile = function (def) {
    var uploadFiles = [];
    var submited = false;
    var config = {
        url: '',
        maxUploads: 3,
        fileBlock: $('<span/>'),
        selectBtn: $('<input type="button" class="btn" style="vertical-align:bottom;border-radius:0px;box-shadow:0 0px 0px" value="选择文件"/>'),
        fileNameInp: $('<input type="text" class="input w-3" style="height:16px;vertical-align:bottom;margin-left:-2px" disabled="true" placeholder="未选择任何文件"/>'),
        submitBtn: $('<input type="button" class="btn" style="vertical-align:bottom;" value="上传照片"/>'),
        photoBox: $('<div/>'),
        getImgContent: function (src, fileName) {
            var span = $('<span class="ui-info uploadImgContent" align="center" style="padding:5px">');
            var a = $('<a>');
            var img = $('<img style="width:75px;height:75px">');
            var name = $('<p style="align">');
            a.bind('click', function () {
                window.open(src, fileName);
            });
            img.attr('src', src);
            name.text(fileName);
            a.append(img);
            span.append(a);
            span.append(name);
            return span;
        },
        prepareUpload: function (event) {
            var f = event.target;
            if (!f.files[0].name.match(/\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$/)) {
                alert('图片只支持JPG、GIF、PNG格式');
                return false;
            }
            else if (f.files[0].size > (1024 * 1024 * 2)) {
                alert('文件大小请控制在2MB内');
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
            var msg = JSON.parse(event.target.responseText);
            if (typeof msg.error === 'undefined') {
                submited = true;
                config.submitBtn.val('上传成功');
                config.submitBtn.attr('disabled', true).addClass('btn-disable');
                config.selectBtn.attr('disabled', true).addClass('btn-disable');
                alert('上传成功');
            } else {
                alert(msg.data);
            }
        },
        uploadFail: function () {
            config.submitBtn.val('上传照片');
            alert("图片无法上传，请稍后再试。");
        },
        uploadCanceled: function () {
            config.submitBtn.val('上传照片');
            alert("图片上传已取消");
        }
    };
    if (def != null) {
        if (def['url'] != null) {
            config.url = def['url'];
        }
        if (def['maxUploads'] != null) {
            config.maxUploads = def['maxUploads'];
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
    }
    config.selectBtn.bind('click', function () {
        var fileBtn = $('<input type="file" name="files" style="display:none">');
        fileBtn.bind('change', function (evt) {
            var currentFiles = evt.target.files;
            var url = $(this).val();
            var fileName = url.substring(url.lastIndexOf('\\') + 1);
            config.fileNameInp.val(fileName);
            if (config.prepareUpload(evt)) {
                var fileName = url.substring(url.lastIndexOf('\\') + 1);
                var reader = new FileReader();
                reader.onload = function (e) {
                    var src = e.target.result;
                    var i = config.getImgContent(src, fileName);
                    config.photoBox.append(i);
                };
                reader.readAsDataURL(currentFiles[0]);
                fileBtn.files = currentFiles;
                uploadFiles.push(fileBtn);
                config.photoBox.show();
            }
            if (uploadFiles.length >= config.maxUploads) {
                config.selectBtn.attr('disabled', true).addClass('btn-disable');
            }
        });
        config.fileBlock.append(fileBtn);
        fileBtn.click();
    });
    config.submitBtn.bind('click', function (event) {
        event.stopPropagation();
        event.preventDefault();
        if (uploadFiles.length > 0) {
            var xhr = new XMLHttpRequest();
            var fd = new FormData();
            for (var i = 0; i < uploadFiles.length; i++) {
                fd.append('idCard' + i, uploadFiles[i].files[0]);
            }
            xhr.upload.addEventListener("progress", config.uploadProgress, false);
            xhr.addEventListener("load", config.uploadComplete, false);
            xhr.addEventListener("error", config.uploadFailed, false);
            xhr.addEventListener("abort", config.uploadCanceled, false);
            xhr.open("POST", config.url);
            xhr.send(fd);
        }
    });
    config.fileBlock.append(config.selectBtn);
    config.fileBlock.append(config.fileNameInp);
    config.fileBlock.append(config.submitBtn);

    return {
        files: function () {
            return uploadFiles;
        },
        clearFiles: function () {
            config.submitBtn.attr('disabled', false).removeClass('btn-disable');
            config.selectBtn.attr('disabled', false).removeClass('btn-disable');
            config.fileNameInp.val('');
            config.photoBox.find('.uploadImgContent').remove();
            config.photoBox.hide();
            for (var i in uploadFiles) {
                var file = uploadFiles[i];
                file.remove();
            }
            submited = false;
            uploadFiles = [];
        },
        submited: function(){
            return submited;
        }
    };
};