package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.ShareInfo;

@Component
public interface ShareInfoMapper {
	int insertShareInfo(ShareInfo shareInfo);

	int insertSelective(ShareInfo record);

	/**
	 * 查询我共享给别人以及群组的共享文件列表信息
	 * 
	 * @param shareInfo
	 * @return List<QuerySharedFileTableInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedFileByOperator(
			Map<String, Object> map);

	/**
	 * 查询别人共享给我或者群组，且群组中有我的共享文件列表信息
	 * 
	 * @param shareInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedFileByShareObjectID(
			Map<String, Object> map);

	/**
	 * 查询我的共享文件列表信息
	 * 
	 * @param shareInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedFileTableInfo(
			Map<String, Object> map);

	/**
	 * 根据目录ID查询该目录下的子文件列表信息
	 * 
	 * @param shareInfo
	 * @return List<QuerySharedFileTableInfo>
	 */
	List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(
			Map<String, Object> paramMap);

	/**
	 * 根据当前目录ID查看其父目录下的子文件列表信息
	 * 
	 * @param folderInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPreviousPageSharedFileInfoByFolderID(
			FolderInfo folderInfo);

	/**
	 * 根据搜索字段查询我的共享文件信息
	 * 
	 * @param map
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedSearchInfo(Map<String, Object> map);

	/**
	 * 根据搜索字段查询公共共享文件信息
	 * 
	 * @param map
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPublicSharedSearchInfo(
			Map<String, Object> map);

	/**
	 * 删除共享文件
	 * 
	 * @param queryMySharedFileInfo
	 * @return int
	 */
	int deleteMySharedInfo(ShareInfo shareInfo);

	/**
	 * 根据文件ID删除共享文件
	 * 
	 * @param shareInfo
	 * @return int
	 */
	int deleteSharedInfoInfo(ShareInfo shareInfo);

	/**
	 * 查询公共共享文件列表信息
	 * 
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPublicSharedFileByShareObjectID(
			Map<String, Object> paramMap);

	/**
	 * 查询共享对象列表信息
	 * 
	 * @param queryShareObjectInfo
	 * @return List<QueryShareObjectInfo>
	 */
	List<QueryShareObjectInfo> queryShareObjectInfo(
			QueryShareObjectInfo queryShareObjectInfo);

	List<QueryMySharedFileInfo> querySharedFileByFileID(String fileID);

	/**
	 * 查询文件是否重复共享
	 * 
	 * @param ShareInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryRepeatedShareInfo(ShareInfo shareInfo);
}