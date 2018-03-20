package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.CloudDiskInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface CloudDiskCapacityManageService{
    /**
     * 查看云盘容量信息 
     * @param cloudDiskInfo
     * @return
     */
	 List<CloudDiskInfo> queryClouddiskCapacityInfo(CloudDiskInfo cloudDiskInfo);
	 
	 /**
	  * 申请扩容
	 * @param cloudDiskInfo
	 * @return int
	 */
	int applyCapacity(CloudDiskInfo cloudDiskInfo);
	 
}
