<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title></title>
<link href="/assets/css/bootstrap.min.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/assets/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="/assets/css/ace.min.css" />
<link type="text/css" rel="stylesheet" href="/assets/css/ace-skins.min.css" />
<link type="text/css" rel="stylesheet" href="/assets/css/ace-responsive.min.css" />
<link type="text/css" rel="stylesheet" href="/plugins/zTree/2.6/zTreeStyle.css"/>
<style type="text/css">
footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
</style>
</head>
<body>
	<div id="zhongxin">
		<ul id="tree" class="tree" style="overflow:auto;"></ul>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="/assets/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>

	<script type="text/javascript" src="/assets/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="/plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		var zTree;
		$(document).ready(function () {
			var setting = {
				showLine: true,
				checkable: true
			};
			zTree = $("#tree").zTree(setting, eval('${zTreeNodes}'));
		});

		function save() {
			var nodes = zTree.getCheckedNodes();
			var tmpNode;
			var ids = "";
			for (var i = 0; i < nodes.length; i++) {
				tmpNode = nodes[i];
				if (i != nodes.length - 1) {
					ids += tmpNode.id + ",";
				} else {
					ids += tmpNode.id;
				}
			}

			var postData = {"ROLE_ID": "${roleId}", "menuIds": ids};
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.post("${ctx}role/auth/save.do", postData, function (data) {
				//if(data && data=="success"){
				top.Dialog.close();
				//}
			});
		}
	</script>
	<footer>
	<div style="width: 100%;" class="center">
		<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
		<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
	</div>
	</footer>
</body>
</html>