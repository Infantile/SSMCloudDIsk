package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.springmvc.model.CloudDiskInfo;

@Component
public interface CloudDiskInfoMapper {
    /**
     * 查询云盘信息 
     * @param cloudDiskInfo
     * @return
     */
	List<CloudDiskInfo> queryCloudDiskInfo(CloudDiskInfo cloudDiskInfo);
    
    /**
     * 删除云盘信息 
     * @param cloudDiskInfo
     * @return
     */
	int deleteCloudDiskInfo(CloudDiskInfo cloudDiskInfo);

	/**
	 * 插入云盘信息 
	 * @param cloudDiskInfo
	 * @return
	 */
    int insertCloudDiskInfo(CloudDiskInfo cloudDiskInfo);

	/**
	 * 更新云盘信息 
	 * @param cloudDiskInfo
	 * @return
	 */
    int updateCloudDiskInfo(CloudDiskInfo cloudDiskInfo);
    
    /**
     * 删除文件时更新用户磁盘已用大小
     * @param cloudDiskInfo
     * @return int
     */
    int updateCloudDiskCapacityInfo(CloudDiskInfo cloudDiskInfo);
}