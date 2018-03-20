<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="collectionContent" style="display: none" class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><i class="fa fa-home"></i>主页</li>
						<li class="active"><span>收藏</span></li>
					</ol>
					<h2>收藏</h2>
				</div>
			</div>
			<!-- 收藏夹表 -->
			<div id="collectionTab" class="row">
				<div class="col-lg-12">
					<div class="main-box clearfix">
						<header class="main-box-header clearfix">
						    <div class="buttons-responsive clearfix">
						        <div class="filter-block pull-left" style="width:60%">
						            <div class="pull-left">
								         <button id="collectionBtn_download" type="button"
									             class="btn btn-primary">
									     		<i class="fa fa-download"></i>
									              	下&nbsp;&nbsp;载</button>
							        </div>
							        <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							        <div id="collectionDiv_cancel" class="pull-left">
								         <button id="collectionBtn_cancel" type="button"
									             class="btn btn-danger">
									            <i class="fa fa-reply"></i>
									             	取消收藏</button>
							        </div>
						        </div>
						        <div class="filter-block pull-right">
								    <div class="form-group pull-left">
									     <input id="collectFileSearch" type="text" class="form-control" placeholder="搜索...">
									     <i class="fa fa-search search-icon"></i>
								    </div>
								    <a id="collectRefreshTable" class="btn btn-primary pull-right">
									    <i class="fa fa-refresh fa-lg"></i> 刷新
								    </a>
							    </div>
						    </div>							
						</header>
						<div class="main-box-body clearfix" style="margin-top: -10px">
							<div id="toolbarCollection">
								<ol class="breadcrumb" id="myCollectContentOL"></ol>
							</div>						
							<div class="table-responsive clearfix">
								<table class="table table-striped table-hover" style="margin-top: -10px" 
									id="tb_collectfileInfo"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>