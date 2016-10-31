##拾卡寻人QQ入口示例

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>拾卡寻人</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <script src="https://cdn.bootcss.com/zepto/1.1.6/zepto.js"></script>
    <script src="js/touch.js"></script>
    <script src="js/animation.js"></script>

    <link href="css/teacherService.css" type="text/css" rel="stylesheet">
    <link href="css/studentService.css" type="text/css" rel="stylesheet">
    <link href="css/weixin.css" type="text/css" rel="stylesheet">
    <script src="style/js/zepto.min.js"></script>
    <script src="js/analyse.js"></script>
</head>
<style>
    *{
        font-family: '黑体';
    }
    @-webkit-keyframes imgAnim {
        0%   { left:20%; -webkit-transform: scale(0.3,0.3)}
        50%  { left:10%;-webkit-transform: scale(0.6,0.6)}
        100% { left:30%;-webkit-transform:scale(0.9,0.9)}

    }
    @-webkit-keyframes bntFadeIn {
        from{opacity: 0.2; -webkit-transform: scale(0.2,0.2)}
        50%{opacity: 0.6;-webkit-transform: scale(0.5,0.5)}
        to{opacity: 0.8;-webkit-transform: scale(0.8,0.8)}
    }
    @-moz-keyframes imgAnim {
        0%   { left:20%; -moz-transform: scale(0.2,0.2)}

        25%  { left:15%;-moz-transform: scale(0.3,0.3)}

        50%  { left:10%;-moz-transform: scale(0.4,0.4)}
        60%  { left:15%;-moz-transform: scale(0.5,0.5)}
        70%  { left:20%;-moz-transform: scale(0.6,0.6)}
        90% { left:25%;-moz-transform:scale(0.8,0.8)}
        100% { left:30%;-moz-transform:scale(0.9,0.9)}

    }
    @-moz-keyframes bntFadeIn {
        from{opacity: 0.2; -moz-transform: scale(0.2,0.2)}
        50%{opacity: 0.6;-moz-transform: scale(0.5,0.5)}
        to{opacity: 0.8;-moz-transform: scale(0.8,0.8)}
    }
    /* @keyframes imgAnim1 {
         from{transform:scale(1,1) }
         to{transform: scale(0.3,0.3)}
     }*/

    div[name='container']{
        /* visibility: hidden;*/
        width:100%;
        height: 100%;
        background:#FFFFFF;
    }
    #lostAccount
    {
        margin: 40% 15% 0 15%;
        width: 70%;
        height: 20%;
        border: none;
        border-bottom: 1px solid #6cbfe1;
        padding-bottom:4px;
    }
    .btn{
        -webkit-animation: bntFadeIn 1s linear 1 0.2s;
        -moz-animation: bntFadeIn 1s linear 1 0.2s;
        width: 70%;
        color: #fff;
        font-size: 14px;
        letter-spacing: 6px;
        height: 32px;
        border-radius: 4px;
        margin: 0 15% 5% 15%;
        background-color: rgb(53, 163, 203);

        border-style: hidden;
    }
    div[name='imageBox']
    {

        position: absolute;
        -moz-animation: imgAnim 1s linear 1 0s;
        -webkit-animation: imgAnim 1s linear 1 0s;
        width:40%;;
        height: auto;
        left: 30%;
        top: 10%;
    }
    div[name='finish']
    {
        visibility: hidden;
        padding-top: 30%;
        position: relative;
        height: auto;
        left: 30%;
        top: 30%;
    }
    div[name='finish'] img
    {
        width:40%;
    }
    .p{
        color: #666;
        margin: 5% 15% 15% 15%;
        font-size: 13px;
    }
    *{margin:0px;padding: 0px;}
    #img{
        position: fixed;
        width: 100%;
        height: 100%;
    }
</style>
<body>
<div name="container" id="mainCon" style="display: none;">
    <div name="imageBox" id="logoCon"><img  src="css/images/logo.png" style="width: 100%;height: auto;"></div>
    <div style="margin-top: 40%" id="textCon">
        <input style="font-size:1.5em" type="text" placeholder="请输入失卡人的学号" id="lostAccount">
        <p class="p">点击确定后，将发送你的手机号码至卡人的客户端，便于失卡人与您联系</p>
        <button id="sure" onclick="init()" class="btn">确定</button>
        <button id="cancel" class="btn">取消</button>
    </div>
    <div name="finish" id="finishCon">
        <img  src="css/images/finish.png">
    </div>
    <p class="p" style="position:fixed;left:0;top:400px;font-size: 12px;" id="con" align="center"></p>
</div>
<div  id="loading" style="">
    <ul>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>

<div id="show" style="display:none;text-align:center;"><img src="css/images/pass.jpg"/></div>
<div class="container" id="container" style="display: none;"><!-- style="background:#aaa;" -->
    <div class="all">
        <div class="head">
            <div class="image"><img src="css/images/scut.png" alt=""></div>
            <div><p class="two" style="color: darkgrey;">身份统一认证</p></div>
        </div>
        <div class="login">
            <p><img width="20px" src="css/images/user.png"><input  type="text" name="user" class="user" id="account"></p>
            <p><img width="28px" src="css/images/password.png"><input type="password" name="password" class="pass" id="password"></p>
            <button class="button" type="button" onclick="log()">绑定</button>
        </div>
    </div>
