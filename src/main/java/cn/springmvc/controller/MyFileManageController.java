package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.common.fastdfs.FileManager;
import cn.common.fastdfs.FileManagerConfig;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;
import cn.springmvc.service.CloudDiskCapacityManageService;
import cn.springmvc.service.CollectFileManageService;
import cn.springmvc.service.MyFileManageService;
import cn.springmvc.service.MySharedManageService;
import cn.springmvc.service.PublicSharedManageService;
import cn.utils.DateUtils;
import cn.utils.FileUtils;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/file")
public class MyFileManageController implements FileManagerConfig {
	private static final long serialVersionUID = 2019847978925175089L;
	private static Logger logger = LoggerFactory
			.getLogger(MyFileManageController.class);
	@Autowired
	private MyFileManageService myFileManageService;
	@Autowired
	private CloudDiskCapacityManageService cloudDiskCapacityManageService;
	@Autowired
	private MySharedManageService mySharedManageService;
	@Autowired
	private PublicSharedManageService publicSharedManageService;
	@Autowired
	private CollectFileManageService collectFileManageService;

	/**
	 * 上传到服务器
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("upload")
	public Map<String, Object> upload(
			HttpServletRequest request,
			@RequestParam("uploadFile") MultipartFile multipartFile,
			@RequestParam(value = "parentFolderID", required = false) String parentFolderID,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "rows", required = false) String rows)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String loginUser = RequestUtils.getSessionKeyValue(request, "userName");
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		boolean upload = myFileManageService.fileUploader(multipartFile, rows,
				loginUser, loginUserId, comment, parentFolderID);
		map.put("result", upload);
		return map;
	}

	/**
	 * 下载文件操作
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("singleDownload")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			HttpServletResponse response, FileInfo fileInfos) throws Exception {
		String loginUser = RequestUtils.getSessionKeyValue(request, "userName");
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		ResponseEntity<byte[]> content = null;
		try {
			ArrayList<FileInfo> fileInfoList = fileInfos.getFileInfoList();
			if (fileInfoList != null && fileInfoList.size() == 1) {
				content = myFileManageService.fileDownloader(loginUser,
						loginUserId, fileInfos.getFileInfoList().get(0));
			}
		} catch (Exception e) {
			logger.error("下载文件操作异常：" + e.getMessage());
		}

		return content;
	}

	/**
	 * 文件下载(多文件时候压缩成zip文件)
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("multDownload")
	public void multDownload(HttpServletRequest request,
			HttpServletResponse response, FileInfo fileInfos) throws Exception {
		ArrayList<FileInfo> fileInfoList = fileInfos.getFileInfoList();
		if (fileInfoList != null && fileInfoList.size() > 0) {
			String zipName = FileUtils.getZipFileName();
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ zipName);
			ZipOutputStream out = new ZipOutputStream(
					response.getOutputStream());
			try {
				for (FileInfo fileInfo : fileInfoList) {
					FileManager
							.multDownload(fileInfo.getFilegroupname(),
									fileInfo.getFilepath(),
									fileInfo.getFilename(), out);
					response.flushBuffer();
				}
			} catch (Exception e) {
				logger.error("下载文件操作异常：" + e.getMessage());
			} finally {
				out.close();
			}
		}
	}

	/**
	 * 下载文件操作(返回url,浏览器下载，避免在服务器端下载过程，前端界面一直等待的问题)
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("downloadUrl")
	public Map<String, Object> downloadUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("downloadUrl", "");
		try {
			String filegroupName = RequestUtils.getRequestKeyValue(request,
					"filegroupName");
			String filePath = RequestUtils.getRequestKeyValue(request,
					"filePath");
			if (filegroupName != null && !"".equals(filegroupName)
					&& filePath != null && !"".equals(filePath)) {
				String fileAbsolutePath = PROTOCOL + TRACKER_NGNIX_ADDR
						+ SEPARATOR_COLON + TRACKER_NGNIX_PORT + SEPARATOR
						+ filegroupName + SEPARATOR + filePath;
				map.put("downloadUrl", fileAbsolutePath);
			}
		} catch (Exception e) {
			logger.error("下载文件操作异常：" + e.getMessage());
		}

		return map;
	}

	/**
	 * 云盘剩余大小验证
	 * 
	 * @param request
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/diskSizeCheck")
	public Map<String, Object> diskSizeCheck(HttpServletRequest request,
			@RequestParam(value = "fileSize", required = true) String fileSize)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();
			cloudDiskInfo.setUserid(loginUserId);
			List<CloudDiskInfo> cloudDiskInfoList = cloudDiskCapacityManageService
					.queryClouddiskCapacityInfo(cloudDiskInfo);
			// 云盘剩余大小检查
			if (cloudDiskInfoList != null && cloudDiskInfoList.size() > 0) {
				long totalSize = Long.parseLong(cloudDiskInfoList.get(0)
						.getTotalsize());
				long usedSize = Long.parseLong(cloudDiskInfoList.get(0)
						.getUsedsize());
				if (totalSize >= usedSize + Long.parseLong(fileSize)) {
					map.put("result", true);
				}
			}
		} catch (Exception e) {
			map.put("message", "云盘剩余大小检查异常");
			logger.error("云盘剩余大小检查异常：" + e.getMessage());
		}

		return map;
	}

	/**
	 * 我的文件表格数据获取
	 * 
	 * @param request
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/myfileTbInit")
	public Map<String, Object> getfileTableInfo(HttpServletRequest request,
			@RequestParam(value = "fileObjId", required = true) String fileObjId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			getTableSortInfo(request, paramMap);
			if (fileObjId == null || "".equals(fileObjId)) {
				fileInfoList = myFileManageService
						.queryMyFileListInfo(paramMap);
			} else {
				paramMap.put("fileObjId", fileObjId);
				fileInfoList = myFileManageService
						.queryFolderContainInfo(paramMap);
			}
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
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
	public Map<String, Object> querySearchInfo(
			HttpServletRequest request,
			@RequestParam(value = "fileObjId", required = true) String fileObjId,
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
			fileInfoList = myFileManageService.querySearchInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}

	/**
	 * 查询共享对象列表信息
	 * 
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/queryShareObjectList")
	public Map<String, Object> queryShareObjectList(HttpServletRequest request,
			@RequestParam(value = "search", required = false) String searchVal) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		QueryShareObjectInfo queryShareObjectInfo = new QueryShareObjectInfo();
		List<QueryShareObjectInfo> queryShareObjectInfos = new ArrayList<QueryShareObjectInfo>();
		try {
			// 获取用户ID
			String userId = RequestUtils.getSessionKeyValue(request, "userId");
			queryShareObjectInfo.setShareObjectID(userId);
			queryShareObjectInfo.setShareSearchString(RequestUtils
					.formatBootstrapTableEncoding(searchVal));
			resultMap.put("result", "");
			queryShareObjectInfos = myFileManageService
					.queryShareObjectInfo(queryShareObjectInfo);
		} catch (Exception e) {
			logger.error("查询共享对象列表信息失败：" + e.getMessage());
		}
		if (queryShareObjectInfos.size() > 0) {
			resultMap.put("result", "success");
			resultMap.put("rows", queryShareObjectInfos);
		}

		return resultMap;
	}

	/**
	 * 共享文件处理
	 * 
	 * @param request
	 * @param fileID
	 * @param shareObjectID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shareFile")
	public Map<String, Object> shareFile(HttpServletRequest request) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String shareObjectInfo = RequestUtils.getRequestKeyValue(request,
				"shareObjectJson");
		// JSON字符串序列化成JSON对象
		JSONObject shareObjectJson = JSonUtils.objectToJson(shareObjectInfo);
		String shareObjectIDs = shareObjectJson.getString("shareObjectIDs");
		JSONArray jsonArray = JSONArray.fromObject(shareObjectIDs);
		Object[] objectIds = jsonArray.toArray();
		String fileIDs = shareObjectJson.getString("fileIDs");
		JSONArray jsonArray2 = JSONArray.fromObject(fileIDs);
		Object[] fileIds = jsonArray2.toArray();
		// 获取用户名
		String operator = RequestUtils.getSessionKeyValue(request, "userName");
		List<String> success = new ArrayList<String>();
		List<String> failue = new ArrayList<String>();
		List<String> repeated = new ArrayList<String>();
		try {
			ShareInfo shareInfo = new ShareInfo();
			for (int i = 0; i < fileIds.length; i++) {
				for (int j = 0; j < objectIds.length; j++) {
					shareInfo.setFileid(JSONObject.fromObject(fileIds[i])
							.getString("fileID"));
					shareInfo.setFileName(JSONObject.fromObject(fileIds[i])
							.getString("fileName"));
					shareInfo.setShareobjectid(JSONObject.fromObject(
							objectIds[j]).getString("shareObjectID"));
					shareInfo.setShareObjectName(JSONObject.fromObject(
							objectIds[j]).getString("shareObjectName"));
					shareInfo.setOperator(operator);
					shareInfo.setCreatedate(DateUtils.getSystemTime());
					Map<String, Object> checkedResultMap = myFileManageService
							.checkRepeatedSharedFile(shareInfo);
					if (checkedResultMap.get("repeatedMessage") != null) {
						repeated.add(checkedResultMap.get("repeatedMessage")
								.toString());
					}
					if (checkedResultMap.get("ShareInfo") != null) {
						int result = myFileManageService
								.shareFileFromMyFile(shareInfo);
						if (result > 0) {
							// 记录共享成功的情况
							String message = "文件："
									+ JSONObject.fromObject(fileIds[i])
											.getString("fileName")
									+ "共享给"
									+ JSONObject.fromObject(objectIds[j])
											.getString("shareObjectName") + " ";
							success.add(message);
							continue;
						} else {
							// 记录共享失败的情况
							String message = "文件："
									+ JSONObject.fromObject(fileIds[i])
											.getString("fileName")
									+ "共享给"
									+ JSONObject.fromObject(objectIds[j])
											.getString("shareObjectName") + " ";
							failue.add(message);
						}
					}
				}
			}
			// 共享过程没有发生异常中断，只是有的共享失败，提示哪些文件共享失败
			resultMap.put("failue", failue);
		} catch (Exception e) {
			logger.error("共享文件出现异常错误：" + e.getMessage());
			// 给前端提示只有哪些文件共享成功，共享过程中发生了中断。
			resultMap.put("isException", true);
		}
		resultMap.put("isException", false);
		resultMap.put("success", success);
		resultMap.put("repeatedMessages", repeated);
		return resultMap;
	}

	/**
	 * 创建新文件夹处理
	 * 
	 * @param request
	 * @param newFolderName
	 * @param currentFolderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createFolder")
	public Map<String, Object> createFolder(
			HttpServletRequest request,
			@RequestParam(value = "newFolderName", required = true) String newFolderName,
			@RequestParam(value = "currentFolderId", required = false) String currentFolderId) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		try {
			String userId = RequestUtils.getSessionKeyValue(request, "userId");
			String userName = RequestUtils.getSessionKeyValue(request,
					"userName");
			result = myFileManageService.createFolder(userName, userId,
					newFolderName, currentFolderId);
		} catch (Exception e) {
			logger.error("创建文件夹发生异常：" + e.getMessage());
		}
		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 收藏文件对象
	 * 
	 * @param request
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/collectObject")
	public Map<String, Object> collectObject(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String loginUser = RequestUtils.getSessionKeyValue(request,
					"userName");
			String rows = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			if (myJsonArray != null && myJsonArray.size() > 0) {
				List<String> failedList = new ArrayList<String>();
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					if (jsonObject.containsKey("fileid")) {
						String fileObjId = jsonObject.getString("fileid");
						result = myFileManageService.collectObject(loginUser,
								loginUserId, fileObjId);
						// 记录失败情况
						if (!result) {
							failedList.add(fileObjId);
						}
					}
				}
				setFailedNameList(failedList, myJsonArray, map);
			}
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		return map;
	}

	/**
	 * 复制移动文件对象
	 * 
	 * @param request
	 * @param parentFolderID
	 * @param controlType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/copyOrMoveTo")
	public Map<String, Object> copyOrMoveTo(
			HttpServletRequest request,
			@RequestParam(value = "parentFolderID", required = true) String parentFolderID,
			@RequestParam(value = "controlType", required = true) String controlType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String rows = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			map.put("result", false);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			getTableSortInfo(request, paramMap);
			List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
			if (parentFolderID == null || "".equals(parentFolderID)) {
				fileInfoList = myFileManageService
						.queryMyFileListInfo(paramMap);
			} else {
				paramMap.put("fileObjId", parentFolderID);
				fileInfoList = myFileManageService
						.queryFolderContainInfo(paramMap);
			}

			if (myJsonArray != null && myJsonArray.size() > 0) {
				List<String> failedList = new ArrayList<String>();
				if ("copy".equals(controlType)) {
					failedList = myFileManageService.copyOrmoveTo(loginUserId,
							parentFolderID, fileInfoList, myJsonArray, 0);
				} else if ("move".equals(controlType)) {
					failedList = myFileManageService.copyOrmoveTo(loginUserId,
							parentFolderID, fileInfoList, myJsonArray, 1);
				}
				setFailedNameList(failedList, myJsonArray, map);
				map.put("result", true);
			}
		} catch (Exception e) {
			logger.error("复制移动文件对象发生异常：" + e.getMessage());
		}

		return map;
	}

	/**
	 * 操作结果处理(如果有失败的，界面提示失败文件已经存在)
	 * 
	 * @param failedList
	 * @param myJsonArray
	 * @param map
	 */
	private void setFailedNameList(List<String> failedList,
			JSONArray myJsonArray, Map<String, Object> map) {
		if (failedList.size() > 0) {
			List<String> failedNameList = new ArrayList<String>();
			for (Object obj : myJsonArray) {
				JSONObject jsonObject = JSONObject.fromObject(obj);
				if (jsonObject.containsKey("fileid")
						&& failedList.contains(jsonObject.getString("fileid"))) {
					failedNameList.add(jsonObject.getString("filename"));
				}
			}
			map.put("failed", failedNameList);
		}
	}

