$(function() {
});

/**
 * 判断对象是否为空的方法
 * 
 * @param data
 * @returns {Boolean}
 */
function isEmpty(data) {
	if (data != null && data != undefined && data != "") {
		return false;
	}

	return true;
}

/**
 * 判断字符串是否为特殊字符
 * 
 * @param str
 * @returns {Boolean}
 */
function isIllegalStr(str) {
	var illegalTxt = new RegExp(/[\*\|\\\:\"\/\<\>\?]+/);
	if (!illegalTxt.test(str)) {
		return false;
	}

	return true;
}

/**
 * 请求模拟休眠方法
 * 
 * @param numberMillis
 */
function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
			return;
	}
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function formatTableFileName(value, formatCondition) {
	var msg1='<font size="2" style="margin-left:5px" ><a style="color:black; text-decoration:none;" title=';

	switch(formatCondition) {
	case '文件夹':
		msg1='<font id="fileFolder" size="2" style="margin-left:5px"><a style="text-decoration:none;" title=';
		return '<img src="../img/folder.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'doc文件':
		return '<img src="../img/doc.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'docx文件':
		return '<img src="../img/doc.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'xlsx文件':
		return '<img src="../img/excel.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'xls文件':
		return '<img src="../img/excel.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'pptx文件':
		return '<img src="../img/ppt.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'ppt文件':
		return '<img src="../img/ppt.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'txt文件':
		return '<img src="../img/txt.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'exe文件':
		return '<img src="../img/exe.png"/>'
		     +msg1+value+'>'+value+'</font></a>';
		break;
	case 'rar文件':
		return '<img src="../img/zip.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'zip文件':
		return '<img src="../img/zip.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'mkv文件':
		return '<img src="../img/video.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'mp4文件':
		return '<img src="../img/video.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'rmvb文件':
		return '<img src="../img/video.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'pdf文件':
		return '<img src="../img/pdf.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'mp3文件':
		return '<img src="../img/music.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'png文件':
		return '<img src="../img/img.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	case 'jpg文件':
		return '<img src="../img/img.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	default:
		return '<img src="../img/file.png"/>'
		       +msg1+value+'>'+value+'</font></a>';
		break;
	}
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function formatTableGroupName(value){
	var msgLeft='<font size="2" style="margin-left:5px;vertical-align:middle;" ><a href="#" title=';
	var msgRight=' style="color:black; text-decoration:none;"';
	return '<img src="../img/group-icon.png"/>'+msgLeft+value+msgRight+'>'+value+'</font></a>';
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function formatTableOperatorName(value){
	var msgLeft='<div class="col-xs-9 text-left"><font style="margin-left:10px;display:table-cell;vertical-align:middle;height:24px"><a href="#" title=';
	var msgRight=' style="color:black; text-decoration:none;"';
	return '<div class="col-xs-10 col-xs-offset-3" style="display:table;vertical-align:middle;"><div class="col-xs-3"><img src="../img/user-icon.png"/></div>'+msgLeft+value+msgRight+'>'+value+'</a></font></div></div>';
}

function formatTableShareObject(value,row){
	
	var msgLeft='<div class="col-xs-9 text-left"><font style="margin-left:10px;display:table-cell;vertical-align:middle;height:24px"><a href="#" title=';
	var msgRight=' style="color:black; text-decoration:none;"';
	//for ()
	switch(row.shareObjectFlag){
	case '1' :
		return '<div class="col-xs-10 col-xs-offset-2" style="display:table;vertical-align:middle;"><div class="col-xs-3"><img src="../img/group-icon.png"/></div>'+msgLeft+value+msgRight+'>'+value+'</a></font></div></div>';
	case '2' :
		return '<div class="col-xs-10 col-xs-offset-2" style="display:table;vertical-align:middle;"><div class="col-xs-3"><img src="../img/all-user.png"/></div>'+msgLeft+value+msgRight+'>'+value+'</a></font></div></div>';
	case '0' :
		return '<div class="col-xs-10 col-xs-offset-2" style="display:table;vertical-align:middle;"><div class="col-xs-3"><img src="../img/user-icon.png"/></div>'+msgLeft+value+msgRight+'>'+value+'</a></font></div></div>';
	}
}

/**
 * 表格数据中文件名称格式化
 * @param value
 * @param formatCondition
 * @returns {String}
 */
function refreshTable(tableId) {
	$("#"+tableId).bootstrapTable('refresh');
}

/**
 * 数据库文件大小显示到页面的转换 如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
 * @param fileSize
 * @returns
 */
function formatFileSize(oldFileSize) {
	var fileSize = "";
	if (!isEmpty(oldFileSize)) {
		if (oldFileSize > 1024 * 1024 * 1024) {
			fileSize = Math.round(oldFileSize * 100 / (1024 * 1024 * 1024)) / 100;
			fileSize = fileSize.toString() + 'G';
		}else if (oldFileSize > 1024 * 1024) {
			fileSize = Math.round(oldFileSize * 100 / (1024 * 1024)) / 100;
			fileSize = fileSize.toString() + 'MB';
		} else {
			fileSize = (Math.round(oldFileSize * 100 / 1024) / 100).toString()
					+ 'KB';
		};
	}
	
	return fileSize;
};

/**
 * BootStrap modal 全局添加拖拽效果
 * @returns
 */
$('.modal').on('show.bs.modal' , function (){
	$(this).draggable({
		cursor: 'move',
		handle: '.modal-header'
	});
	$(this).css('overflow' , 'hidden');
});
