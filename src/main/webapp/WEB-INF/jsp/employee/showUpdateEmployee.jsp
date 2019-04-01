<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>人事管理系统</title>
    <!--引入bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!--引入layui -->
    <link href="../layui/css/layui.css" rel="stylesheet">
    <link href="../layui/css/modules/layer/default/layer.css?v=3.1.0" rel="stylesheet" media="all">
</head>
<body>
<div class="layui-form">
    <!--第一行 -->
    <div class="layui-form-item" style="margin-top: 6px;margin-left: 10px">
        <!--姓名 -->
        <div class="layui-inline">
            <label class="layui-form-label">姓名:</label>
            <div class="layui-input-inline">
                <input class="form-control" type="text" name="name" lay-verify="name" id="name" value="${employee.name}"/>
            </div>
        </div>
        <!--身份证号 -->
        <div class="layui-inline">
            <label class="layui-form-label">身份证:</label>
            <div class="layui-input-inline">
                <input class="form-control" type="text" name="cardId" lay-verify="identity" id="cardId" value="${employee.cardId}">
            </div>
        </div>
    </div>
    <!--第二行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--性别 -->
        <label class="layui-form-label">性别:</label>
        <div class="layui-input-inline">
            <input type="radio" name="gender" value="1" title="男" checked="checked">
            <input type="radio" name="gender" value="0" title="女">
        </div>
    </div>
    <!--第三行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--学历 -->
        <div class="layui-inline">
            <label class="layui-form-label">学历:</label>
            <div class="layui-input-inline">
                <input class="form-control" type="text" name="education" id="education" value="${employee.education}">
            </div>
        </div>

        <!--专业 -->
        <div class="layui-inline">
            <label class="layui-form-label">专业:</label>
            <div class="layui-input-inline">
                <input class="form-control" type="text" name="speciality" id="speciality" value="${employee.speciality}">
            </div>
        </div>
    </div>

    <!--第四行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--手机 -->
        <div class="layui-inline">
            <label class="layui-form-label">手机:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="phone" type="text" lay-verify="phone" id="phone" value="${employee.phone}">
            </div>
        </div>
        <!--邮箱 -->
        <div class="layui-inline">
            <label class="layui-form-label">邮箱:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="email" type="text" lay-verify="email" id="email" value="${employee.email}">
            </div>
        </div>
    </div>

    <!--第五行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--民族 -->
        <div class="layui-inline">
            <label class="layui-form-label">民族</label>
            <div class="layui-input-inline">
                <input class="form-control" name="race" type="text" id="race" value="${employee.race}">
            </div>
        </div>
        <!--政治面貌 -->
        <div class="layui-inline">
            <label class="layui-form-label" style="width: 90px">政治面貌:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="party" type="text" id="party" value="${employee.party}">
            </div>
        </div>
    </div>

    <!--第六行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--QQ -->
        <div class="layui-inline">
            <label class="layui-form-label">QQ:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="qq" type="text" id="qq" value="${employee.qq}">
            </div>
        </div>
        <!--生日 -->
        <div class="layui-inline">
            <label class="layui-form-label">生日:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="birthday" type="text" id="date">
            </div>
        </div>
    </div>

    <!--第七行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--住址 -->
        <label class="layui-form-label">住址:</label>
        <div id="citybox" class="layui-inline">
            <div class="layui-input-inline">
                <select name="province" id="province" lay-filter="province">
                    <option value="">--请选择--</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="city" id="city" lay-filter="city">
                    <option value="">--请选择--</option>
                </select></div>
            <div class="layui-input-inline">
                <select name="area" id="area" lay-filter="area">
                    <option value="">--请选择--</option>
                </select>
            </div>
        </div>
    </div>

    <!--第八行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--职位 -->
        <div class="layui-inline">
            <label class="layui-form-label">职位:</label>
            <div class="layui-input-inline">
                <select name="jid" lay-search="" id="jsearch">
                </select>
            </div>
        </div>
        <!--所属部门 -->
        <div class="layui-inline">
            <label class="layui-form-label">部门:</label>
            <div class="layui-input-inline">
                <select name="did" lay-search="" id="dsearch">
                </select>
            </div>
        </div>
    </div>

    <!--第九行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--爱好 -->
        <div class="layui-inline">
            <label class="layui-form-label">爱好:</label>
            <div class="layui-input-inline">
                <input class="form-control" name="hobby" type="text" id="hobby" value="${employee.hobby}">
            </div>
        </div>
    </div>

    <!--第十行 -->
    <div class="layui-form-item" style="margin-left: 10px">
        <!--备注 -->
        <div class="layui-inline">
            <label class="layui-form-label">备注:</label>
            <div class="layui-input-inline">
                <textarea class="form-control" name="remark" rows="1" cols="10" id="remark">${employee.remark}</textarea>
            </div>
        </div>
    </div>

    <!--第十一行 -->
    <div class="form-group" style="margin-left: 50px">
        <button class="btn-sm btn btn-primary" type="button" lay-submit="" lay-filter="change">修改</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-sm btn-warning" type="reset">重置</button>
    </div>
    <!--表单结束 -->
