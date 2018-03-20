<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!-- 模态框示例（Modal） -->
	<form method="post" action="" class="form-horizontal" role="form" style="margin: 20px;">
		<div class="modal fade" id="creatNewFolderModal" tabindex="-1"
			data-backdrop="static" data-keyboard="false"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel" >新建文件信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="newFolderName" class="col-sm-3 control-label">文件夹名称：</label>
								<div class="col-sm-7">
									<input type="text" maxlength="50" id="newFolderName" class="form-control" placeholder="请输入文件夹名称">
								</div>								
							</div>
							<p><span id="newFolderNameAlert"></span>					
						</form>
					</div>
					<div class="modal-footer">
					<button type="button" id="creatNewFolderSumbit" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>						
						<span id="tip"> </span>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>