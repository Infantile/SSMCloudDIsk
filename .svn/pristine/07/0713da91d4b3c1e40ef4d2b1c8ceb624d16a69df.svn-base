/**
 * 
 */
$(function(){
	initGroupContextMenu();
	$("#createGroupModal.modal,#groupInfoModal.modal,#changeGroupInfoModal.modal,#shareFileModal.modal").on('show.bs.modal', centerModals());
	$(window).on('resize', centerModals);
});

var leftList = new Array();
var rightList = new Array();

$("#changeGroupInfoModal").on("shown.bs.modal", function() {
	leftList = getAllMemUserId("tb_allMember");
	rightList = getAllMemUserId("tb_allGrpMember");
});

$("#createGroupModal").on("shown.bs.modal", function(){
	leftList = getAllMemUserId("tb_allCrtMember");
	rightList = getAllMemUserId("tb_allCrtGrpMember");
});

/**
 * 模态框关闭后清空输入框
 */
$("#changeGroupNameModal").on("hidden.bs.modal", function() {
    $(this).find("#inGroupNewName").val("");
});

$("#changeGroupInfoModal,#groupInfoModal,#createGroupModal").on("hidden.bs.modal",function(){
	$(this).find("input.form-control").val("");
});

/**
 * 初始化右键功能
 */
function initGroupContextMenu(){
	$.contextMenu({
		selector : "#tb_groupFileInfo td",
		items : {
			btn_changeMember : {
				name : "新建群组",
				callback : function() {
					$("#createGroupModal").modal('show');
					getAllCrtMemberInfo();
					$("#tb_allCrtMember").bootstrapTable('refresh');
					getAllCrtGrpMemberInfo();
					$("#tb_allCrtGrpMember").bootstrapTable('refresh');
				}
			},
			btn_deleteGroup : {
				name : "删除",
				callback : function(key, opt) {
					validPermission();
				}
			},
			btn_changeGrpName : {
				name : "修改群名称",
				callback : function(key, opt) {
					changeGroupName();
				}
			},
		},
		autoHide: true
	});
}

/**
 * 绑定新建群组按钮的点击事件
 */
$("#btn_new").on('click',function(){
	var $modal = $("#createGroupModal");
	$modal.modal('show');
	$modal.find("input.name-input").parent().removeClass("has-error");
	$modal.find("div.input-valid").addClass("hidden");
	getAllCrtMemberInfo();
	$("#tb_allCrtMember").bootstrapTable('refresh');
	getAllCrtGrpMemberInfo();
	$("#tb_allCrtGrpMember").bootstrapTable('refresh');
});

/**
 * 绑定修改组名按钮点击事件
 */
$("#btn_changeGrpName").on("click", function(){
	changeGroupName();
});

/**
 * 绑定删除群组按钮的点击事件
 */
$("#btn_delete").on("click",function(){
	validPermission();
});

/**
 * 搜索事件
 */
$("#groupManageTab").find("input.form-control").on("input propertychange",function(){
	var opt = {
			url: "../group/queryGroup.do",
			silent: true, //刷新事件必须设置
			query:{
				search : $(this).val(),
			} 
	};
	$('#tb_groupFileInfo').bootstrapTable('refresh');
	$('#tb_groupFileInfo').bootstrapTable('refresh',opt);
});

/**
 * 绑定刷新按钮
 */
$("#refreshGroupTable").on("click",function(){
	$("#tb_groupFileInfo").bootstrapTable('refresh');
	var opt = {
		url: '../group/queryGroup.do',
		silent: true
	};
	$("#tb_groupFileInfo").bootstrapTable('refresh',opt);
	//清空rowClick数组
	rowClick.length = 0;
});

/**
 * 绑定创建群组中创建按钮的点击事件
 */
