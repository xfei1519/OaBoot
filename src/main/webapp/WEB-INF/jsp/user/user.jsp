<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>人事管理系统</title>
<!-- jquery -->
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<!-- BootstrapTable插件 -->
<link href="${pageContext.request.contextPath}/css/bootstrap-table.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table-locale-all.min.js"></script>

<!--layer -->
<link href="${pageContext.request.contextPath}/js/layer/theme/default/layer.css" rel="stylesheet">

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
	<div class="container" style="margin-top: 5px">
		<div class="row">
			<!--!查询区 -->
			<div class="col-sm-12">
				<div class="panel-heading" style="background-color: #428bca; color: white">查询条件</div>
				<div class="panel-body form-group" style="margin-bottom: 0px;">
					<label class="col-sm-2 control-label" style="text-align: right; margin-top: 5px">用户名：</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" name="username" id="search_name" />
					</div>
					<label class="col-sm-2 control-label" style="text-align: right; margin-top: 5px">用户状态：</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" name="status" id="search_status" />
					</div>
					<div class="col-sm-2">
						<button class="btn btn-primary" class="search" type="button"
							id="search_btn" style="background-color: #0767C8">查询</button>
					</div>
				</div>
			</div>

			<!-- 工具栏:新增,修改,批量删除 -->
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
	
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	
	<script type="text/javascript">

    //获取选中的所有行的id
    function getIds() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id;
        })
    }

    //新增用户
    $("#btn_add").click(function () {
        window.location.href = "${pageContext.request.contextPath}/user/addUser?flag=1";
    })

    //批量删除的方法
    $("#btn_delete").click(function () {
    	//获取选中的所有id
        var ids = getIds();
        if (ids.length < 1) {
            layer.alert('请选择至少一个用户!', {
                skin: 'layui-layer-lan'
                , closeBtn: 0
            })
        }else {
            layer.confirm('确定删除这些用户吗?？', {
                btn: ['是的', '按错了'] //可以有无限个按钮
            }, function () {
                //确定按钮的回调函数--->执行用户删除操作
                var url = "${pageContext.request.contextPath}/user/removeUser";
                //要传递的参数
                var params = {
                    flag: 2,
                    ids: ids
                }
                
                //进行ajax请求
                $.ajax({
                    url: url,
                    data: params,
                    type: "post",
                    //dataType: "json",注意:此处需要去掉json类型限制,否则返回的数据如果不是json格式,会进入到error回调函数中!
                    success: function (data) {
                        if (data == "success") {
                            setTimeout(function () {
                            	//会加载返回的json格式数据
                                //window.location.href = "${pageContext.request.contextPath}/user/getUsers";
                            	 $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/user/getUsers'});
                            }, 500);
                            //关闭所有dialog
                            layer.closeAll('dialog');
                        }
                    },
                    error: function () {
                        $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/user/getUsers'});
                        //关闭所有dialog
                        layer.closeAll('dialog');
                    }
                })
            })
        }
    })

    //删除用户
    function deleteone(id) {
        layer.confirm('确定删除这个用户吗?', {
            btn: ['是的', '按错了'] //可以无限个按钮
        }, function () {
            //确定按钮的回调函数---->发起删除请求
            window.location.href = "${pageContext.request.contextPath}/user/removeUser?flag=1&id=" + id;
        });
    }

    //定义一个Bootstrap里的表格操作类
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
                url: '${pageContext.request.contextPath}/user/getUsers',
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//必须有
                //【查询设置】
                /* queryParamsType的默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort;
                                               设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber */
                queryParamsType: '',
                queryParams: function queryParams(params) {
                    //自定义传递的参数
                    var param = {
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        username: $("#search_name").val(),
                        status: $("#search_status").val()
                    };
                    return param;
                },

                //【其它设置】
                locale: 'zh-CN',//支持中文
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
                height: 384,//表格高度
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
                    //注意:field后面的属性名称,必须与JavaBean中的属性名称一致!
                    {field: 'loginName', title: '登录名',align: 'center',valign: 'middle'},
                    {field: 'password', title: '密码',align: 'center',valign: 'middle'},
                    {field: 'username', title: '用户名',align: 'center',valign: 'middle'},
                    {field: 'status', title: '状态',align: 'center',valign: 'middle'},
                    {field: 'createDate', title: '创建时间',
                        formatter:function (value) {
                          return String(value).substr(0,10);
                        }
                    	,align: 'center',valign: 'middle',
                    },
                    {
                        field: 'tool', title: '操作', align: 'center',valign: 'middle',
                        formatter: function (value, row, index) {
                            var element =
                                "<a  data-id='" + row.id + "' href='${pageContext.request.contextPath}/user/updateUser?flag=1&id=" + row.id + "' class='btn btn-info btn-sm'>编辑</a> &nbsp;" +
                                "<button  data-id='" + row.id + "' href='#' onclick='deleteone(" + row.id + ")' class='btn btn-danger btn-sm'>删除</button> ";
                            return element;
                        }
                    }
                ]
            })
        }
    }

    //默认就进行一次查询
    var bstpTable = new BstpTable($("table"));
    bstpTable.init({});

    //查询按钮的处理逻辑
    $("#search_btn").click(function () {
        $("#table").bootstrapTable("refresh", {url: '${pageContext.request.contextPath}/user/getUsers'});
    });

</script>
</body>
</html>