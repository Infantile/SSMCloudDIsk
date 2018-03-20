$(function() {
	queryCloudDiskInfo();
});

/**
 * enter键也实现登录提交
 */
$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		login();
	}
});

/**
 * 登录按钮
 */
$("#btn_login").click(function() {
	login();
});

/**
 * 登录实现
 */
function login() {
	$("#pwdDiv").removeClass("has-error");
	$("#errMsg").addClass("hidden");
	var userLoginID = $("#userLoginID").val();
	var password = $("#password").val();
	//判断用户名密码是否为空
	if (isEmpty(userLoginID)) {
		$("#accountDiv").addClass("has-error");
		$("#errMsg").removeClass("hidden");
		$("#errMsg").text("请输入用户名!");
		$("#userLoginID").focus();
	} else if (isEmpty(password)) {
		$("#pwdDiv").addClass("has-error");
		$("#errMsg").removeClass("hidden");
		$("#errMsg").text("请输入密码!");
		$("#password").focus();
	} else {
		$.ajax({
			url : "userManage/check.do", 			//请求的url地址
			dataType : "json", 				//返回格式为json
			async : true, 					//请求是否异步，默认为异步，这也是ajax重要特性
			data : {						//参数值
				userLoginID : $("#userLoginID").val(), 
				password : $("#password").val()
			},
			type : "post", 					//请求方式
			beforeSend : function() {
				//请求前的处理
			},
			success : function(data) {
				//请求成功时处理
				if (!isEmpty(data.result)) {
					$("#pwdDiv").addClass("has-error");
					$("#errMsg").removeClass("hidden");
					$("#errMsg").text(data.result);					
				} else {
					$("#loginForm").submit();
				}
			},
			complete : function() {
				//请求完成的处理
			},
			error : function() {
				//请求出错处理
			}
		});
	}
}
/**
 * 退出鼠标移动改变字体颜色问题
 */
$("#logOut").mouseover(function() {
	$("#logOutFont").addClass('mouse-on-title');
	$(this).css("cursor","pointer");
}).mouseout(function() {
	$("#logOutFont").removeClass('mouse-on-title');
	$(this).css("cursor","default");
});
/**
 * 退出操作
 */
$("#logOut , #logOutPic").click(function(){
	bootbox.confirm({
		title:"退出",
		size: "small", 
		buttons : {
			confirm : {
				label : '确认'
			},
			cancel : {
				label : '取消'
			}
		},
		message: '<div style="padding:8px;display:table-cell;text-align:center;vertical-align:middle"><span style="font-size:18px;" class="fa fa-sign-out"></span></div><div style="padding:3px;display:table-cell;vertical-align:middle">请确认是否退出?</div>', 
		callback: function(result){ 
			if(result){
				$.ajax({
					data : {},
					type : "post",
					url : "../userManage/logOut.do",
					async : true,
					success : function(data) {
							// 请求成功时处理
						if(!isEmpty(data)){
							window.location.href = data;
						}else{
							bootbox.alert("退出操作失败！");
						}			
					},
					error : function() {
						bootbox.alert("退出操作失败！");
					}
				});
			}
		}
	});
	
	$(".modal-sm .bootbox-body").css({
		"padding":10,
		});
});

/**
 * 设置修改密码的按钮可用状态
 * 只有当所有参数都选择并且错误信息框里面为空白的时候设置为可用
 */
function setUpdatePasswordBtnStatue() {
	if (!isEmpty($("#changePassword").val()) && !isEmpty($("#checkPassword").val())
			&& isEmpty($("#changeAlert").html()) && isEmpty($("#checkAlert").html())) {
		$("#updatePassword").attr("disabled", false);
	}else{
		$("#updatePassword").attr("disabled", true);
	}
}
/**
 * 修改密码鼠标移动改变字体颜色问题
 */
$("#updatePwd").mouseover(function() {
	$("#updatePwdFont").addClass('mouse-on-title');
	$(this).css("cursor","pointer");
}).mouseout(function() {
	$("#updatePwdFont").removeClass('mouse-on-title');
	$(this).css("cursor","default");
});
/**
 * 打开修改密码窗口
 */
