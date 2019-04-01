<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>人事管理系统——添加职位</title>

    <link rel="stylesheet" href="../css/vendor/bootstrap/css/bootstrap.min.css" type="text/css" charset="UTF-8"/>
    <link rel="stylesheet" href="../css/flat-ui.css" type="text/css" charset="UTF-8"/>
    <link href="../layui/css/layui.css" rel="stylesheet">
    <style type="text/css">
        .form-group {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="layui-form">
    <div class="form-group">
        <label>部门名称:<input class="form-control" type="text" name="name" id="dname" lay-verify="dname" /></label>
    </div>
    <div class="form-group">
        <label>详细描述:<textarea rows="1" cols="17" id="remark" name="remark" class="form-control"
                              lay-verify="remark"></textarea></label>
    </div>
    <div class="form-group">
        <button class="btn-sm btn btn-primary" type="button" lay-submit="" lay-filter="insert">添加</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-sm btn-warning" type="reset">重置</button>
    </div>
</div>
<script src="../js/jquery-3.1.1.min.js" charset="UTF-8"></script>
<script src="../layui/layui.js" charset="UTF-8"></script>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        var form = layui.form,
            layer = layui.layer;
        form.verify({
            dname: function (value) {
                if (value.length == 0) {
                    return '部门名不能为空哦';
                }
            },
            remark: function (value) {
                if (value.length == 0) {
                    return "描述不能为空哦";
                }
            }
        });

        //监听提交
        form.on('submit(insert)', function () {
            var url = "${pageContext.request.contextPath}/dept/addDept";
            var param = {
                flag: 2,
                name:$("#dname").val(),
                remark:$("#remark").val()
            }
            $.ajax({
                url: url,
                data: param,
                type: 'post',
                success: function (data) {
                    if (data == "success") {
                        layer.msg('添加成功', {icon: 1});
                        setTimeout(function () {
                            window.location.href = "${pageContext.request.contextPath}/dept/selectDept";
                        }, 2000);
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