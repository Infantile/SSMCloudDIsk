package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.CloudDiskUser;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface UserManageService {
    /**
     * 云盘用户登录验证信息 
     * @param cloudDiskUser
     * @return
     */
	List<CloudDiskUser> loginCheck(CloudDiskUser cloudDiskUser);
	/**
	 * 修改用户密码
	 * @param cloudDiskUser
	 * @return
	 */
	int updatePassword(CloudDiskUser cloudDiskUser);	
	/**
	 * 退出系统
	 * @param cloudDiskUser
	 * @return
	 */	
	void quitSystem(CloudDiskUser cloudDiskUser);
	
}
