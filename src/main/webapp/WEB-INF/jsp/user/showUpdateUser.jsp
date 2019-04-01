<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vendor/bootstrap/css/bootstrap.min.css" type="text/css" charset="UTF-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flat-ui.css" type="text/css" charset="UTF-8"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js" charset="UTF-8"></script>
    <link href="${pageContext.request.contextPath}/layui/css/layui.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/flat-ui.js" type="text/javascript" charset="UTF-8"></script>
    <style type="text/css">
        .form-group-sm {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
    <tr valign="top">
        <td>
            <div id="userForm" class="layui-form">
                <!-- 隐藏表单，flag表示添加标记 -->
                <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
                    <tr>
                        <td class="font3 fftd">
                            <div class="form-group-sm">
                                <label>姓&nbsp;名:
                                	<input class="form-control" type="text" name="username"
                                		id="username" value="${user.username}" lay-verify="username">
                                </label>&nbsp;&nbsp;
                                <label>状&nbsp;态:
                                	<input class="form-control" type="text" name="status"
                                        id="status" value="${user.status}" lay-verify="status">
                                </label>
                            </div>
                            <div class="form-group-sm">
                                <label>登录名:
                                	<input class="form-control" type="text" name="loginName"
                                        id="loginName" value="${user.loginName}" lay-verify="loginName">
                                </label>&nbsp;&nbsp;
                                <label>密&nbsp;码:
                                	<input class="form-control" type="text" name="password"
                                         id="password" value="${user.password}" readonly="readonly" lay-verify="password">
                                </label>
                            </div>
                            <div class="form-group-sm">
                                <button class="btn-sm btn btn-primary" type="button" lay-submit="" lay-filter="change">修改</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-sm btn-warning" type="reset">重置</button>
                            </div>
                         </td>
                     </tr>
                </table>
            </div>
        </td>
    </tr>
</table>

<div style="height:10px;"></div>

<script src="${pageContext.request.contextPath}/layui/layui.js" charset="UTF-8"></script>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        var form = layui.form,
            layer = layui.layer;
        form.verify({
            username: function (value) {
                if (value.length == 0) {
                    return '姓名不能为空哦';
                } else if (value.length > 6) {
                    return "请输入正确的姓名格式";
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
        form.on('submit(change)', function () {
            var url = "${pageContext.request.contextPath}/user/updateUser";
            var param = {
                flag: 2,
                id:${user.id},
                username: $("#username").val(),
                status: $("#status").val(),
                loginName: $("#loginName").val(),
                password: $("#password").val()
            }
            $.ajax({
                url: url,
                data: param,
                type: 'post',
                success: function (data) {
                    if (data == "success") {
                        layer.msg('修改成功', {icon: 1});
                        setTimeout(function () {
                            window.location.href = "${pageContext.request.contextPath}/user/selectUser";
                        }, 1000);
                    }else{
                        layer.msg("修改失败", {icon: 2});
                    }
                },
                error: function () {
                    layer.msg("修改失败", {icon: 2});
                }
            })
        })
    });
</script>
</body>
</html>