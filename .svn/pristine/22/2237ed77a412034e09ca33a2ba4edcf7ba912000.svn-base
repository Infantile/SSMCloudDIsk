package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;

@Component
public interface CollectInfoMapper {
	/**
	 * 查询收藏夹文件表格信息
	 * 
	 * @param fileInfo
	 * @return
	 */
	List<FileInfo> queryCollectTableInfo(Map<String, Object> map);

	/**
	 * 查询收藏信息
	 * 
	 * @param collectInfo
	 * @return
	 */
	List<CollectInfo> queryCollectInfoInfo(CollectInfo collectInfo);

	/**
	 * 删除收藏信息
	 * 
	 * @param collectInfo
	 * @return
	 */
	int deleteCollectInfoInfo(CollectInfo collectInfo);

	/**
	 * 插入收藏信息
	 * 
	 * @param collectInfo
	 * @return
	 */
	int insertCollectInfoInfo(CollectInfo collectInfo);

	/**
	 * 更新收藏信息
	 * 
	 * @param collectInfo
	 * @return
	 */
	int updateCollectInfoInfo(CollectInfo collectInfo);
}