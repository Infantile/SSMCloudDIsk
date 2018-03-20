package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FileInfo;

@Component
public interface FileInfoMapper {
    /**
     * 查询文件信息 
     * @param fileInfo
     * @return
     */
	List<FileInfo> queryFileInfo(FileInfo fileInfo);

	/**
     * 根据文件ID查询文件详细信息，以便后续下载文件 
     * @param map
     * @return
     */
	List<FileInfo> queryMyFileInfo(Map<String, Object> map);
	
	/**
	 * 通过文件ID查询系统中一共有多少个文件副本
	 * @param fileInfo
	 * @return List<FileInfo>
	 */
	List<FileInfo> queryFileNumber(FileInfo fileInfo);
    /**
     * 查询页面我的文件表格信息 
     * @param map
     * @return
     */
	List<FileInfo> queryMyFileTableInfo(Map<String, Object> map);
	
    /**
     * 点击目录查询目录下所有文件以及文件目录
     * @param map
     * @return
     */
	List<FileInfo> queryFolderContainInfo(Map<String, Object> map);
	
    /**
     * 输入搜索框查询列表信息
     * @param map
     * @return
     */
	List<FileInfo> querySearchInfo(Map<String, Object> map);
	
	/**
     * 共享文件输入搜索框查询列表信息
     * @param map
     * @return
     */
	List<FileInfo> querySharedFileSearchInfo(Map<String, Object> map);
	/**
     * 删除文件信息 
     * @param fileInfo
     * @return
     */
	int deleteFileInfo(FileInfo fileInfo);

	/**
	 * 插入文件信息 
	 * @param fileInfo
	 * @return
	 */
    int insertFileInfo(FileInfo fileInfo);

	/**
	 * 更新文件信息 
	 * @param fileInfo
	 * @return
	 */
    int updateFileInfo(FileInfo fileInfo);

	/**
	 * @param fileID
	 * @return int
	 */
	int deleteFile(String fileID);
}