$('#updatePwd').bind('click',function(){
	$("#checkPassword").removeClass("warning");
	$("#changePassword").removeClass("warning");
	$("#changeAlert").html("");
	$("#checkAlert").html("");
	$('#changePwdModal').modal('show');
	$("#updatePassword").attr("disabled", true);
	//清空密码输入框
	$("#changePassword").val("");
	//清空密码确认输入框
	$("#checkPassword").val("");
});
/**
 * 修改密码相关check
 */
$("#changePassword").bind("input propertychange", function(){
	$("#changeAlert").html("");
	$("#changePassword").removeClass("warning");
	var changePassword = $("#changePassword").val();
	var checkPassword = $("#checkPassword").val();
	if (isEmpty(changePassword) && isEmpty($("#changeAlert").html())) {
		$("#changeAlert").html("<font color='red'>请输入修改密码</font>");
		$("#changePassword").addClass("warning");
	}
	if (changePassword.length < 6 || changePassword.length>30){
		$("#changeAlert").html("<font color='red'>密码位数应为6~30</font>");
		$("#changePassword").addClass("warning");
	}
	if ($.trim(changePassword) == $.trim(checkPassword) && !isEmpty(changePassword)) {
		$("#checkAlert").html("");
		$("#checkPassword").removeClass("warning");
	}
	//判断按钮可用性
	setUpdatePasswordBtnStatue();		
});

/**
 * 确认密码相关check
 */
$("#checkPassword").bind("input propertychange", function(){
	$("#checkPassword").removeClass("warning");
	$("#checkAlert").html("");
	var changePassword = $("#changePassword").val();
	var checkPassword = $("#checkPassword").val();
	if (isEmpty(checkPassword) && isEmpty($("#checkAlert").html())) {
		$("#checkAlert").html("<font color='red'>请输入确认密码</font>");
		$("#checkPassword").addClass("warning");
	}
	if ($.trim(changePassword) != $.trim(checkPassword)) {
		$("#checkAlert").html("<font color='red'>两次密码不一致</font>");
		$("#checkPassword").addClass("warning");
	}
	//判断按钮可用性
	setUpdatePasswordBtnStatue();		
});

/**
 * 更新密码
 */
$("#updatePassword").click(function() {
	var checkPassword = $("#checkPassword").val();
	$.ajax({
		url : "../userManage/changePassword.do", 
		data : $.param({
			password : checkPassword
		}),
		type : "post",
		async : true,
		beforeSend : function() {
			// alert("请求前的处理");
		},
		success : function(data) {
			//  alert("请求成功时处理");
			if(data.result){
				bootbox.alert("修改密码成功");
				$('#changePwdModal').modal('hide');
			}else{
				bootbox.alert("修改密码失败");
			}			
		},
		complete : function(data) {
			// alert("请求完成的处理");
		},
		error : function() {
			bootbox.alert("修改密码出现异常");
		}
	});
	
});

/**
 * 获取云盘使用情况 
 */
function queryCloudDiskInfo() {
	$.ajax({
		url : "../init/queryCloudDiskInfo.do", 
		type : "post",
		async : true,
		success : function(data) {
			//  alert("请求成功时处理");
			if(!isEmpty(data.userid)){
				if(!isEmpty(data.usedsize)){
					$('#usedSize').html(formatFileSize(data.usedsize));
				}else{
					$('#usedSize').html('0');
				}
				$('#totalSize').html(formatFileSize(data.totalsize));
				
				var percent=data.usedsize/data.totalsize;
				var totalDiv=$('#bar').width();
				var percentWidth=totalDiv*percent;
				$('#barId').css("width",percentWidth);
				$('#bar').hover(function (){
					$('#info').removeClass('sr-only');
					$('#info').html(parseInt(percent*100)+"%");
				}).mouseleave(function (){
					$('#info').addClass('sr-only');
				});
			}else{
				$('.useSpace').hide();
				$('.blankSpace').html("<p style='font-size:12px;margin-left:30px;'>获取云盘空间失败，请<a id='CapacityRefresh' href='#' style='color:#2932E1;'>刷新</a>！</p>");
				$('.blankSpace').css('margin-left','-30px');
			}		
		},
		error : function() {
			bootbox.alert("获取云盘使用情况出现异常");
		}
	});
}
/**
 * 刷新云盘容量显示
 */
$('#CapacityRefresh').click(function (){
	queryCloudDiskInfo();
});
