$(function() {
	initContextMenu();
});

$("#uploadFile").fileinput({
    language: 'zh', //设置语言
    uploadUrl:"../file/upload.do", //上传的地址    
	uploadExtraData:function (previewId, index) {//传给后台参数
		var data = { 
				parentFolderID:$("#currentFolderId").val(), 
				comment:$("#uploadNote").val(), 
				rows:JSON.stringify($("#tb_fileInfo").bootstrapTable('getData')) 
		}; 
		return data;
	},
    uploadAsync:true, 					//默认异步上传
    showUpload:true,   					//是否显示上传按钮
    showRemove:true,  					//显示移除按钮
    showPreview:true, 					//是否显示预览
    showCaption:true,  					//是否显示标题
    allowedPreviewTypes: ['image'],		//预览类型
    browseClass:"btn btn-primary", 		//按钮样式
    dropZoneEnabled: true,				//是否显示拖拽区域
    initialCaption: "请选择上传文件",		//文本框初始话value
    maxFileSize:1024*1024,				//单位为kb，如果为0表示不限制文件大小
    minFileCount: 1,					//表示允许上传的最小文件个数
    maxFileCount:10, 					//表示允许同时上传的最大文件个数
    enctype:'multipart/form-data',
    validateInitialCount:true,
    previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",    
});

/**
 * 选择文件后处理事件
 */
$("#uploadFile").on("filebatchselected", function(event, files) {
	$("#checkfileMessage").html('');
	// 文件名称长度限制
	if (files[0].name.length > 50) {
		$("#checkfileMessage").html('<font color="red">错误：文件名称长度超过50位，请重新选择文件！<font>');
		$("#uploadFile").fileinput("clear");
		//$("#uploadFile").fileinput({'showUpload':false});
		//var uploadFileBtn = $("#uploadFile").find('.kv-file-upload');
		//uploadFileBtn.attr('disabled', true);
		return;
	}	
	// 云盘剩余大小检查
	$.ajax({
		url : "../file/diskSizeCheck.do",
		dataType : "json",
		async : false,
		data : {
			fileSize : files[0].size
		},
		type : "post",
		success : function(data) {
			if (!data.result) {
				var message = '<font color="red">提示：云盘剩余空间不足, 请清理云盘或者选择其他文件！<font>';
				if(!isEmpty(data.message)){
					message = '<font color="red">提示：云盘剩余大小检查异常，请稍后再试！<font>';
				}					
				$("#uploadFile").fileinput("clear");
				$("#checkfileMessage").html(message);
			} else {
				$("#checkfileMessage").html("");
			}
		},
		error : function() {
		}
	});
});

/**
 * 移除事件处理
 */
$("#uploadFile").on("fileclear",function(event, data, msg){
	$("#checkfileMessage").html("");
});

/**
 * 异步上传错误结果处理
 */
$('#uploadfile').on('fileerror', function(event, data, msg) {
	$("#checkfileMessage").html('<font color="red">错误提示：上传文件出现未知异常，请稍后再试！<font>');
});

/**
 * 异步上传成功结果处理
 */
$('#uploadFile').on('fileuploaded', function(event, data, previewId, index) {
	var response = data.response;
	if(response.result){
		//$('#uploadFileModal').modal('hide');
		refreshMyFileTable();
		queryCloudDiskInfo();
	}else{
		$("#checkfileMessage").html('<font color="red">错误提示：上传文件失败，请稍后再试！<font>');
	}
});



/**
 * 初始化右键功能
 */
function initContextMenu(){
	$.contextMenu({
		selector : "#tb_fileInfo td",
		items : {
			btn_upload : {
				name : "上传",
				callback : function(key, opt) {
					clearFileInfo();
					$("#progressBarDiv").hide();
					$('#uploadFileModal').modal('show');
				}
			},
			btn_download : {
				name : "下载",
				callback : function(key, opt) {
					downloadMyFile();
				}
			},
			btn_share : {
				name : "共享",
				callback : function(key, opt) {
					shareMyFile();
				}
			},
			delete_file : {
				name : "删除",
				callback : function(key, opt) {
					deleteMyFile();
				}
			},
			collect : {
				name : "收藏",
				callback : function(key, opt) {
					collectMyFile();
				}
			},
			copyTo : {
				name : "复制到",
				callback : function(key, opt) {
					copyToMyFile();
				}
			},
			moveTo : {
				name : "移动到",
				callback : function(key, opt) {
					moveToMyFile();
				}
			},
			reName : {
				name : "重命名",
				callback : function(key, opt) {
					reNameMyFile();
				}
			},
			creatNewFolder : {
				name : "新建文件夹",
				callback : function(key, opt) {
					$("#newFolderName").val("");
					$("#newFolderNameAlert").html("");
					$('#creatNewFolderModal').modal('show');
				}
			}
		},
		autoHide: true
	});
}

/**
 * 点击上传按钮打开上传文件modal
 */
