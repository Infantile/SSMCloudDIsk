$(function() {
	initRecycleContextMenu();
});

function initRecycleContextMenu(){
	$.contextMenu({
		selector : "#tb_recyclefileInfo td",
		items : {
			btn_restore : {
				name : "恢复",
				callback : function(key, opt) {
					restoreDeletedFile();
				}
			},
			btn_destory : {
				name : "彻底删除",
				callback : function(key, opt) {
					destoryDeletedFile();
				}
			},
			btn_destoryAll : {
				name : "清空回收站",
				callback : function(key, opt) {
					destoryAllDeletedFile();
				}
			},
		},
		autoHide: true
	});
}

/**
 * 搜索功能
 */
$("#recycleFileSearch").bind("input propertychange", function(){
	recycleFileSearch();
});

function recycleFileSearch(){
	var opt = { 
			url: "../recycle/querySearchInfo.do",
			silent: false, //刷新事件必须设置
			query:{ 
				search : $.trim($("#recycleFileSearch").val())
			} 
	};
	
	$("#tb_recyclefileInfo").bootstrapTable('refresh', opt);
}

/**
 * 恢复文件功能
 */
$("#recycleBtn_restore").click(function() {
	restoreDeletedFile();
});

function restoreDeletedFile(){
	var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:"提示",
			message:"请选择需要恢复的对象!",
			size:"small",
			buttons:{
				ok:{
					label:"确认"
				}
			}
		});
		return;
	}
	bootbox.confirm({
		title : "恢复文件",
		size:"small",
		message : "确认将执行恢复操作？",
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
				var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
				var jsonRows = JSON.stringify(rows);
				$.ajax({
					url : "../recycle/restoreFile.do",
					dataType : "json",
					async : true,
					data : {
						rows : jsonRows
					},
					type : "post",
					success : function(data) {
						if(!isEmpty(data.error)){
							bootbox.alert({
								title:"提示",
								size:'small',
								message:"以下文件: "+data.error.toString()+" 还原失败！",
								buttons:{
									ok:{
										label:"确认"
									}
								}
							});
						}
						if(!isEmpty(data.root)){
							bootbox.alert({
								title:"提示",
								size: 'small',
								message: "以下文件: "+data.root.toString()+" 还原到了我的文件根目录！",
								buttons:{
									ok:{
										label:"确认"
									}
								}
							});
						}
						if(!isEmpty(data.other)){
							bootbox.alert({
								title:'提示',
								size:'middle',
								message:"以下父目录: "+data.other.toString()+" 必须得被先还原，才能还原其子文件！",
								buttons:{
									ok:{
										label:"确认"
									}
								}
							});
						}
						if(!isEmpty(data.success)){
							bootbox.alert({
								title:'提示',
								size:'small',
								message:"文件: "+data.success.toString()+" 还原成功！",
								buttons:{
									ok:{
										label:"确认"
									}
								}
							});
						}
						$("#tb_recyclefileInfo").bootstrapTable('refresh');
					},
					error : function() {
						message = "还原文件操作发生未知错误，请稍后再试！";
					}
				});
				var opt = { 
						url: "../recycle/recycleTbInit.do",
						silent: false, 
						query:{} 
				}; 
				$("#tb_recyclefileInfo").bootstrapTable('refresh', opt); 
				//清空rowClick数组
				rowClick.length = 0;
			}
		}
	});
}

/**
 * 点击更新按钮
 */
$('#recycleRefreshTable').click(function(){
	recycleFileSearch();
	//清空rowClick数组
	rowClick.length = 0;
});

/**
 * 彻底删除文件
 */
$("#recycleBtn_destory").click(function() {
	destoryDeletedFile();
});

/**
 * 彻底删除文件
 */
$("#recycleBtn_destoryAll").click(function() {
	destoryAllDeletedFile();
});

function destoryDeletedFile(){
	//判断是否已经选择了共享文件
	var rows = $("#tb_recyclefileInfo").bootstrapTable('getSelections');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "请先选择彻底删除对象!",
			size: "small"
		});
		return;
	}	
	bootbox.confirm({
		title : "删除文件",
		message : "确认将执行彻底删除操作？",
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
				var info="";
				for (var i = 0; i < rows.length; i++) {
					$.ajax({
						url : "../recycle/absolutelyDeleteFile.do",
						dataType : "json",
						async : false,
						data : {
							fileID : rows[i].fileid
						},
						type : "post",
						success : function(data) {					
							if (data.result) {
								//删除文件成功
								queryCloudDiskInfo();
							}
							else{
								//删除文件失败
								info=info+" "+rows[i].fileName;
							}							
						},
						error : function() {
							message = "删除文件"+rows[i].filename+"失败，请稍后再试！";
						}
					});
				}
				if(!isEmpty(info)){
					bootbox.alert({
						title:'提示',
						size:'small',
						message:"删除文件: "+info+" 失败!",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}else{
					bootbox.alert({
						title:'提示',
						size:'small',
						message:"删除文件成功!",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}
				var opt = { 
						url: "../recycle/recycleTbInit.do",
						silent: false, 
						query:{} 
				}; 
				$("#tb_recyclefileInfo").bootstrapTable('refresh', opt); 
				//清空rowClick数组
				rowClick.length = 0;
			}
		}
	});
}

function destoryAllDeletedFile(){
	//判断是否已经选择了共享文件
	var rows = $("#tb_recyclefileInfo").bootstrapTable('getData');
	if(rows.length < 1){
		bootbox.alert({
			title:'提示',
			buttons: {
				ok:{
					label: "确认",
					className: "btn-primary",
				}
			},
			message: "回收站空空如也,无需清空",
			size: "small"
		});
		return;
	}	
	bootbox.confirm({
		title : "清空回收站",
		message : "确认清空回收站？(删除回收站所有文件)",
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
				var info="";
				for (var i = 0; i < rows.length; i++) {
					$.ajax({
						url : "../recycle/absolutelyDeleteFile.do",
						dataType : "json",
						async : false,
						data : {
							fileID : rows[i].fileid
						},
						type : "post",
						success : function(data) {					
							if (data.result) {
								//删除文件成功
								queryCloudDiskInfo();
							}
							else{
								//删除文件失败
								info=info+" "+rows[i].fileName;
							}							
						},
						error : function() {
							message = "删除文件"+rows[i].filename+"失败，请稍后再试！";
						}
					});
				}
				if(!isEmpty(info)){
					bootbox.alert({
						title:'提示',
						size:'small',
						message:"删除文件 : "+info+" 失败!",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}else{
					bootbox.alert({
						title:'提示',
						size:'small',
						message:"清空回收站成功!",
						buttons:{
							ok:{
								label:"确认"
							}
						}
					});
				}
				var opt = { 
						url: "../recycle/recycleTbInit.do",
						silent: false, 
						query:{} 
				}; 
				$("#tb_recyclefileInfo").bootstrapTable('refresh', opt); 
				//清空rowClick数组
				rowClick.length = 0;
			}
		}
	});
}