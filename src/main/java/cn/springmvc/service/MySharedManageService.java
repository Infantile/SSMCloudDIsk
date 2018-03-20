package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface MySharedManageService {
    /**
     * 查看我的共享文件列表信息 
     * @param shareInfo
     * @return
     */
	 List<QueryMySharedFileInfo> queryMySharedFileListInfo(Map<String, Object> map);
	 
	 /**
	  * 根据目录ID查看共享目录中的子文件列表信息
	 * @param shareInfo
	 * @return List<FileInfo>
	 */
	List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(Map<String, Object> paramMap);
	 
	 /**
	  * 取消共享文件
	 * @param fileInfo
	 * @return int
	 */
	int cancelSharedFile(ShareInfo shareInfo);
	 
	/**
	 * 根据搜索条件查询我的共享文件信息
	 * @param map
	 * @return List<QueryMySharedFileInfo>
	 */
	public List<QueryMySharedFileInfo> queryMySharedSearchInfo(Map<String, Object> map,Map<String, Object> map2);
	/**
	 * 文件预览
	 * @param fileInfo
	 * @return String
	 */
	String filePreview(FileInfo fileInfo);
	 
	/**
	 * 文件下载
	 * @param fileInfo
	 * @return String
	 */
	String fileDownloader(FileInfo fileInfo);
}