$("#btn_upload").click(function() {
	clearFileInfo();
	$("#progressBarDiv").hide();
	$('#uploadFileModal').modal('show');
});

/**
 * 移除选择文件处理
 */
$("#clearUploadFile").click(function() {
	clearFileInfo();
});

/**
 * 共同处理，清理文件信息
 */
function clearFileInfo() {
	$("#fileToUpload").val("");
	$("#addfileName").html("");
	$("#progressNumber").val("");
	$("#uploadNote").val("");
	$("#checkfileMessage").html("");
	$("#uploadFile").fileinput("clear");
}

/**
 * 文件上传提交处理
 */
$("#uploadFileSumbit").click(function() {
	var checkFlag = true;
	var daName = $("#fileToUpload").val();
	if (isEmpty(daName)) {
		checkFlag = false;
		$("#addfileName").html('<font color="red">提示：未选择文件，请重新选择待上传文件！<font>');
	}
	if (checkFlag) { 
		$("#progressBarDiv").show();
        //传入后台各种参数设置
		var form = new FormData();
		var file = document.getElementById("fileToUpload").files[0];
		var rows = $("#tb_fileInfo").bootstrapTable('getData');
        form.append("fileHandler", file); 
        form.append("parentFolderID", $("#currentFolderId").val());
        form.append("comment", $("#uploadNote").val());
        form.append("rows", JSON.stringify(rows));
        var xhr = new XMLHttpRequest(); 
        xhr.open("POST", "../file/upload.do", true);
        xhr.onload = uploadComplete; 
        xhr.onerror =  uploadFailed;
        xhr.abort = uploadCanceled;
        xhr.upload.onprogress = uploadProgress;
        xhr.upload.onloadstart = function(){
            ot = new Date().getTime();   
            oloaded = 0;
        };
        xhr.send(form);        
	}
});

/**
 * 选择文件事件处理
 */
$("#fileToUpload").change(function() {
	$("#addfileName").html("");
	var file = document.getElementById('fileToUpload').files[0];
	if (file == null) {
		$("#addfileName").html('<font color="red">提示：文件为空, 请重新选择文件！<font>');
		return;
	}
	// 文件大小限制
	if (file.size == 0) {
		$("#fileToUpload").val("");
		$("#addfileName").html('<font color="red">错误：选择文件为空文件，请重新选择！<font>');
		return;
	}
	// 文件大小限制
	if (file.size > 1024 * 1024 * 1024) {
		$("#fileToUpload").val("");
		$("#addfileName").html('<font color="red">错误：选择文件大小超过1G，请重新选择！<font>');
		return;
	}
	// 文件名称长度限制
	if (file.name.length > 50) {
		$("#fileToUpload").val("");
		$("#addfileName").html('<font color="red">错误：文件名称超过50位，请重新选择文件！<font>');
		return;
	}	
	// 云盘剩余大小检查
	$.ajax({
		url : "../file/diskSizeCheck.do",
		dataType : "json",
		async : false,
		data : {
			fileSize : file.size
		},
		type : "post",
		success : function(data) {
			if (!data.result) {
				var message = '<font color="red">提示：云盘剩余空间不足, 请清理云盘或者选择其他文件！<font>';
				if(!isEmpty(data.message)){
					message = '<font color="red">提示：云盘剩余大小检查异常，请稍后再试！<font>';
				}
				$("#addfileName").html(message);
				$("#fileToUpload").val("");
			} else {
				$("#addfileName").html("");
			}
		},
		error : function() {
		}
	});
});

/**
 * 上传进度实现方法，上传过程中会频繁调用该方法
 * @param evt
 */
function uploadProgress(evt) {
	var progressBar = document.getElementById("progressBar");
	var percentageDiv = document.getElementById("percentage");
	// event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
	if (evt.lengthComputable) {//
		progressBar.max = evt.total;
		progressBar.value = evt.loaded;
		percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
	}

	var time = document.getElementById("time");
	var nt = new Date().getTime();// 获取当前时间
	var pertime = (nt - ot) / 1000; // 计算出上次调用该方法时到现在的时间差，单位为s
	ot = new Date().getTime(); // 重新赋值时间，用于下次计算

	var perload = evt.loaded - oloaded; // 计算该分段上传的文件大小，单位b
	oloaded = evt.loaded;// 重新赋值已上传文件大小，用以下次计算

	// 上传速度计算
	var speed = perload / pertime;// 单位b/s
	var bspeed = speed;
	var units = 'b/s';// 单位名称
	if (speed / 1024 > 1) {
		speed = speed / 1024;
		units = 'k/s';
	}
	if (speed / 1024 > 1) {
		speed = speed / 1024;
		units = 'M/s';
	}
	speed = speed.toFixed(1);
	//剩余时间
	var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1);
	time.innerHTML = '<br>速度：' + speed + units + '，剩余时间：' + resttime + 's';
	if (bspeed == 0) {
		time.innerHTML = '上传已取消';
	}
}

