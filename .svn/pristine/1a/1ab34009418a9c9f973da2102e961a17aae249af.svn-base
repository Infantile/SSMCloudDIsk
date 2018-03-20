package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.common.CommonConstant;
import cn.springmvc.dao.CloudDiskInfoMapper;
import cn.springmvc.dao.CollectInfoMapper;
import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.dao.FolderRelationMapper;
import cn.springmvc.dao.RecycleInfoMapper;
import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.FolderRelation;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MyFileManageService;
import cn.utils.CreatUuidUtil;
import cn.utils.DateUtils;
import cn.utils.FileUtils;

@Service
public class MyFileManageServiceImpl extends BaseFileService implements
		MyFileManageService {
	private static Logger logger = LoggerFactory
			.getLogger(MyFileManageServiceImpl.class);
	@Autowired
	private FileInfoMapper fileInfoMapper;
	@Autowired
	private CollectInfoMapper collectInfoMapper;
	@Autowired
	private CloudDiskInfoMapper cloudDiskInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;
	@Autowired
	private FolderRelationMapper folderRelationMapper;
	@Autowired
	private ShareInfoMapper shareInfoMapper;
	@Autowired
	private RecycleInfoMapper recycleInfoMapper;

	@Override
	public List<FileInfo> queryMyFileListInfo(Map<String, Object> map) {
		return fileInfoMapper.queryMyFileTableInfo(map);
	}

	@Override
	public List<FileInfo> queryFolderContainInfo(Map<String, Object> map) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			FolderRelation folderRelation = new FolderRelation();
			folderRelation.setParentfolderid(map.get("fileObjId").toString());
			// 获取用户父目录下所有的子目录Id
			List<FolderRelation> folderRelationList = folderRelationMapper
					.queryFolderRelation(folderRelation);
			if (folderRelationList != null && folderRelationList.size() > 0) {
				String folderRelations = "";
				for (FolderRelation fr : folderRelationList) {
					folderRelations = folderRelations
							+ fr.getChildrenfolderid() + ",";
				}
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("folderRelations", folderRelations);
				if (map.containsKey("sort")) {
					paramMap.put("sort", map.get("sort").toString());
					paramMap.put("order", map.get("order").toString());
				}
				fileInfoList = fileInfoMapper.queryFolderContainInfo(paramMap);
			}
		} catch (Exception e) {
			logger.error("点击目录查询目录下所有文件以及文件目录发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return fileInfoList;
	}

	@Override
	public List<FileInfo> querySearchInfo(Map<String, Object> map) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			fileInfoList = fileInfoMapper.querySearchInfo(map);
		} catch (Exception e) {
			logger.error("搜索框输入查询功能发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return fileInfoList;
	}

	@Transactional
	@Override
	public boolean createFolder(String loginUser, String loginUserId,
			String folderName, String currentFolderId) {
		boolean createFolderResult = false;
		try {
			// 文件目录信息表数据插入
			FolderInfo folderInfo = new FolderInfo();
			String folderid = CreatUuidUtil.getUuid();
			folderInfo.setFolderid(folderid);
			folderInfo.setFoldername(folderName);
			folderInfo.setFolderState(CommonConstant.fileState.normal
					.toString());
			folderInfo.setOperator(loginUser);
			folderInfo.setCreatedate(DateUtils.getSystemTime());
			folderInfoMapper.insertFolderInfo(folderInfo);
			// 文件目录关系表数据插入
			FolderRelation folderRelation = new FolderRelation();
			folderRelation.setUserid(loginUserId);
			folderRelation.setParentfolderid(currentFolderId);
			folderRelation.setChildrenfolderid(folderid);
			String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
			if (currentFolderId == null || "".equals(currentFolderId)) {
				isRootFolder = CommonConstant.IS_ROOT_FOLDER;
			}
			folderRelation.setIsRootFolder(isRootFolder);
			folderRelationMapper.insertFolderRelation(folderRelation);
			createFolderResult = true;
		} catch (Exception e) {
			logger.error("创建文件夹数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return createFolderResult;
	}

	@Transactional
	@Override
	public boolean collectObject(String loginUser, String loginUserId,
			String fileObjId) {
		boolean collectObjectResult = false;
		try {
			CollectInfo collectInfo = new CollectInfo();
			collectInfo.setUserid(loginUserId);
			collectInfo.setCollectobjectid(fileObjId);
			List<CollectInfo> collectInfoList = collectInfoMapper
					.queryCollectInfoInfo(collectInfo);
			// 未共享的文件对象才需要再次操作数据库
			if (collectInfoList == null || collectInfoList.size() < 1) {
				collectInfo.setOperator(loginUser);
				collectInfo.setCreatedate(DateUtils.getSystemTime());
				collectInfoMapper.insertCollectInfoInfo(collectInfo);
				collectObjectResult = true;
			}
		} catch (Exception e) {
			logger.error("收藏文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return collectObjectResult;
	}

	@Transactional
	@Override
	public List<String> copyOrmoveTo(String loginUserId, String parentFolderID,
			List<FileInfo> fileInfoList, JSONArray childrenFolderIdJSONArray,
			int controlType) {
		List<String> failedList = new ArrayList<String>();
		try {
			for (Object obj : childrenFolderIdJSONArray) {
				JSONObject jsonObject = JSONObject.fromObject(obj);
				if (jsonObject.containsKey("fileid")
						&& jsonObject.containsKey("filename")
						&& jsonObject.containsKey("filetype")) {
					String childrenFolderID = jsonObject.getString("fileid");
					try {
						String filename = jsonObject.getString("filename");
						String filetype = jsonObject.getString("filetype");
						String newfileName = getCopyOrmoveNewName(fileInfoList,
								filename, filetype);
						if (controlType == 0) { // 复制文件对象
							if (newfileName != null
									&& !newfileName.equals(filename)) {
								// 复制目标目录存在复制文件的名称时候，需要重命名
								copyTo(parentFolderID, childrenFolderID,
										loginUserId, newfileName);
							} else {
								copyTo(parentFolderID, childrenFolderID,
										loginUserId, null);
							}

						} else if (controlType == 1) { // 移动文件对象
							if (newfileName != null
									&& !newfileName.equals(filename)) {
								if ("文件夹".equals(filetype)) {
									// 目录的情况
									FolderInfo folderInfo = new FolderInfo();
									folderInfo.setFoldername(newfileName);
									folderInfo.setFolderid(childrenFolderID);
									folderInfoMapper
											.updateFolderInfo(folderInfo);
								} else {
									// 如果复制到对象目录已经存在该文件，文件重命名
									FileInfo fileInfo = new FileInfo();
									fileInfo.setFilename(newfileName);
									fileInfo.setFileid(childrenFolderID);
									fileInfoMapper.updateFileInfo(fileInfo);
								}
							}
							// 更新目录信息表
							FolderRelation folderRelation = new FolderRelation();
							folderRelation.setUserid(loginUserId);
							folderRelation.setParentfolderid(parentFolderID);
							folderRelation
									.setChildrenfolderid(childrenFolderID);
							String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
							if (parentFolderID == null
									|| "".equals(parentFolderID)) {
								isRootFolder = CommonConstant.IS_ROOT_FOLDER;
							}
							folderRelation.setIsRootFolder(isRootFolder);
							folderRelationMapper
									.moveToFolderRelation(folderRelation);
						}
					} catch (Exception e) {
						failedList.add(childrenFolderID);
						throw new RuntimeException();
					}
				}
			}
		} catch (Exception e) {
			logger.error("复制移动文件对象时数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return failedList;
	}

	/**
	 * 复制移动过程中，如果已经存在文件，直接重命名
	 * 
	 * @param fileInfoList
	 * @param fileObjName
	 * @param fileObjType
	 */
	private String getCopyOrmoveNewName(List<FileInfo> fileInfoList,
			String fileObjName, String fileObjType) {
		String fileName = fileObjName;
		List<String> fileNameList = new ArrayList<String>();
		for (FileInfo fileInfo : fileInfoList) {
			fileNameList.add(fileInfo.getFilename());
		}
		if ("文件夹".equals(fileObjType)) {
			for (int i = 1; i <= fileInfoList.size(); i++) {
				// 目标目录中包含复制移动的文件对象名称
				if (fileNameList.contains(fileName)) {
					fileName = fileObjName + "(" + i + ")";
				} else {
					// 循环到不包含文件时候结束，同时返回组合好的文件名
					break;
				}
			}
		} else {
			// 文件后缀名
			String ext = fileObjName
					.substring(fileObjName.lastIndexOf(".") + 1);
			for (int i = 1; i <= fileInfoList.size(); i++) {
				// 目标目录中包含复制移动的文件对象名称
				if (fileNameList.contains(fileName)) {
					fileName = fileObjName.replace("." + ext, "") + "(" + i
							+ ")" + "." + ext;
				} else {
					// 循环到不包含文件时候结束，同时返回组合好的文件名
					break;
				}
			}
		}

		return fileName;
	}

	/**
	 * 复制到递归复制处理
	 * 
	 * @param parentId
	 * @param nodeId
	 * @param userId
	 * @param newfileName
	 * @throws Exception
	 */
	private void copyTo(String parentId, String nodeId, String userId,
			String newfileName) throws Exception {
		// 生成一个新的nodeId(newNodeId), 结合parentId, userId插入目录关系表一条新数据
		FolderRelation folderRelation = new FolderRelation();
		folderRelation.setUserid(userId);
		folderRelation.setParentfolderid(parentId);
		String newNodeId = CreatUuidUtil.getUuid();
		folderRelation.setChildrenfolderid(newNodeId);
		String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
		if (parentId == null || "".equals(parentId)) {
			isRootFolder = CommonConstant.IS_ROOT_FOLDER;
		}
		folderRelation.setIsRootFolder(isRootFolder);
		folderRelationMapper.insertFolderRelation(folderRelation);

		// 通过nodeId查询关系表对应的所有子节点Id
		FolderRelation param = new FolderRelation();
		param.setUserid(userId);
		param.setParentfolderid(nodeId);
		List<FolderRelation> folderRelationList = folderRelationMapper
				.queryFolderRelation(param);

		// 判断nodeId是文件还是文件夹（nodeId在文件信息表中查询，如果有记录则为文件，否则为文件夹）
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileid(nodeId);
		fileInfo.setFilestate(CommonConstant.fileState.normal.toString());
		List<FileInfo> fileInfoList = fileInfoMapper.queryFileInfo(fileInfo);
		if (fileInfoList != null && fileInfoList.size() > 0) {
			// 文件的情况，新文件ID，其它信息和nodeId查询的信息一致，插入文件信息表一条记录
			FileInfo resultFileInfo = fileInfoList.get(0);
			resultFileInfo.setFileid(newNodeId);
			// 如果复制到对象目录已经存在该文件，文件重命名
			if (newfileName != null) {
				resultFileInfo.setFilename(newfileName);
			}
			fileInfoMapper.insertFileInfo(resultFileInfo);
			// 更新用户已用的存储容量
			CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();
			cloudDiskInfo.setUserid(userId);
			List<CloudDiskInfo> cloudDiskInfoList = cloudDiskInfoMapper
					.queryCloudDiskInfo(cloudDiskInfo);
			try {
				if (cloudDiskInfoList != null && cloudDiskInfoList.size() > 0) {
					long totalSize = Long.parseLong(cloudDiskInfoList.get(0)
							.getTotalsize());
					long usedSize = Long.parseLong(cloudDiskInfoList.get(0)
							.getUsedsize());
					if (totalSize >= usedSize
							+ Long.parseLong(fileInfoList.get(0).getFilesize())) {
						String usedTotalSize = String.valueOf(usedSize
								+ Long.parseLong(fileInfoList.get(0)
										.getFilesize()));
						cloudDiskInfo.setUsedsize(usedTotalSize);
						cloudDiskInfo.setUpdatedate(DateUtils.getSystemTime());
						cloudDiskInfoMapper.updateCloudDiskInfo(cloudDiskInfo);
					} else {
						// 超出容量直接抛出异常，回滚程序
						throw new RuntimeException();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
		} else {
			// 目录的情况，新目录ID，其它信息和nodeId查询的信息一致，插入目录信息表一条记录
			FolderInfo folderInfo = new FolderInfo();
			folderInfo.setFolderid(nodeId);
			folderInfo.setFolderState(CommonConstant.fileState.normal
					.toString());
			List<FolderInfo> folderInfoList = folderInfoMapper
					.queryFolderInfo(folderInfo);
			if (folderInfoList != null && folderInfoList.size() > 0) {
				FolderInfo resultFolderInfo = folderInfoList.get(0);
				resultFolderInfo.setFolderid(newNodeId);
				resultFolderInfo.setCreatedate(DateUtils.getSystemTime());
				// 如果复制到对象目录已经存在该文件，文件重命名
				if (newfileName != null) {
					resultFolderInfo.setFoldername(newfileName);
				}
				folderInfoMapper.insertFolderInfo(resultFolderInfo);
			}
		}

		// 查询子节点存在的情况
		if (folderRelationList != null && folderRelationList.size() > 0) {
			for (FolderRelation fr : folderRelationList) {
				// 循环子节点内容，生成一个新的parentId(newParentId),递归处理copyTo(newParentId,
				// 查询子节点， userId)
				// String newFolderId = CreatUuidUtil.getUuid();
				copyTo(newNodeId, fr.getChildrenfolderid(), userId, null);
			}
		}
	}

	@Override
	public int shareFileFromMyFile(ShareInfo shareInfo) {
		return shareInfoMapper.insertShareInfo(shareInfo);
	}

	@Override
	public List<QueryShareObjectInfo> queryShareObjectInfo(
			QueryShareObjectInfo queryShareObjectInfo) {
		return shareInfoMapper.queryShareObjectInfo(queryShareObjectInfo);
	}

	@Override
	public boolean renameFile(String fileType, Map<String, Object> map) {
		boolean renameFileResult = false;
		try {
			String id = map.get("id").toString();
			if ("folder".equals(fileType)) {
				FolderInfo folderInfo = new FolderInfo();
				folderInfo.setFolderid(id);
				folderInfo.setFoldername(map.get("newName").toString());
				folderInfo.setCreatedate(DateUtils.getSystemTime());
				folderInfoMapper.updateFolderInfo(folderInfo);
			} else if ("file".equals(fileType)) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileid(id);
				String newName = FileUtils.getReNameFileName(
						map.get("oldFileName").toString(), map.get("newName")
								.toString());
				fileInfo.setFilename(newName);
				fileInfoMapper.updateFileInfo(fileInfo);
			}
			renameFileResult = true;
		} catch (Exception e) {
			logger.error("修改文件对象名称发生异常：" + e.getMessage());
		}

		return renameFileResult;
	}

	@Transactional
	@Override
	public boolean fileUploader(MultipartFile multipartFile, String rows,
			String loginUser, String loginUserId, String comment,
			String parentFolderID) {
		boolean uploadResult = false;
		// 文件上传处理
		String[] uploadArr = super.uploadFile(multipartFile, loginUser);
		// 上传成功后数据库对应操作
		if (uploadArr != null && uploadArr.length > 1) {
			try {
				// 文件信息表插入数据
				FileInfo fileInfo = new FileInfo();
				String fileUuid = CreatUuidUtil.getUuid();
				fileInfo.setFileid(fileUuid);
				fileInfo.setFilename(FileUtils.getUploadFileName(
						multipartFile.getOriginalFilename(), rows));
				fileInfo.setFilesize(String.valueOf(multipartFile.getSize()));
				fileInfo.setFiletype(FileUtils.getFileType(multipartFile));
				fileInfo.setFilestate(CommonConstant.fileState.normal
						.toString());
				fileInfo.setFileuploader(loginUser);
				fileInfo.setFileuploadertime(DateUtils.getSystemTime());
				fileInfo.setFilegroupname(uploadArr[0]);
				fileInfo.setFilepath(uploadArr[1]);
				fileInfo.setComment(comment);
				fileInfoMapper.insertFileInfo(fileInfo);
				// 云盘信息剩余空间更新
				CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();
				cloudDiskInfo.setUserid(loginUserId);
				List<CloudDiskInfo> cloudDiskInfoList = cloudDiskInfoMapper
						.queryCloudDiskInfo(cloudDiskInfo);
				if (cloudDiskInfoList != null && cloudDiskInfoList.size() > 0) {
					long usedSize = Long.parseLong(cloudDiskInfoList.get(0)
							.getUsedsize()) + multipartFile.getSize();
					cloudDiskInfo.setUsedsize(String.valueOf(usedSize));
					cloudDiskInfo.setUpdatedate(DateUtils.getSystemTime());
					cloudDiskInfoMapper.updateCloudDiskInfo(cloudDiskInfo);
				}
				// 文件目录关系表插入数据
				FolderRelation folderRelation = new FolderRelation();
				folderRelation.setUserid(loginUserId);
				folderRelation.setParentfolderid(parentFolderID);
				folderRelation.setChildrenfolderid(fileUuid);
				String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
				if (parentFolderID == null || "".equals(parentFolderID)) {
					isRootFolder = CommonConstant.IS_ROOT_FOLDER;
				}
				folderRelation.setIsRootFolder(isRootFolder);
				folderRelationMapper.insertFolderRelation(folderRelation);
				// 操作完成设置返回成功
				uploadResult = true;
			} catch (Exception e) {
				// 上传成功但是更新数据库失败情况删除上传文件
				super.delete(uploadArr[0], uploadArr[1]);
				logger.error("上传成功后数据库操作发生异常：" + e.getMessage());
				throw new RuntimeException();
			}
		}

		return uploadResult;
	}

	@Override
	public ResponseEntity<byte[]> fileDownloader(String loginUser,
			String loginUserId, FileInfo fileInfo) {
		return super.downloadFile(fileInfo.getFilegroupname(),
				fileInfo.getFilepath(), fileInfo.getFilename());
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public int deleteFile(String fileID, RecycleInfo recycleInfo) {
		int result = 0;
		try {
			int flag = fileInfoMapper.deleteFile(fileID);
			int flag2 = folderInfoMapper.deleteFile(fileID);
			int flag3 = recycleInfoMapper.insertRecycleInfoInfo(recycleInfo);
			if (flag >= 0 && flag2 >= 0 && flag3 >= 0) {
				result = 1;
			}
		} catch (Exception e) {
			logger.error("删除文件发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public TreeFolderDataInfo queryFolderIds(String loginUserId,
			String parentFolderID, String selectFolderIds) {
		TreeFolderDataInfo treeFolderDataInfo = new TreeFolderDataInfo();
		try {
			queryTreeFolderDataInfo(loginUserId, treeFolderDataInfo,
					selectFolderIds);
		} catch (Exception e) {
			logger.error("目标目录下拉框值获取发生异常：" + e.getMessage());
			treeFolderDataInfo = new TreeFolderDataInfo();
		}
		// 增加全部文件为根目录
		TreeFolderDataInfo allFolderIds = new TreeFolderDataInfo();
		List<TreeFolderDataInfo> nodes = new ArrayList<TreeFolderDataInfo>();
		allFolderIds.setId("");
		allFolderIds.setNodes(treeFolderDataInfo.getNodes());
		allFolderIds.setTags("folder");
		allFolderIds.setText("全部文件");
		nodes.add(allFolderIds);

		return allFolderIds;
	}

	/**
	 * 递归查询到目录结构
	 * 
	 * @param loginUserId
	 * @param treeFolderDataInfo
	 * @param selectFolderIds
	 */
	private void queryTreeFolderDataInfo(String loginUserId,
			TreeFolderDataInfo treeFolderDataInfo, String selectFolderIds)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 更目录情况
		if (treeFolderDataInfo.getNodes() == null
				|| treeFolderDataInfo.getNodes().size() < 1) {
			map.put("userID", loginUserId);
			map.put("parentFolderID", "");
			map.put("selectFolderIds", selectFolderIds);
			List<TreeFolderDataInfo> parentInfoList = folderInfoMapper
					.queryAllFolderInfoByKey(map);
			// 递归执行查询子目录
			if (parentInfoList != null && parentInfoList.size() > 0) {
				treeFolderDataInfo.setNodes(parentInfoList);
				queryTreeFolderDataInfo(loginUserId, treeFolderDataInfo,
						selectFolderIds);
			}
		} else {// 子目录情况
			List<TreeFolderDataInfo> parentInfoList = treeFolderDataInfo
					.getNodes();
			if (parentInfoList != null && parentInfoList.size() > 0) {
				for (TreeFolderDataInfo tfd : parentInfoList) {
					map.put("userID", loginUserId);
					map.put("parentFolderID", tfd.getId());
					map.put("selectFolderIds", selectFolderIds);
					List<TreeFolderDataInfo> nodeList = folderInfoMapper
							.queryAllFolderInfoByKey(map);
					// 递归执行查询子目录
					if (nodeList != null && nodeList.size() > 0) {
						tfd.setNodes(nodeList);
						queryTreeFolderDataInfo(loginUserId, tfd,
								selectFolderIds);
					}
				}
			}
		}
	}

	/*
	 * (实现接口方法)
	 * 
	 * @see cn.springmvc.service.MyFileManageService#fileType(java.lang.String)
	 */
	@Override
	public String fileType(String fileID) {
		// TODO Auto-generated method stub
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileid(fileID);
		List<FileInfo> fileInfos = fileInfoMapper.queryFileInfo(fileInfo);
		if (fileInfos.size() > 0) {
			return "file";
		}
		FolderInfo folderInfo = new FolderInfo();
		folderInfo.setFolderid(fileID);
		List<FolderInfo> folderInfos = folderInfoMapper
				.queryFolderInfo(folderInfo);
		if (folderInfos.size() > 0) {
			return "folder";
		}
		return "none";
	}

	/*
	 * (实现接口方法)
	 * 
	 * @see
	 * cn.springmvc.service.MyFileManageService#queryMyFileInfo(java.util.Map)
	 */
	@Override
	public List<FileInfo> queryMyFileInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return fileInfoMapper.queryMyFileInfo(map);
	}

	@Override
	public Map<String, Object> checkRepeatedSharedFile(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		Map<String, Object> checkedResultMap = new HashMap<String, Object>();
		List<QueryMySharedFileInfo> RepeatShareInfo = shareInfoMapper
				.queryRepeatedShareInfo(shareInfo);
		if (RepeatShareInfo == null || RepeatShareInfo.size() == 0) {
			checkedResultMap.put("ShareInfo", shareInfo);
		} else {
			String repeatedMessage = "★ <font style='color:red'>["
					+ shareInfo.getFileName()
					+ "]</font> 已经共享给 <font style='color:red'>["
					+ shareInfo.getShareObjectName() + "]</font><br>";
			checkedResultMap.put("repeatedMessage", repeatedMessage);
		}
		return checkedResultMap;
	}
}
