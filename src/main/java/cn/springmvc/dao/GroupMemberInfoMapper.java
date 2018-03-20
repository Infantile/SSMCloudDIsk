package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;

@Component
public interface GroupMemberInfoMapper {
	/**
	 * 根据用户userID查询
	 * @param paramMap
	 * @return
	 */
	List<GroupInfo> queryByUserId(Map<String, Object> paramMap);
	
	/**
	 * 根据组的groupID查询
	 * @param map
	 * @return
	 */
	List<CloudDiskUser> queryByGroupId(Map<String, Object> map);
	
	/**
	 * 查询所有云盘用户信息(除session中用户外的)
	 */
	List<CloudDiskUser> queryCloudRestUser(Map<String, Object> paramMap);
	
	/**
	 * 查询所有云盘用户信息(除session中用户外的)
	 */
	List<CloudDiskUser> queryCloudAllUser(Map<String, Object> paramMap);
	
	/**
	 * 查询有效的用户列表
	 * @param map
	 * @return
	 */
	List<CloudDiskUser> selectValidMember(Map<String, Object> map);
	
	/**
	 * 通过主键groupID删除
	 * @param groupid
	 * @return
	 */
    int deleteByPrimaryKey(String groupid);
    
    /**
     * 根据不同条件删除
     * @param key
     * @return
     */
    int delete(GroupMemberInfoKey key);

    /**
     * 根据不同条件插入
     * @param record
     * @return
     */
    int insertSelective(GroupMemberInfoKey record);
}