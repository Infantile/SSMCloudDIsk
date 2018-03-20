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
		<div class="modal fade" id="uploadFileModal" tabindex="-1"
			data-backdrop="static" data-keyboard="false"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel" >上传文件信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">												
							<div class="form-group">
								<label for="uploadFile" class="col-sm-3 control-label">上传文件名：</label>
								<div class="col-sm-5">
									 <div class="col-xs-3" style="margin-left:-5px;margin-top:5px"><input id="clearUploadFile" type="button" value="移除"></div>
									 <div class="col-xs-6" style="margin-left:10px;margin-top:5px"><input id="fileToUpload" type="file" class="file" onchange="fileSelected()"></div>									 	
								</div>
							</div>					
							<div class="form-group">
								<label for="uploadNote" class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-7">
									<textarea class="form-control" maxlength="100" name="uploadNote" id="uploadNote" rows="3" style="min-width: 200px;max-width: 400px;"></textarea> 
								</div>								
							</div>
							<div id="progressBarDiv" class="form-group" style="display:none">
								<label for="uploadProgressBar" class="col-sm-3 control-label">上传进度：</label>
								<div class="col-sm-9">
									<progress id="progressBar" value="0" max="100" style="width:280px;margin-top:10px"></progress> 
									<span id="percentage"></span><span id="time"></span>									
								</div>								
							</div>
							<p><span id="addfileName"></span></p>					
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
						<button type="button" id="uploadFileSumbit" class="btn btn-primary">提交</button>
						<span id="tip"> </span>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>