$("#create_btn").on("click",function(){
	if (checkInput($("#inGroupName").val(),"createGroupModal")){
		var userObj = new Object();
		userObj.groupName = $("#inGroupName").val();;
		userObj.userID = getAllMemUserId("tb_allCrtGrpMember");
		var userJson = JSON.stringify(userObj);
		$.ajax({
			data : {
				userJson : userJson,
			},
			type : 'post',
			url : "../group/createGroup.do",
			success :function(data){
				if (data.result){
					$("#createGroupModal").modal('hide');
					refreshTable('tb_groupFileInfo');
					bootbox.alert({
						title : '提示',
						size : 'small',
						message : "群组创建成功!",
						buttons:{
							ok:{
								label:"确定"
							}
						}
					});
				}else{
					bootbox.alert({
						title : '提示',
						size : 'small',
						message : "群组创建失败，请重试!",
						buttons:{
							ok:{
								label:"确定"
							}
						}
					});
				}
			}
		});
	}
});

/**
 * 群组信息确认提交按钮
 */
$("#sureChange_btn").on("click",function(){
	bootbox.confirm({
		title:'修改成员',
		size:'small',
		message : '确定要提交修改信息吗?',
		buttons : {
			cancel : {
				label : '取消'
			},
			confirm : {
				label : '确定'
			},
		},
		callback : function(result){
			if (result){
				var userObj = new Object();
				userObj.newGrpUserID =  getAllMemUserId("tb_allGrpMember");
				userObj.groupID = $("#changeGroupInfoModal").find("label.hide").text();
				var userJson = JSON.stringify(userObj);
				//alert(userJson);
				$.ajax({
					data : {
						userJson : userJson,
					},
					type : 'post',
					url : "../group/changeGroupMem.do",
					success :function(data){
						if (data.result){
							$("#changeGroupInfoModal").modal('hide');
							bootbox.alert({
								title : '提示',
								size : 'small',
								message : "成员信息修改成功!",
								buttons:{
									ok:{
										label:"确定"
									}
								}
							});
						}else{
							bootbox.alert({
								title : '提示',
								size : 'small',
								message : "成员信息修改失败，请重试!",
								buttons:{
									ok:{
										label:"确定"
									}
								}
							});
						}
					}
				});
			}
		}
	});
});

/**
 * 修改群组名中确认按钮
 */
$("#sure_btn").on("click",function(){
	if ($("#inGroupNewName").val() === $("#changeGroupNameModal").find("h4.modal-title").text()){
		$("#inGroupNewName").parent().addClass("has-error");
		$("#nameErrMsg").removeClass("hidden");
		$("#nameErrMsg").text("新群组名和原群组名相同，请重新修改!");
		return false;
	}
	if (checkInput($("#inGroupNewName").val(),"changeGroupNameModal")){
		$.ajax({
			data:{
				groupID : $("#changeGroupNameModal").find("div.modal-header label").text(),
				newName : $("#inGroupNewName").val()
			},
			url: '../group/changeGroupName.do',
			type: 'post',
			aysnc: true,
			success: function(data){
				if (!isEmpty(data.result)){
					$("#changeGroupNameModal").modal('hide');
					refreshTable('tb_groupFileInfo');
				}else{
					bootbox.alert({
						title: '提示',
						size: 'small',
						message: '修改失败，请重试!',
						buttons:{
							ok:{
								label:"确定"
							}
						}
					});
				}
			}
		});
	}
});

/**
 * 群组信息中退出群组事件监听
 */
$("#exitGroup_btn").on("click",function(){
	bootbox.confirm({
		title:'退出群组',
		size:'small',
		message : '确定要退出群组吗?',
		buttons : {
			confirm : {
				label : '确定'
			},
			cancel : {
				label : '取消'
			}
		},
		callback : function(result){
			if (result){
				$.ajax({
					url:'../group/quitGroup.do',
					data: {
						groupID :$("#groupInfoModal").find(".modal-header label").text(),
						},
					type : 'post',
					async : true,
					success: function(data){
						if (!isEmpty(data)){
							refreshTable('tb_groupFileInfo');
							$("#groupInfoModal").modal('hide');
							bootbox.alert({
								title: '提示',
								size: 'small',
								message: '退出成功!',
								buttons:{
									ok:{
										label:"确定"
									}
								}
							});
							refreshTable('tb_groupFileInfo');
						}else {
							bootbox.alert({
								title: '提示',
								size: 'small',
								message: '退出失败!',
								buttons:{
									ok:{
										label:"确定"
									}
								}
							});
						}
					}
				});
			}
		}
	});
});