</div>
<!--引入jquery -->
<script src="../js/jquery-3.1.1.min.js" charset="UTF-8"></script>
<!--引入layui.js -->
<script src="../layui/layui.js" charset="UTF-8"></script>
<!--引入省市县三级联动js -->
<script src="../js/city.js" charset="UTF-8"></script>

<script type="text/javascript">
    layui.use(['form', 'laydate'], function () {
        var form = layui.form,
            laydate = layui.laydate;

        <!--验证规则 -->
        form.verify({
            name: function (value) {
                if (value.length > 20) {
                    return "请输入正确的姓名"
                }
            }
        });

        <!--监听提交 -->
        form.on('submit(change)', function () {
            var url = "${pageContext.request.contextPath}/employee/updateEmployee";
            var param = {
                flag: 2,
                id:${employee.id},
                jid: $("#jsearch").val(),
                did: $("#dsearch").val(),
                gender: $("input[name='gender']:checked").val(),
                name: $("#name").val(),
                cardId: $("#cardId").val(),
                education: $("#education").val(),
                speciality: $("#speciality").val(),
                phone: $("#phone").val(),
                email: $("#email").val(),
                race: $("#race").val(),
                party: $("#party").val(),
                qq: $("#qq").val(),
                address: $("#province").val() + '-' + $("#city").val() + '-' + $("#area").val(),
                hobby: $("#hobby").val(),
                remark: $("#remark").val()
                //注意:birthday格式不对,会导致修改失败!
                //birthday: $("#birthday").val()
            };
            $.ajax({
                url: url,
                data: param,
                type: 'post',
                success: function (res) {
                    if (res == "success") {
                        layer.msg("修改成功", {icon: 1});
                        setTimeout(function () {
                            window.location.href = "${pageContext.request.contextPath}/employee/selectEmployee";
                        });
                    }
                },
                error: function () {
                    layer.msg("修改失败", {icon: 2});
                }
            })
        });

        //墨绿主题
        laydate.render({
            elem: '#date',
            theme: 'molv',
            max:getDate(),
            value:'${employee.birthday}'.substr(0,10)
        });
    });

    <!--获取当天日期 -->
    function getDate() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var date = date.getDate();
        return year + "-" + month + "-" + date;
    }


    //向select添加选项
    function initSelect() {
        <!--向部门下拉框中追加选项 -->
        //在最前面追加一个选项
        $("#dsearch").prepend("<option value=''>请输入或选择部门</option>");
        <!--获取所有部门信息 -->
        var deptIdArray = new Array();
        var deptNameArray = new Array();
        <!--遍历部门集合,取出所有部门的名字 -->
        <c:forEach items="${deptList}" var="dept">
        deptIdArray.push(${dept.id})
        deptNameArray.push("${dept.name}")
        </c:forEach>
        //向select中追加选项
        for (var i = 0; i < deptIdArray.length; i++) {
            $("#dsearch").append("<option value='" + deptIdArray[i] + "'>" + deptNameArray[i] + "</option>");
        }

        <!--向职位下拉框中追加选项 -->
        $("#jsearch").prepend("<option value=''>请输入或选择职位</option>");

        //获取joblist中所有的job
        var jobIdArray = new Array();
        var jobNameArray = new Array();
        <c:forEach items="${jobList}" var="job">
        jobIdArray.push(${job.id});
        jobNameArray.push("${job.name}");
        </c:forEach>
        //职位追加开始
        for (var i = 0; i < jobIdArray.length; i++) {
            $("#jsearch").append("<option value='" + jobIdArray[i] + "'>" + jobNameArray[i] + "</option>");
        }
        //追加结束
    }

    //调用方法
    initSelect();
</script>
</body>
</html>