package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.CollectInfoMapper;
import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.CollectFileManageService;

@Service
public class CollectFileManageServiceImpl extends BaseFileService implements
		CollectFileManageService {
	private static Logger logger = LoggerFactory
			.getLogger(MyFileManageServiceImpl.class);
	@Autowired
	private CollectInfoMapper collectInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Override
	public List<FileInfo> queryCollectTableInfo(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return collectInfoMapper.queryCollectTableInfo(map);
	}

	@Transactional
	@Override
	public boolean cancelCollectFile(String loginUserId,
			List<String> collectObjectList) {
		// TODO Auto-generated method stub
		boolean cancleCollectResult = false;
		try {
			if (collectObjectList != null && collectObjectList.size() > 0) {
				for (String fileObjId : collectObjectList) {
					CollectInfo collectInfo = new CollectInfo();
					collectInfo.setUserid(loginUserId);
					collectInfo.setCollectobjectid(fileObjId);
					collectInfoMapper.deleteCollectInfoInfo(collectInfo);
				}
			}
			cancleCollectResult = true;
		} catch (Exception e) {
			logger.error("删除收藏文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return cancleCollectResult;
	}

	@Override
	public String fileDownloader(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileInfo> queryCollectSearchInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<FileInfo> collectFileInfos = new ArrayList<FileInfo>();
		List<FileInfo> queryCollectFileResultInfos = new ArrayList<FileInfo>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			collectFileInfos = collectInfoMapper.queryCollectTableInfo(map);
			// 从我的文件中模糊查询出搜索的关键字文件
			fileInfoList = fileInfoMapper.querySearchInfo(map);
			// 筛选出该文件或者直系父目录是否在我的共享列表中
			for (int i = 0; i < fileInfoList.size(); i++) {
				FileInfo fileInfo = fileInfoList.get(i);
				List<FolderInfo> folderInfos = folderInfoMapper
						.queryAllParentFolderNodeInfo(fileInfo.getFileid());
				for (int j = 0; j < collectFileInfos.size(); j++) {
					for (int k = 0; k < folderInfos.size(); k++) {
						if (collectFileInfos.get(j).getFileid()
								.equals(folderInfos.get(k).getFolderid())) {
							// 该搜索文件在我的共享文件中，生成一个共享对象
							FileInfo queryCollectFileInfo = new FileInfo();
							queryCollectFileInfo = fileInfo;
							queryCollectFileInfo
									.setFileuploader(collectFileInfos.get(j)
											.getFileuploader());
							queryCollectFileInfo
									.setFileuploadertime(collectFileInfos
											.get(j).getFileuploadertime());
							queryCollectFileResultInfos
									.add(queryCollectFileInfo);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("搜索框输入查询功能发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return queryCollectFileResultInfos;
	}

	@Override
	public void updateCollectTime(List<FileInfo> collectInfoList,
			String rootFolderId) {
		// TODO Auto-generated method stub
		CollectInfo collectInfo = new CollectInfo();
		collectInfo.setCollectobjectid(rootFolderId);
		try {
			List<CollectInfo> rootCollectInfo = collectInfoMapper
					.queryCollectInfoInfo(collectInfo);
			for (int i = 0; i < collectInfoList.size(); i++) {
				collectInfoList.get(i).setFileuploadertime(
						rootCollectInfo.get(0).getCreatedate());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修正子目录收藏时间发生异常：" + e.getMessage());
		}

	}

}
