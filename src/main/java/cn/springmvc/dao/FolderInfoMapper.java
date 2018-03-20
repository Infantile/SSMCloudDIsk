package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.TreeFolderDataInfo;

@Component
public interface FolderInfoMapper {
    /**
     * 查询目录信息 
     * @param folderInfo
     * @return
     */
	List<FolderInfo> queryFolderInfo(FolderInfo folderInfo);
	
    /**
     * 查询用户所有目录信息（为了移动复制文件选择目标目录用） 
     * @param userId
     * @return
     */
	List<TreeFolderDataInfo> queryAllFolderInfoByKey(Map<String, Object> map);
    
	/**
	 * 根据子文件查询其直接父目录
	 * @param folderInfo
	 * @return List<FolderInfo>
	 */
	List<FolderInfo> queryParentFolderInfo(String fileID);
	
	/**
	 * 根据子文件查询其所有父目录
	 * @param fileID
	 * @return List<FolderInfo>
	 */
	List<FolderInfo> queryAllParentFolderNodeInfo(String fileID);
	
	/**
     * 删除目录信息 
     * @param folderInfo
     * @return
     */
	int deleteFolderInfo(FolderInfo folderInfo);

	/**
	 * 插入目录信息 
	 * @param folderInfo
	 * @return
	 */
    int insertFolderInfo(FolderInfo folderInfo);

	/**
	 * 更新目录信息 
	 * @param folderInfo
	 * @return
	 */
    int updateFolderInfo(FolderInfo folderInfo);
    
    /**
     * 删除目录
     * @param fileID
     * @return int
     */
    int deleteFile(String fileID);
}