package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.MessageInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface MessageManageService {
    /**
     * 查看系统消息列表信息 
     * @param 
     * @return
     */
	 List<MessageInfo> queryMessageListInfo(cn.springmvc.model.MessageInfo messageInfo);
	 
}
