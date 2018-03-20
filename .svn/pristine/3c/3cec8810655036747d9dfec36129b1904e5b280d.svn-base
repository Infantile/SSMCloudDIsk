<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>智云网盘</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/bootstrap-table.css"/>    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/fileinput.min.css"/> 
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.contextMenu.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/nanoscroller.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/main.css"/>
    <link type="image/x-icon" href="<%=basePath%>img/cloud_icon.png" rel="shortcut icon"/>
    <link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400' rel='stylesheet'
          type='text/css'/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/modal.css"/>
    
</head>
<body>
<div id="theme-wrapper">
    <header class="navbar" id="header-navbar">
        <div class="container">
            <a href="#" id="logo" class="navbar-brand">
                <%-- <img src="<%=basePath%>img/logo.png" alt="" class="normal-logo logo-white"/> --%>
                <font>&nbsp;&nbsp;智&nbsp;&nbsp;云&nbsp;&nbsp;网&nbsp;&nbsp;盘</font>
                <img src="<%=basePath%>img/logo-black.png" alt="" class="normal-logo logo-black"/>
                <img src="<%=basePath%>img/logo-small.png" alt="" class="small-logo hidden-xs hidden-sm hidden"/>
            </a>
            <div class="clearfix">
                <button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="fa fa-bars"></span>
                </button>
                <div class="nav-no-collapse navbar-left pull-left hidden-sm hidden-xs">
                    <ul class="nav navbar-nav pull-left">
                        <li>
                            <a class="btn" id="make-small-nav">
                                <i class="fa fa-bars"></i>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="nav-no-collapse pull-right" id="header-nav">
                    <ul class="nav navbar-nav pull-right">
                        <li class="dropdown profile-dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <img src="<%=basePath%>img/myHead.png" alt=""/>
                                <span class="hidden-xs"><%=request.getSession().getAttribute("userName")%></span> <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li id=updatePwd><a href="#"><i class="fa fa-pencil-square-o"></i>修改密码</a></li>
                                <!-- <li><a href="#"><i class="fa fa-pencil-square"></i>修改用户名</a></li> -->
                                <li id=logOut><a href="#"><i class="fa fa-sign-out"></i>退出</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <div id="page-wrapper" class="container">
        <div class="row">
            <div id="nav-col">
                <section id="col-left" class="col-left-nano">
                    <div id="col-left-inner" class="col-left-nano-content">
                        <div id="user-left-box" class="clearfix hidden-sm hidden-xs">
                            <img alt="" src="<%=basePath%>img/myHead.png"/>
                            <div class="user-box">
                                <span class="name">
                                    <p class="text-center">欢迎<br/><%=request.getSession().getAttribute("userName")%></p>
                                </span>
                            </div>
                            <div class="capacity">
                                <div id="bar" class="progress">
                                    <div id="barId" class="progress-bar progress-bar-success" role="progressbar">
                                         
                                    </div> 
                                    <font id="info"></font>  
                                </div>
                                <div class="useSpace">
                                    <label id="usedSize"></label>/<label id="totalSize"></label>
                                    <div style="float:right; margin-right:10px;"><a href="#" style="color:#7BB1FD">扩容</a></div>
                                </div>
                                <div class="blankSpace"></div>
                               
                            </div>
                        </div>
                        <div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">
                            <ul class="nav nav-pills nav-stacked">
                                <li class="open">
                                    <a id="myFile-content" href="#" class="dropdown-toggle">
                                        <i class="fa fa-folder"></i>
                                        <span>文件</span>
                                    </a>
                                    <div style="width: 100%;height: 1px;background-color: #F9F9F9"></div>
                                    <ul class="submenu" style="display: block;">
                                        <li id="myFile">
                                        	<i class="fa fa-folder-open"></i>
                                            <a href="#">我的文件</a>
                                        </li>
                                        <li id="myShare">
                                       		<i class="fa fa-share-alt"></i>
                                            <a href="#">我的共享</a>
                                        </li>
                                        <li id="commonShare">
                                        	<i class="fa fa-share-square-o"></i>
                                            <a href="#">公共共享</a>
                                        </li>
                                    </ul>
                                </li>
                                <li id="groupManage">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="fa fa-users fa-lg"></i>
                                        <span>群组管理</span>
                                    </a>
                                    <div style="width: 100%;height: 1px;background-color: #F9F9F9"></div>                                    
                                </li>
                                <li id="collection">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="fa fa-star fa-lg"></i>
                                        <span>收藏夹</span>
                                    </a>
                                    <div style="width: 100%;height: 1px;background-color: #F9F9F9"></div>
                                </li>
                                <li id="recycle">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="fa fa-recycle fa-lg" aria-hidden="true"></i>
                                        <span>回收站</span>
                                    </a>
                                    <div style="width: 100%;height: 1px;background-color: #F9F9F9"></div>
                              	</li>
                        	</ul>
                        </div>
                    </div>
                </section>
            </div>
            <div id="content-wrapper">                 
					<%@include file="../include/myFileContent.jsp"%>
					<%@include file="../include/myShareContent.jsp"%>
					<%@include file="../include/publicShareContent.jsp"%>
                    <%@include file="../include/groupManageContent.jsp"%>
                   	<%@include file="../include/collectionContent.jsp"%>
                	<%@include file="../include/recycleContent.jsp"%>
				<footer id="footer-bar" class="row">
                	<p id="footer-copyright" class="col-xs-12">
                        &copy;<a href="" target="_blank"></a>武汉智云方达信息科技有限公司  版权所有
					</p>
				</footer>
            </div>
		</div>
	</div>