/**
 * 上传文件完成回调方法
 * 
 * @param evt
 */
function uploadComplete(evt) {
	$("#addfileName").html("");
	var data = eval('(' + evt.target.responseText + ')');
	if (data.result) {
		$('#uploadFileModal').modal('hide');
		refreshMyFileTable();
		queryCloudDiskInfo();
	} else {
		$("#addfileName").html('<font color="red">错误：上传文件异常！<font>');
	}
}
/**
 * 文件上传失败
 * 
 * @param evt
 */
function uploadFailed(evt) {
	$("#addfileName").html('<font color="red">错误：上传文件出现错误！<font>');
}
/**
 * 文件上传取消
 * 
 * @param evt
 */
function uploadCanceled(evt) {
	$("#addfileName").html('<font color="red">错误：由于网络原因上传文件被终止！<font>');
}

/**
 * 删除文件
 */
$("#delete").click(function() {
	deleteMyFile();
	//清空rowClick数组
	rowClick.length = 0;
});

function deleteMyFile(){
	//判断是否已经选择了共享文件
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var array=[];
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请先选择删除文件对象!",
			size: "small"
		});
		return;
	}
	//用于统计哪些文件删除失败，哪些删除成功，哪些已被共享
	var success="";
	var failure="";
	var info="";
	var isShared="";
	bootbox.confirm({
		title : "删除文件",
		message : "确认将执行删除操作？",
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		size: "small",
		callback : function(result) {
			// 点击确认的时候继续下面操作
			if (result) {
				for (var i = 0; i < rows.length; i++) {
					$.ajax({
						url : "../file/deleteFile.do",
						dataType : "json",
						async : false,
						data : {
							fileID : rows[i].fileid
						},
						type : "post",
						success : function(data) {					
								if (data.result===true) {
									//成功之后需要刷新文件列表
									success=success+" "+rows[i].filename;
									refreshMyFileTable();
								}
								else if(data.result===false){
									//bootbox.alert("删除文件: "+rows[i].filename+" 失败！");
									failure=failure+" "+rows[i].filename;								
								}else if(data.result===""){
									isShared=isShared+" "+i;
								}	
								if(data.isShared){
									info=info+" "+rows[i].filename;	
									var object=new Object();
									object.fileid=rows[i].fileid;
									object.filename=rows[i].filename;
									array.push(object);
								}
						},
						error : function() {
							message = "删除文件"+rows[i].filename+"失败，请稍后再试！";
						}
					});
				}
				if(isEmpty(failure) && isEmpty(isShared) && isEmpty(info)){
					bootbox.alert({
						title:"提示",
						message:"文件删除成功,在回收站中将保留7天!",
						size:"small",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}
				if(!isEmpty(failure)){
					bootbox.alert({
						title:"提示",
						message:"文件: "+failure+"  删除失败！",
						size:"small",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}
				if(!isEmpty(info)){
					var success2="";
					var failure2="";
					bootbox.confirm({
						title : "删除文件",
						message : "文件: "+info+" 已被共享，是否确认要删除？",
						buttons : {
							confirm : {
								label : '确认'
							},
							cancel : {
								label : '取消'
							}
						},
						size: "small",
						callback : function(result) {
							// 点击确认的时候继续下面操作
							if (result) {
								for (var i = 0; i < array.length; i++) {
									$.ajax({
										url : "../file/deleteFile.do",
										dataType : "json",
										async : false,
										data : {
											fileID : array[i].fileid,
											confirm:true
										},
										type : "post",
										success : function(data) {					
												if (data.result) {
													//成功之后需要刷新文件列表
													success2=success2+" "+array[i].filename;
													refreshMyFileTable();
												}
												else{
													//bootbox.alert("删除文件: "+rows[i].filename+" 失败！");
													failure2=failure2+" "+array[i].filename;								
												}	
										},
										error : function() {
											message = "删除文件"+array[i].filename+"失败，请稍后再试！";
										}
									});
								}
								if(isEmpty(failure2)){
									bootbox.alert({
										title:"提示",
										message:"文件删除成功,在回收站中将保留7天!",
										size:"small",
										buttons:{
											ok:{
												label:"确认"
											}
										}
									});
								}
								else{
									bootbox.alert({
										title:"提示",
										message:"文件: "+failure2+"  删除失败！",
										size:"small",
										buttons:{
											ok:{
												label:"确认"
											}
										}
									});
								}
							}
						}
					});								
				}
			}
		}
	});
}

/**
 * 界面文件下载操作
 */
$("#btn_download").click(function() {
	downloadMyFile();
	//清空rowClick数组
	rowClick.length = 0;
});

/**
 * 下载文件相关检查
 */
function downloadMyFile(){
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1) {
		bootbox.alert({
		    title: "提示",
			buttons:{
				ok:{
					label:"确认",
					className: "btn-primary",
				}
			},
			message: "未选择待下载的文件对象，请重新选择！",
			size: "small"
		   });
		return;
	}
	
	//判断选择文件中是否有重复的（搜索后可能有重复的）
	for (var i = 0; i < rows.length; i++) {	
		//文件情况下才需要判断
		if(!isEmpty(rows[i].filegroupname)){
			for (var j = 0; j < rows.length; j++) {	
				if(i != j && rows[i].filename == rows[j].filename){
					bootbox.alert({
					    title: "提示",
						buttons:{
							ok:{
								label:"确认",
								className: "btn-primary",
							}
						},
						message: "选择文件中有相同名称文件，无法压缩到同一压缩文件中，请分别选择进行下载！",
						size: "small"
					   });
					return;
				}
			}
		}
	}
	
	//判断是否为文件夹标志
	var folderFlag = false;
	for (var i = 0; i < rows.length; i++) {	
		if(isEmpty(rows[i].filegroupname)){
			folderFlag = true;
			break;
		}
	}
	//下载文件大小判断
	var downloadSize = 0;
	for (var i = 0; i < rows.length; i++) {	
		if(!isEmpty(rows[i].filegroupname)){
			downloadSize = downloadSize + parseInt(rows[i].filesize);
		}
	}

	var maxDownLoad = 2*1024*1024*1024;
	//包含文件夹情况给出提示消息
	if(folderFlag || downloadSize > maxDownLoad){		
		var confirmMessage = "";
		if(folderFlag){
			confirmMessage = confirmMessage + '<span class="fa fa-star"></span>&nbsp&nbsp所选文件中包含文件夹，文件夹将无法下载!';
		}
		if(downloadSize > maxDownLoad){
			confirmMessage = confirmMessage + '<br><span class="fa fa-star">&nbsp&nbsp所选文件总大小超过2G，下载可能会很慢或者出现未知异常!';
		}
		confirmMessage = confirmMessage + "<br><br>确认将继续执行下载操作？";		
		bootbox.confirm({
			title:'提示',
			size:'small',
			message : confirmMessage,
			buttons : {
				confirm : {
					label : '确认'
				},
				cancel : {
					label : '取消'
				}
			},
			callback : function(result) {
				// 点击确认的时候继续下面操作
				if (result) {
					creatDownLoadForm();
				}
			}
		});	
	}else{
		//没有文件夹情况直接进行下载
		creatDownLoadForm();
	}
}

/**
 * 设置下载参数
 */
function creatDownLoadForm(){
	//定义form表单
	var form = $("<form>");
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var fileNumber = 0;
	for(var i = 0; i < rows.length; i++){
		if(!isEmpty(rows[i].filegroupname)){
			fileNumber++;
		}
	}
	//只选择了一个文件的时候，不需要压缩成zip包
	if(fileNumber == 1){		
		form.attr("action", "../file/singleDownload.do");	
	}else{
		form.attr("action", "../file/multDownload.do");	
	}
	var fileIndex = 0;
	for (var i = 0; i < rows.length; i++) {	
		//选择对象为文件夹时候不下载
		if(isEmpty(rows[i].filegroupname)){
			continue;
		}
		//参数准备
		var filegroupName = $("<input>");
		filegroupName.attr("type", "hidden");
		filegroupName.attr("name", "fileInfoList["+fileIndex+"].filegroupname");
		filegroupName.attr("value", rows[i].filegroupname);
		var filePath = $("<input>");
		filePath.attr("type", "hidden");
		filePath.attr("name", "fileInfoList["+fileIndex+"].filepath");
		filePath.attr("value", rows[i].filepath);
		var fileName = $("<input>");
		fileName.attr("type", "hidden");
		fileName.attr("name", "fileInfoList["+fileIndex+"].filename");
		fileName.attr("value", rows[i].filename);		
		form.append(filegroupName);
		form.append(filePath);
		form.append(fileName);
		$("body").append(form);
		fileIndex++;
	}
	//表单提交
	form.submit(); 	
}

/**
 * 界面刷新按钮操作
 */
$("#refreshTable").click(function() {
	refreshMyFileTable();
});

/**
 * 新建文件夹处理窗口打开事件
 */
$("#creatNewFolder").click(function() {
	$("#newFolderName").val("");
	$("#newFolderNameAlert").html("");
	$('#creatNewFolderModal').modal('show');
});

/**
 * 新建文件夹处理窗口
 */
$("#creatNewFolderSumbit").click(function() {
	creatNewFolder();
});

function creatNewFolder(){
	var message = newFolderInfoCheck();
	if(!isEmpty(message)){
		$("#newFolderNameAlert").html('<font color="red">' + message + '<font>');
		return;
	}	
	$.ajax({
		url : "../file/createFolder.do",
		dataType : "json",
		async : false,
		data : {
			currentFolderId : $("#currentFolderId").val(), //获取当前目录的ID
			newFolderName : $("#newFolderName").val()
		},
		type : "post",
		success : function(data) {
			if (data.result) {
				$('#creatNewFolderModal').modal('hide');
				refreshMyFileTable();
			}
		},
		error : function() {
			message = "创建文件夹发生未知错误，请稍后再试！";
		}
	});
}

/**
 * 创建文件夹对应check
 */
function newFolderInfoCheck() {
	$("#newFolderNameAlert").html("");
	var newFolderName = $("#newFolderName").val();
	if(isEmpty(newFolderName)){
		return "文件夹名称为空，请重新输入！";
	}
	if(isIllegalStr(newFolderName)){
		return "文件名不能包含以下字符:   \ / : ? * \" < > | ";
	}
	if(newFolderName.length > 50){
		return "文件夹名称超过50位，请重新输入！";
	}
	var rows = $("#tb_fileInfo").bootstrapTable('getData');
	for (var i = 0; i < rows.length; i++) {
		if(isEmpty(rows[i].filesize) && newFolderName == rows[i].filename){
			return "当前目录下该文件夹名称已经存在，请重新命名！";
		}
	}
	//回收站中相同名称检查
	var recycleMessage = checkRecycleFile(newFolderName);
	
	return recycleMessage;
}

/**
 * 回收站中相同名称检查（暂时删除check，到回收站中恢复文件的时候进行处理）
 * @returns {String}
 */
function checkRecycleFile(newFileObjName){
	return null;//删除改行后就实现了重命名以及新建文件夹的时候判断回收站中是否有该文件
	
	var recycleMessage = null;
	$.ajax({
		url : "../recycle/recycleTbInit.do",
		dataType : "json",
		async : false,
		type : "post",
		success : function(data) {
			for (var i = 0; i < data.rows.length; i++) {
				if(newFileObjName == data.rows[i].filename && isEmpty(data.rows[i].filesize)){					
					recycleMessage =  "回收站中该文件对象名称已经存在，请重新命名！";
					break;
				}
			}
		}
	});	
	
	return recycleMessage;
}


/**
 * 文件夹或者文件收藏功能
 */
$("#collect").click(function() {
	collectMyFile();
});

function collectMyFile(){
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title: "提示",
			message: "请选择需要收藏的文件！",
			size: "small",
			buttons : {
				ok:{
					label:"确认"
				}
			},
		});
		return;
	}
	bootbox.confirm({
		title : "收藏操作",
		message : "确认将执行收藏操作？",
		size:"small",
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		callback : function(result) {
			// 点击确认的时候继续下面操作
			if (result) {
				var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../file/collectObject.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if(isEmpty(data.failed) || data.failed.length == 0){
							bootbox.alert({
								title:'提示',
								buttons: {
									ok:{
										label: "确认",
										className: "btn-primary",
									}
								},
								message: "收藏文件操作成功!",
								size: "small"
							});
							$("#creatNewFolderModal").modal("hide");
						}else{
							var message = "";
							for (var i = 0; i < data.failed.length; i++) {
								message = message + "[" + data.failed[i] + "]";
							}
							bootbox.alert("提示:</br>选择文件对象中{<font style='color:red'>" + message + "</font>}</br>已经收藏，或者由于其他未知原因操作失败，其他文件对象收藏成功！");
						}
					},
					error : function() {
						message = "收藏文件操作发生未知错误，请稍后再试！";
					}
				});
			}
		}
	});
}

