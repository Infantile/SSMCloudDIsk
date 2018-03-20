<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="groupManageContent" style="display: none" class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><i class="fa fa-home"></i>主页</li>
						<li class="active"><span>群组管理</span></li>
					</ol>
					<h2>群组管理</h2>
				</div>
			</div>
			<!-- 群组管理表 -->
			<div id="groupManageTab" class="row">
				<div class="col-lg-12">
					<div class="main-box clearfix">
						<header class="main-box-header clearfix">
						    <div class="buttons-responsive clearfix">
						        <div class="filter-block pull-left" style="width:60%">
						             <div class="pull-left">
								          <button id="btn_new" type="button" class="btn btn-primary"
									               data-toggle="modal_create" data-target="#createGroupModal">
									      		<i class="fa fa-plus-circle"></i>
									               	新&nbsp;&nbsp;建
								          </button>
							         </div>
							         <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							         <div class="pull-left">
								          <button id="btn_changeGrpName" type="button" class="btn btn-primary"
									              data-toggle="" data-target="">
									            <i class="fa fa-pencil"></i>
									              	修&nbsp;&nbsp;改
								          </button>
							         </div>
							         <div class="pull-left" style="width: 3%;min-height: 1px"></div>
							         <div class="pull-left">
								          <button id="btn_delete" type="button" class="btn btn-danger"
									              data-toggle="" data-target="">
									            <i class="fa fa-times-circle"></i>
									              	删&nbsp;&nbsp;除
								          </button>
							         </div>
						        </div>
                                <div class="filter-block pull-right">
								     <div class="form-group pull-left">
									      <input type="text" class="form-control" placeholder="搜索...">
									      <i class="fa fa-search search-icon"></i>
								     </div>
								     <a id="refreshGroupTable" class="btn btn-primary pull-right">
									      <i class="fa fa-refresh fa-lg"></i> 刷新
								     </a>
							   </div>
							</div>
						</header>
						<div class="main-box-body clearfix" style="margin-top: -10px">
							<div class="table-responsive clearfix">
								<table class="table table-striped table-hover"  style="margin-top: -10px" 
									id="tb_groupFileInfo"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>