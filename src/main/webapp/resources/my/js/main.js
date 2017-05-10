/**
 * Created by king on 2017/5/6.
 */
function  jMap() {
    var arr = {};
    this.put  = function (key,value) {
        arr[key] = value;
    }
    this.get = function (key) {
        if(arr[key]){
            return arr[key];
        }else {
            return null;
        }
    }
    this.remove = function (key) {
        delete  arr[key];
    }
    this.eachMap = function (fn) {
        for(var key  in  arr){
            fn(key,arr[key]);
        }
    }
}

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
var  contactMap = new jMap();
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
                for(var index in linkmans){
                    var headImage =  linkmans[index].headImage;
                    var remark = linkmans[index].remark;
                    var watchword = linkmans[index].watchword;
                    var uplineStatus= linkmans[index].uplineStatus;
                    var account = linkmans[index].account;
                    contactMap.put(account,[]);
                    var tr =  $("<tr class='linkmanItem' id='linkmanItem_"+account+"'></tr>");
                    if(!headImage){
                        headImage = "/resources/img/defaulthead.png";
                    }
                    var td1 = $("<td class='client-avatar'></td>");
                    var img = $("<img src='"+headImage+"' id='img_"+account+"'>");
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

var  contactsMap = new jMap();

$(function () {
    var contactArray = [];
    var path = window.location.hostname + ":" + window.location.port;
    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + path + "/ws");
    }
    websocket.onopen = function (event) {
       vm.websocket = websocket;
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
        if(data.code == -1){
            var messageInstant = data.msg;
            var mess = {};
            mess.fromAccount = messageInstant.fromAccount;
            mess.toAccount = messageInstant.toAccount;
            mess.text = messageInstant.text;
            contactMap.get(messageInstant.fromAccount).push(mess);
            if(vm.linkmanAccount==messageInstant.fromAccount){
                var chatItem = "<div style='overflow:hidden'>"+
                    "<div style='float: left'>"+
                    "<img class='message-avatar' style='float: left;'   src='"+vm.linkmanHeadImage+"' alt=''>" +
                    "<div style='width: 100%;padding-left: 65px; position:relative;' ><div  style='display:inline-block;margin-top: 15px;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;background-color: #81DDDD'><span class='message-content' style='padding: 10px'>"+messageInstant.text+"</span></div><span style='position:absolute;top:21px; left:56px;width: 0;height: 0;border-top: 5px solid transparent;border-right: 10px solid #81DDDD;border-bottom: 5px solid transparent;display:inline-block;'></span></div></div></div>";
                $("#chatContent").append(chatItem);
            }
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
        vm.linkmanAccount = linkmanAccount;
        console.log(linkmanAccount);
        $("#chatWindow").hide();
        $("#chatContent").empty();
        var chatMessageArray = contactMap.get(linkmanAccount);
        for(var messageInstant in chatMessageArray){
            //获取到被点击联系人的头像信息
             var headImage = $("#img_"+linkmanAccount).attr("src");
             vm.linkmanHeadImage = headImage;
             console.log(headImage);
            //自己写的信息，放在右边
            if(chatMessageArray[messageInstant].fromAccount == vm.user.account){
                var chatItem = "<div style='overflow:hidden'>"+
                    "<div style='float: right'>"+
                    "<img class='message-avatar' style='float: right;'   src='"+vm.user.headImage+"' alt=''>" +
                    "<div style='width: 100%;padding-right: 65px; position:relative;' ><div  style='display:inline-block;margin-top: 15px;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;background-color: #81DDDD'><span class='message-content' style='padding: 10px'>"+chatMessageArray[messageInstant].text+"</span></div><span style='position:absolute;top:21px; right:56px;width: 0;height: 0;border-top: 5px solid transparent;border-left: 10px solid #81DDDD;border-bottom: 5px solid transparent;display:inline-block;'></span></div></div></div>";
                $("#chatContent").append(chatItem);
            }
            //收到的消息，放在左边
            if(chatMessageArray[messageInstant].fromAccount == vm.linkmanAccount){
                var chatItem = "<div style='overflow:hidden'>"+
                    "<div style='float: left'>"+
                    "<img class='message-avatar' style='float: left;'   src='"+headImage+"' alt=''>" +
                    "<div style='width: 100%;padding-left: 65px; position:relative;' ><div  style='display:inline-block;margin-top: 15px;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;background-color: #81DDDD'><span class='message-content' style='padding: 10px'>"+chatMessageArray[messageInstant].text+"</span></div><span style='position:absolute;top:21px; left:56px;width: 0;height: 0;border-top: 5px solid transparent;border-right: 10px solid #81DDDD;border-bottom: 5px solid transparent;display:inline-block;'></span></div></div></div>";
                $("#chatContent").append(chatItem);
            }
        }
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
        linkmanAccount:"",
        messageInstant:{text:""},
        user:{},
        websocket:null,
        linkmanHeadImage: null,
    },
    methods: {
        addNewFriend: function () {
            $("#dialog").modal('show');
        },
        sendMessage:function (ev){
            ev.preventDefault();
            vm.messageInstant.fromAccount = vm.user.account;
            vm.messageInstant.toAccount = vm.linkmanAccount;
            var mess = {};
            mess.fromAccount = vm.messageInstant.fromAccount;
            mess.toAccount = vm.messageInstant.toAccount;
            mess.text = vm.messageInstant.text ;
            console.log(JSON.stringify(vm.linkmanAccount));
            contactMap.get(vm.linkmanAccount).push(mess);
             vm.websocket.send(JSON.stringify(vm.messageInstant));
            var chatItem = "<div style='overflow:hidden'>"+
                "<div style='float: right'>"+
               "<img class='message-avatar' style='float: right;'   src='"+vm.user.headImage+"' alt=''>" +
               "<div style='width: 100%;padding-right: 65px; position:relative;' ><div  style='display:inline-block;margin-top: 15px;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;background-color: #81DDDD'><span class='message-content' style='padding: 10px'>"+vm.messageInstant.text+"</span></div><span style='position:absolute;top:21px; right:56px;width: 0;height: 0;border-top: 5px solid transparent;border-left: 10px solid #81DDDD;border-bottom: 5px solid transparent;display:inline-block;'></span></div></div></div>";
            $("#chatContent").append(chatItem);
            var div = document.getElementById('chatContent');
            div.scrollTop = div.scrollHeight;
            vm.messageInstant.text = "";
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