/**
 * 复制文件夹操作
 */
$("#copyTo").click(function() {
	copyToMyFile();
	//清空rowClick数组
	rowClick.length = 0;
});

function copyToMyFile(){
	$("#folderTreeMessage").html("");
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title: "提示",
			buttons:{
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请选择需要复制的文件对象!",
			size: "small"
		});
		return;
	}
	initTreeValue();
	$("#controlType").val("copy");	
	$("#copyOrMoveToModalTitle").html("复制文件目录选择");
}

/**
 * 移动文件夹操作
 */
$("#moveTo").click(function() {
	moveToMyFile();
	//清空rowClick数组
	rowClick.length = 0;
});

function moveToMyFile(){
	$("#folderTreeMessage").html("");
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请选择需要移动的文件对象!",
			size: "small"
		});
		return;
	}
	initTreeValue();
	$("#controlType").val("move");	
	$("#copyOrMoveToModalTitle").html("移动文件目录选择");	
}

/**
 * 设置目录的初始化值
 */
function initTreeValue(){
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var jsonRows = JSON.stringify(rows);
	$.ajax({
		type : "post",
		url : "../file/getFolderIds.do",
		async : true,
		data : {
			rows : jsonRows
		},
		success : function(result) {			
			if(result != null){
				var data = eval(result);
				if(data.length == 0){
					bootbox.alert({
						title:'提示',
						buttons: {
							ok:{
								label: "确认",
								className: "btn-primary",
							}
						},
						message: "没有可选择的目标目录，请先新建文件夹!",
						size: "small"
					});
				}else{
					initTree(data);	
					$("#copyOrMoveToModal").modal("show");	
				}				
			}
		}
	});
}


