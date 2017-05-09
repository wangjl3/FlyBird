/**
 * Created by king on 2017/5/6.
 */
function statusToText(uplineStatus) {
    var uplineinfo = "离线";
    if(uplineStatus == 1){
        uplineinfo = "有空";
    }else if(uplineStatus == 2){
        uplineinfo = "忙碌";
    }else  if(uplineStatus == 3){
        uplineinfo = "请勿打扰";
    }else  if(uplineStatus == 4){
        uplineinfo = "马上回来";
    }else if(uplineStatus == 5){
        uplineinfo = "离线";
    }else if(uplineStatus == 6){
        uplineinfo ="下班";
    }
    return uplineinfo;
}
function loadLinkmanMessage() {
    var linkmans = [];
    $.ajax({
        type: "GET",
        url: "/sys/linkman/info",
        success: function(result){
            if(result.code == 0){
                linkmans = result.linkmans;
                var table = $("#tableLinkmans");
                //     <!--</tr>-->
                console.log(linkmans[0].headImage);
                for(var index in linkmans){
                    var headImage =  linkmans[index].headImage;
                    var remark = linkmans[index].remark;
                    var watchword = linkmans[index].watchword;
                    var uplineStatus= linkmans[index].uplineStatus;
                    var account = linkmans[index].account;
                    var tr =  $("<tr class='linkmanItem' id='linkmanItem_"+account+"'></tr>");
                    console.log(headImage);
                    console.log(remark);
                    console.log(uplineStatus);
                    if(!headImage){
                        headImage = "/resources/img/defaulthead.png";
                    }
                    var td1 = $("<td class='client-avatar'></td>");
                    var img = $("<img src='"+headImage+"'>");
                    img.appendTo(td1);
                    td1.appendTo(tr);

                    var td2 =$("<td class='client-link'></td>");;
                    var label = $("<label>"+remark+"</label>");
                    label.appendTo(td2);
                    td2.appendTo(tr);
                    var uplineinfo = statusToText(uplineStatus);
                    var td3 = $("<td class='client-status'></td>");
                    var span = $("<span class='label label-primary' id='status_"+account+"'>"+uplineinfo+"</span>")
                    span.appendTo(td3);
                    td3.appendTo(tr);
                    tr.appendTo(table);
                }
            }
        }
    });


}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
// //文本消息
// public static final int CODE_TEXT_DATA = -1;
// //好友上线状态改变通知
// public static final int CODE_UPDATE_STATUS = -2;
// //被别人添加为好友
// public static  final int CODE_FRIENDING_DATA  = -3;
$(function () {
    var path = window.location.hostname + ":" + window.location.port;
    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + path + "/ws");
    }
    websocket.onopen = function (event) {

    }
    websocket.onmessage = function (event) {
        var data = JSON.parse(event.data);
        if(data.code == -2){
            var message = data.msg;
            var account = message.account;
            var status = message.status;
            var statusInfo = statusToText(status);
            $("#status_"+account).text(statusInfo);
        }
    }

    $(".full-height-scroll").slimScroll({height:"100%"});
    //加载个人信息
    $.ajax({
        type: "GET",
        url: "/sys/user/info",
        success: function(result){
            if(result.code == 0){
                 vm.user = result.user;
            }
        }
    });
    //加载联系人信息
    loadLinkmanMessage();

    $(document).on('click','.linkmanItem',function () {
        var linkmanItemId = $(this).attr("id");
        var linkmanAccount = linkmanItemId.split('_')[1];
        vm.messageInstant.toAccount = linkmanAccount;
        $("#chatWindow").show();
    });
    $("#strangeAccount").click(function () {
        var strangeAcccount = $(this).attr("data-strangeAccount");
        $.ajax({
            type: "POST",
            url: "/sys/addfrienditem/new",
            data: "strangeAccount="+strangeAcccount,
            dataType: "json",
            success: function(result){
                if(result.code == 0){//登录成功
                    alert("添加成功");
                }else{
                    alert(result.msg);
                }
            }
        });
    });
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            linkmanAccount: null,
            strangeAcc:null
        },
        linkmanAccount:null,
        messageInstant:{},
        user:{}
    },
    methods: {
        addNewFriend: function () {
            $("#dialog").modal('show');
        },
        sendMessage:function (){
            messageInstant.fromAccount = user.account;

            websocket.send(JSON.stringify(messageInstant));
        },
        findByStrangeAcc:function () {
            $.ajax({
                type: "GET",
                url: "/sys/user/findByAccount?strangeAcc="+vm.q.strangeAcc,
                success: function(result){
                    if(result.code == 0){
                        $("#divNotFind").hide();
                        $("#divChatUser").show();
                        $("#strangeNickname").text(result.user.nickname);
                        $("#strangeAccount").attr("data-strangeAccount",result.user.account);
                    }else{
                        $("#divChatUser").hide();
                        $("#divNotFind").show();
                    }
                }
            });
        }
    }
});

