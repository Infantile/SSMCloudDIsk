package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.PublicSharedManageService;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/publicShared")
public class PublicSharedManageController {
	private static Logger logger = LoggerFactory
			.getLogger(PublicSharedManageController.class);
	@Autowired
	private PublicSharedManageService publicSharedManageService;
	/**
	 * 点击公共共享查询公共共享文件列表
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/queryList")
	public Map<String, Object> queryList(HttpServletRequest request,
			@RequestParam(value = "fileid", required = false) String fileid) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//调用服务层的查询我的共享信息列表
		List<QueryMySharedFileInfo> querySharedFileTableInfos=new ArrayList<QueryMySharedFileInfo>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			getTableSortInfo(request, paramMap);
			if(fileid == null || "".equals(fileid)){
				querySharedFileTableInfos=publicSharedManageService.queryPublicSharedFileListInfo(paramMap);
			}else{
				paramMap.put("folderID", fileid);
				querySharedFileTableInfos = publicSharedManageService.
						queryNextPageSharedFileInfoByFolderID(paramMap);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询公共共享表格数据异常：" + e.getMessage());
			resultMap.put("result", "");
		}		
		resultMap.put("result", "查询成功！");
		resultMap.put("total", querySharedFileTableInfos.size());
		resultMap.put("rows", querySharedFileTableInfos);	
    	return resultMap;
	}
	
	/**
	 * 选中共享文件列表中的某个目录，双击打开目录，显示其目录下的文件列表信息
	 * @param request
	 * @param folderID
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/queryNextPageSharedFileInfoByFolderID")
	public Map<String, Object> queryNextPageSharedFileInfoByFolderID(HttpServletRequest request,
			@RequestParam(value="fileid", required=true) String fileid) {
		//定义返回前台对应集合
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		// 根据选中的文件目录查询当前目录下的子文件列表信息
		List<QueryMySharedFileInfo> queryMySharedFileInfos = new ArrayList<QueryMySharedFileInfo>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("folderID", fileid);
			getTableSortInfo(request, paramMap);
			queryMySharedFileInfos = publicSharedManageService.
					queryNextPageSharedFileInfoByFolderID(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询公共共享文件表格数据异常：" + e.getMessage());
			resultMap.put("result", "");
		}
		resultMap.put("result", "查询成功！");
		resultMap.put("total", queryMySharedFileInfos.size());
		resultMap.put("rows", queryMySharedFileInfos);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/cancelPublicShared")
	public Map<String, Object> cancelMyShared(HttpServletRequest request,
			@RequestParam(value = "confirm", required = false) String confirm) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//获取form表单数据
        String mySharedFileInfo = RequestUtils.getRequestKeyValue(request, "mySharedFileInfo");
        //获取用户名
        String userName = RequestUtils.getSessionKeyValue(request, "userName");
        //JSON字符串序列化成JSON对象
        JSONObject mySharedFileInfoJson = JSonUtils.objectToJson(mySharedFileInfo);
        ShareInfo shareInfo=new ShareInfo();
        shareInfo.setFileid(mySharedFileInfoJson.getString("fileID"));
        shareInfo.setOperator(mySharedFileInfoJson.getString("operator"));
        shareInfo.setShareobjectid(mySharedFileInfoJson.getString("shareObjectID"));
        shareInfo.setCreatedate(mySharedFileInfoJson.getString("createDate"));
        resultMap.put("isShareObject", true);
		try {
			if (userName.equals(shareInfo.getOperator())) {
				if (confirm!=null) {
					int result=publicSharedManageService.cancelSharedFile(shareInfo);
					resultMap.put("result", result);
				}		
			}else {
				resultMap.put("isShareObject", false);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("取消公共共享文件失败：" + e.getMessage());
			resultMap.put("result", false);
		}		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/checkIsSharedFile")
	public Map<String, Object> checkIsSharedFile(HttpServletRequest request,
			@RequestParam(value="fileid", required=true) String fileid,
			HttpServletResponse response) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//调用服务层的查询我的共享信息列表
		List<QueryMySharedFileInfo> querySharedFileTableInfos=new ArrayList<QueryMySharedFileInfo>();
		try {
			querySharedFileTableInfos=publicSharedManageService.checkIsSharedFile(fileid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询失败！");
			resultMap.put("result", "");
			return resultMap;
		}		
		if (querySharedFileTableInfos.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", querySharedFileTableInfos.size());
			resultMap.put("rows", querySharedFileTableInfos);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/querySearchInfo")
	public Map<String, Object> querySearchInfo(HttpServletRequest request,
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "fileid", required = false) String fileid) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<QueryMySharedFileInfo> fileInfoList = new ArrayList<QueryMySharedFileInfo>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			getTableSortInfo(request, paramMap);
			paramMap2.put("search", RequestUtils.formatBootstrapTableEncoding(search));
			if (search==null || "".equals(search)) {
		      //什么不做，直接返回空列表
				if(fileid == null || "".equals(fileid)){
					fileInfoList=publicSharedManageService.queryPublicSharedFileListInfo(paramMap);
				}else{
					paramMap.put("folderID", fileid);
					fileInfoList = publicSharedManageService.
							queryNextPageSharedFileInfoByFolderID(paramMap);
				}	
			}else {
				fileInfoList = publicSharedManageService.queryPublicSharedSearchInfo(paramMap,paramMap2);
			}
		} catch (Exception e) {
			logger.error("查询我的共享文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}
	
	/**
	 * 组合排序的信息 默认时间降序
	 * @param request
	 * @return
	 */
	private void getTableSortInfo(HttpServletRequest request, Map<String, Object> paramMap){
		//排序列名
		String sortName = RequestUtils.getRequestKeyValue(request, "sort");
		//排序顺序
		String sortOrder = RequestUtils.getRequestKeyValue(request, "order");
		if(sortName != null && !"".equals(sortName)
				&& sortOrder != null && !"".equals(sortOrder)){	
			paramMap.put("sort", sortName);
			paramMap.put("order", sortOrder);
		}
	}
}