package cn.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.service.RecycleSchedulerService;
import cn.springmvc.service.UserManageService;
import cn.utils.DateUtils;
import cn.utils.EncryptUtil;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/userManage")
public class UserManageController {
	private static Logger logger = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private UserManageService userManageService;
	@Autowired
	private RecycleSchedulerService recycleSchedulerService;
	/**
	 * 登陆表单校验
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/check")
	public Map<String, Object> loginCheck(HttpServletRequest request, 
			@RequestParam(value="userLoginID", required=false) String userLoginID,
			@RequestParam(value="password", required=false) String password) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//获取前台页面相关参数（方法一）
		CloudDiskUser cloudDiskUser = new CloudDiskUser();
		//cloudDiskUser.setUsername(request.getParameter("userName"));
		//cloudDiskUser.setPassword(request.getParameter("password"));
		// 根据账号查询用户名是否存在		
		cloudDiskUser.setUserLoginID(userLoginID);
		List<CloudDiskUser> userNameCheck = userManageService.loginCheck(cloudDiskUser);
		if(userNameCheck == null || userNameCheck.size() == 0){
			resultMap.put("result", "用户名不存在, 请重新输入!");
		}else{
			// 根据账号查询用户密码是否正确
			cloudDiskUser.setPassword(EncryptUtil.SHA256(password));
			List<CloudDiskUser> passwordCheck = userManageService.loginCheck(cloudDiskUser);
			if(passwordCheck != null && passwordCheck.size() > 0){
				HttpSession session = request.getSession();
				session.setAttribute("userName", passwordCheck.get(0).getUsername());
				session.setAttribute("userId", passwordCheck.get(0).getUserid());
				session.setAttribute("userLoginId", passwordCheck.get(0).getUserLoginID());
				resultMap.put("result", "");
			}else{
				resultMap.put("result", "用户密码错误, 请重新输入!");
			}
		}

		return resultMap;
	}
	/**
	 * @param request
	 * @return  修改密码成功与否
	 */
	@ResponseBody
	@RequestMapping("/changePassword")
	public Map<String, Object> updatePassword(HttpServletRequest request,
			@RequestParam(value = "password", required = false) String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		CloudDiskUser cloudDiskUser = new CloudDiskUser();
		cloudDiskUser.setUserid(loginUserId);
		cloudDiskUser.setPassword(EncryptUtil.SHA256(password));
		cloudDiskUser.setCreatedate(DateUtils.getSystemTime());
		int updateResult = userManageService.updatePassword(cloudDiskUser);
		if (updateResult == 1) {
			result.put("result", true);
		} else {
			result.put("result", false);
		}
		
		return result;
	}
	
	/**
	 * @param request
	 * @return 退出登录
	 */
	@ResponseBody
	@RequestMapping("/logOut")
	public String quitSystem(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			request.getSession().invalidate();
			result = "login.jsp";
		} catch (Exception e) {
			logger.error("退出登录异常：" + e.getMessage());
		}

		return result;
	}
}