function initTree(result){
	//设置目录的初始化值
	$('#folderTree').treeview({
        data: result,
        multiSelect: false,
        levels: 2       
    });
}

/**
 * 移动复制modal点击提交处理
 */

$("#copyOrMoveTo").click(function() {
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var jsonRows = JSON.stringify(rows);
	var nodes = $('#folderTree').treeview('getSelected');
	if(isEmpty(nodes) || nodes.length < 1){
		$("#folderTreeMessage").html("<font style='color:red'>请选择需要操作的目标目录!</font>");	
		return;
	}
	if("move"== $("#controlType").val() && nodes[0].id == $("#currentFolderId").val()){		
		$("#folderTreeMessage").html("<font style='color:red'>源文件已经存在该目录，请重新选择移动目标目录！</font>");	
		return;
	}
	$.ajax({
		type : "post",
		url : "../file/copyOrMoveTo.do",
		async : true,
		data : {
			parentFolderID : nodes[0].id,
			controlType : $("#controlType").val(),
			rows : jsonRows
		},
		success : function(data) {
			if(data.result){
				if(isEmpty(data.failed) || data.failed.length == 0){
					bootbox.alert({
						title:'提示',
						buttons: {
							ok:{
								label: "确认",
								className: "btn-primary",
							}
						},
						message: "处理操作成功!",
						size: "small"
					});
					$("#copyOrMoveToModal").modal("hide");
					refreshMyFileTable();					
				}else{
					var message = "";
					for (var i = 0; i < data.failed.length; i++) {
						message = message + "[" + data.failed[i] + "]";
					}
					$("#folderTreeMessage").html("提示:</br>选择文件对象中{<font style='color:red'>" + message + "</font>}</br>操作处理失败，其他文件对象处理成功！");
				}				
			}else{
				$("#folderTreeMessage").html("提示:</br><font style='color:red'>处理操作失败，可用容量不足或者出现未知异常，请检查后重试!</font>");				
			}
			
			//刷新云盘剩余容量
			queryCloudDiskInfo();
		}
	});
}); 