/**
 * 双击Table的某一列触发事件
 */
$("#tb_groupFileInfo").on("dbl-click-row.bs.table",function(e, row, $element) {
	//queryAllGroupMember(row);
	//alert(isGroupBoss(row.userLoginID));
	if (isGroupBoss(row.userLoginID)){
		$("#changeGroupInfoModal").find("label.hide").text(row.groupid);
		$("#changeGroupInfoModal").modal('show');
		getAllGrpMemberInfo();
		$("#groupModalRightTab").show();
		getAllMemberInfo();
		$("#groupModalLeftTab").show();
		$("#tb_allGrpMember").bootstrapTable('refresh');
		$("#tb_allMember").bootstrapTable('refresh');
	}else{
		$("#groupInfoModal").find("label.hide").text(row.groupid);
		$("#groupInfoModal").modal('show');
		$("#exitGroup_btn").show();
		getGrpMemberInfo();
		$("#grpInfoModalRightTab").show();
		$("#tb_grpMember").bootstrapTable('refresh');
	}
	//清空rowClick数组
	rowClick.length = 0;
});

/**
 * 验证是否是群主
 * @param groupId
 */
function isGroupBoss(userLoginID){
	var result = false;
	$.ajax({
		url: '../group/getPresentUserLoginId.do',
		type: 'post',
		async: false,
		success:function(data){
			if (!isEmpty(data.result)){
				if (data.rows === userLoginID){
					result = true;
				}
			}
		}
	});
	return result;
}

/**
 * 检验删除权限以及添加提示语
 * @returns {Boolean}
 */
function validPermission(){
	var allSlectLineData = $("#tb_groupFileInfo").bootstrapTable('getSelections');
	if (allSlectLineData.length === 0){
		bootbox.alert({
			title: '提示',
			size: 'small',
			message: '请选择需要删除的群组!',
			buttons:{
				ok:{
					label:"确定"
				}
			}
		});
		return false;
	}
	$.ajax({
		url : '../group/getPresentUserLoginId.do',
		type : 'post',
		async : true,
		success :function(data){
			if (!isEmpty(data)){
				var isAllCanDel = true;
				var tipInfo = "";
				
				var displayGrpNum = 0;
				var ajaxGroupId = new Array();
				//给confirm提示语句赋值
				for (var i = 0;i < allSlectLineData.length;i++){
					if (allSlectLineData[i].userLoginID === data.rows){
						ajaxGroupId[displayGrpNum++] = allSlectLineData[i].groupid;
						if (displayGrpNum <= 5){
							tipInfo += allSlectLineData[i].groupname +"、";
						}else if (displayGrpNum == 6){
							tipInfo = tipInfo.substring(0, tipInfo.length-1);
							tipInfo += " 等群组吗?";
						}
					}else {
						isAllCanDel = false;
					}
					if (i == allSlectLineData.length - 1){
						if (displayGrpNum <= 5){
							tipInfo = tipInfo.substring(0, tipInfo.length-1);
							tipInfo += " 吗?";
						}
					}
					
				}
				
				var messageStr;
				if (isAllCanDel == false){
					messageStr = '只能删除已选项中创建者为本人(' + data.rows +')的群组，点击确认继续执行删除操作?';
				}else {
					messageStr =  '确定解散群组 ' + tipInfo;
				}
				bootbox.confirm({
					title:'删除群组',
					size:'small',
					message : messageStr,
					buttons : {
						confirm : {
							label : '确定'
						},
						cancel : {
							label : '取消'
						}
					},
					callback :function(result){
						if (result){
							deleteGroup(ajaxGroupId);
						}
					}
				});
			}else {
				bootbox.alert({
					title: '提示',
					size: 'small',
					message: '删除失败!',
					buttons:{
						ok:{
							label:"确定"
						}
					}
				});
			}
		}
	});
}

