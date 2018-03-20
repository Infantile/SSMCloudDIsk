package cn.springmvc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.CloudDiskUserMapper;
import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.service.UserManageService;
@Service
public class UserManageServiceImpl implements UserManageService {
	private static Logger logger = LoggerFactory.getLogger(UserManageServiceImpl.class);
    @Autowired
	private CloudDiskUserMapper cloudDiskUserMapper;
	
    @Override
	public List<CloudDiskUser> loginCheck(CloudDiskUser cloudDiskUser) {
		return cloudDiskUserMapper.queryCloudDiskUserInfo(cloudDiskUser);
	}

    @Override
	public int updatePassword(CloudDiskUser cloudDiskUser) {
    	int result = -1;
		try {
			result = cloudDiskUserMapper.updateCloudDiskUserInfo(cloudDiskUser);
		} catch (Exception e) {
			logger.error("修改密码异常：" + e.getMessage());
		}
		return result;
	}

	@Override
	public void quitSystem(CloudDiskUser cloudDiskUser) {
		// TODO Auto-generated method stub
	}
}
		
		
		
