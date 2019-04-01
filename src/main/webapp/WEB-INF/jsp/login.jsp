<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title>OA人事管理 - 登录</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/layui/css/layui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
    
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1>[ OA管理 ]</h1>
                </div>
                <div class="m-b"></div>
                <h4>欢迎使用 <strong>OA人事管理系统</strong></h4>
                <ul class="m-b">
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 银行级安全</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 多平台覆盖</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 专家专业团队</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 坚实品牌基础</li>
                </ul>
            </div>
        </div>
        
        <div class="col-sm-5">
        	<!-- action对应着UserController中的loginForm请求 -->
            <form method="post" action="user/loginForm">
                <h4 class="no-margins">登录：</h4>
                <p class="m-t-md">登录到OA后台</p>
                <!-- value里的值是从Cookie中加载过来的,${cookie['loginName'].value},Cookie的key就是loginName -->
                <input type="text" class="form-control uname" placeholder="用户名" name="loginName" value="${cookie['loginName'].value}" />
                <input type="password" class="form-control pword m-b" placeholder="密码" name="password" />
                <!-- isCheck:标记勾选状态.${cookie['remember'].value}就是checked='checked' -->
                <input type="checkbox" style="color:red" id="isCheck" name="isCheck" value="remember" ${cookie['remember'].value}/>&nbsp;记住账号
                <button class="btn btn-success btn-block">登录</button>
            </form>
        </div>
    </div>
    
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2017-2099 OA管理系统 All Rights Reserved.
        </div>
    </div>
</div>
</body>

</html>

