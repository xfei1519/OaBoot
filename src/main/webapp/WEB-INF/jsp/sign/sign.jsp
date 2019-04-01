<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
    <html>
    <head>
    <meta charset="utf-8">
        <title>人事管理系统</title>
        <!-- jq -->
        <script src="../js/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/bootstrap.min.js"></script>

<!-- 分页插件 -->
<link href="../css/bootstrap-table.min.css" rel="stylesheet">
<script src="../js/bootstrap-table.js"></script>
<script src="../js/bootstrap-table-locale-all.min.js"></script>

<!--layer -->
<link href="../js/layer/theme/default/layer.css" rel="stylesheet">

<link href="../laydate/theme/default/laydate.css" rel="stylesheet">

<!--layui -->
<link href="../layui/css/layui.css" rel="stylesheet" media="all">

<!--flat -->
<style type="text/css">
    .panel {
        margin-left: -56px;
        width: 1245px;
    }

    .col-sm-12 {
        margin-left: -70px;
        margin-top: 15px;
        width: 1270px;
    }

    thead {
        background: #428bca;
        color: white;
    }

    .btn-search {
        margin-left: 100px;
    }

    .btn-see {
        margin-left: 10px;
    }
</style>
</head>
<body onload="isSign()">
<div class="container" style="margin-top:5px">
    <div class="row">
        <!--!查询区 -->
        <div class="panel panel-default">
            <div class="panel-heading" style="background-color:#428bca;color: white">
                查询条件
            </div>
            <div class="layui-form">
                <div class="layui-form-item" style="margin-left: 10px;margin-top: 5px">
                    <!--开始日期选择 -->
                    <label class="layui-form-label" style="width: 120px">起始日期选择&nbsp;</label>
                    <div class="layui-input-inline">
                        <input class="form-control" type="text" id="start" placeholder="起始日期选择"/>
                    </div>

                    <!--结束日期选择 -->
                    <label class="layui-form-label" style="width: 120px">结束日期选择&nbsp;</label>
                    <div class="layui-input-inline">
                        <input class="form-control" type="text" id="end" placeholder="结束日期选择"/>
                    </div>

                    <!--按钮组 -->
                    <button class="btn btn-search" style="background-color: #0767C8;color: white" type="button"
                            id="search_btn">搜&nbsp;&nbsp;索
                    </button>
                    <button class="btn btn-see" style="background-color: #0767C8;color: white" type="button"
                            id="seeChart">查看图表
                    </button>
                </div>
            </div>
        </div>

        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-success">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>打卡
            </button>
        </div>
        <!-- 表格 -->
        <div class="col-sm-12">
            <table class="table table-striped table-bordered table-hover" id="table"></table>
        </div>

    </div>
</div>

<script src="../js/layer/layer.js" charset="UTF-8"></script>
<script src="../laydate/laydate.js" charset="UTF-8"></script>
<script type="text/javascript">


    //获取当前日期并格式化
    function getDate() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var date = date.getDate();
        return year + "-" + month + "-" + date;
    }

    <!--渲染开始日期选择 -->
    var start = laydate.render({
        elem: "#start",
        max: getDate(),
        done: function (value, date) {
            <!--动态指定结束日期选择时的最小值为开始日期的选择 -->
            end.config.min = {
                year: date.year
                , month: date.month - 1
                , date: date.date
            }
        }
    });
    <!--结束日期选择的渲染 -->
    var end = laydate.render({
        elem: "#end",
        max: getDate(),
        done: function (value, date) {
            <!--动态指定开始日期的最大值是结束日期的选择结果 -->
            start.config.max = {
                year: date.year
                , month: date.month - 1
                , date: date.date
            }
        }
    });

    <!--打卡方法 -->
    $("#btn_add").click(function () {
        var id = ${sessionScope.user_session.id};
        var url = "${pageContext.request.contextPath}/sign/addSign";
        $.ajax({
            url: url,
            type: 'post',
            data: {uid: id},
            success: function (res) {
                if (res.code == 1) {
                    layer.msg("打卡成功", {icon: 1});
                    $("#btn_add").html("已打卡");
                    $("#btn_add").attr("disabled", true);
                }
            },
            error: function () {
                layer.msg("打卡失败,请稍后重试", {icon: 2});
            }
        });
    });

    <!--判断用户今天是否已经打卡 -->
    function isSign() {
        var url = "${pageContext.request.contextPath}/sign/decideSign";
        var id =${sessionScope.user_session.id};
        var nowDate = getDate();
        var param = {
            uid: id,
            nowDate: nowDate
        }
        $.ajax({
            url: url,
            type: 'post',
            data: param,
            success: function (res) {
                if (res.code == 1) {
                    $("#btn_add").html("已打卡");
                    $("#btn_add").attr("disabled", true);
                }
            }
        });
    }

    class BstpTable {
        constructor(obj) {
            this.obj = obj;
        }

        inint() {
            //---先销毁表格 ---
            this.obj.bootstrapTable('destroy');
            //---初始化表格,动态从服务器加载数据---
            this.obj.bootstrapTable({
                //【发出请求的基础信息】
                url: '${pageContext.request.contextPath}/sign/loadSign',
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//必须有
                //【查询设置】
                /* queryParamsType的默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                                  设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber */
                queryParamsType: '',
                queryParams: function queryParams(params) {
                    //自定义传递的参数
                    var param = {
                        pageNum: params.pageNumber,
                        pageSize: params.pageSize,
                        startDate: $("#start").val(),
                        endDate: $("#end").val()
                    };
                    return param;
                },

                //【其它设置】
                locale: 'zh-CN',//中文支持
                pagination: true,//是否开启分页（*）
                striped: true,
                pageNumber: 1,//初始化加载第一页，默认第一页
                pageSize: 5,//每页的记录行数（*）
                pageList: [5, 10, 15],//可供选择的每页的行数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                showRefresh: true,//刷新按钮
                showToggle: true,//卡片视图
                toolbar: '#toolbar',//工具栏

                //【样式设置】
                height: 384,
                //按需求设置不同的样式：5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];

                //【设置列】
                columns: [
                    {
                        title: '全选',
                        field: 'select',
                        //复选框
                        checkbox: true,
                        width: 25,
                        align: 'center',
                        valign: 'middle'
                    },
                    {field: 'user.username', title: '员工姓名'},
                    {field: 'createTime', title: '签到日期'},
                ]
            })
        }
    }

    //初始化表格
    var bstpTable = new BstpTable($("table"));
    bstpTable.inint({})

    //查询按钮的逻辑
    $("#search_btn").click(function () {
        $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/sign/loadSign'})
    })

    //查看图表的逻辑
    $("#seeChart").click(function () {
        window.location.href = "${pageContext.request.contextPath}/sign/showChart";
    })

</script>
</body>
</html>