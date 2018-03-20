<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!-- 模态框示例（Modal） -->
	<form method="post" action="" class="form-horizontal" role="form" id="form_data" style="margin: 20px;">
		<div class="modal fade" id="downLoadFileModal" tabindex="-1"
			data-backdrop="static" data-keyboard="false"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel" >下载文件信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">												
							<div class="form-group">
								<div id="downloadUrls"></div>
							</div>					
							<div class="form-group">
								<label for="downloadPath" class="col-sm-3 control-label">保存文件：</label>
								<div class="col-sm-9">
    								<div class="col-xs-2" style="margin-left:10px;margin-top:5px"><input type=button value="选择" onclick="browseFolder('path')"></div>
									<div class="col-xs-2" style="margin-left:10px;margin-top:5px"><input id="path" type="text" name="path" size="30"></div>
								</div>								
							</div>												
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" id="downLoadFileSumbit" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>						
						<span id="tip"> </span>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>