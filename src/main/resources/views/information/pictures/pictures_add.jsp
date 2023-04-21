<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/views/include/top.jsp"%>
<!-- webuploader上传插件css -->
<link rel="stylesheet" type="text/css" href="/plugins/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="/plugins/webuploader/style.css" />
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
		    <div id="wrapper">
		        <div id="container">
		            <!--头部，相册选择和格式选择-->
		            <div id="uploader">
		                <div class="queueList">
		                    <div id="dndArea" class="placeholder">
		                        <div id="filePicker"></div>
		                        <p>或将照片拖到这里，单次最多可选300张</p>
		                    </div>
		                </div>
		                <div class="statusBar" style="display:none;">
		                    <div class="progress">
		                        <span class="text">0%</span>
		                        <span class="percentage"></span>
		                    </div><div class="info"></div>
		                    <div class="btns">
		                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
			</div>
		</div>
	</div>
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="/views/include/foot.jsp"%>
	<!-- ace scripts -->
	<script src="/assets/ace/js/ace/ace.js"></script>
	<!-- webuploader上传插件js -->
   	<script type="text/javascript" src="/plugins/webuploader/webuploader.js"></script>
   	<script type="text/javascript" src="/plugins/webuploader/upload.js"></script>
	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>	