/**
 * 点击共享按钮，弹出共享对象列表对话框
 */
//显示共享对象列表信息
var shareObjectData="";
$("#btn_share").click(function(){	
	shareMyFile();
});

function shareMyFile(){
	//判断是否已经选择了共享文件
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请先选择共享文件!",
			size: "small"
		});
		return;
	}	
	$("#shareFileModal").modal("show");
	$("#exitGroup_btn").hide();
	getShareMemberInfo();
	$("#tb_shareMemList").show();
	$('#tb_shareMemList').bootstrapTable('refresh');
}

//点击共享按钮，处理共享文件操作
$('#shareFileBtn').click(function(){
	var selectData = $("#tb_shareMemList").bootstrapTable("getSelections");
	//alert(JSON.stringify(selectData));
	//判断已经勾选了共享对象
	if(selectData.length === 0){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请选择共享对象!",
			size: "small"
		});
		return;
	}
	var shareObjectIds = new Array();
	var object = new Object();
	for (var i=0;i<selectData.length;i++){
		object.shareObjectID = selectData[i].shareObjectID;
		object.shareObjectName = selectData[i].shareObjectName;
		shareObjectIds[i] = JSON.stringify(object);
	}
	$('#shareFileModal').modal('hide');
	//封装共享文件ids
	var rows=$("#tb_fileInfo").bootstrapTable('getSelections');
    var fileIDs=new Array();
    i=0;
    while(i<rows.length){
    	var object=new Object();
    	object.fileID=rows[i].fileid;
    	object.fileName=rows[i].filename;
    	fileIDs[i]=JSON.stringify(object);
    	i++;
    }
    var shareObjectInfo=new Object();
    shareObjectInfo.shareObjectIDs = shareObjectIds;
    shareObjectInfo.fileIDs = fileIDs;
    var shareObjectJson = JSON.stringify(shareObjectInfo);
	$.ajax({
		url : "../file/shareFile.do",
		dataType : "json",
		data : {
			shareObjectJson : shareObjectJson
		},
		type : "post",		
		async : false,
		beforeSend : function() {
				// alert("请求前的处理");
		},
		success :function(data) {
			if (data.isException) {
				if(!isEmpty(data.success)){
				bootbox.alert({
					title:"提示",
					message:"只有以下: "+data.success.toString()+" 成功！",
					size:"small",
					buttons:{
						ok:{
							label:"确认"
						}
					}
				});
				}
				else{
					bootbox.alert({
						title:"提示",
						message:"文件共享发生异常,共享失败！",
						size:"small",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}
			}
			else{
				if(!isEmpty(data.failue)){
				bootbox.alert({
					title:"提示",
					message: " "+data.failue.toString()+" 失败！",
					size:"small",
					buttons:{
						ok:{
							label:"确认"
						}
					}
				});
				}
				else{
					if(!isEmpty(data.repeatedMessages)){
						if(!isEmpty(data.success)){
							bootbox.alert({
							    title: "提示",
							    message: " "+data.repeatedMessages.toString().replace(',','')+"<br>无需重复共享。其余文件共享成功！",
							    size: "small",
							    buttons:{
							    	ok:{
							    		label:"确认"
							    	}
							    }
							});
						}else{
							bootbox.alert({
							    title: "提示",
							    message: " "+data.repeatedMessages.toString().replace(',','')+"<br>无需重复共享。",
							    size: "small",
							    buttons:{
							    	ok:{
							    		label:"确认"
							    	}
							    }
							});
						}
						
					}else{
						bootbox.alert({
						    title: "提示",
						    message: "文件共享成功！",
						    size: "small",
						    buttons:{
						    	ok:{
						    		label:"确认"
						    	}
						    }
						});
					}
				}
			}	
		},
		error : function() {
			message = "文件共享发生未知错误，请重新尝试共享！";
		}
	});
});

