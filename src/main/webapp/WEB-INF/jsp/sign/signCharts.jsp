<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>人事管理系统</title>
    <!--引入echarts -->

    <link href="../layui/css/layui.css" rel="stylesheet"/>
    <link href="../css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../laydate/theme/default/laydate.css" rel="stylesheet"/>

    <style type="text/css">

        .mainDiv{
            margin-top: 6px;
            margin-left: 10px;
        }
        .panel{
            margin-right: 100px;
        }
        #main{
            margin-left: 340px;
        }
        .layui-form{
            margin-left: 400px;
            margin-top: 6px;
        }
    </style>
</head>
<body onload="ec()">
<div class="mainDiv">
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

                <button class="btn btn-generate" style="background-color: #0767C8;color: white;margin-left: 6px" type="button"
                        id="generateCharts">生成图表
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>


<script src="../js/jquery-3.1.1.min.js" charset="UTF-8"></script>
<script src="../js/echarts.js" charset="UTF-8"></script>
<script src="../js/layer/layer.js" charset="UTF-8"></script>
<script src="../laydate/laydate.js" charset="UTF-8"></script>
<script type="text/javascript">

    var begin;
    $("#generateCharts").click(function () {
        if (begin !=''){
            generate(begin);
        }else{
            generate('1900-1-1');
        }
    });

    var myChart = echarts.init(document.getElementById('main'));
    function ec() {
        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            title: {
                text: '签到图表'
            },
            tooltip:{},
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data: ['人数']
            },
            xAxis: {
                data: []
            },
            yAxis: {splitArea: {show: true}},
            series: [{
                name: '人数',
                type: 'bar',
                data: []
            }]
        });
        generate('1900-1-1');
    }

    var days = [];    //日期（实际用来盛放X轴坐标值）
    var nums = [];    //人数（实际用来盛放Y坐标值）


    function generate(begin) {
        $.ajax({
            type: "post",
            async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "${pageContext.request.contextPath}/sign/loadSignChart?beginDay="+begin,  //接口
            dataType: 'json',// 返回数据形式为json
            success: function (result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (result) {
                    days=[];
                    nums=[];
                    for (var i = 0; i < result.length; i++) {
                        days.push(result[i].day);    //挨个取出日期并填入类别数组
                    }
                    for (var i = 0; i < result.length; i++) {
                        nums.push(result[i].num);    //挨个取出人数并填入销量数组
                    }
                    myChart.hideLoading();    //隐藏加载动画
                    myChart.setOption({        //加载数据图表
                        xAxis: {
                            data: days
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '人数',
                            data: nums
                        }]
                    });
                }
            },
            error: function () {
                //请求失败时执行该函数
                layer.msg("请求数据失败", {icon: 2});
                myChart.hideLoading();
            }
        });
    }
</script>
<script type="text/javascript">

    //获取当前日期并格式化
    function getDate() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var date = date.getDate();
        return year + "-" + month + "-" + date;
    }
    laydate.render({
        elem: "#start",
        max: getDate(),
        done:function (value) {
            begin = value;
        }
    });
</script>
</body>
</html>