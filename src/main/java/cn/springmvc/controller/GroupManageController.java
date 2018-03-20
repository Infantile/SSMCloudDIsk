package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;
import cn.springmvc.service.GroupManageService;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

/**
 * Servlet implementation class GroupManageController
 */

@Controller
@RequestMapping("/group")
public class GroupManageController{
	private static Logger logger = LoggerFactory
			.getLogger(GroupManageController.class);
	@Autowired
	private GroupManageService groupManageService;
	
	@ResponseBody
	@RequestMapping("/queryAllMember")
	public Map<String, Object> queryAllMember (HttpServletRequest request,
			@RequestParam(value="search",required=false) String searchVal) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("userID", RequestUtils.getSessionKeyValue(request, "userId"));
		paramMap.put("searchVal", RequestUtils.formatBootstrapTableEncoding(searchVal));
		
		getTableSortInfo(request,paramMap);
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		try {
			list = groupManageService.queryAllUser(paramMap);
		} catch (Exception e) {
			logger.error("查询所有用户时发生异常:" + e.getMessage());
			return resultMap;
		}
		if (list.size()>0){
			resultMap.put("result", "数据库查询成功!");
			resultMap.put("rows", list);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryLeftMember")
	public Map<String, Object> queryLeftMember (HttpServletRequest request,
			@RequestParam(value="search",required=false) String searchVal,
			@RequestParam(value="groupID",required=false) String groupId) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("groupID", groupId);
		paramMap.put("userID", RequestUtils.getSessionKeyValue(request, "userId"));
		paramMap.put("searchVal", RequestUtils.formatBootstrapTableEncoding(searchVal));
		if (searchVal != null && searchVal.replace(" ", "").equals("")){
			searchVal = null;
		}
		getTableSortInfo(request,paramMap);
		JSONObject userJson = JSonUtils.objectToJson(RequestUtils.getRequestKeyValue(request, "ajaxUserId"));
		if (!userJson.isEmpty()){
			String userIdStr = "";
			if (userJson.has("userid")){
				for (int i = 0;i<userJson.getJSONArray("userid").size();i++){
					userIdStr += userJson.getJSONArray("userid").get(i).toString() + ',';
				}
				paramMap.put("select", userIdStr);
			}else if(userJson.has("listid")) {
				for (int i = 0;i<userJson.getJSONArray("listid").size();i++){
					userIdStr += userJson.getJSONArray("listid").get(i).toString() + ',';
				}
				paramMap.put("list", userIdStr);
			}
		}
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		try {
			list = groupManageService.queryLeftUser(paramMap);
		} catch (Exception e) {
			logger.error("查询所有用户时发生异常:" + e.getMessage());
			return resultMap;
		}
		if (list.size()>0){
			resultMap.put("result", "数据库查询成功!");
			resultMap.put("rows", list);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryRightMember")
	public Map<String, Object> queryRightMember(HttpServletRequest request,
			@RequestParam(value="groupID", required=false) String groupid,
			@RequestParam(value="search", required=false) String searchVal) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupID", groupid);
		paramMap.put("userID", RequestUtils.getSessionKeyValue(request, "userId"));
		paramMap.put("searchVal", RequestUtils.formatBootstrapTableEncoding(searchVal));
		if (searchVal != null && searchVal.replace(" ", "").equals("")){
			searchVal = null;
		}
		JSONObject userJson = JSonUtils.objectToJson(RequestUtils.getRequestKeyValue(request, "ajaxUserId"));
		if (!userJson.isEmpty()){
			String userIdStr = "";
			if (userJson.has("userid")){
				for (int i = 0;i<userJson.getJSONArray("userid").size();i++){
					userIdStr += userJson.getJSONArray("userid").get(i).toString() + ',';
				}
				paramMap.put("select", userIdStr);
			}else if(userJson.has("listid")) {
				for (int i = 0;i<userJson.getJSONArray("listid").size();i++){
					userIdStr += userJson.getJSONArray("listid").get(i).toString() + ',';
				}
				paramMap.put("list", userIdStr);
			}
		}
		
		getTableSortInfo(request, paramMap);
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		try{
			list = groupManageService.queryRightUser(paramMap);
			resultMap.put("result", "查询成功！");
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.error("查询群组所有用户时发生异常:" + e.getMessage());
			return resultMap;
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/createGroup")
	public Map<String, Object> createGroup(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "userJson");
        //JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
		GroupInfo groupInfo = new GroupInfo();
		GroupMemberInfoKey groupMemberInfoKey=new GroupMemberInfoKey();
		groupInfo.setGroupname(groupJson.get("groupName").toString());
		groupInfo.setOperator((String)RequestUtils.getSessionKeyValue(request,"userId"));
		ArrayList<String> userId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("userID").size();i++){
			userId.add(groupJson.getJSONArray("userID").get(i).toString());
		}
		userId.add((String)RequestUtils.getSessionKeyValue(request,"userId"));
		
		boolean create = groupManageService.createGroup(groupMemberInfoKey, groupInfo, userId);
		resultMap.put("result", create);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryGroup")
	public Map<String, Object> queryGroup(HttpServletRequest request,
			@RequestParam(value="search",required=false) String searchVal) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", (String)RequestUtils.getSessionKeyValue(request,"userId"));
		paramMap.put("searchVal", RequestUtils.formatBootstrapTableEncoding(searchVal));
		getTableSortInfo(request, paramMap);
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		try {
			list = groupManageService.queryGroupListInfo(paramMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询群组列表时发生异常:" + e.getMessage());
			resultMap.put("result", "数据库查询失败!");
			return resultMap;
		}
		if (list.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", list.size());
			resultMap.put("rows", list);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/kickGroupMember")
	public Map<String, Object> kickGroupMember(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupJson");
        //JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
		ArrayList<String> userId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("userID").size();i++){
			userId.add(groupJson.getJSONArray("userID").get(i).toString());
		}
		
		boolean create = groupManageService.kickingGroupMember(groupJson.get("groupID").toString(), userId);
		resultMap.put("result", create);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/getPresentUserLoginId")
	public Map<String, Object> getPresentUserName(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userLoginID = RequestUtils.getSessionKeyValue(request, "userLoginId");
		if (userLoginID != null && !"".equals(userLoginID)){
			resultMap.put("result", "用户名查询成功!");
			resultMap.put("rows", userLoginID);
		}else{
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/quitGroup")
	public Map<String, Object> quitOrDissolveGroup(HttpServletRequest request,
			@RequestParam(value="groupID",required = false) String groupID){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
		groupMemberInfoKey.setGroupid(groupID);
		groupMemberInfoKey.setUserid(RequestUtils.getSessionKeyValue(request, "userId"));
		
		boolean result = groupManageService.quitGroup(groupMemberInfoKey);
		
		resultMap.put("result", result);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/deleteGroup")
	public Map<String, Object> deleteGroup(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupID");
		//JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
        
        ArrayList<String> groupId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("groupID").size();i++){
			groupId.add(groupJson.getJSONArray("groupID").get(i).toString());
		}
		
		boolean delete = groupManageService.deleteGroup(groupId);
		resultMap.put("result", delete);
		return resultMap;
	}
	
	
	@ResponseBody
	@RequestMapping("/changeGroupName")
	public Map<String, Object> changeGroupName(HttpServletRequest request,
			@RequestParam(value="groupID",required = false) String groupID,
			@RequestParam(value="newName", required=false) String newName){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupInfo groupInfo = new GroupInfo();
		
		groupInfo.setGroupid(groupID);
		groupInfo.setGroupname(newName);
		boolean change = groupManageService.updateGroupName(groupInfo);

		resultMap.put("result", change);
		return resultMap;
	}
	
	/**
	 * 
	 * @param request
	 * @param groupID
	 * @param newName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeGroupMem")
	public Map<String, Object> changeGroupMem(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userObj = RequestUtils.getRequestKeyValue(request, "userJson");
        //JSON字符串序列化成JSON对象
        JSONObject userJson = JSonUtils.objectToJson(userObj);
        
		boolean add = groupManageService.changeGroupMember(userJson,RequestUtils.getSessionKeyValue(request, "userId"));
		resultMap.put("result", add);
		return resultMap;
	}
	
	/**
	 * 组合排序的信息 默认时间降序
	 * @param request
	 * @return
	 */
	private void getTableSortInfo(HttpServletRequest request, Map<String, Object> paramMap){
		//排序列名
		String sortName = RequestUtils.getRequestKeyValue(request, "sort");
		//排序顺序
		String sortOrder = RequestUtils.getRequestKeyValue(request, "order");
		if(sortName != null && !"".equals(sortName)
				&& sortOrder != null && !"".equals(sortOrder)){	
			paramMap.put("sort", sortName);
			paramMap.put("order", sortOrder);
		}
	}
}