</div>
<div id="config-tool" class="closed">
    <a id="config-tool-cog">
        <i class="fa fa-cog"></i>
    </a>
    <div id="config-tool-options">
        <h4>布局选项</h4>
        <ul>
            <li>
                <div class="checkbox-nice">
                    <input type="checkbox" id="config-boxed-layout"/>
                    <label for="config-boxed-layout" style="font-size: 14px">箱式布局</label>
                </div>
            </li>
        </ul>
        <br/>
        <h4>主色调</h4>
        <ul id="skin-colors" class="clearfix">
            <li>
                <a class="skin-changer" data-skin="" data-toggle="tooltip" title="Default"
                   style="background-color: #34495e;">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-white" data-toggle="tooltip" title="White/Green"
                   style="background-color: #2ecc71;">
                </a>
            </li>
            <li>
                <a class="skin-changer blue-gradient" data-skin="theme-blue-gradient" data-toggle="tooltip"
                   title="Gradient">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-turquoise" data-toggle="tooltip" title="Green Sea"
                   style="background-color: #1abc9c;">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-amethyst" data-toggle="tooltip" title="Amethyst"
                   style="background-color: #9b59b6;">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-blue" data-toggle="tooltip" title="Blue"
                   style="background-color: #2980b9;">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-red" data-toggle="tooltip" title="Red"
                   style="background-color: #e74c3c;">
                </a>
            </li>
            <li>
                <a class="skin-changer" data-skin="theme-whbl" data-toggle="tooltip" title="White/Blue"
                   style="background-color: #3498db;">
                </a>
            </li>
        </ul>
    </div>
</div>
<%@include file="../include/copyOrMoveToModal.jsp"%>
<%@include file="../include/multipleUploadFileModal.jsp"%>
<%@include file="../include/updatePassModal.jsp"%>
<%@include file="../include/createGroupModal.jsp"%>
<%@include file="../include/changeGroupInfoModal.jsp"%>
<%@include file="../include/creatNewFolderModal.jsp"%>
<%@include file="../include/shareFileModal.jsp"%>
<%@include file="../include/groupInfoModal.jsp"%>
<%@include file="../include/updateFileNameModal.jsp"%>
<%@include file="../include/changeGroupNameModal.jsp"%>
<%@include file="../include/downLoadFileModal.jsp"%>
<script src="<%=basePath%>js/demo-skin-changer.js"></script>
<script src="<%=basePath%>jquery/jquery-1.11.1.js"></script>
<script src="<%=basePath%>js/jquery-ui.js"></script>
<script src="<%=basePath%>jquery/jquery.contextMenu.js"></script>
<script src="<%=basePath%>jquery/jquery.nanoscroller.min.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap-table.js"></script>
<script src="<%=basePath%>bootstrap/js/bootstrap-table-zh-CN.js"></script>
<script src="<%=basePath%>bootstrap/js/bootbox.js"></script>
<script src="<%=basePath%>js/bootstrap-treeview-edit.js"></script>
<script src="<%=basePath%>bootstrap/js/fileinput.min.js"></script>
<script src="<%=basePath%>bootstrap/js/zh.js"></script>
<script src="<%=basePath%>js/demo.js"></script>
<script src="<%=basePath%>js/scripts.js"></script>
<script src="<%=basePath%>js/pace.min.js"></script>
<script src="<%=basePath%>js/shiftTableSelected.js"></script>
<script src="<%=basePath%>js/commonController.js"></script>
<script src="<%=basePath%>js/collectController.js"></script>
<script src="<%=basePath%>js/recycleController.js"></script>
<script src="<%=basePath%>js/tableInitController.js"></script>
<script src="<%=basePath%>js/myfileController.js"></script>
<script src="<%=basePath%>js/groupController.js"></script>
<script src="<%=basePath%>js/mainMenuController.js"></script>
<script src="<%=basePath%>js/publicSharedFileController.js"></script>
<script src="<%=basePath%>js/mySharedFileController.js"></script>
</body>
</html>