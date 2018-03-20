<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" 
			+ request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title></title>
<script src="<%=basePath%>jquery/jquery-1.11.1.js"></script>
<script type="text/javascript">
	$(function() {
		$("#addCs").val("");
	});
	function fileUpload() {
		var checkFlag = true;
		var daName = $("#addCs").val();
		if(daName == null || daName == undefined || daName == ""){
			checkFlag = false;
			alert("未选择文件，请重新选择待上传文件！");
		}
		if(checkFlag){
			var xhr = new XMLHttpRequest();
			xhr.open("POST", "file/upload.do", false);
			xhr.upload.addEventListener("progress", uploadProgress, false);
			xhr.addEventListener("load", uploadComplete, false);
			xhr.addEventListener("error", uploadFailed, false);
			xhr.addEventListener("abort", uploadCanceled, false);
			//xhr.setRequestHeader("Content-Type", "multipart/form-data");
			var fd = new FormData();
			fd.append("fileHandler", document.getElementById('fileToUpload').files[0]);		
			xhr.send(fd);
		}
	}
	
	function fileSelected() {		
		var file = document.getElementById('fileToUpload').files[0];
		if (file == null) {
			alert('文件为空, 请重新选择文件');
			return;
		}
		
		//计算文件大小
		var fileSize = 0;
		//如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
		if (file.size > 1024 * 1024) {
			fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
			if (fileSize > 200) {
				alert('错误，文件超过200MB，禁止上传！');
				return;
			}
			fileSize = fileSize.toString() + 'MB';
		} else {
			fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
		}
		//将文件名和文件大小显示在前端label文本中
		$("#addfileName").html("<span style='color:Blue;'>文件名: "
				+ file.name
				+ "<span style='margin-left:25px;'></span>"
				+ '大小: ' + fileSize + "</span>");
		$("#addCs").val(file.name);
	}

	function uploadProgress(evt) {
		if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
			document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
		} else {
			document.getElementById('progressNumber').innerHTML = 'unable to compute';
		}
	}

	function uploadComplete(evt) {
		var result = eval('(' + evt.target.responseText + ')');
		if (result.path != "" && result.path != null
				&& result.path != undefined) {
			alert("上传文件成功:" + result.path);
		} else {
			alert("上传文件异常");
		}
	}

	function uploadFailed(evt) {
		alert("上传文件出现错误");
	}

	function uploadCanceled(evt) {
		alert("由于网络原因上传文件被终止");
	}

	function clearFileInput(name) {
		var obj = document.getElementById(name);
		obj.outerHTML = obj.outerHTML;
	}
</script>

</head>
<body>
	<p><label for="fileName">上传文件名：</label>
	<input type="file" name="fileToUpload" id="fileToUpload" style="display: none;" onchange="fileSelected();" />
	<input style="margin-left: 30px;" id="addCs" name="collectionSorce" value="" placeholder="请选择上传文件" readonly="readonly" maxlength="150" /> 
	<input type="button" value="浏览.." onclick="fileToUpload.click()"/> <input type="button" value="提交" onclick="fileUpload()"/></p>
	<p><span id="addfileName"></span><span id="progressNumber"></span></p>
</body>
</html>