function changeGroupName(){
	var allSlectLineData = $("#tb_groupFileInfo").bootstrapTable('getSelections');
	if (allSlectLineData.length === 1){
		if (isGroupBoss(allSlectLineData[0].userLoginID)){
			$("#changeGroupNameModal").modal('show');
			$("#changeGroupNameModal").find("h4.modal-title").text(allSlectLineData[0].groupname);
			$("#changeGroupNameModal").find("div.modal-header label").text(allSlectLineData[0].groupid);
			$("#inGroupNewName").parent().removeClass("has-error");
			$("#nameErrMsg").addClass("hidden");
		}else{
			bootbox.alert({
				title: '提示',
				size: 'small',
				message: '所选的群组非本人创建，无法修改群名称!',
				buttons:{
					ok:{
						label:"确定"
					}
				}
			});
			return false;
		}
	}
	
	if (allSlectLineData.length === 0){
		bootbox.alert({
			title: '提示',
			size: 'small',
			message: '请选择需要修改群名称的群组!',
			buttons:{
				ok:{
					label:"确定"
				}
			}
		});
		return false;
	}else if(allSlectLineData.length > 1){
		bootbox.alert({
			title: '提示',
			size: 'small',
			message: '每次只能修改一个群组的名称!',
			buttons:{
				ok:{
					label:"确定"
				}
			}
		});
		return false;
	}
}

/**
 * 删除群组功能实现
 * @param ajaxGroupId
 */
function deleteGroup(ajaxGroupId){
	var groupObj = new Object();
	groupObj.groupID = ajaxGroupId;
	var groupJson = JSON.stringify(groupObj);
	$.ajax({
		url:'../group/deleteGroup.do',
		data: {
			groupID: groupJson,
			},
		type : 'post',
		async : true,
		success :function (data){
			$('#tb_groupFileInfo').bootstrapTable('refresh');
			if (data.result == true && ajaxGroupId.length > 0){
				$('#tb_groupFileInfo').bootstrapTable('refresh');
			}else{
				bootbox.alert({
					title: '提示',
					size: 'small',
					message: '操作失败，请重试!',
					buttons:{
						ok:{
							label:"确定"
						}
					}
				});
			}
		}
	});
}

/**
 * 验输入的群组名称是否合格
 */
function checkInput(inputVal,modalName){
	var $errorDiv = $("#"+modalName).find("input.name-input").parent();
	var $nameInput = $("#"+modalName).find("div.input-valid");
	if (inputVal.length === 0){
		$errorDiv.addClass("has-error");
		$nameInput.text("请输入组名!").removeClass("hidden");
		return false;
	}else if (inputVal.length > 20){
		$errorDiv.addClass("has-error");
		$nameInput.text("组名过长(已超过20位字符)!").removeClass("hidden");
		return false;
	}else{
		return true;
	}
}

$("#addToCrtGroup_btn").on("click",function(){
	var userSelctData = $("#tb_allCrtMember").bootstrapTable('getSelections');
	for (var i=0;i<userSelctData.length;i++){
		rightList.push(userSelctData[i].userid);
		leftList.splice($.inArray(userSelctData[i].userid, leftList) , 1);
		$("#tb_allCrtMember").bootstrapTable("removeByUniqueId",userSelctData[i].userid);
	}
	$("#crtGroupModalRightTab").find("input.form-control").val("");
	var objRight = new Object();
	objRight.userid = rightList;
	var jsonRight = JSON.stringify(objRight);
	refreshCrtRight(jsonRight);
});

$("#removeFromCrtGroup_btn").on("click",function(){
	var userSelctData = $("#tb_allCrtGrpMember").bootstrapTable('getSelections');
	for (var i=0;i<userSelctData.length;i++){
		leftList.push(userSelctData[i].userid);
		rightList.splice($.inArray(userSelctData[i].userid, rightList) , 1);
		$("#tb_allCrtGrpMember").bootstrapTable("removeByUniqueId",userSelctData[i].userid);
	}
	$("#crtGroupModalLeftTab").find("input.form-control").val("");
	var objLeft = new Object();
	objLeft.userid = leftList;
	var jsonLeft = JSON.stringify(objLeft);
	refreshCrtLeft(jsonLeft);
});

