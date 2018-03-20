package cn.springmvc.service;

import cn.springmvc.model.CloudDiskUser;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface SettingManageService {
    
	 /**
	  * 修改用户密码
	 * @param cloudDiskUser
	 * @return int
	 */
	int updatePassword(CloudDiskUser cloudDiskUser);
}
