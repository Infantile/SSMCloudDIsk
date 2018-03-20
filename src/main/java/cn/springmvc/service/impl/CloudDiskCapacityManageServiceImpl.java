package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.CloudDiskInfoMapper;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.service.CloudDiskCapacityManageService;
@Service
public class CloudDiskCapacityManageServiceImpl implements
		CloudDiskCapacityManageService {
	@Autowired
	private CloudDiskInfoMapper cloudDiskInfoMapper;

	/**
	 *获取对应用户网盘使用的情况
	 */
	@Override
	public List<CloudDiskInfo> queryClouddiskCapacityInfo(CloudDiskInfo cloudDiskInfo) {
		return cloudDiskInfoMapper.queryCloudDiskInfo(cloudDiskInfo);
	}

	@Override
	public int applyCapacity(CloudDiskInfo cloudDiskInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