/**
 * 创建群组左表刷新
 * @param data
 */
function refreshCrtLeft(data){
	var optLeft = {
			url: "../group/queryLeftMember.do",
			silent: true, //刷新事件必须设置
			query:{
				ajaxUserId : data,
			} 
	};
	$("#tb_allCrtMember").bootstrapTable('refresh', optLeft);
}

/**
 * 创建群组右表刷新
 * @param data
 */
function refreshCrtRight(data){
	var optRight = {
			url: "../group/queryRightMember.do",
			silent: true, //刷新事件必须设置
			query:{
				ajaxUserId : data,
			} 
	};
	$("#tb_allCrtGrpMember").bootstrapTable('refresh', optRight);
}

/**
 * 创建群组modal框右边表格的搜索实现
 */
$("#crtGroupModalRightTab").find("input.form-control").on("input propertychange", function(){
	var userObj = new Object();
	userObj.userid = rightList;
	var userJson = JSON.stringify(userObj);
	var opt = {
			url: "../group/queryRightMember.do",
			silent: true, //刷新事件必须设置
			query:{ 
				ajaxUserId : userJson,
				search : $(this).val()
			} 
	};
	$("#tb_allCrtGrpMember").bootstrapTable('refresh', opt);
});


/**
 * 创建群组modal框左边表格的搜索实现
 */
$("#crtGroupModalLeftTab").find("input.form-control").on("input propertychange", function(){
	var userObj = new Object();
	userObj.userid = leftList;
	var userJson = JSON.stringify(userObj);
	//alert(userJson);
	var opt = { 
			url: "../group/queryLeftMember.do",
			silent: true, //刷新事件必须设置
			query:{ 
				ajaxUserId : userJson,
				search : $(this).val()
			} 
	};
	$("#tb_allCrtMember").bootstrapTable('refresh');
	$("#tb_allCrtMember").bootstrapTable('refresh',opt);
});


/**
 * 群组修改modal框右边表格的搜索实现
 */
$("#groupModalRightTab").find("input.form-control").on("input propertychange", function(){
	//alert(getAllMemUserId("tb_allGrpMember"));
	var userObj = new Object();
	userObj.userid = rightList;
	var userJson = JSON.stringify(userObj);
	var opt = {
			url: "../group/queryRightMember.do",
			silent: true, //刷新事件必须设置
			query:{ 
				ajaxUserId : userJson,
				search : $(this).val()
			} 
	};
	$("#tb_allGrpMember").bootstrapTable('refresh', opt);
});


/**
 * 群组修改modal框左边表格的搜索实现
 */
$("#groupModalLeftTab").find("input.form-control").on("input propertychange", function(){
	//alert(getAllMemUserId("tb_allMember"));
	var userObj = new Object();
	userObj.userid = leftList;
	var userJson = JSON.stringify(userObj);
	//alert(userJson);
	var opt = { 
			url: "../group/queryLeftMember.do",
			silent: true, //刷新事件必须设置
			query:{ 
				ajaxUserId : userJson,
				search : $(this).val()
			} 
	};
	$("#tb_allMember").bootstrapTable('refresh');
	$("#tb_allMember").bootstrapTable('refresh',opt);
});

/**
 * 群组修改右移的实现
 */
$("#addToGroup_btn").on("click",function(){
	var userSelctData = $("#tb_allMember").bootstrapTable('getSelections');
	for (var i=0;i<userSelctData.length;i++){
		rightList.push(userSelctData[i].userid);
		leftList.splice($.inArray(userSelctData[i].userid, leftList) , 1);
		$("#tb_allMember").bootstrapTable("removeByUniqueId",userSelctData[i].userid);
	}
	$("#groupModalRightTab").find("input.form-control").val("");
	var objRight = new Object();
	objRight.userid = rightList;
	var jsonRight = JSON.stringify(objRight);
	refreshRight(jsonRight);
});

