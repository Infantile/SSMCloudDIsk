package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.ShareInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface ShareManageService {
    
	/**
	 * 从本地电脑共享文件
	 * @param shareInfo
	 * @return int
	 */
	int shareFileFromLocal(ShareInfo shareInfo);
	
	/**
	 * 查询共享对象信息
	 * @param cloudDiskUser
	 * @param groupInfo
	 * @return List<GroupInfo>
	 */
	List<GroupInfo> queryShareObjectInfo(CloudDiskUser cloudDiskUser,GroupInfo groupInfo);
}