/**
 * id和name必须同步化，同时清空或者同时删除
 * 目录点击全部文件和选择我的文件两种情况将清空该集合
 * table的onDblClickRow事件中增加数据
 * 点击目录中其他目录时候两个集合将减少数据或者不变
 */
var myFileFolderIds = new Array();
var myFileFolderNames = new Array();

/**
 * 生成目录结构共通js
 * @param folderIds 传入对应的目录ID
 * @param folderNames 目录显示名称
 */
function getLiInfo(folderIds, folderNames) {
	var levelOne = '<li><a href="#" id ="all" class="active"><b>全部文件</b></a></li>';
	$("#myFileContentOL").html("");
	if (!isEmpty(folderNames) && folderNames.length > 0) {
		for (var i = 0; i < folderNames.length; i++) {
			levelOne = levelOne + '<li><a id="' + folderIds[i]
					+ '" href="#" class="active"><b>' + folderNames[i] + '</b></a></li>';
		}
	}
	// 动态生成
	$("#myFileContentOL").append(levelOne);

	// 绑定事件 (点击目录时候删除目录后面的数据)
	$('#myFileContentOL li').on('click', function(data) {
		$("#myFileSearch").val("");
		var liId = $(this).find("a").attr("id");
		//var liText = $(this).find("a").eq(0).text();
		var filterFolderIds = new Array();
		var filterFolderNames = new Array();		
		if (liId != "all") {			
			for (var int = 0; int < myFileFolderIds.length; int++) {
				filterFolderIds[int] = folderIds[int];
				filterFolderNames[int] = folderNames[int];
				if (liId == filterFolderIds[int]) {
					$("#currentFolderId").val(folderIds[int]);
					break;
				}
			}
			myFileFolderIds = filterFolderIds;
			myFileFolderNames = filterFolderNames;			
			//更新列表信息 
			refreshMyFileTable();
		}else{
			//选择全部文件的情况
			myFileFolderIds = new Array();
			myFileFolderNames  = new Array();
			$("#currentFolderId").val("");
			refreshMyFileTable();
		}
		//清空rowClick数组
		rowClick.length = 0;
		//更新目录结构
		getLiInfo(filterFolderIds, filterFolderNames);
	});
}

/**
 * 更新我的文件列表
 */
function refreshMyFileTable(){
	var currentFolderId = $("#currentFolderId").val();
	if(isEmpty(currentFolderId)){
		$("#tb_fileInfo").bootstrapTable('refresh');
	}else{
		var opt = { 
				url: "../file/myfileTbInit.do",
				silent: false, //刷新事件必须设置
				query:{ 
					fileObjId : currentFolderId
				} 
		}; 
		$("#tb_fileInfo").bootstrapTable('refresh', opt);
		//清空rowClick数组
		rowClick.length = 0;
	}
}

/**
 * 点击更新按钮
 */
$('#myFileRefreshTable').click(function(){
	myFileSearch();
});

/**
 * 搜索功能
 */
$("#myFileSearch").bind("input propertychange", function(){
	myFileSearch();
});

function myFileSearch(){
	var myFileSearch = $("#myFileSearch").val();
	if(isEmpty($.trim(myFileSearch))){
		refreshMyFileTable();
		return;
	}
	var opt = { 
			url: "../file/querySearchInfo.do",
			silent: false, //刷新事件必须设置
			query:{ 
				search : $.trim(myFileSearch)
			} 
	};
	
	$("#tb_fileInfo").bootstrapTable('refresh', opt);
	//清空rowClick数组
	rowClick.length = 0;
}

/**
 * 重命名
 */
$('#reName').click(function(){	
	reNameMyFile();
});

