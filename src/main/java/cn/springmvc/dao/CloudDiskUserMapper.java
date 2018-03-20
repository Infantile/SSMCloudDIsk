package cn.springmvc.dao;

import java.util.List;
import org.springframework.stereotype.Component;

import cn.springmvc.model.CloudDiskUser;
@Component
public interface CloudDiskUserMapper {
    /**
     * 查询云盘用户信息 
     * @param cloudDiskUser
     * @return
     */
	List<CloudDiskUser> queryCloudDiskUserInfo(CloudDiskUser cloudDiskUser);
	
    /**
     * 删除云盘用户信息 
     * @param cloudDiskUser
     * @return
     */
	int deleteCloudDiskUserInfo(CloudDiskUser cloudDiskUser);

	/**
	 * 插入云盘用户信息 
	 * @param cloudDiskUser
	 * @return
	 */
    int insertCloudDiskUserInfo(CloudDiskUser cloudDiskUser);

	/**
	 * 更新云盘用户信息 
	 * @param cloudDiskUser
	 * @return
	 */
    int updateCloudDiskUserInfo(CloudDiskUser cloudDiskUser);
}