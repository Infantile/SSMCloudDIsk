package cn.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.service.CloudDiskCapacityManageService;
import cn.utils.RequestUtils;

/**
 * 初始化处理
 * @author hzq
 * 
 */
@Controller
@RequestMapping("/init")
public class MainPageInitController {
	
	private static Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private CloudDiskCapacityManageService cloudDiskCapacityManageService;
	/**
	 * 跳转主界面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/main")
	public ModelAndView login(HttpServletRequest request, ModelAndView model) {
		model.setViewName("main");
		return model;

	}
	
	/**
	 * 获取云盘使用情况 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCloudDiskInfo")
	public CloudDiskInfo queryCloudDiskInfo(HttpServletRequest request) {
		CloudDiskInfo result = new CloudDiskInfo();		
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");//获取userId给loginUserId
			CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();//新建构造函数
			cloudDiskInfo.setUserid(loginUserId);//获取ID
			List<CloudDiskInfo> cloudDiskInfoList = cloudDiskCapacityManageService.queryClouddiskCapacityInfo(cloudDiskInfo);
			if(cloudDiskInfoList != null && cloudDiskInfoList.size() > 0){
				result = cloudDiskInfoList.get(0);//获取第一个元素
			}
		} catch (Exception e) {
			logger.error("获取云盘使用情况 异常：" + e.getMessage());
		}
		
		return result;

	}
}
