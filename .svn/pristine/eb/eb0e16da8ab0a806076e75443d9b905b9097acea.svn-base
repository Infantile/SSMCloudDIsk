$(function() {
	initPublicSharedFileMenu();
});

/**
 * 初始化右键功能
 */
function initPublicSharedFileMenu(){
	$.contextMenu({
		selector : "#tb_publicSharedFileInfo td",
		items : {
			publicShared_download : {
				name : "下载",
				callback : function(key, opt) {
					downloadPublicSharedFile();
				}
			},
			btn_cancelShare : {
				name : "取消共享",
				callback : function(key, opt) {
					cancePublicSharedFile();
				}
			},
			refreshPublicSharedTable : {
				name : "刷新",
				callback : function(key, opt) {
					refreshPublicSharedFileTable();
				}
			}
		},
		autoHide: true
	});
}

/**
 * 取消共享
 */
$("#btn_cancelShare").click(function() {
	cancePublicSharedFile();
});
/**
 * 取消文件共享
 */
function cancePublicSharedFile() {
	//判断是否已经选择了共享文件
	//判断是否已经选择了共享文件
	var rows = $("#tb_publicSharedFileInfo").bootstrapTable('getSelections');
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
			message: "请先选择取消文件对象!",
			size: "small"
		});
		return;
	}	
	//查找出哪些是本人共享，那些不是本人共享，
	var info = "";
	for (var i = 0; i < rows.length; i++) {
		// alert(rows[i].fileName);
		var shareObj = new Object();
		shareObj.fileID = rows[i].fileid;
		shareObj.operator = rows[i].operator;
		shareObj.shareObjectID = rows[i].shareobjectid;
		shareObj.createDate = rows[i].createdate;
		// 将JSON对象转化为JSON字符
		var sharedFileInfoJson = JSON.stringify(shareObj);
		$.ajax({
			url : "../publicShared/cancelPublicShared.do",
			dataType : "json",
			async : false,
			data : {
				mySharedFileInfo : sharedFileInfoJson
			},
			type : "post",
			success : function(data) {
				if (data.isShareObject == true) {
					//是共享者本人
					var shareObj = new Object();
					shareObj.fileID = rows[i].fileid;
					shareObj.fileName = rows[i].fileName;
					shareObj.operator = rows[i].operator;
					shareObj.shareObjectID = rows[i].shareobjectid;
					shareObj.createDate = rows[i].createdate;
					// 将JSON对象转化为JSON字符
					var sharedFileInfoJson = JSON.stringify(shareObj);
					array.push(sharedFileInfoJson);
				} else {
					//不是共享者本人
					info = info + " " + rows[i].fileName;	
				}
			},
			error : function() {
				message = "取消共享文件" + rows[i].fileName + "失败，请稍后再试！";
			}
		});
	}
    //有部分文件不是共享者本人
	if (!isEmpty(info)) {
		bootbox.confirm({
			title : "取消共享",
			message : "取消共享的文件中包括非你共享的，没有权限取消，还要继续取消其他共享文件吗!",
			buttons : {
				confirm : {
					label : '确认'
				},
				cancel : {
					label : '取消'
				}
			},
			size : "small",
			callback : function(result) {
				// 点击确认的时候继续下面操作
				// alert("ok");
				if (result && array.length!=0) {
					var error = "";
					for (var i = 0; i < array.length; i++) {
						$.ajax({
							url : "../publicShared/cancelPublicShared.do",
							dataType : "json",
							async : false,
							data : {
								mySharedFileInfo : array[i],
								confirm : true
							},
							type : "post",
							success : function(data) {				
									if (data.result) {
										// bootbox.alert("取消共享文件:
										// "+rows[i].fileName+" 成功！");
										// 成功之后需要刷新文件列表
									} else {
										// bootbox.alert("共享文件失败:
										// "+rows[i].fileName+" !");
										error = error + " " + array[i].fileName;
									}
							},
							error : function() {
								message = "取消共享文件" + array[i].fileName
										+ "失败，请稍后再试！";
							}
						});
					}
					var message = "";
					if (!isEmpty(error)) {
						message = message + "\n" + " 共享文件: " + error
								+ "  取消失败,其他文件取消成功！";
					}
					else {
						bootbox.alert({
							title : "提示",
							message : "取消共享文件成功！",
							size : "small",
							buttons : {
								ok : {
									label : "确认"
								}
							}
						});
					}
					var opt = {
							url : "../publicShared/queryList.do",
							silent : true,
							query : {}
						};
						$("#tb_publicSharedFileInfo").bootstrapTable('refresh', opt);
						// 清空rowClick数组
						rowClick.length = 0;
				}
				else{
					//所选文件中全部都是非用户共享的，什么都不干。  
				}
			}
		});	
	}	
	//全部都是共享者本人，直接取消共享
	else{
		var error = "";
		for (var j = 0; j < rows.length; j++) {
			//alert(rows[i].fileName);
			var Obj = new Object();
			Obj.fileID=rows[j].fileid;
			Obj.operator=rows[j].operator;
			Obj.shareObjectID=rows[j].shareobjectid;
			Obj.createDate=rows[j].createdate;
			// 将JSON对象转化为JSON字符
			var sharedFileJson = JSON.stringify(Obj);
			$.ajax({
				url : "../publicShared/cancelPublicShared.do",
				dataType : "json",
				async : false,
				data : {
					mySharedFileInfo : sharedFileJson,
					confirm : true
				},
				type : "post",
				success : function(data) {				
						if (data.result) {
							// bootbox.alert("取消共享文件:
							// "+rows[i].fileName+" 成功！");
							// 成功之后需要刷新文件列表
						} else {
							// bootbox.alert("共享文件失败:
							// "+rows[i].fileName+" !");
							error = error + " " + rows[i].fileName;
						}
				},
				error : function() {
					message = "取消共享文件" + rows[i].fileName
							+ "失败，请稍后再试！";
				}
			});
		}
		var message = "";
		if (!isEmpty(error)) {
			message = message + "\n" + " 共享文件: " + error
					+ "  取消失败,其他文件取消成功！";
		}
		else {
			bootbox.alert({
				title : "提示",
				message : "取消共享文件成功！",
				size : "small",
				buttons : {
					ok : {
						label : "确认"
					}
				}
			});
		}
		var opt = {
				url : "../publicShared/queryList.do",
				silent : true,
				query : {}
			};
			$("#tb_publicSharedFileInfo").bootstrapTable('refresh', opt);
			// 清空rowClick数组
			rowClick.length = 0;
	}
}

