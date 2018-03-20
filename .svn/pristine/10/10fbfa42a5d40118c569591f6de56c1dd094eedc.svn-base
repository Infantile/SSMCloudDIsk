<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!-- modal框 -->
	<div class="container">
		<div id="createGroupModal" class="modal fade" 
			style="display: none;" tabindex="-1" data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">						 
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="groupInfoModalLabel">创建群组</h4>
						<label class="hide"></label>			 
					</div>
					<div class="modal-body">
						<div class="text-center" style="margin-bottom: 10px">
							<input id="inGroupName" autofocus="autofocus" class="form-control name-input" type="text" placeholder="群组名" />
						</div>
						<div class="text-center hidden input-valid"  style="font-size:14px;font-weight:600;color: #cbc"></div>
						<div class="form-height">
							<div class="form-left">
								<div id="crtGroupModalLeftTab" class="row">
									<div class="col-lg-12">
										<div class="main-box clearfix">
											<header class="main-box-header clearfix">
												<div class="filter-block pull-right">
													<div class="form-group pull-left">
														<input type="text" class="form-control" placeholder="搜索成员名">
														<i class="fa fa-search search-icon"></i>
													</div>
												</div>
											</header>
											<div class="main-box-body clearfix" style="margin-top: -10px">
												<div class="table-responsive clearfix">
													<table class="table table-striped table-hover"  style="margin-top: -10px" 
														id="tb_allCrtMember"></table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-move-button" style="width: 18%;float: left;text-align: center;">
								<div style="display: table-cell;vertical-align: middle">
									<button id="addToCrtGroup_btn" class="btn btn-primary">
										加入&nbsp;
										<i class="fa fa-arrow-right"></i>
									</button>
									<button id="removeFromCrtGroup_btn" class="btn btn-danger">
										<i class="fa fa-arrow-left"></i>
										&nbsp;移除
									</button>
								</div>
							</div>
							<div class="form-right">
								<div id="crtGroupModalRightTab" class="row">
									<div class="col-lg-12">
										<div class="main-box clearfix">
											<header class="main-box-header clearfix">
												<div class="filter-block pull-right">
													<div class="form-group pull-left">
														<input type="text" class="form-control" placeholder="搜索成员名">
														<i class="fa fa-search search-icon"></i>
													</div>
												</div>
											</header>
											<div class="main-box-body clearfix" style="margin-top: -10px">
												<div class="table-responsive clearfix">
													<table class="table table-striped table-hover"  style="margin-top: -10px" 
														id="tb_allCrtGrpMember"></table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button id="create_btn" type="button" class="btn btn-danger" style="margin: auto 5px;">创建</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>