	/**
	 * 目标目录下拉框值
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFolderIds")
	public List<TreeFolderDataInfo> getFolderIds(
			HttpServletRequest request,
			@RequestParam(value = "parentFolderID", required = false) String parentFolderID) {
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		List<TreeFolderDataInfo> modelList = new ArrayList<TreeFolderDataInfo>();
		try {
			if (loginUserId != null) {
				String rows = RequestUtils.getRequestKeyValue(request, "rows");
				JSONArray myJsonArray = JSONArray.fromObject(rows);
				String selectFolderIds = "";
				// 获取选中的文件对象ID集合，查询的时候不需要该文件集合
				if (myJsonArray != null && myJsonArray.size() > 0) {
					for (Object obj : myJsonArray) {
						JSONObject jsonObject = JSONObject.fromObject(obj);
						if (jsonObject.containsKey("fileid")) {
							selectFolderIds = selectFolderIds
									+ jsonObject.getString("fileid") + ",";
						}
					}
				}
				// 获取对应的目录数据
				TreeFolderDataInfo treeFolderDataInfo = myFileManageService
						.queryFolderIds(loginUserId, parentFolderID,
								selectFolderIds);
				if (treeFolderDataInfo != null
						&& treeFolderDataInfo.getNodes() != null
						&& treeFolderDataInfo.getNodes().size() > 0) {
					modelList.add(treeFolderDataInfo);
				}
			}
		} catch (Exception e) {
			logger.error("目标目录下拉框值获取发生异常：" + e.getMessage());
		}
		return modelList;
	}

	/**
	 * 文件对象修改名称处理
	 * 
	 * @param request
	 * @param fileID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/renameFile")
	public Map<String, Object> renameFile(
			HttpServletRequest request,
			@RequestParam(value = "oldFileName", required = true) String oldFileName,
			@RequestParam(value = "fileType", required = true) String fileType,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "newName", required = true) String newName) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("newName", newName);
			param.put("oldFileName", oldFileName);
			result = myFileManageService.renameFile(fileType, param);
		} catch (Exception e) {
			logger.error("文件对象重命名发生异常：" + e.getMessage());
		}

		resultMap.put("result", result);
		return resultMap;
	}

	/**
	 * 文件删除处理
	 * 
	 * @param request
	 * @param fileID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/deleteFile")
	public Map<String, Object> deleteFile(HttpServletRequest request,
			@RequestParam(value = "fileID", required = true) String fileID,
			@RequestParam(value = "confirm", required = false) String confirm) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<QueryMySharedFileInfo> querySharedFileTableResult = new ArrayList<QueryMySharedFileInfo>();
		resultMap.put("result", "");
		resultMap.put("isShared", false);
		try {
			Map<String, Object> map = querySharedFileListByMe(request);
			if (map.get("result") != null) {
				querySharedFileTableResult = (List<QueryMySharedFileInfo>) map
						.get("result");
			}
			int flag = 0;
			if (confirm == null) {
				for (QueryMySharedFileInfo queryMySharedFileInfo : querySharedFileTableResult) {
					if (fileID.equals(queryMySharedFileInfo.getFileid())) {
						flag = 1;
						break;
					} else {
						continue;
					}
				}
			}
			if (flag == 0 || "true".equals(confirm)) {
				String userID = RequestUtils.getSessionKeyValue(request,
						"userId");
				String userName = RequestUtils.getSessionKeyValue(request,
						"userName");
				RecycleInfo recycleInfo = new RecycleInfo();
				recycleInfo.setUserid(userID);
				recycleInfo.setRecycleobjectid(fileID);
				recycleInfo.setOperator(userName);
				recycleInfo.setCreatedate(DateUtils.getSystemTime());
				int result = myFileManageService
						.deleteFile(fileID, recycleInfo);
				if (result >= 0) {
					resultMap.put("result", true);
				}
			} else {
				resultMap.put("isShared", true);
			}

		} catch (Exception e) {
			logger.error("删除文件发生异常：" + e.getMessage());
			resultMap.put("result", false);
		}

		return resultMap;
	}

	/**
	 * 文件详细信息获取
	 * 
	 * @param request
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getFileInfo")
	public Map<String, Object> getFileInfo(HttpServletRequest request,
			@RequestParam(value = "fileID", required = true) String fileID)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fileID", fileID);
			fileInfoList = myFileManageService.queryMyFileInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询文件信息数据异常：" + e.getMessage());
		}
		map.put("fileName", fileInfoList.get(0).getFilename());
		map.put("fileGroupName", fileInfoList.get(0).getFilegroupname());
		map.put("filePath", fileInfoList.get(0).getFilepath());
		return map;
	}

	/**
	 * 点击我的共享查询我的共享文件列表
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/querySharedFileListByMe")
	public Map<String, Object> querySharedFileListByMe(
			HttpServletRequest request) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = RequestUtils.getSessionKeyValue(request, "userId");
		String userName = RequestUtils.getSessionKeyValue(request, "userName");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shareObjectID", userId);
		map.put("operator", userName);
		// 调用服务层的查询我的共享信息列表
		List<QueryMySharedFileInfo> querySharedFileTableInfos = new ArrayList<QueryMySharedFileInfo>();
		List<QueryMySharedFileInfo> querySharedFileTableInfos2 = new ArrayList<QueryMySharedFileInfo>();
		List<QueryMySharedFileInfo> querySharedFileTableResult = new ArrayList<QueryMySharedFileInfo>();
		try {
			querySharedFileTableInfos = mySharedManageService
					.queryMySharedFileListInfo(map);
			querySharedFileTableInfos2 = publicSharedManageService
					.queryPublicSharedFileListInfo(map);
			if (querySharedFileTableInfos.size() > 0) {
				for (int i = 0; i < querySharedFileTableInfos.size(); i++) {
					if (userName.equals(querySharedFileTableInfos.get(i)
							.getOperator())) {
						QueryMySharedFileInfo queryMySharedFileInfo = new QueryMySharedFileInfo();
						queryMySharedFileInfo
								.setFileid(querySharedFileTableInfos.get(i)
										.getFileid());
						queryMySharedFileInfo
								.setFileName(querySharedFileTableInfos.get(i)
										.getFileName());
						querySharedFileTableResult.add(queryMySharedFileInfo);
					}

				}
			}
			if (querySharedFileTableInfos2.size() > 0) {
				for (int i = 0; i < querySharedFileTableInfos2.size(); i++) {
					if (userName.equals(querySharedFileTableInfos2.get(i)
							.getOperator())) {
						QueryMySharedFileInfo queryMySharedFileInfo = new QueryMySharedFileInfo();
						queryMySharedFileInfo
								.setFileid(querySharedFileTableInfos2.get(i)
										.getFileid());
						queryMySharedFileInfo
								.setFileName(querySharedFileTableInfos2.get(i)
										.getFileName());
						querySharedFileTableResult.add(queryMySharedFileInfo);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询共享文件表格数据异常：" + e.getMessage());
		}
		resultMap.put("result", querySharedFileTableResult);
		return resultMap;
	}

}