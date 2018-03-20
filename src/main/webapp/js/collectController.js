$(function() {
	initCollectContextMenu();
});

/**
 * 初始化右键功能
 */
function initCollectContextMenu(){
		$.contextMenu({
			selector : "#tb_collectfileInfo td",
			items : {
				btn_downloadCollectFile : {
					name : "下载",
					callback : function(key, opt) {
						downloadCollectFile();
					}
				},
				btn_cancelCollect : {
					name : "取消收藏",
					callback : function(key, opt) {
						cancelCollectFile();
					}
				},
			},
			autoHide: true
		});	
}

/**
 * id和name必须同步化，同时清空或者同时删除
 * 目录点击全部文件和选择我的文件两种情况将清空该集合
 * table的onDblClickRow事件中增加数据
 * 点击目录中其他目录时候两个集合将减少数据或者不变
 */
var myCollectFolderIds = new Array();
var myCollectFolderNames = new Array();

/**
 * 生成收藏夹目录结构共通js
 * @param folderIds 传入对应的目录ID
 * @param folderNames 目录显示名称
 */
function getCollectLiInfo(folderIds, folderNames) {
	var levelOne = '<li><a href="#" id ="all" class="active"><b>全部文件</b></a></li>';
	$("#myCollectContentOL").html("");
	if (!isEmpty(folderNames) && folderNames.length > 0) {
		for (var i = 0; i < folderNames.length; i++) {
			levelOne = levelOne + '<li><a id="' + folderIds[i]
					+ '" href="#" class="active"><b>' + folderNames[i] + '</b></a></li>';
		}
	}
	// 动态生成
	$("#myCollectContentOL").append(levelOne);

	// 绑定事件 (点击目录时候删除目录后面的数据)
	$('#myCollectContentOL li').on('click', function(data) {
		$("#collectFileSearch").val("");
		var liId = $(this).find("a").attr("id");
		//var liText = $(this).find("a").eq(0).text();
		var filterFolderIds = new Array();
		var filterFolderNames = new Array();		
		if (liId != "all") {			
			for (var int = 0; int < myCollectFolderIds.length; int++) {
				filterFolderIds[int] = folderIds[int];
				filterFolderNames[int] = folderNames[int];
				if (liId == filterFolderIds[int]) {
					$("#currentFolderId").val(folderIds[int]);
					break;
				}
			}
			myCollectFolderIds = filterFolderIds;
			myCollectFolderNames = filterFolderNames;			
			//更新列表信息 
			refreshCollectFileTable();
		}else{
			//选择全部文件的情况
			myCollectFolderIds = new Array();
			myCollectFolderNames  = new Array();
			$("#currentFolderId").val("");
			refreshCollectFileTable();
			$("#collectionDiv_cancel").show();
		}
		//清空rowClick数组
		rowClick.length = 0;
		//更新目录结构
		getCollectLiInfo(filterFolderIds, filterFolderNames);
	});
}

/**
 * 更新我的收藏列表
 */
function refreshCollectFileTable(){
	var currentFolderId = $("#currentFolderId").val();
	if(isEmpty(currentFolderId)){
		$("#tb_collectfileInfo").bootstrapTable('refresh');
	}else{
		var opt = { 
				url: "../collect/collectTbInit.do",
				silent: false, //刷新事件必须设置
				query:{ 
					fileObjId : currentFolderId,
					rootFolderId : myCollectFolderIds.length!=0 ? myCollectFolderIds[0] : ""
				} 
		}; 
		$("#tb_collectfileInfo").bootstrapTable('refresh', opt);
		//清空rowClick数组
		rowClick.length = 0;
	}
}

/**
 * 界面文件下载操作
 */
$("#collectionBtn_download").click(function() {
	downloadCollectFile();
	//清空rowClick数组
	rowClick.length = 0;
});

function downloadCollectFile(){
	var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
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
	var folderFlag = false;
	for (var i = 0; i < rows.length; i++) {	
		if(isEmpty(rows[i].filegroupname)){
			folderFlag = true;
			break;
		}
	}
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
					creatCollectDownLoadForm();
				}
			}
		});	
	}else{
		//没有文件夹情况直接进行下载
		creatCollectDownLoadForm();
	}
}

/**
 * 设置下载参数
 */
function creatCollectDownLoadForm(){
	//定义form表单
	var form = $("<form>");
	form.attr("style", "display:none");
	form.attr("target", "");
	form.attr("method", "post");
	var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
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
 * 点击更新按钮
 */
$('#collectRefreshTable').click(function(){
	collectFileSearch();
});

/**
 * 搜索功能
 */
$("#collectFileSearch").bind("input propertychange", function(){
	collectFileSearch();
});

function collectFileSearch(){
	if(isEmpty($.trim($("#collectFileSearch").val()))){
		refreshCollectFileTable();
		return;
	}
	var opt = { 
			url: "../collect/querySearchInfo.do",
			silent: false, //刷新事件必须设置
			query:{ 
				search : $.trim($("#collectFileSearch").val()),
			} 
	};
	
	$("#tb_collectfileInfo").bootstrapTable('refresh', opt);
}

/**
 * 取消收藏功能
 */
$("#collectionBtn_cancel").click(function() {
	cancelCollectFile();
});

function cancelCollectFile(){
	var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title: "提示",
			size: "small",
			message: "请选择需要取消收藏的对象!",
			buttons:{
				ok:{
					label:"确认"
				}
			}
		});
		return;
	}
	if ( !isEmpty($("#currentFolderId").val()) ){
		bootbox.alert({
			title: "提示",
			size: "small",
			message: "不能取消收藏子文件!",
			buttons:{
				ok:{
					label:"确认"
				}
			}
		});
		return;
	}
	bootbox.confirm({
		title : "取消收藏",
		message : "确认将执行取消收藏操作？",
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
				var rows = $("#tb_collectfileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../collect/cancelMyCollection.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if (data.result) {
							bootbox.alert({
								title:"提示",
								message:"取消收藏操作成功",
								size:"small",
								buttons:{
									ok:{
										label:"确认"
									}
								}
							});
							refreshCollectFileTable();
						}
					},
					error : function() {
						message = "取消收藏操作发生未知错误，请稍后再试！";
					}
				});
			}
		}
	});
}