function reNameMyFile(){
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	if(rows.length < 1) {
		bootbox.alert({
		    title: "提示",
			buttons:{
				ok:{
					label:"确认",
					className: "btn-primary",
				}
			},
			message: "未选择重命名的文件对象，请重新选择！",
			size: "small"
		   });
		return;
	}
	if(rows.length > 1){
		bootbox.alert({
		    title: "提示",
			buttons:{
				ok:{
					label:"确认",
					className: "btn-primary",
				}
			},
			message: "目前仅支持单文件对象重命名，暂时无法多文件同时重命名！",
			size: "small"
		   });
		return;
	}
	$('#newFileName').val("");
	$("#checkFileNameAlert").html("");
	$('#updateFileNameModal').modal("show");
	$('#currentFileName').val(rows[0].filename);	
}

/**
 * 文件对象重命名确定按钮
 */
$('#updateFileName').click(function() {
	updateFileName();
});

function updateFileName(){
	var newName = $('#newFileName').val();
		
	if (isEmpty(newName)) {		
		$('#newFileName').focus().select();
		$("#checkFileNameAlert").html('<font color="red">输入的文件对象名称为空，请重新输入！<font>');
		return;
	}else if(isIllegalStr(newName)){
		$('#newFileName').focus().select();
		$("#checkFileNameAlert").html('<font color="red">文件名不能包含以下字符:   \ / : ? * \" < > | <font>');
		return;
	}else {	
		var rows = $("#tb_fileInfo").bootstrapTable('getData');
		var isExist = false;
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].filename == newName){
				isExist = true;
				break;
			}
		}
		//如果当前目录里面已经存在该文件
		if(isExist){
			$('#newFileName').focus().select();
			$("#checkFileNameAlert").html('<font color="red">当前目录下该文件对象已经存在，请重新输入！<font>');
			return;	
		}
		//回收站中相同名称检查
		var message = checkRecycleFile(newName);
		if(!isEmpty(message)){
			$('#newFileName').focus().select();
			$("#checkFileNameAlert").html('<font color="red">'+message+'<font>');
			return;
		}
	}
	//发送后台处理
	var rows = $("#tb_fileInfo").bootstrapTable('getSelections');
	var fileObjType = "folder";
	if (!isEmpty(rows[0].filesize)) {
		fileObjType = "file";
	}
	$.ajax({
		url : "../file/renameFile.do",
		dataType : "json",
		async : true,
		data : {
			oldFileName : $('#currentFileName').val(),
			fileType : fileObjType,
			id : rows[0].fileid,
			newName : newName
		},
		type : "post",
		success : function(data) {
			if (data.result) {				
				$('#updateFileNameModal').modal("hide");
				refreshMyFileTable();
			} else {
				bootbox.alert({
					title : "提示",
					buttons : {
						ok : {
							label : "确认",
							className : "btn-primary",
						}
					},
					message : "文件对象重命名失败，请稍后再试！",
					size : "small"
				});
			}
		},
		error : function() {
			bootbox.alert({
				title : "提示",
				buttons : {
					ok : {
						label : "确认",
						className : "btn-primary",
					}
				},
				message : "文件对象重命名发生未知错误，请稍后再试！",
				size : "small"
			});
		}
	});	
}

/**
 * 共享列表搜索框的实现
 */
$("#shareFileModal").find("input.form-control").on("input propertychange",function(){
	var opt = {
			url: "../file/queryShareObjectList.do",
			silent: false, //刷新事件必须设置
			query:{
				search : $(this).val(),
			} 
	};
	$('#tb_shareMemList').bootstrapTable('refresh',opt);
});

/**
 * 共享成员表的双击事件
 */
$("#tb_shareMemList").on("dbl-click-row.bs.table",function(e, row, $element){
	if (row.shareObjectFlag === "1"){
		$("#groupInfoModal").find("label.hide").text(row.shareObjectID);
		$("#groupInfoModal").modal('show');
		getGrpMemberInfo();
		$("#grpInfoModalRightTab").show();
		$("#tb_grpMember").bootstrapTable('refresh');
	}
});

/**
 * 共享成员表的初始化
 */
function getShareMemberInfo() {
	$('#tb_shareMemList').bootstrapTable({
		url : '../file/queryShareObjectList.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : false, //用 是否启排序
		sortOrder : "asc", // 排序方式
		sortStable : true,
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "shareObjectID", // 每一行的唯一标识，一般为主键列
		queryParams : function getParams(params) {
			var queryParams = params;
			return queryParams;
		},
		columns : [{
			align : 'middle',
			checkbox : true
		},{
			field : 'shareObjectID',
			visible: false,
		},{
			field : 'shareObjectLoginID',
			title : '账号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'shareObjectName',
			title : '名称',
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableShareObject(value, row);
			}
		}],
		silent : true, // 刷新事件必须设置
		formatLoadingMessage : function() {
			return "载入成员信息中......";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '未查询到群组成员';
		},
		onLoadError : function(data) {
			$('#tb_shareMemList').bootstrapTable('removeAll');
		}
	});
}