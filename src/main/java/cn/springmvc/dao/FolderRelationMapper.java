package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FolderRelation;

@Component
public interface FolderRelationMapper {
    /**
     * 查询目录关系信息 
     * @param folderRelation
     * @return
     */
	List<FolderRelation> queryFolderRelation(FolderRelation folderRelation);
    
    /**
     * 删除目录关系信息 
     * @param folderRelation
     * @return
     */
	int deleteFolderRelation(FolderRelation folderRelation);

	/**
	 * 插入目录关系信息 
	 * @param folderRelation
	 * @return
	 */
    int insertFolderRelation(FolderRelation folderRelation);

	/**
	 * 更新目录关系信息 
	 * @param folderRelation
	 * @return
	 */
    int updateFolderRelation(FolderRelation folderRelation);
    
    /**
     * 设置某个目录为根目录
     * @param folderRelation
     * @return 
     */
    int setRootFolder(FolderRelation folderRelation);
	
    /**
	 * 移动文件对象时更新目录关系信息
	 * @param folderRelation
	 * @return
	 */
    int moveToFolderRelation(FolderRelation folderRelation);
}