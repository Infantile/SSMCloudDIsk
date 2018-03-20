package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.FileInfo;

/**
 * @author 胡志强 2017年5月23日
 */
public interface CollectFileManageService {
	/**
	 * 收藏夹表格信息初始化
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> queryCollectTableInfo(Map<String, Object> map)
			throws Exception;

	/**
	 * 输入搜索框查询收藏列表信息
	 * 
	 * @param map
	 * @return
	 */
	public List<FileInfo> queryCollectSearchInfo(Map<String, Object> map);

	/**
	 * 修正子目录文件的收藏日期
	 * 
	 * @param map
	 * @return
	 */
	public void updateCollectTime(List<FileInfo> collectInfoList,
			String rootFolderId);

	/**
	 * 取消收藏文件
	 * 
	 * @param collectInfo
	 * @return int
	 */
	public boolean cancelCollectFile(String loginUserId,
			List<String> collectObjectList);

	/**
	 * 文件下载
	 * 
	 * @param fileID
	 * @return String
	 */
	String fileDownloader(String fileID);

	/**
	 * 文件预览
	 * 
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);

}