/**
 * 搜索功能
 */
$("#publicSharedFileSearch").bind("input propertychange", function(){
	publicSharedFileSearch();
});

function publicSharedFileSearch(){
	if(isEmpty($.trim($("#publicSharedFileSearch").val()))){
		refreshPublicSharedFileTable();
		return;
	}
	var opt = { 
			url: "../publicShared/querySearchInfo.do",
			silent: false, //刷新事件必须设置
			query:{ 
				search : $.trim($("#publicSharedFileSearch").val()),
				fileid : $("#currentFolderId").val()
			} 
	};
	
	$("#tb_publicSharedFileInfo").bootstrapTable('refresh', opt);
	//清空rowClick数组
	rowClick.length = 0;
}


/**
 * 公共共享文件下载
 */
$("#publicShared_download").click(function() {
	downloadPublicSharedFile();
	//清空rowClick数组
	rowClick.length = 0;
});

/**
 * 界面文件下载操作
 */
function downloadPublicSharedFile(){
	var rows = $("#tb_publicSharedFileInfo").bootstrapTable('getSelections');
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
		if(!isEmpty(rows[i].fileSize)){
			for (var j = 0; j < rows.length; j++) {	
				if(i != j && rows[i].fileName == rows[j].fileName){
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
	
	var folderFlag = false;
	for (var i = 0; i < rows.length; i++) {	
		if(isEmpty(rows[i].fileSize)){
			folderFlag = true;
			break;
		}
	}
	var downloadSize = 0;
	for (var i = 0; i < rows.length; i++) {	
		if(!isEmpty(rows[i].fileSize)){
			downloadSize = downloadSize + parseInt(rows[i].fileSize);
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
					createPublicShareDownLoadForm();
				}
			}
		});	
	}else{
		//没有文件夹情况直接进行下载
		createPublicShareDownLoadForm();
	}
}

