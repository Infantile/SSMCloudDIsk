package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.RecycleInfo;

@Component
public interface RecycleInfoMapper {
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
	 * 查询搜索信息
	 * 
	 * @param collectInfo
	 * @return
	 */
	List<FileInfo> querySearchRecycleInfo(Map<String, Object> map);

	/**
	 * 删除回收信息
	 * 
	 * @param recycleInfo
	 * @return
	 */
	int deleteRecycleInfoInfo(RecycleInfo recycleInfo);

	/**
	 * 插入回收信息
	 * 
	 * @param recycleInfo
	 * @return
	 */
	int insertRecycleInfoInfo(RecycleInfo recycleInfo);

	/**
	 * 更新回收信息
	 * 
	 * @param recycleInfo
	 * @return
	 */
	int updateRecycleInfoInfo(RecycleInfo recycleInfo);
}