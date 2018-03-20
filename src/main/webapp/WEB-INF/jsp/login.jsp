<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>智云私有云盘系统</title>
<link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="<%=basePath%>css/login.css">
</head>
<body>
<div class="top-content">
	<nav style="" class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container-fluid">
			<p class="navbar-text text-center" style="color: #ffffff; font-size: 22px;">智云私有云盘系统</p>
		</div>
	</nav>
    <div class="inner-bg">
        <div class="container text-center">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1>
                    </h1>
                    <div class="description">
                        <p></p>
                    </div>
                </div>
            </div>
            <div class="row clearfix text-center">
                <div class="form-box text-center">
                    <div class="form-top">
                        <h4 class="text-center">云盘登录</h4>
                    </div>
                    <div class="form-content">
                        <form id="loginForm" role="form" action="init/main.do" method="post" class="login-form">
                            <div class="form-group" id="accountDiv">
                                <div>
                                    <div class="input-group input-interval">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </span>
                                        <input type="text" name="userLoginID" id="userLoginID" class="form-control input-size" placeholder="用户名">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" id="pwdDiv">
                                <div>
                                    <div class="input-group input-interval">
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-lock"></span>
                                        </span>
                                        <input type="password"  name="password" id="password" class="form-control input-size" placeholder="密码">
                                    </div>
                                </div>
                            </div>
                            <div class="hidden text-center" id="errMsg">
                                <span class="glyphicon glyphicon-exclamation-sign"></span>
                            </div>
                            <div>
                            	<button id="btn_login" type="button" class="btn btn-info">登录</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
	<script src="<%=basePath %>jquery/jquery-1.11.1.js"></script>
	<script src="<%=basePath %>bootstrap/js/bootstrap.js"></script>
	<script src="<%=basePath %>js/mainMenuController.js"></script>
	<script src="<%=basePath %>js/commonController.js"></script>
</body>
</html>