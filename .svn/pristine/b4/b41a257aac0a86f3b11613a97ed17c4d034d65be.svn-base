package cn.springmvc.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.common.CommonConstant;
import cn.springmvc.dao.RecycleInfoMapper;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.service.RecycleFileManageService;
import cn.springmvc.service.RecycleSchedulerService;
import cn.utils.DateUtils;

/**
 * 每天定时清理回收站中的文件
 * @author hzq
 *
 */
@Service
public class RecycleSchedulerServiceImpl implements RecycleSchedulerService {
	private static Logger logger = LoggerFactory.getLogger(RecycleSchedulerServiceImpl.class);
	private static boolean isRunning = false;
	@Autowired
	private RecycleInfoMapper recycleInfoMapper;
	@Autowired
	private RecycleFileManageService recycleFileManageService;

	/*
	 * (实现接口方法)
	 * @see cn.springmvc.service.RecycleSchedulerService#scheduleTask()
	 */
	@Scheduled(cron = "0 0/5 23 * * ?")// 每天23点1-59分钟中每隔5分钟执行一次
	@Override
	public void scheduleTask() {
		Calendar c = Calendar.getInstance();
		if (!isRunning) {
			if (c.get(Calendar.HOUR_OF_DAY) >= CommonConstant.C_SCHEDULE_HOUR
					&& c.get(Calendar.HOUR_OF_DAY) <= CommonConstant.C_SCHEDULE_HOUR2) {
				isRunning = true;
				logger.info("开始执行指定任务");
				task();
				isRunning = false;
				logger.info("指定任务执行结束");
			} else {
				logger.info("还未到执行任务时间，每天23点1-59分钟中每隔5分钟执行一次！");
			}
		}

	}

	/**
	 * 遍历出所有的回收信息
	 * 回收超过7天的文件，执行彻底删除业务
	 */
	public void task() {
		List<RecycleInfo> recycleInfos = recycleInfoMapper.queryRecycleInfoInfo(new RecycleInfo());
		DateFormat df = new SimpleDateFormat(CommonConstant.DF_YYYY_MM_DD_HH_MM_SS);
		for (RecycleInfo recycleInfo : recycleInfos) {
			try {
				Date d1 = df.parse(DateUtils.getSystemTime());
				Date d2 = df.parse(recycleInfo.getCreatedate());
				long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
				long days = diff / (1000 * 60 * 60 * 24);
				if (days >= CommonConstant.TIME) {
					boolean result = recycleFileManageService
							.absolutelyDeleteFile(
									recycleInfo.getRecycleobjectid(),
									recycleInfo.getUserid());
					if (result == true) {
						logger.info("定时删除文件成功！");
					}
				}
			} catch (Exception e) {
				logger.error("定时删除文件发生异常：" + e.getMessage());
			}
		}
	}
}
