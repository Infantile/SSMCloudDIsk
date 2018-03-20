<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="min-width :700px">
	<div id="myFileContent" style="display: none;" class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><i class="fa fa-home"></i>主页</li>
						<li class="active"><span>我的文件</span></li>
					</ol>
					<h2>我的文件</h2>
				</div>
			</div>
			<!-- 文件数据表 -->
			<div id="myFileTab" class="row">
				<div class="col-lg-12">
					<div class="main-box clearfix">
						<header class="main-box-header clearfix">
						   <div class="buttons-responsive clearfix">
						       <div class="filter-block pull-left" style="width:60%">
						            <div class="pull-left">
								        <button id="btn_upload" type="button" class="btn btn-primary">
								        	<i class="fa fa-upload"></i>
									                            上&nbsp;&nbsp;传</button>
							        </div>
							        <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							        <div class="pull-left">
								         <button id="btn_download" type="button" class="btn btn-primary">
								         	<i class="fa fa-download"></i>
								 	                           下&nbsp;&nbsp;载</button>
							        </div>
							        <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							        <div class="pull-left">
								         <button id="btn_share" type="button" class="btn btn-primary">
								         	<i class="fa fa-share"></i>
									                           共&nbsp;&nbsp;享</button>
							       </div>
							       <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							       <div class="pull-left dropdown">
								        <button class="btn btn-primary dropdown-toggle" type="button"
									         id="btn_moreControl" data-toggle="dropdown"
									         aria-haspopup="true" aria-expanded="true">
									        <i class="fa fa-navicon"></i>
									                       更多操作<span class="caret" style="margin-left: 5px"></span>
								        </button>
								        <ul class="dropdown-menu" id="moreControl" role="menu"
									          aria-labelledby="moreControl" style="width:100px;">
									          <li><a href="#" id="delete">删除</a></li>
									          <li><a href="#" id="collect">收藏</a></li>
									          <li><a href="#" id="copyTo">复制到</a></li>
									          <li><a href="#" id="moveTo">移动到</a></li>
									          <li><a href="#" id="reName">重命名</a></li>
									          <li class="divider"></li>
									          <li><a href="#" id="creatNewFolder">新建文件夹</a></li>
								       </ul>
							      </div>
						        </div>
							   <div class="filter-block pull-right">
								  <div class="form-group pull-left">
									   <input id="myFileSearch" type="text" class="form-control" placeholder="搜索...">
									   <i class="fa fa-search search-icon"></i>
							      </div>
								  <a id="myFileRefreshTable" class="btn btn-primary pull-right">
								       <i class="fa fa-refresh fa-lg"></i> 刷新
								       </a>
							   </div>
						   </div>
						</header>
						<div class="main-box-body clearfix" style="margin-top: -10px">
							<div id="toolbarFile">
								<ol class="breadcrumb" id="myFileContentOL"></ol>
							</div>
							<div class="table-responsive clearfix" >
								<table class="table table-striped table-hover" style="margin-top: -10px" id="tb_fileInfo"></table>
							</div>
						</div>
						<input type="hidden" id="currentFolderId" value="">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>