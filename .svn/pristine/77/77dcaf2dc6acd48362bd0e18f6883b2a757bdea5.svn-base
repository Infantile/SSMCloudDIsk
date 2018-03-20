package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.springmvc.dao.CloudDiskUserMapper;
import cn.springmvc.dao.GroupInfoMapper;
import cn.springmvc.dao.GroupMemberInfoMapper;
import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;
import cn.springmvc.service.GroupManageService;
import cn.utils.CreatUuidUtil;
import cn.utils.DateUtils;
@Service
public class GroupManageServiceImpl implements GroupManageService {
	private static Logger logger = LoggerFactory
			.getLogger(GroupManageServiceImpl.class);
	@Autowired
	private GroupMemberInfoMapper groupMemberInfoMapper;
	@Autowired
	private GroupInfoMapper groupInfoMapper;
	@Autowired
	private CloudDiskUserMapper cloudDiskUserMapper;
	
	@Override
	public List<CloudDiskUser> queryAllUser(Map<String, Object> paramMap){
		try {
			return groupMemberInfoMapper.queryCloudAllUser(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("数据库查询所有用户发生异常:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<CloudDiskUser> queryLeftUser(Map<String, Object> paramMap){
		try {
			if (paramMap.containsKey("select")){
				return groupMemberInfoMapper.selectValidMember(paramMap);
			}else {
				return groupMemberInfoMapper.queryCloudRestUser(paramMap);
			}
		} catch (Exception e) {
			logger.error("数据库查询左列表用户发生异常:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<CloudDiskUser> queryRightUser(Map<String, Object> paramMap){
		try {
			if (paramMap.containsKey("select")){
				return groupMemberInfoMapper.selectValidMember(paramMap);
			}else {
				return groupMemberInfoMapper.queryByGroupId(paramMap);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("数据库查询右列表用户发生异常:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<GroupInfo> queryGroupListInfo(Map<String, Object> paramMap) {
		try{
			return groupMemberInfoMapper.queryByUserId(paramMap);
		}catch (Exception e){
			logger.error("数据库查询群组列表发生异常:" + e.getMessage());
			return null;
		}
	}
	
	@Transactional
	@Override
	public boolean createGroup(GroupMemberInfoKey groupMemberInfoKey,GroupInfo groupInfo,ArrayList<String> userId) {
		try{
			String groupID = CreatUuidUtil.getUuid();
			groupInfo.setGroupid(groupID);
			groupMemberInfoKey.setGroupid(groupID);
			groupInfo.setCreatedate(DateUtils.getSystemTime());
			for (int i=0; i<userId.size();i++){
				groupMemberInfoKey.setUserid(userId.get(i));
				groupMemberInfoMapper.insertSelective(groupMemberInfoKey);
			}
			groupInfoMapper.insertSelective(groupInfo);
			return true;
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
	
	@Transactional
	@Override
	public boolean quitGroup(GroupMemberInfoKey groupMemberInfoKey) {
		// TODO Auto-generated method stub
	try {
			groupMemberInfoMapper.delete(groupMemberInfoKey);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	@Transactional
	@Override
	public boolean deleteGroup(ArrayList<String> groupId) {
		// TODO Auto-generated method stub
		try{
			for (int i=0; i<groupId.size();i++){
				groupMemberInfoMapper.deleteByPrimaryKey(groupId.get(i));
				groupInfoMapper.deleteByPrimaryKey(groupId.get(i));
			}
			return true;
		}catch(Exception e){
			logger.error("删除用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
		
	}

	@Transactional
	@Override
	public boolean updateGroupName(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		try {
			return groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
		} catch (Exception e) {
			logger.error("修改群组名称时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
		
	}

	@Transactional
	@Override
	public boolean kickingGroupMember(String groupId,ArrayList<String> userId) {
		// TODO Auto-generated method stub
		GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
		try{
			for (int i=0; i<userId.size();i++){
				groupMemberInfoKey.setGroupid(groupId);
				groupMemberInfoKey.setUserid(userId.get(i));
				groupMemberInfoMapper.delete(groupMemberInfoKey);
			}
			return true;
		}catch(Exception e){
			logger.error("删除用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
	}

	@Transactional
	@Override
	public boolean changeGroupMember(JSONObject json,String userId){
		// TODO Auto-generated method stub
		try{
			//先删除群组所有成员的信息
			groupMemberInfoMapper.deleteByPrimaryKey(json.get("groupID").toString());
			GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
			groupMemberInfoKey.setGroupid(json.get("groupID").toString());
			for (int i=0;i<json.getJSONArray("newGrpUserID").size();i++){
				groupMemberInfoKey.setUserid(json.getJSONArray("newGrpUserID").get(i).toString());
				groupMemberInfoMapper.insertSelective(groupMemberInfoKey);
			}
			groupMemberInfoKey.setUserid(userId);
			groupMemberInfoMapper.insertSelective(groupMemberInfoKey);
			return true;
		}catch(Exception e){
			logger.error("修改群组用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
	}
}
