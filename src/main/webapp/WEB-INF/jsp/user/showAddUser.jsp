<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>人事管理系统——添加用户</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vendor/bootstrap/css/bootstrap.min.css" type="text/css" charset="UTF-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flat-ui.css" type="text/css" charset="UTF-8"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js" charset="UTF-8"></script>
    <link href="${pageContext.request.contextPath}/layui/css/layui.css" rel="stylesheet">
    <style type="text/css">
        .form-group-sm {
            margin-left: 10px;
        }
    </style>

</head>
<body>

<div class="layui-form" id="userForm">
    <div class="form-group-sm">
        <label>姓&nbsp;名:
       	 	<input class="form-control" type="text" name="username" id="username" lay-verify="username">
        </label>&nbsp;&nbsp;
        <label>状&nbsp;态:
        	<input class="form-control" type="text" name="status" id="status" lay-verify="status">
        </label>
    </div>
    <div class="form-group-sm">
        <label>登录名:
        	<input class="form-control" type="text" name="loginName" id="loginName" lay-verify="loginName">
        </label>&nbsp;&nbsp;
        <label>密&nbsp;码:
        	<input class="form-control" type="text" name="password" id="password" lay-verify="password">
        </label>
    </div>
    <div class="form-group-sm">
        <button class="btn-sm btn btn-primary" lay-submit="" lay-filter="add">添加</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-sm btn-warning" type="reset">重置</button>
    </div>
</div>

<script src="${pageContext.request.contextPath}/layui/layui.js" charset="UTF-8"></script>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        var form = layui.form,
            layer = layui.layer;
        
        //验证表单元素
        form.verify({
            username: function (value) {
                if (value.length == 0) {
                    return '姓名不能为空哦';
                } else if (value.length > 6) {
                    return "请输入正确的姓名格式";
                }
            },
            password: function (value) {
                if (value.length == 0) {
                    return "密码不能为空哦";
                } else if (value.length < 3) {
                    return "密码长度不能少于3位";
                }
            },
            status: function (value) {
                if (value != 1 && value != 0) {
                    return "状态只能是0或1";
                }
            },
            loginName: function (value) {
                if (value.length == 0) {
                    return "登录名不能为空";
                }
            }
        });

        //监听提交
        form.on('submit(add)', function () {
            var url = "${pageContext.request.contextPath}/user/addUser";
            //注意param中的属性名要与JavaBean中的属性名一致!
            var param = {
                flag: 2,
                username: $("#username").val(),
                status: $("#status").val(),
                loginName: $("#loginName").val(),
                password: $("#password").val()
            }
            
            //进行ajax请求
            $.ajax({
                url: url,
                data: param,
                type: 'post',
                success: function (data) {
                	//layer.msg(data);
                    if (data == "success") {
                        layer.msg('添加成功', {icon: 1});
                        setTimeout(function () {
                            window.location.href = "${pageContext.request.contextPath}/user/selectUser";
                        }, 1000);
                    }
                },
                error: function () {
                    layer.msg("添加失败", {icon: 2});
                }
            })
        })
    });
</script>
</body>
</html>