/**
 * 设置下载参数
 */
function createPublicShareDownLoadForm(){
	//定义form表单
	var form = $("<form>");
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	var rows = $("#tb_publicSharedFileInfo").bootstrapTable('getSelections');
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
		fileName.attr("value", rows[i].fileName);		
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
 * id和name必须同步化，同时清空或者同时删除
 * 目录点击全部文件和选择我的文件两种情况将清空该集合
 * table的onDblClickRow事件中增加数据
 * 点击目录中其他目录时候两个集合将减少数据或者不变
 */
var publicSharedFileFolderIds = new Array();
var publicSharedFileFolderNames = new Array();

/**
 * 生成目录结构共通js
 * @param folderIds 传入对应的目录ID
 * @param folderNames 目录显示名称
 */
function getPublicSharedLiInfo(folderIds, folderNames) {
	var levelOne = '<li><a href="#" id ="all" class="active"><b>全部文件</b></a></li>';
	$("#publicSharedContentOL").html("");
	if (!isEmpty(folderNames) && folderNames.length > 0) {
		for (var i = 0; i < folderNames.length; i++) {
			levelOne = levelOne + '<li><a id="' + folderIds[i]
					+ '" href="#" class="active"><b>' + folderNames[i] + '</b></a></li>';
		}
	}
	// 动态生成
	$("#publicSharedContentOL").append(levelOne);

	// 绑定事件 (点击目录时候删除目录后面的数据)
	$('#publicSharedContentOL li').on('click', function(data) {
		$("#publicSharedFileSearch").val("");
		var liId = $(this).find("a").attr("id");
		//var liText = $(this).find("a").eq(0).text();
		var filterFolderIds = new Array();
		var filterFolderNames = new Array();		
		if (liId != "all") {			
			for (var int = 0; int < publicSharedFileFolderIds.length; int++) {
				filterFolderIds[int] = folderIds[int];
				filterFolderNames[int] = folderNames[int];
				if (liId == filterFolderIds[int]) {
					$("#currentFolderId").val(folderIds[int]);
					break;
				}
			}
			publicSharedFileFolderIds = filterFolderIds;
			publicSharedFileFolderNames = filterFolderNames;			
			//更新列表信息 
			refreshPublicSharedFileTable();
			$("#btn_cancelShare").hide();
		}else{
			//选择全部文件的情况
			publicSharedFileFolderIds = new Array();
			publicSharedFileFolderNames  = new Array();
			$("#currentFolderId").val("");
			refreshPublicSharedFileTable();
			$("#btn_cancelShare").show();
		}
		//清空rowClick数组
		rowClick.length = 0;
		//更新目录结构
		getPublicSharedLiInfo(filterFolderIds, filterFolderNames);
	});
}

/**
 * 更新公共共享文件列表
 */
function refreshPublicSharedFileTable(){
	var currentFolderId = $("#currentFolderId").val();
	if(isEmpty(currentFolderId)){
		$("#tb_publicSharedFileInfo").bootstrapTable('refresh');
	}else{
		var opt = { 
				url: "../publicShared/queryNextPageSharedFileInfoByFolderID.do",
				silent: false, //刷新事件必须设置
				query:{ 
					fileid : currentFolderId
				} 
		}; 
		$("#tb_publicSharedFileInfo").bootstrapTable('refresh', opt);	
		//清空rowClick数组
		rowClick.length = 0;
	}
}

/**
 * 点击更新按钮
 */
$('#refreshPublicSharedTable').click(function(){
	publicSharedFileSearch();
});