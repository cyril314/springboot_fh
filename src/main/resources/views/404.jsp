<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>404</title>
	<%@ include file="/views/include/top.jsp"%>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="hr hr-18 dotted hr-double"></div>
				<div class="row">
					<div class="col-xs-12">
						<div class="error-container">
							<div class="well">
								<h1 class="grey lighter smaller">
									<span class="blue bigger-125"><i class="icon-sitemap"></i> 404</span> 没有找到此页面
								</h1>
								<hr />
								<div>
									<div class="space"></div>
									<h4 class="smaller">检查一下可能性:</h4>
									<ul>
										<li><i class="icon-hand-right blue"></i> 检查请求链接是不是有误</li>
										<li><i class="icon-hand-right blue"></i> 检查处理类代码是不是有误</li>
										<li><i class="icon-hand-right blue"></i> 检查环境配置是不是有误</li>
										<li><i class="icon-hand-right blue"></i> 检查处理类视图映射路径</li>
									</ul>
								</div>
								<hr />
								<div class="space"></div>
								<div class="row-fluid">
									<div id="zhongxin"></div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
</div>
<!-- /.main-container -->
<!-- basic scripts -->
<%@ include file="/views/include/foot.jsp"%>
<!-- 页面底部js¨ -->
<script src="/assets/ace/js/bootstrap.js"></script>
<!-- ace scripts -->
<script src="/assets/ace/js/ace/ace.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">$(top.hangge());</script>
</body>
</html>