/**
 * 群组修改左移的实现
 */
$("#removeFromGroup_btn").on("click",function(){
	var userSelctData = $("#tb_allGrpMember").bootstrapTable('getSelections');
	for (var i=0;i<userSelctData.length;i++){
		leftList.push(userSelctData[i].userid);
		rightList.splice($.inArray(userSelctData[i].userid, rightList) , 1);
		$("#tb_allGrpMember").bootstrapTable("removeByUniqueId",userSelctData[i].userid);
	}
	$("#groupModalLeftTab").find("input.form-control").val("");
	var objLeft = new Object();
	objLeft.userid = leftList;
	var jsonLeft = JSON.stringify(objLeft);
	refreshLeft(jsonLeft);
});

/**
 * 群组修改右表刷新的实现
 * @param data
 */
function refreshLeft(data){
	var optLeft = {
			url: "../group/queryLeftMember.do",
			silent: true, //刷新事件必须设置
			query:{
				ajaxUserId : data,
			} 
	};
	$("#tb_allMember").bootstrapTable('refresh', optLeft);
}

/**
 * 群组修改左表刷新的实现
 * @param data
 */
function refreshRight(data){
	var optRight = {
			url: "../group/queryRightMember.do",
			silent: true, //刷新事件必须设置
			query:{
				ajaxUserId : data,
			} 
	};
	$("#tb_allGrpMember").bootstrapTable('refresh', optRight);
}

/**
 * 获得当前群组成员的userid
*/
function getSectMemUserId(id){
	var addSelectionsData = $("#" + id).bootstrapTable('getSelections');
	//alert(JSON.stringify(addSelectionsData));
	var ajaxUserId = new Array();
	for (var i=0;i < addSelectionsData.length;i++){
		ajaxUserId[i] = addSelectionsData[i].userid;
	}
	return ajaxUserId;
};

/**
 * 获得表中所有成员的userid
 * @param id
 * @returns {Array}
 */
function getAllMemUserId(id){
	var allData = $("#" + id).bootstrapTable('getData');
	//alert(JSON.stringify(addSelectionsData));
	var ajaxUserId = new Array();
	for (var i=0;i < allData.length;i++){
		ajaxUserId[i] = allData[i].userid;
	}
	return ajaxUserId;
}

/**
 * 群组查看搜索框的实现
 */
$("#groupInfoModal").find("input.form-control").on("input propertychange", function(){
	//alert(getAllMemUserId("tb_allMember"));
	var opt = { 
			url: "../group/queryRightMember.do",
			silent: true, //刷新事件必须设置
			query:{ 
				search : $(this).val()
			} 
	};
	$("#tb_grpMember").bootstrapTable('refresh',opt);
});

/**
 * 成员修改左表初始化
 */
function getAllMemberInfo() {
	$('#tb_allMember').bootstrapTable({
		url : '../group/queryLeftMember.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : false, //用 是否启排序
		sortStable : true,
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "userid", // 每一行的唯一标识，一般为主键列
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'userid',
			visible: false,
		},{
			field : 'userLoginID',
			title : '帐号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'username',
			title : '姓名',
			align : 'center',
			sortable : true,
		}],
		queryParams : function getParams(params) {
			var queryParams = params;
			queryParams.groupID = $("#changeGroupInfoModal").find("label.hide").text();
			return queryParams;
		},
		silent : true, // 刷新事件必须设置
		formatLoadingMessage : function() {
			return "载入成员信息中......";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '未查询到成员';
		},
		onLoadError : function(data) {
			$('#tb_allMember').bootstrapTable('removeAll');
		}
	});
}

/**
 * 成员修改右表初始化
 */
