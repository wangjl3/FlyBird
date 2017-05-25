/**
 * Created by king on 2017/5/11.
 */
$(function () {
    $.ajax({
        type: "GET",
        url: "/sys/user/info",
        success: function(result){
            if(result.code == 0){
                vm.user = result.user;
            }
        }
    });
    $("#editNickname").click(function () {
        var input = $(this).parent().parent().find("input");
       input.removeAttr("readonly");
    });
    $("#editWatchword").click(function () {
        var input = $(this).parent().parent().find("textarea");
        input.removeAttr("readonly");
    });
    $("#up_img").uploadify({
        'height'        : 27,
        'width'         : 80,
        'buttonText'    : '选择图片',
        'swf'           : '/resources/uploadify/uploadify.swf',
        'uploader'      : '/sys/user/uploadHeadImage',
        'auto'          : true,
        'multi'         : false,
        'removeCompleted':true,
        'cancelImg'     : '/resources/uploadify/uploadify-cancel.png',
        'fileTypeExts'  : '*.jpg;*.jpge;*.gif;*.png',
        'fileSizeLimit' : '5MB',
        'fileObjName'   : 'file',
        'onUploadSuccess':function(file,data,response){
            var r = JSON.parse(data);
            if (r.code == 0) {
                alert("上传成功");
                vm.user.headImage = r.headImage;
            }else {
                alert(r.msg);
            }
        },
        //加上此句会重写onSelectError方法【需要重写的事件】
        'overrideEvents': ['onSelectError', 'onDialogClose'],
        //返回一个错误，选择文件的时候触发
        'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -110:
                    alert("文件 ["+file.name+"] 大小超出系统限制的" + jQuery('#upload_org_code').uploadify('settings', 'fileSizeLimit') + "大小！");
                    break;
                case -120:
                    alert("文件 ["+file.name+"] 大小异常！");
                    break;
                case -130:
                    alert("文件 ["+file.name+"] 类型不正确！");
                    break;
            }
        }});
});
var vm = new Vue({
    el:'#profile',
    data:{
        user:{
            headImage:""
        }
    },
    methods:{
        updateProfile:function () {
            $.ajax({
                type: "POST",
                url: "/sys/user/updateProfile",
                data: JSON.stringify(vm.user),
                contentType: "application/json",
                dataType: "json",
                success: function (r) {
                    if (r.code === 0) {
                        alert('更新个人信息成功');
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }, cancle:function () {
            parent.location.href ='/sys/main.html?date='+ new Date();
        }
    }
});