package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.RecycleInfo;

/**
 * @author 胡志强 2017年5月23日
 */
public interface RecycleFileManageService {

	/**
	 * 查看回收站文件列表信息
	 * 
	 * @param recycleInfo
	 * @return List<FileInfo>
	 */
	List<FileInfo> queryRecycleTableInfo(Map<String, Object> map);
	
	/**
	 * @param recycleInfo
	 * @return List<RecycleInfo>
	 */
	List<RecycleInfo> queryRecycleInfoInfo(RecycleInfo recycleInfo);

	/**
	 * 还原文件
	 * 
	 * @param recycleInfo
	 * @return int
	 */
	String restoreFile(String loginUserId, String fileID);

	/**
	 * 输入搜索框查询收藏列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> queryRecycleSearchInfo(Map<String, Object> map);

	/**
	 * 彻底删除文件
	 * 
	 * @param recycleInfo
	 * @return int
	 */
	boolean absolutelyDeleteFile(String fileID,String userID);

	/**
	 * 文件预览
	 * 
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);
}
