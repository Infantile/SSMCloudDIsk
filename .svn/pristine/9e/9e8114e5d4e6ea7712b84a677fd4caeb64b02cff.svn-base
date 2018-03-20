package cn.springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface GroupManageService {
	/**
     * 查看系统用户信息 
     * @param fileInfo
     * @return
     */
	List<CloudDiskUser> queryAllUser(Map<String, Object> paramMap);
	
	/**
     * 筛选右边用户信息 
     * @param fileInfo
     * @return
     */
	List<CloudDiskUser> queryLeftUser(Map<String, Object> paramMap);
	 
	/**
     * 筛选左边用户信息 
     * @param fileInfo
     * @return
     */
	List<CloudDiskUser> queryRightUser(Map<String, Object> paramMap);
	
	 /**
     * 查看群组列表信息 
     * @param fileInfo
     * @return
     */
	List<GroupInfo> queryGroupListInfo(Map<String, Object> paramMap);
	
	/**
     * 创建群组 
     * @param fileInfo
     * @return
     */
	boolean createGroup(GroupMemberInfoKey groupMemberInfoKey,GroupInfo groupInfo,ArrayList<String> userId);
	
	/**
	  * 退出群组
	 * @param cloudDiskUser
	 * @return boolean
	 */
	boolean quitGroup(GroupMemberInfoKey groupMemberInfoKey);
	 
	/**
	 * 解散群组
	 * @param cloudDiskUser
	 * @return boolean
	 */
	boolean deleteGroup(ArrayList<String> groupId);
	 
	/**
	 * 修改群组名称
	 * @param groupInfo
	 * @return int
	 */
	boolean updateGroupName(GroupInfo groupInfo);
	 
	 /**
	  * 踢人出群组
	 * @param groupMemberInfoKey
	 * @return boolean
	 */
	boolean kickingGroupMember(String groupId,ArrayList<String> userId);
	 
	/**
	 * 修改群组成员
	 * @param changeGroupMember
	 * @return boolean
	 */
	boolean changeGroupMember(JSONObject json,String userId);
}
