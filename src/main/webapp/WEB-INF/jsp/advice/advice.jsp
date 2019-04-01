<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>人事管理系统--公告模块</title>
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
    
    <style type="text/css">
        .panel {
            margin-left: -48px;
            width: 1145px;
        }

        .col-sm-12 {
            margin-left: -60px;
        }

        thead {
            background: #428bca;
            color: white;
        }
    </style>
</head>
<body>
<!-- 模态框（Modal）,实现预览公告 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="align:center">
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body" id="content"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<div class="container" style="margin-top:5px">
    <div class="row">
        <!--!查询区 -->
        <div class="panel panel-default">
            <div class="panel-heading" style="background-color:#428bca;color: white">
               	 查询条件
            </div>
            <div class="panel-body form-group" style="margin-bottom:0px;">
                <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px;width: 100px;">公告标题：</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="atitle" id="search_name"/>
                </div>
                <label class="col-sm-2 control-label" style="text-align: right; margin-top:5px">公告内容：</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="acontent" id="search_content"/>
                </div>
                <div class="col-sm-1 col-sm-offset-2">
                    <button class="btn btn-primary" class="search" type="button" id="search_btn"
                         style="background-color: #0767C8 ">查询
                    </button>
                </div>
            </div>
        </div>

		<!-- 工具栏 -->
        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-success">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-primary" style="margin-left: 0px">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-danger" style="margin-left: 0px">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量删除
            </button>
        </div>
        
        <!-- 表格 -->
        <div class="col-sm-12">
            <table class="table table-striped table-bordered table-hover" id="table"></table>
        </div>
    </div>
</div>

<script src="../js/layer/layer.js" charset="UTF-8"></script>

<script type="text/javascript">

    //获取选中的所有行的id
    function getIds() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id;
        })
    }

    //新增公告
    $("#btn_add").click(function () {
        window.location.href = "${pageContext.request.contextPath}/advice/addAdvice?flag=1";
    })

    //批量删除的方法
    $("#btn_delete").click(function () {
        var ids = getIds();
        if (ids.length < 1) {
            layer.alert('请选择至少一条公告!', {
                skin: 'layui-layer-lan'
                , closeBtn: 0
            })
        } else {
            layer.confirm('确定删除这些公告吗?？', {
                btn: ['是的', '按错了'] //可以无限个按钮
            }, function () {
                //确定按钮的回调函数
                var url = "${pageContext.request.contextPath}/advice/removeAdvice";
                var params = {
                    flag: 2,
                    ids: ids
                }
                $.ajax({
                    url: url,
                    data: params,
                    type: "post",
                    success:function (res) {
                    	//alert(res);
                        if (res == "success") {
                            setTimeout(function () {
                            	//会加载返回的json格式数据
                            	 $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/advice/loadAdvice'});
                            }, 500);
                            //关闭所有dialog
                            layer.closeAll('dialog');
                        }
                    },
                    error: function () {
                        $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/advice/loadAdvice'});
                        layer.closeAll("dialog");
                    }
                })
            })
        }
    })

    //删除公告
    function deleteone(id) {
        layer.confirm('确定删除这条公告吗?？', {
            btn: ['是的', '按错了'] //可以无限个按钮
        }, function () {
            //确定按钮的回调函数
            window.location.href = "${pageContext.request.contextPath}/advice/removeAdvice?flag=1&id=" + id;
        })
    }

    //实现预览功能
    function pre(nid) {
        var url="${pageContext.request.contextPath}/advice/preAdvice";
        //console.log("nid="+nid);
        
        var param={
            id:nid,
        }
        
        $.ajax({
            url:url,
            data:param,
            type:'post',
            success:function (data) {
            	//先进行json解析,将返回的json格式字符串转为json对象
            	//var obj=$.parseJSON(data);
            	//console.log(obj);
            	console.log(data.title);
            	//console.log(data.content);
            	//模态框加载数据
                $("#myModalLabel").html(data.title);
                $("#content").html(data.content);
            },
            error:function () {
                layer.alert("请求失败");
            }
        });
    }

    class BstpTable {
        constructor(obj) {
            this.obj = obj;
        }

        init() {
            //---先销毁表格 ---
            this.obj.bootstrapTable('destroy');
            //---初始化表格,动态从服务器加载数据---
            this.obj.bootstrapTable({
                //【发出请求的基础信息】
                url: '${pageContext.request.contextPath}/advice/loadAdvice',
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//必须有
                //【查询设置】
                /* queryParamsType的默认值为'limit',在默认情况下 传给服务端的参数为:offset,limit,sort;
                                               设置为'',在这种情况下传给服务器的参数为:pageSize,pageNumber */
                queryParamsType: '',
                queryParams: function queryParams(params) {
                    //自定义传递的参数
                    var param = {
                        pageNum: params.pageNumber,
                        pageSize: params.pageSize,
                        title: $("#search_name").val(),
                        content: $("#search_content").val()
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
                    {field: 'title', title: '公告标题'},
                    {field: 'content', title: '公告内容'},
                    {field: 'createDate', title: '创建时间'},
                    {field: 'user.username', title: '公告人'},
                    {
                        field: 'tool', title: '操作', align: 'center',
                        formatter: function (value, row, index) {
                            var element =
                                "<a  data-id='" + row.id + "' href='${pageContext.request.contextPath}/advice/updateAdvice?flag=1&id=" + row.id + "' class='btn btn-info btn-sm'>编辑</a> &nbsp;" +
                                "<button  data-id='" + row.id + "' onclick='deleteone(" + row.id + ")' class='btn btn-danger btn-sm'>删除</button> ";
                            return element;
                        }
                    },
                    {
                        field: 'preview', title: '预览', align: 'center',
                        formatter: function (value, row, index) {
                            var element = "<button data-toggle=\"modal\" data-target=\"#myModal\" class='btn btn-sm btn-success' id='test1' onclick='pre("+row.id+")'>预览</button>";
                            return element;
                        }
                    }
                ]
            })
        }
    }

    var bstpTable = new BstpTable($("table"));
    bstpTable.init({})

    //查询按钮的逻辑
    $("#search_btn").click(function () {
        $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/advice/loadAdvice'});
    });

</script>
</body>
</html>