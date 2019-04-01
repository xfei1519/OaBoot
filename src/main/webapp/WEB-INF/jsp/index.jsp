<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>人事管理系统</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo.ico">

<!-- css样式 -->
<link href="${pageContext.request.contextPath}/script/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<!-- 全局js -->
<script src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/script/bootstrap/js/bootstrap.min.js"></script>

<!--导航-->
<script src="${pageContext.request.contextPath}/script/metisMenu/jquery.metisMenu.js"></script>

<!--滚动条-->
<script src="${pageContext.request.contextPath}/script/slimscroll/jquery.slimscroll.min.js"></script>

<!--浮层-->
<script src="${pageContext.request.contextPath}/script/layer/layer.min.js"></script>

<!--进度条 -->
<script src="${pageContext.request.contextPath}/script/pace/pace.min.js"></script>

<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/script/hplus.js"></script>
<script src="${pageContext.request.contextPath}/script/contabs.js"></script>

</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div class="dropdown profile-element">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
							<span class="clear"> 
								<span class="block m-t-xs">
									<!-- 获取session域中存储的用户名称 -->
									<strong class="font-bold">${sessionScope.user_session.username}</strong>
								</span>
								<span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
							</span>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<!-- 修改密码,功能为实现! -->
								<li><a class="J_menuItem" href="#">修改密码</a></li>
								<li class="divider"></li>
								<!-- 退出登陆 -->
								<li><a href="loginOut">安全退出</a></li>
							</ul>
						</div>
					</li>
					<li>
					<!-- 注意:此处通过href属性,默认加载主页!,可以写${pageContext.request.contextPath}/home,也可以直接home -->
						<a class="J_menuItem" href="home" data-index="0"> 
							<i class="fa fa-home"></i> <span class="nav-label">主页</span>
						</a>
					</li>
					<li>
						<a href="#"> 
							<i class="fa fa-user"></i> 
							<span class="nav-label">用户管理</span> 
							<span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="selectUser">用户查询</a></li>
							<li><a class="J_menuItem" href="addUser?flag=1">添加用户</a></li>
						</ul>
					</li>
					<li>
						<a href="#"> 
							<i class="fa fa-home"></i> 
							<span class="nav-label">部门管理</span> <span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/dept/selectDept">部门查询</a></li>
							<li><a class="J_menuItem" href="/dept/addDept?flag=1">添加部门</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-ra"></i> 
							<span class="nav-label">职位管理</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/job/selectJob">职位查询</a></li>
							<li><a class="J_menuItem" href="/job/addJob?flag=1">添加职位</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-users"></i> 
							<span class="nav-label">员工管理</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/employee/selectEmployee">员工查询</a></li>
							<li><a class="J_menuItem" href="/employee/addEmployee?flag=1">添加员工</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-tv"></i> 
							<span class="nav-label">公告管理</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/advice/selectAdvice">公告查询</a></li>
							<li><a class="J_menuItem" href="/advice/addAdvice?flag=1">添加公告</a></li>
							<li><a class="J_menuItem" href="/advice/addEmail?flag=1">发送邮件</a></li>
							<li><a class="J_menuItem" href="/advice/addMsg?flag=1">发送短信</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-cloud-download"></i> 
							<span class="nav-label">下载中心</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/doc/selectDocument">文档查询</a></li>
							<li><a class="J_menuItem" href="/doc/addDocument">上传文档</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-sign-in"></i> 
							<span class="nav-label">签到管理</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/sign/selectSign">签到查询</a></li>
							<li><a class="J_menuItem" href="/sign/showChart">签到图表</a></li>
						</ul>
					</li>
					<li>
						<a href="#">
							<i class="fa fa fa-bar-chart-o"></i> 
							<span class="nav-label">报表管理</span><span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/poi/createPoi">报表生成</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
					</div>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<!-- 点击"首页"按钮,加载首页面,index?method=home -->
						<a href="javascript:;" class="active J_menuTab" data-id="index?method=home">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作
						<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="loginOut" class="roll-nav roll-right J_tabExit">
					<i class="fa fa fa-sign-out"></i>退出
				</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<!-- iframe框架里的src属性指明要请求加载的页面时home,对应着/WEB-INF/jsp/home.jsp页面 -->
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="home" frameborder="0" data-id="index?method=home" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2017-2099 <a href="#" target="_blank">人事管理</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->

		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">
				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。
							</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu"> 
											<label class="onoffswitch-label" for="collapsemenu"> 
											<span class="onoffswitch-inner"></span> 
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar"> 
											<label class="onoffswitch-label" for="fixednavbar"> 
											<span class="onoffswitch-inner"></span> 
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定宽度 </span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout"> 
										<label class="onoffswitch-label" for="boxedlayout"> 
											<span class="onoffswitch-inner"></span> 
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">默认皮肤</a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">蓝色主题</a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">黄色主题</a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--右侧边栏结束-->
	</div>

</body>
</html>