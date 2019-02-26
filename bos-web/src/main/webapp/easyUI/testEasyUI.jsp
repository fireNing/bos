<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<div title="测试系统" data-options="region:'north'" style="height:90px">北部区域</div>
	<div title="测试系统菜单" data-options="region:'west',split:true" title="West" style="width:200px;">
		<div class="easyui-accordion" data-options="fit:true">
			<div data-options="iconCls:'icon-cut'" title="面板一">
				<a id="but1" class="easyui-linkbutton">添加一个选项卡</a>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，为上面的按钮绑定事件
						$("#but1").click(function(){
							//判断“系统管理”选项卡是否存在
							var e = $("#mytabs").tabs("exists","系统管理");
							if(e){
								//已经存在，选中就可以
								$("#mytabs").tabs("select","系统管理");
							}else{
								//调用tabs对象的add方法动态添加一个选项卡
								$("#mytabs").tabs("add",{
									title:'系统管理',
									iconCls:'icon-edit',
									closable:true,
									content:'<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'
								});
							}
						});
					});
				</script>
			</div>
			<div title="面板二">
				<ul id="testztree" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						var setting= {
								data: {
									simpleData: {
										enable: true
									}
										},
								callback: {
									onClick: function(event, treeId, treeNode){
											var exist = $("#mytabs").tabs("exists",treeNode.name);
											if(exist){
												$("#mytabs").tabs("select",treeNode.name);
											}else{
												var nul = treeNode.page;
												if(nul !=undefined){
													$("#mytabs").tabs("add",{
														title:treeNode.name,
														closable:true,
														content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
													});
												}
											}
									}
								}
								
						};
						$.post(
							"${pageContext.request.contextPath}/json/menu.json"	,
							function(data){
								$.fn.zTree.init($("#testztree"), setting, data);
							},
							"json"
						);
					});
				</script>
			</div>
			<div title="面板三">
				xxxx
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
	<!-- 制作一个tabs选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			
		</div></div>
	<div data-options="region:'east',split:true" title="East" style="width:150px;">东部区域</div>
	<div data-options="region:'south',split:true" style="height:70px;">南部区域</div>
</body>
</html>