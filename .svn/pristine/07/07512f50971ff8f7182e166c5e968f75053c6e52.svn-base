package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface PublicSharedManageService {
    /**
     * 查看公共共享文件列表信息 
     * @param fileInfo
     * @return
     */
	 List<QueryMySharedFileInfo> queryPublicSharedFileListInfo(Map<String, Object> paramMap);
	 
	 /**
	  * 根据目录ID查看共享目录中的子文件列表信息
	 * @param shareInfo
	 * @return List<FileInfo>
	 */
	List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(Map<String, Object> paramMap);
	 
	/**
	  * 根据搜索条件查询公共共享文件信息
	  * @param map
	  * @return List<QueryMySharedFileInfo>
	  */
	public List<QueryMySharedFileInfo> queryPublicSharedSearchInfo(Map<String, Object> map,Map<String, Object> map2);
		
	 /**
	  * 取消共享文件
	 * @param fileInfo
	 * @return int
	 */
	int cancelSharedFile(ShareInfo shareInfo);	 
	 
	/**
	 * 文件下载
	 * @param fileID
	 * @return String
	 */
	String fileDownloader(String fileID);
	 
	/**
	 * 文件预览
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);

	/**
	 * @param folderInfo
	 * @param queryMySharedFileInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPublicSharedFileListInfoByFolderID(
			Map<String, Object> paramMap, QueryMySharedFileInfo queryMySharedFileInfo);

	/**
	 * @param fileid
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> checkIsSharedFile(String fileID);
}
