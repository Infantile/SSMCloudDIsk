document.onselectstart=function(event){  
    event = window.event||event;  
    event.returnValue = false;  
};
/*document.onmousedown = new Function("return false");        
document.onmouseup = new Function("return true");  */

/*用于标识shift键是否处于按下状态*/
var shiftKeypress = false;
/*定义一数组用于保存点击表格tr的index信息*/
var rowClick=new Array();  
//监听shift按键是否按下
$(document).keydown(function(event){  
	if(event.keyCode==16){
		shiftKeypress = true;
	}
});  
$(document).keyup(function(event){  
	if(event.keyCode==16){
		shiftKeypress = false;
	}
});
//shift多选
function shiftChecked(table,tr){
	if(shiftKeypress){
		rowClick.push(tr.index());
		if(rowClick.length>1){
			var i=0;
			var lastClickIndex = rowClick[rowClick.length-2];
			var nowClickIndex = rowClick[rowClick.length-1];
			var min_index = Math.min(lastClickIndex,nowClickIndex);
			var max_index = Math.max(lastClickIndex,nowClickIndex);
			/*var allTableData = $(table).bootstrapTable('getData');
			var row = $(table).bootstrapTable('getRowByUniqueId', allTableData[max_index].fileid);*/
			var inputs = $(table).find("input");
			var checkedNum = 0;
			var uncheckedNum = 0;
			for (i=min_index;i<=max_index;i++){
				if(inputs[i+1].checked){
					checkedNum++;
				}else{
					uncheckedNum++;
				}
			}
			if(checkedNum<=uncheckedNum){
				for (i=min_index;i<=max_index;i++){
					$(table).bootstrapTable('check', i);  
				}
			}else{
				for (i=min_index;i<=max_index;i++){
					$(table).bootstrapTable('uncheck', i);  
				}
			}	
			if(!inputs[nowClickIndex+1].checked){
				$(table).bootstrapTable('check', nowClickIndex);
			}else{
				$(table).bootstrapTable('uncheck', nowClickIndex);
			}
		}
	}else{
		rowClick.length = 0;
		rowClick.push(tr.index());
	}
	
}