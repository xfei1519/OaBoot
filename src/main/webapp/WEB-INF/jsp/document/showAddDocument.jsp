<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>人事管理系统</title>
    <!--引入bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!--引入layui -->
    <link href="../layui/css/layui.css" rel="stylesheet">
</head>
<body>
<form class="layui-form" id="uploadForm">
    <!--文档标题 -->
    <div class="layui-form-item" style="margin-top: 5px">
        <label class="layui-form-label" style="width: 100px">文件标题&nbsp;</label>
        <div class="layui-input-block" style="margin-right: 50px">
            <input class="form-control" name="title" placeholder="请输入文件标题" type="text" lay-verify="title" id="title"/>
        </div>
    </div>
    <!--文件描述 -->
    <div class="layui-form-item" style="margin-top: 5px">
        <label class="layui-form-label" style="width: 100px">文件描述</label><br>
        <div class="layui-input-block" style="margin-right: 50px">
            <textarea type="text" class="form-control" placeholder="请简单描述文件" rows="5" cols="10" name="remark"
                      id="remark"></textarea>
        </div>
    </div>
    <!--上传文件 -->
    <div class="layui-form-item" style="margin-top: 5px">
        <label class="layui-form-label" style="width: 110px">请选择文件</label>
        <div class="layui-input-inline">
            <button type="button" class="btn-success btn" id="upload"><i class="layui-icon"></i>上传文件</button>
        </div>
    </div>
    <!--按钮组 -->
    <div class="layui-form-item" style="margin-top: 5px">
        <div class="layui-input-inline" style="margin-left: 24px">
            <button class="btn-sm btn btn-primary" id="submit" type="button">上传</button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button class="btn btn-sm btn-warning" type="reset">重置</button>
        </div>
    </div>
</form>

<!--引入jquery -->
<script src="../js/jquery-3.1.1.min.js" charset="UTF-8"></script>
<!--引入layui.js -->
<script src="../layui/layui.js" charset="UTF-8"></script>

<script type="text/javascript">
    layui.use(['form', 'layer', 'upload'], function () {
        var form = layui.form,
            layer = layui.layer,
            upload = layui.upload;

        form.verify({
            title: function (value) {
                if (value.length == 0) {
                    return '标题不能为空哦';
                }
            }
        });
        
        var uploadInit = upload.render({
            elem: '#upload',
            url: '${pageContext.request.contextPath}/doc/saveDocument',
            accept: 'file',
            auto: false,
            data: {title:'w',fileName:'f',remark:'s',uid:0},
            bindAction: '#submit',
            before: function () {
                uploadInit.config.data.title = $("#title").val();
                uploadInit.config.data.remark = $("#remark").val();
                uploadInit.config.data.uid = '001';
            },
            done: function (res) {
                if (res.code == 1) {
                    layer.msg('上传成功', {icon: 1});
                    setTimeout(function () {
                        window.location.href = "${pageContext.request.contextPath}/doc/selectDocument";
                    },2000);
                }
            },
            error: function () {
                layer.msg('上传失败', {icon: 2});
            }
        });
    });
</script>
</body>