function getAllGrpMemberInfo() {
	$('#tb_allGrpMember').bootstrapTable({
		url : '../group/queryRightMember.do', // 请求后台的URL（*）			
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
		uniqueId : "userid", // 每一行的唯一标识，一般为主键列
		queryParams : function getParams(params) {
			var queryParams = params;
			queryParams.groupID = $("#changeGroupInfoModal").find("label.hide").text();
			return queryParams;
		},
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'userid',
			visible: false,
		},{
			field : 'userLoginID',
			title : '帐号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'username',
			title : '姓名',
			align : 'center',
			valign : 'middle',
			sortable : true,
		}],
		silent : true, // 刷新事件必须设置
		formatLoadingMessage : function() {
			return "载入成员信息中......";
		},
		formatNoMatches : function() { // 没有匹配的结果
			return '未查询到群组成员';
		},
		onLoadError : function(data) {
			$('#tb_allGrpMember').bootstrapTable('removeAll');
		},
		onLoadSuccess : function(){
			leftListUserId = getAllMemUserId("tb_allMember");
		}
	});
}

/**
 * 创建群组左表的初始化
 */
function getAllCrtMemberInfo(){  
	$('#tb_allCrtMember').bootstrapTable({
		url : '../group/queryAllMember.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : false, //用 是否启排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "userid", // 每一行的唯一标识，一般为主键列
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'userid',
			visible: false,
		},{
			field : 'userLoginID',
			title : '帐号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'username',
			title : '姓名',
			align : 'center',
			sortable : true,
		}],
		silent : true, // 刷新事件必须设置
		formatNoMatches : function() { // 没有匹配的结果
			return '未查询到成员';
		},
		onLoadError : function(data) {
			$('#tb_allCrtMember').bootstrapTable('removeAll');
		},
	});
}

/**
 * 创建群组右表的初始化
 * @param groupId
 */
function getAllCrtGrpMemberInfo(groupId){
	$('#tb_allCrtGrpMember').bootstrapTable({
		url : '../group/queryRightMember.do', // 请求后台的URL（*）			
		method : 'get', // 请求方式（*）
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		sortable : false, //用 是否启排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : false, // 是否显示分页（*）
		onlyInfoPagination:false, //设置为 true 只显示总数据数，而不显示分页
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : true,
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "userid", // 每一行的唯一标识，一般为主键列
		queryParams : function getParams(params) {
			var queryParams = params;
			queryParams.groupID = groupId;
			return queryParams;
		},
		columns : [ {
			align : 'middle',
			checkbox : true
		},{
			field : 'userid',
			visible: false,
		},{
			field : 'userLoginID',
			title : '帐号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'username',
			title : '姓名',
			align : 'center',
			valign : 'middle',
			sortable : true,
		}],
		silent : true, // 刷新事件必须设置
		formatNoMatches : function() { // 没有匹配的结果
			return '未查询到群组成员';
		},
		onLoadError : function(data) {
			$('#tb_allCrtGrpMember').bootstrapTable('removeAll');
		},
	});
}

/**
 * 成员查看表初始化
 */
function getGrpMemberInfo() {
	$('#tb_grpMember').bootstrapTable({
		url : '../group/queryRightMember.do', // 请求后台的URL（*）			
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
		uniqueId : "userid", // 每一行的唯一标识，一般为主键列
		queryParams : function getParams(params) {
			var queryParams = params;
			queryParams.groupID = $("#groupInfoModal").find("label.hide").text();
			return queryParams;
		},
		columns : [{
			field : 'userid',
			visible: false,
		},{
			field : 'userLoginID',
			title : '帐号',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			field : 'username',
			title : '姓名',
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				return formatTableOperatorName(value);
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
			$('#tb_grpMember').bootstrapTable('removeAll');
		}
	});
}

function centerModals(){
	$('#createGroupModal.modal,#groupInfoModal.modal,#changeGroupInfoModal.modal,#shareFileModal.modal').each(function(i){
		var $clone = $(this).clone().css('display', 'block').appendTo('body');
		var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
		top = top > 0 ? top : 0;
		$clone.remove();
		$(this).find('.modal-dialog').css("margin-top", top);
	});
}