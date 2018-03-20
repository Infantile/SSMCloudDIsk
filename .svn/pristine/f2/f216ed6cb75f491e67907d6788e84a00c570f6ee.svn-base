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
import cn.springmvc.service.CollectFileManageService;
import cn.springmvc.service.MyFileManageService;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/collect")
public class CollectManageController {
	private static Logger logger = LoggerFactory
			.getLogger(CollectManageController.class);

	@Autowired
	private CollectFileManageService collectFileManageService;
	@Autowired
	private MyFileManageService myFileManageService;

	@ResponseBody
	@RequestMapping("/collectTbInit")
	public Map<String, Object> getfileTableInfo(
			HttpServletRequest request,
			@RequestParam(value = "fileObjId", required = true) String fileObjId,
			@RequestParam(value = "rootFolderId", required = true) String rootFolderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> collectInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			getTableSortInfo(request, paramMap);
			if (fileObjId == null || "".equals(fileObjId)) {
				collectInfoList = collectFileManageService
						.queryCollectTableInfo(paramMap);
			} else {
				paramMap.put("fileObjId", fileObjId);
				collectInfoList = myFileManageService
						.queryFolderContainInfo(paramMap);
				collectFileManageService.updateCollectTime(collectInfoList,
						rootFolderId);
			}
		} catch (Exception e) {
			logger.error("查询我的收藏表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", collectInfoList.size());
		map.put("rows", collectInfoList);
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
			if (search == null || "".equals(search)) {
				// 什么不做，直接返回空列表
			} else {
				fileInfoList = collectFileManageService
						.queryCollectSearchInfo(paramMap);
			}

		} catch (Exception e) {
			logger.error("查询收藏文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}

	@ResponseBody
	@RequestMapping("/cancelMyCollection")
	public Map<String, Object> cancelMyCollection(HttpServletRequest request,
			@RequestParam(value = "rows", required = false) String rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String loginObj = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(loginObj);
			List<String> collectObjectList = new ArrayList<String>();
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					collectObjectList.add(jsonObject.getString("fileid"));
				}
				result = collectFileManageService.cancelCollectFile(
						loginUserId, collectObjectList);
			}
		} catch (Exception e) {
			logger.error("删除收藏数据异常：" + e.getMessage());
		}
		map.put("result", result);
		return map;
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
