package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.FileInfo;
import cn.springmvc.service.RecycleFileManageService;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/recycle")
public class RecycleManageController {
	private static Logger logger = LoggerFactory
			.getLogger(RecycleManageController.class);

	@Autowired
	private RecycleFileManageService recycleFileManageService;

	@ResponseBody
	@RequestMapping("/recycleTbInit")
	public Map<String, Object> getfileTableInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> recycleInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			getTableSortInfo(request, paramMap);
			recycleInfoList = recycleFileManageService
					.queryRecycleTableInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询我的收藏表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", recycleInfoList.size());
		map.put("rows", recycleInfoList);
		return map;
	}

	/**
	 * 搜索框输入查询功能
	 * 
	 * @param request
	 * @param newFolderName
	 * @param currentFolderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/querySearchInfo")
	public Map<String, Object> querySearchInfo(HttpServletRequest request,
			@RequestParam(value = "search", required = true) String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			paramMap.put("search",
					RequestUtils.formatBootstrapTableEncoding(search));
			fileInfoList = recycleFileManageService
					.queryRecycleSearchInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询回收文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}

	@ResponseBody
	@RequestMapping("/restoreFile")
	public Map<String, Object> cancelMyCollection(HttpServletRequest request,
			@RequestParam(value = "rows", required = false) String rows) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "";
		List<String> success=new ArrayList<String>();
		List<String> error=new ArrayList<String>();
		List<String> root=new ArrayList<String>();
		List<String> other=new ArrayList<String>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String loginObj = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(loginObj);
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					message = recycleFileManageService.restoreFile(loginUserId,
							jsonObject.getString("fileid"));
					if ("success".equals(message)) {
						success.add(jsonObject.getString("filename")+" ");
						resultMap.put("success", success);
					}else if("root".equals(message)) {
						root.add(jsonObject.getString("filename")+" ");
						resultMap.put("root", root);
					}else if("error".equals(message)) {
						error.add(jsonObject.getString("filename")+" ");
						resultMap.put("error", error);
					}else {
						other.add(message+" ");
						resultMap.put("other", other);
					}
				}
			}
		} catch (Exception e) {
			logger.error("恢复数据异常：" + e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/absolutelyDeleteFile")
	public Map<String, Object> absolutelyDeleteFile(HttpServletRequest request,
			@RequestParam(value = "fileID", required = true) String fileID) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", false);
		String userID = RequestUtils.getSessionKeyValue(request,
				"userId");
		try {
			boolean result = recycleFileManageService
					.absolutelyDeleteFile(fileID,userID);
			resultMap.put("result", result);
		} catch (Exception e) {
			logger.error("彻底删除文件发生异常：" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 组合排序的信息 默认时间降序
	 * 
	 * @param request
	 * @return
	 */
	private void getTableSortInfo(HttpServletRequest request,
			Map<String, Object> paramMap) {
		// 排序列名
		String sortName = RequestUtils.getRequestKeyValue(request, "sort");
		// 排序顺序
		String sortOrder = RequestUtils.getRequestKeyValue(request, "order");
		if (sortName != null && !"".equals(sortName) && sortOrder != null
				&& !"".equals(sortOrder)) {
			paramMap.put("sort", sortName);
			paramMap.put("order", sortOrder);
		}
	}
}