</div>
<p style="position:fixed;left:16%;right:16%;bottom:30%;font-size: 16px;" id="content" align="center"></p>
<script>
    var swidth=screen.width;  //频幕宽度
    var lwidth=$("#loading ul li").width()*12; //进度条的宽度
    var left=(swidth-lwidth)/2;
    $("#loading").css("margin-left",left);  //进度条居中
    $("#loading").css("margin-top",left);

    var backCode;
    var account;
    //实现功能方法
    function init() {
        var sendData = {
            "code":backCode,
            "account": account,
            "desAccount": $("#lostAccount").val()
        };
        $.ajax({
            url: "/Wisdom/message/findPerson",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(sendData),
            async: false,
            success: function (json) {
                if (json.result == "checked") {
                    //   alert(111);
                    $("#loading").remove();//隐藏加载动画
                    $("#logoCon").remove();
                    $("#textCon").remove();
                    $("#finishCon").remove();
                    //    alert(222);
                    $("#mainCon").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/check-circle.png'>"));
                    console.log(screen.height);

                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
//
                    $("#content").html(json.content);
                    //   alert(333);
                }
                else if (json.result == "failsave") {
                    $("body").children().remove();
                    $("body").append($(" <img id='img'  src='css/images/netWorkError.png'>"));
                    console.log(screen.height);

                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                }
                else if (json.result == "no such service") {
                    $("body").children().remove();
                    $("body").append($(" <img id='img'  src='css/images/netWorkError.png'>"));
                    console.log(screen.height);

                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                }
                else if(json.result == "no such a user")     {
                    $("#loading").remove();//隐藏加载动画
                    $("#logoCon").remove();
                    $("#textCon").remove();
                    $("#finishCon").remove();
                    $("#mainCon").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/warning.png'>"));
                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                    $("#content").html("该用户不存在！ 请重试！");
                }
                else{
                    $("#loading").remove();//隐藏加载动画
                    $("#logoCon").remove();
                    $("#textCon").remove();
                    $("#finishCon").remove();
                    $("#mainCon").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/warning.png'>"));
                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                    $("#content").html("网络连接失败！ 请重试！");
                }
            },
            error: function () { //处理出错信息
                $("body").children().remove();
                $("body").append($(" <img id='img'  src='css/images/netWorkError.png'>"));
                console.log(screen.height);

                var img = document.getElementById("img");
                img.style.width = (screen.width);
                img.style.height = (screen.height);
            }
        });
    }

//绑定帐号的方法
    function log() {
        // alert($("input").eq("0").val());
        // alert($("input").eq("1").val());
        // alert(openId);
        var sendData={
            "account":$("#account").val(),
            "password":$("#password").val(),
            "openId":sessionStorage.openId
        }
        $.ajax({
            url:"/Wisdom/qq/auth",
            type:"post",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(sendData),
            success:function (json) {
                // alert(111);
                if(json.result=="success"){
                    $("#container").hide();
                    account = json.account;
                    backCode = json.code;
                    $("#mainCon").show();
                }
                else if(json.result == "no such a user"){
                    $("#container").children().remove();
                    $("#container").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/unfinished.png'>"));
                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                    $("#content").html("用户不存在! 请重试！");
                }
                else if(json.result == "fail"){
                    $("#container").children().remove();
                    $("#container").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/error.png'>"));
                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                    $("#content").html("账号或密码错误! 请重试！");
                }
                else{
                    $("#container").children().remove();
                    $("#container").append($(" <img id='img' style='position:fixed; width:80%;height: 50%;top:10%;left:10%;right: 10%;' src='css/images/warning.png'>"));
                    var img = document.getElementById("img");
                    img.style.width = (screen.width);
                    img.style.height = (screen.height);
                    $("#content").html("网络连接失败! 请重试！");
                }
            },
            error:function() { //处理出错信息
                // window.location.href="networkError.html";
                //$("#loading").hide();
                $("body").children().remove();
                $("body").append($(" <img id='img'  src='css/images/netWorkError.png'>"));
                console.log(screen.height);

                var img=document.getElementById("img");
                img.style.width=(screen.width);
                img.style.height=(screen.height);
            }
        });
    }

//根据code获取openId,并判断是否已绑定
    $(function () {
        $("#cancel").tap(function () {
            $("#lostAccount").val("");
        });
        var args = GetUrl();
       // alert(args['code']);
        var  sendData={
            "code":args['code']
        };
        $.ajax({
            type: "post",
            url: "/Wisdom/qq/getOpenId",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(sendData),
            timeout: 0,   //请求超时时间，0表示不超时
            async: true,  //默认请求为异步
            success: function (json){
                if(json.result=="alreadyAuth"){
                    //  account = json.account;
                    $("#loading").hide();
                    account = json.account;
                    backCode = json.code;
                    $("#mainCon").show();
                }
                else{
                    $("#loading").hide();
                    sessionStorage.openId = json.openId;
                    $("#container").show();
                }
            },
            error:function() { //处理出错信息
                confirm("获取不到信息");
            }
        });
   });

</script>
</body>
</html>

```
